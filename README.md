# midterm_26855_groupE

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


## Screenshots

### Province Operations

#### Create Province 2
![Create Province 2](screenshot/create%20province2.JPG)
Demonstrates creating a province with POST request to `/api/provinces`. Shows the request body with provinceCode and provinceName fields, and the successful response with status 201 Created.

#### Create Province 3
![Create Province 3](screenshot/create%20province%203.JPG)
Another example of province creation, showing how multiple provinces can be added to the system. Each province has a unique code and name.

#### Get All Provinces
![Get All Provinces](screenshot/get%20all%20province.JPG)
Shows GET request to `/api/provinces` retrieving all provinces in the system. Returns a list of all created provinces with their IDs, codes, and names.

### Location Operations

#### Create Location
![Create Location](screenshot/create%20location.JPG)
Demonstrates POST request to `/api/locations` creating a location linked to a province. Shows the hierarchical structure with district, sector, cell, and village fields.

#### Create Location 2
![Create Location 2](screenshot/create%20location%202.JPG)
Second location creation example showing different location details. Demonstrates the one-to-many relationship between Province and Location.

#### Create Location 3
![Create Location 3](screenshot/create%20location%203.JPG)
Third location creation example, further demonstrating how locations are associated with provinces using provinceCode.

#### Get All Locations
![Get All Locations](screenshot/get%20all%20locations.JPG)
GET request to `/api/locations` showing all locations with their complete details including province information, demonstrating the joined data from the province relationship.

### User Operations

#### Creating User
![Creating User](screenshot/creating%20user.JPG)
Shows POST request to `/api/users` with complete user data including profile information (phone, gender, dateOfBirth). Demonstrates the one-to-one relationship between User and UserProfile being created in a single request.

#### Create User 2
![Create User 2](screenshot/create%20user2.JPG)
Another user creation example showing different user details. The response includes locationId and province information, demonstrating the relationship between User and Location.

#### Get User by ID
![Get User by ID](screenshot/get%20user%20by%20id.JPG)
GET request to `/api/users/{id}` retrieving a specific user with all related data including profile information, location details, and province information in a single response.

#### Update User
![Update User](screenshot/update%20user.JPG)
PUT request to `/api/users/{id}` demonstrating user update functionality. Shows how user information and profile data can be modified while maintaining relationships.

#### Get Users by Province Code
![Get Users by Province Code](screenshot/get%20users%20by%20province%20code.JPG)
GET request to `/api/users/by-province-code/{provinceCode}` demonstrating the custom query method that filters users by province. Shows the property path navigation through User → Location → Province.

#### Get User by Province Name
![Get User by Province Name](screenshot/get%20user%20by%20province%20name.JPG)
GET request to `/api/users/by-province-name/{provinceName}` showing another filtering option. Demonstrates Spring Data JPA's ability to generate queries from method names with nested property paths.

### Room Operations

#### Create Room
![Create Room](screenshot/create%20room.JPG)
POST request to `/api/rooms` creating a room with roomNumber, roomType, price, status, and locationId. Shows the one-to-many relationship between Location and Room.

#### Get All Rooms
![Get All Rooms](screenshot/get%20all%20rooms.JPG)
GET request to `/api/rooms` retrieving all rooms in the system with their complete details including location information.

#### Get Room by ID
![Get Room by ID](screenshot/get%20room%20by%20id.JPG)
GET request to `/api/rooms/{id}` showing detailed information for a specific room including its location and availability status.

### Booking Operations

#### Create Booking
![Create Booking](screenshot/create%20booking.JPG)
POST request to `/api/bookings` demonstrating the many-to-many relationship between User and Room through the Booking entity. Shows userId, roomId, checkInDate, and checkOutDate fields.

#### Get All Bookings
![Get All Bookings](screenshot/get%20all%20booking.JPG)
GET request to `/api/bookings` retrieving all bookings with user and room information. Demonstrates how the Booking entity acts as an association table with additional attributes (dates, status).

#### Get Booking by ID
![Get Booking by ID](screenshot/get%20booking%20by%20id.JPG)
GET request to `/api/bookings/{id}` showing detailed booking information including userFullName and roomNumber, demonstrating the relationships being resolved.

#### Cancel Booking
![Cancel Booking](screenshot/cancel%20booking.JPG)
PUT/PATCH request to cancel a booking, showing how the bookingStatus is updated from PENDING to CANCELLED. Demonstrates state management in the booking lifecycle.
