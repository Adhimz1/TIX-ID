# 🎬 TIX ID - Backend API

Backend API untuk aplikasi TIX ID (Cinema Ticket Booking App)

---

## 📁 Struktur File

```
backend_api/
├── config.php              # Database configuration (JANGAN COMMIT!)
├── config.example.php      # Template konfigurasi
├── database.sql            # Database schema & data
├── index.html              # API testing page
├── test_api.php            # API endpoint tester
├── test_api_lengkap.html   # Full API test interface
│
├── register.php            # User registration endpoint
├── login.php               # User login endpoint
├── movies.php              # Get movies list
├── admin_movies.php        # Admin movie management
├── upload_image.php        # Upload poster images
├── fix_users.php           # Database utility
│
└── uploads/                # Uploaded images folder (JANGAN COMMIT!)
    └── [poster images]
```

---

## 🚀 Quick Setup

### 1. Install Dependencies

Pastikan sudah terinstall:
- PHP 7.4+
- MySQL 5.7+
- Apache/Nginx atau Laragon

### 2. Setup Database

```sql
CREATE DATABASE tix_id;
USE tix_id;
SOURCE database.sql;
```

### 3. Konfigurasi

```bash
cp config.example.php config.php
```

Edit `config.php`:
```php
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'tix_id');
```

### 4. Permissions

```bash
mkdir uploads
chmod 777 uploads
```

---

## 📡 API Endpoints

### Authentication

#### Register User
```http
POST /register.php
Content-Type: application/json

{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "123456"
}

Response:
{
    "success": true,
    "message": "Registration successful",
    "data": {
        "user_id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    }
}
```

#### Login User
```http
POST /login.php
Content-Type: application/json

{
    "email": "john@example.com",
    "password": "123456"
}

Response:
{
    "success": true,
    "message": "Login successful",
    "data": {
        "user_id": 1,
        "name": "John Doe",
        "email": "john@example.com"
    }
}
```

---

### Movies

#### Get All Movies
```http
GET /movies.php

Response:
{
    "success": true,
    "message": "Movies retrieved successfully",
    "data": [
        {
            "id": 1,
            "title": "Spider-Man",
            "genre": "Action",
            "rating": "8.5",
            "poster": "spiderman.jpg",
            "status": "now_playing"
        }
    ]
}
```

#### Get Movies by Status
```http
GET /movies.php?status=now_playing
GET /movies.php?status=coming_soon

Response: Same as above, filtered
```

---

### Admin

#### Upload Movie Poster
```http
POST /upload_image.php
Content-Type: multipart/form-data

Fields:
- image: [File] Image file (JPG/PNG)
- movie_id: [Integer] Movie ID

Response:
{
    "success": true,
    "message": "Image uploaded successfully",
    "data": {
        "url": "http://localhost/tix_id_api/uploads/movie_123.jpg"
    }
}
```

#### Manage Movies (Admin)
```http
POST /admin_movies.php
Content-Type: application/json

Actions:
- add: Add new movie
- update: Update movie
- delete: Delete movie
```

---

## 🗄️ Database Schema

### Table: users
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Table: movies
```sql
CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    genre VARCHAR(100),
    rating VARCHAR(10),
    duration VARCHAR(50),
    release_date DATE,
    description TEXT,
    poster VARCHAR(255),
    status ENUM('now_playing', 'coming_soon') DEFAULT 'now_playing',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Table: bookings
```sql
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    movie_id INT,
    cinema VARCHAR(100),
    date DATE,
    time TIME,
    seats TEXT,
    total_price DECIMAL(10,2),
    payment_method VARCHAR(50),
    status ENUM('pending', 'confirmed', 'cancelled') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id)
);
```

---

## 🔧 Maintenance

### Update Database
```bash
# Run update scripts
mysql -u root -p tix_id < update_database.sql
```

### Backup Database
```bash
mysqldump -u root -p tix_id > backup_$(date +%Y%m%d).sql
```

### Clear Upload Cache
```bash
cd uploads
rm -rf cache/*  # If caching implemented
```

---

## 🔐 Security Notes

1. **JANGAN commit `config.php`** ke Git
2. **Hash password** dengan `password_hash()`
3. **Validasi input** di semua endpoint
4. **Sanitize SQL** queries dengan prepared statements
5. **Limit upload size** untuk images
6. **Set proper folder permissions** untuk uploads/

---

## 🐛 Common Issues

### CORS Error
Sudah dihandle di config.php:
```php
header("Access-Control-Allow-Origin: *");
```

### Upload Failed
- Cek folder `uploads/` exists
- Cek permission 777 (Linux/Mac)
- Cek `upload_max_filesize` di php.ini

### Connection Failed
- Cek MySQL running
- Cek kredensial di config.php
- Cek database sudah dibuat

---

## 📞 Testing

### Manual Test
1. Buka `http://localhost/tix_id_api/`
2. Gunakan form test yang ada
3. Cek response di browser console

### API Test Tool
1. Buka `test_api_lengkap.html`
2. Test semua endpoint
3. Lihat hasil response

### Via Postman
Import collection dan test semua endpoint

---

## 📝 Changelog

### Version 1.2
- Fix upload image handling
- Add missing movies to database
- Fix force close issues

### Version 1.1
- Add admin movie management
- Add upload poster feature
- Improve error handling

### Version 1.0
- Initial release
- User registration & login
- Movie listing
- Basic API structure

---

**Developer:** TIX ID Team  
**Last Updated:** December 2025

