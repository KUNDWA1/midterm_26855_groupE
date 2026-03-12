# Hotel Management System

A Spring Boot REST API for managing hotel operations including rooms, bookings, guests, and locations.

## Features

- Manage provinces and locations
- User registration with profiles
- Room management
- Booking system
- Filter users by province
- Pagination and sorting

## Database Structure

The system uses 6 tables:
- **provinces** - Province information
- **locations** - Location details (district, sector, cell, village)
- **users** - User accounts
- **user_profiles** - Additional user information
- **rooms** - Hotel rooms
- **bookings** - Room reservations

## Relationships

- Province → Location (one-to-many)
- Location → User (one-to-many)
- Location → Room (one-to-many)
- User → UserProfile (one-to-one)
- User → Booking (one-to-many)
- Room → Booking (one-to-many)

## API Endpoints

### Provinces
- `POST /api/provinces` - Create province
- `GET /api/provinces` - Get all provinces

### Locations
- `POST /api/locations` - Create location
- `GET /api/locations` - Get all locations

### Users
- `POST /api/users` - Create user
- `GET /api/users` - Get all users (with pagination)
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user
- `GET /api/users/by-province-code/{code}` - Get users by province code
- `GET /api/users/by-province-name/{name}` - Get users by province name

### Rooms
- `POST /api/rooms` - Create room
- `GET /api/rooms` - Get all rooms
- `GET /api/rooms/{id}` - Get room by ID

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings` - Get all bookings
- `GET /api/bookings/{id}` - Get booking by ID
- `PUT /api/bookings/{id}/cancel` - Cancel booking

## Example Requests

### Create Province
```json
POST /api/provinces
{
  "provinceCode": "KIG",
  "provinceName": "Kigali"
}
```

### Create Location
```json
POST /api/locations
{
  "provinceCode": "KIG",
  "district": "Gasabo",
  "sector": "Kimironko",
  "cell": "Bibare",
  "village": "Umucyo"
}
```

### Create User
```json
POST /api/users
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

### Create Room
```json
POST /api/rooms
{
  "roomNumber": "101",
  "roomType": "SINGLE",
  "price": 50000,
  "status": "AVAILABLE",
  "locationId": 1
}
```

### Create Booking
```json
POST /api/bookings
{
  "userId": 1,
  "roomId": 1,
  "checkInDate": "2026-03-20",
  "checkOutDate": "2026-03-25"
}
```

## Pagination Example

Get users with pagination and sorting:
```
GET /api/users?page=0&size=10&sort=firstName&direction=asc
```

## Screenshots

### Province Operations
![Create Province](screenshot/create%20province2.JPG)
![Get All Provinces](screenshot/get%20all%20province.JPG)

### Location Operations
![Create Location](screenshot/create%20location.JPG)
![Get All Locations](screenshot/get%20all%20locations.JPG)

### User Operations
![Create User](screenshot/creating%20user.JPG)
![Get User by ID](screenshot/get%20user%20by%20id.JPG)
![Update User](screenshot/update%20user.JPG)
![Get Users by Province](screenshot/get%20users%20by%20province%20code.JPG)

### Room Operations
![Create Room](screenshot/create%20room.JPG)
![Get All Rooms](screenshot/get%20all%20rooms.JPG)

### Booking Operations
![Create Booking](screenshot/create%20booking.JPG)
![Get All Bookings](screenshot/get%20all%20booking.JPG)
![Cancel Booking](screenshot/cancel%20booking.JPG)

## Technologies Used

- Spring Boot 3.5.11
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## Setup

1. Clone the repository
2. Configure database in `application.properties`
3. Run `mvn spring-boot:run`
4. API will be available at `http://localhost:8585`
