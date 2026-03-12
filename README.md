## Hotel Management System – Spring Boot Backend

This project is a Spring Boot REST API for a simple hotel management system.  
It demonstrates different database relationships including **one-to-many**, **one-to-one**, and **many-to-many (via association entity)**.

- **Province ↔ Location**: one-to-many  
- **Location ↔ User**: one-to-many  
- **Location ↔ Room**: one-to-many  
- **User ↔ UserProfile**: one-to-one  
- **User ↔ Booking**: one-to-many  
- **Room ↔ Booking**: one-to-many  
- Taken together, **Booking connects User and Room as a many-to-many relationship** (a user can book many rooms and a room can be booked by many users over time).

### ERD (textual description)

- `provinces` (id, province_code, province_name)  
  - 1 ────< `locations` (id, district, sector, cell, village, province_id)
- `locations`  
  - 1 ────< `users` (id, ..., location_id)  
  - 1 ────< `rooms` (id, ..., location_id)
- `users`  
  - 1 ────1 `user_profiles` (id, phone_number, gender, date_of_birth, user_id)  
  - 1 ────< `bookings` (id, ..., user_id, room_id)
- `rooms`  
  - 1 ────< `bookings`

So `bookings` plays the role of a **join / association table** between `users` and `rooms` and also carries extra attributes (check-in/check-out dates, booking status).

### Pagination & Sorting explanation

- Endpoint: `GET /api/users?page=0&size=5&sort=firstName,asc`  
- In `UserController` we build a `Pageable`:
  - `PageRequest.of(page, size, Sort.by(sortBy).ascending()/descending())`  
- Spring Data JPA translates this into SQL with:
  - `LIMIT` + `OFFSET` for **pagination** (only a page of results is loaded)  
  - `ORDER BY first_name ASC/DESC` for **sorting**.  
- This improves performance because we never load the entire `users` table in memory; we only fetch the specific slice the client asked for.

### existBy() explanation

- In `UserRepository`:
  - `boolean existsByEmail(String email);`  
- Spring Data parses the method name and generates a query like:
  - `SELECT EXISTS(SELECT 1 FROM users WHERE email = ?)`  
- In `UserServiceImpl#createUser` we call this **before** saving to prevent duplicate emails.  
  If it returns `true` we throw `BadRequestException`.

### Province filtering explanation

- Repository methods:
  - `List<User> findByLocationProvinceProvinceCode(String provinceCode);`  
  - `List<User> findByLocationProvinceProvinceName(String provinceName);`
- The method name is a **property path**:
  - `user.location.province.provinceCode` / `provinceName`  
- Spring Data automatically generates a query that joins `users` → `locations` → `provinces` and applies a `WHERE` on `province_code`/`province_name`.
- `UserController` exposes:
  - `GET /api/users/by-province-code/{provinceCode}`  
  - `GET /api/users/by-province-name/{provinceName}`

### Example Postman requests

#### 1. Create a Province

POST `http://localhost:8585/api/provinces`

Request body:
```json
{
  "provinceCode": "KIG",
  "provinceName": "Kigali"
}
```

#### 2. Create a Location

POST `http://localhost:8585/api/locations`

```json
{
  "provinceCode": "KIG",
  "district": "Gasabo",
  "sector": "Kimironko",
  "cell": "Bibare",
  "village": "Umucyo"
}
```

Example response:
```json
{
  "id": 1,
  "provinceCode": "KIG",
  "provinceName": "Kigali",
  "district": "Gasabo",
  "sector": "Kimironko",
  "cell": "Bibare",
  "village": "Umucyo"
}
```

#### 3. Create a User (with profile)

POST `http://localhost:8585/api/users`

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "secret123",
  "role": "GUEST",
  "locationId": 1,
  "phoneNumber": "0780000000",
  "gender": "MALE",
  "dateOfBirth": "1995-05-20"
}
```

Example response (simplified):
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "role": "GUEST",
  "locationId": 1,
  "provinceCode": "KIG",
  "provinceName": "Kigali",
  "district": "Gasabo",
  "profileId": 1,
  "phoneNumber": "0780000000",
  "gender": "MALE",
  "dateOfBirth": "1995-05-20"
}
```

#### 4. Create a Room

POST `http://localhost:8585/api/rooms`

```json
{
  "roomNumber": "101",
  "roomType": "SINGLE",
  "price": 50000,
  "status": "AVAILABLE",
  "locationId": 1
}
```

#### 5. Book a Room

POST `http://localhost:8585/api/bookings`

```json
{
  "userId": 1,
  "roomId": 1,
  "checkInDate": "2026-03-20",
  "checkOutDate": "2026-03-25"
}
```

Example response:
```json
{
  "id": 1,
  "checkInDate": "2026-03-20",
  "checkOutDate": "2026-03-25",
  "bookingStatus": "PENDING",
  "userId": 1,
  "userFullName": "John Doe",
  "roomId": 1,
  "roomNumber": "101"
}
```

#### 6. Retrieve users by province code

GET `http://localhost:8585/api/users/by-province-code/KIG`

Example response:
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "role": "GUEST",
    "locationId": 1,
    "provinceCode": "KIG",
    "provinceName": "Kigali",
    "district": "Gasabo",
    "profileId": 1,
    "phoneNumber": "0780000000",
    "gender": "MALE",
    "dateOfBirth": "1995-05-20"
  }
]
```

#### 7. Pagination + Sorting example

GET `http://localhost:8585/api/users?page=0&size=5&sort=firstName,asc`

Example response structure:
```json
{
  "content": [
    { "id": 1, "firstName": "Alice", "...": "..." },
    { "id": 2, "firstName": "Bob", "...": "..." }
  ],
  "pageable": { "...": "..." },
  "totalElements": 10,
  "totalPages": 2,
  "size": 5,
  "number": 0
}
```

