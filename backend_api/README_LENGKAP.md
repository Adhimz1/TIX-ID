# 🎬 TIX-ID Backend API - Complete Guide

API Backend untuk aplikasi TIX-ID Cinema dengan Admin Panel

---

## ⚡ Quick Start (3 Langkah)

### 1. Copy File API
```bash
Double-click: copy_to_laragon.bat
```

### 2. Fix User Data (PENTING!)
```
http://localhost/tix_id_api/fix_users.php
```

### 3. Test API
```
http://localhost/tix_id_api/test_api_lengkap.html
```

**Done!** API siap digunakan! ✅

---

## 📂 Struktur File

### Core API:
- **config.php** - Database config & helper functions
- **login.php** - Login API (with role: user/admin)
- **register.php** - Register new user
- **movies.php** - Get movies list (public)
- **admin_movies.php** - Manage movies (admin only)

### Database:
- **database.sql** - Complete DB (users & movies)
- **fix_user_data.sql** - SQL to fix users
- **fix_users.php** - PHP script to fix users ⭐

### Testing:
- **test_api_lengkap.html** - Complete API tester ⭐
- **test_api.php** - Simple tester
- **index.html** - Landing page

### Scripts:
- **copy_to_laragon.bat** - Auto copy files ⭐
- **update_database.bat** - Auto import DB
- **cek_database.bat** - Check DB status

---

## 🔐 Login Credentials

**Admin** (Kelola Film):
```
Email: admin@tixid.com
Password: password123
Role: admin
```

**User** (Lihat Film):
```
Email: test@example.com
Password: password123
Role: user
```

---

## 🧪 API Endpoints

### Public API:

#### Get All Movies
```http
GET /movies.php
Response: { "success": true, "data": { "movies": [...] } }
```

#### Get Movies by Status
```http
GET /movies.php?status=now_showing
GET /movies.php?status=coming_soon
```

#### Login
```http
POST /login.php
Content-Type: application/json

{
  "email": "admin@tixid.com",
  "password": "password123"
}

Response: {
  "success": true,
  "message": "Login berhasil",
  "data": {
    "id": 2,
    "name": "Admin TIX ID",
    "email": "admin@tixid.com",
    "role": "admin"
  }
}
```

#### Register
```http
POST /register.php
Content-Type: application/json

{
  "name": "User Baru",
  "email": "user@example.com",
  "password": "password123",
  "phone": "081234567890"
}
```

---

### Admin API (Requires admin role):

#### Get Movies (Admin)
```http
POST /admin_movies.php
Content-Type: application/json

{
  "admin_email": "admin@tixid.com",
  "status": "now_showing"
}
```

#### Add Movie
```http
POST /admin_movies.php
Content-Type: application/json

{
  "admin_email": "admin@tixid.com",
  "title": "Spider-Man: No Way Home",
  "poster_url": "https://image.tmdb.org/t/p/w500/poster.jpg",
  "synopsis": "Film description...",
  "youtube_url": "https://www.youtube.com/watch?v=xxx",
  "rating": 8.5,
  "duration": "148 Menit",
  "genre": "Action, Adventure",
  "status": "now_showing",
  "release_date": "2021-12-15"
}
```

#### Update Movie
```http
PUT /admin_movies.php
Content-Type: application/json

{
  "admin_email": "admin@tixid.com",
  "id": 1,
  "title": "New Title",
  "rating": 9.0
}
```

#### Delete Movie
```http
DELETE /admin_movies.php
Content-Type: application/json

{
  "admin_email": "admin@tixid.com",
  "id": 1
}
```

---

## 🔧 Setup Detail

### Prerequisites:
- ✅ Laragon installed & running
- ✅ MySQL active
- ✅ PHP 7.4+ or 8.x

### Manual Setup:

#### 1. Copy Files
```bash
# From:
C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\

# To:
C:\laragon\www\tix_id_api\
```

#### 2. Import Database
- Open HeidiSQL
- Create database: `tix_id`
- Import file: `database.sql`

#### 3. Fix Users
- Open browser
- Go to: `http://localhost/tix_id_api/fix_users.php`
- Click execute

#### 4. Test
- Open: `http://localhost/tix_id_api/test_api_lengkap.html`
- Click all test buttons
- All should be ✓ OK

---

## 🐛 Troubleshooting

### ❌ Login Error "Email atau password salah"

**Problem:** User data not synced or wrong password hash

**Solution:**
```
http://localhost/tix_id_api/fix_users.php
```

This will:
- Delete old users
- Create new users with correct password hash
- Password: `password123` guaranteed to work!

---

### ❌ movies.php "Not Found" (404)

**Problem:** Files not copied to Laragon

**Solution:**
```bash
# Run:
copy_to_laragon.bat

# Or manual copy all files to:
C:\laragon\www\tix_id_api\
```

---

### ❌ Database Connection Error

**Problem:** Database not imported or wrong config

**Solution:**
1. Check Laragon is running (green light)
2. Check MySQL is running
3. Import `database.sql` to HeidiSQL
4. Check `config.php` settings

---

### ❌ "Call to undefined function sendResponse()"

**Problem:** config.php not found or not included

**Solution:**
- Make sure `config.php` exists in same folder
- Copy all files again with `copy_to_laragon.bat`

---

## 📖 Documentation Files

- **FIX_LOGIN_ERROR.md** - Fix login issues
- **CARA_UPDATE_DATABASE.md** - Database setup guide
- **ADMIN_SETUP_GUIDE.md** - Admin features guide
- **MULAI_SINI_DATABASE.md** - Quick database start
- **FIX_LOGIN_SUPER_CEPAT.md** - Super fast login fix

---

## ✅ Setup Checklist

Complete these in order:

- [ ] ✅ Laragon running
- [ ] ✅ Copy files → `copy_to_laragon.bat`
- [ ] ✅ Import database → HeidiSQL
- [ ] ✅ Fix users → `fix_users.php`
- [ ] ✅ Test API → `test_api_lengkap.html`
- [ ] ✅ All tests pass (✓ OK)
- [ ] ✅ Build Android app
- [ ] ✅ Login as admin
- [ ] ✅ Test add/edit/delete movie
- [ ] ✅ **DONE!** 🎉

---

## 🎯 Common Use Cases

### Create Admin User
```sql
UPDATE users SET role = 'admin' WHERE email = 'your@email.com';
```

### Reset Password
```sql
-- Password: password123
UPDATE users 
SET password = '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi' 
WHERE email = 'user@example.com';
```

### Check All Movies
```sql
SELECT id, title, status, rating FROM movies ORDER BY created_at DESC;
```

### Count Movies by Status
```sql
SELECT status, COUNT(*) as total FROM movies GROUP BY status;
```

---

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role ENUM('user', 'admin') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Movies Table
```sql
CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    poster_url VARCHAR(500) NOT NULL,
    synopsis TEXT NOT NULL,
    youtube_url VARCHAR(500),
    rating DECIMAL(2,1) DEFAULT 0.0,
    duration VARCHAR(20),
    genre VARCHAR(100),
    status ENUM('now_showing', 'coming_soon') DEFAULT 'now_showing',
    release_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## 🚀 Performance Tips

1. **Use Indexes:** Already added for email, status, title
2. **Cache Results:** Implement Redis for movie lists
3. **Optimize Queries:** Use LIMIT for pagination
4. **CDN for Images:** Store posters on CDN
5. **API Rate Limiting:** Implement throttling

---

## 🔒 Security Notes

- ✅ Passwords hashed with BCrypt
- ✅ SQL Injection protected (prepared statements)
- ✅ Admin verification for sensitive operations
- ✅ Input validation on all endpoints
- ⚠️ Use HTTPS in production!
- ⚠️ Add API rate limiting
- ⚠️ Implement JWT tokens for authentication

---

## 📞 Support

**Problems?** Check these files:
1. `FIX_LOGIN_ERROR.md` - Login issues
2. `CARA_UPDATE_DATABASE.md` - Database problems
3. `ADMIN_SETUP_GUIDE.md` - Admin features

**Still stuck?** Check the error logs:
```
C:\laragon\www\tix_id_api\error.log
```

---

## 📦 Version Info

- **Version:** 1.0.0
- **Last Update:** December 23, 2025
- **PHP:** 7.4+
- **MySQL:** 5.7+
- **Features:** User Auth, Movie CRUD, Admin Panel

---

## 🎉 Summary

**3 Steps to Success:**
1. **Copy** → `copy_to_laragon.bat`
2. **Fix** → `fix_users.php`
3. **Test** → `test_api_lengkap.html`

**Login:**
- Admin: `admin@tixid.com` / `password123`
- User: `test@example.com` / `password123`

**That's it!** You're ready to go! 🚀

