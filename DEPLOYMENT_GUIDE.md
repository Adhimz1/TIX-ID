# 🚀 Deployment Guide - TIX ID

Panduan lengkap untuk setup backend dan database di server baru.

---

## 📋 Prerequisites

- PHP 7.4 atau lebih baru
- MySQL 5.7 atau lebih baru
- Apache/Nginx Web Server (atau Laragon untuk development)
- Android Studio (untuk build aplikasi)

---

## 🔧 Setup Backend API

### 1. Copy File Backend

Copy semua file dari folder `backend_api/` ke web server Anda:
- Untuk Laragon: `C:\laragon\www\tix_id_api\`
- Untuk hosting: `/public_html/tix_id_api/` atau `/var/www/html/tix_id_api/`

### 2. Konfigurasi Database

```bash
# Copy template config
cd backend_api
cp config.example.php config.php
```

Edit file `config.php`:
```php
define('DB_HOST', 'localhost');
define('DB_USER', 'root');           // Ganti dengan username Anda
define('DB_PASS', '');               // Ganti dengan password Anda
define('DB_NAME', 'tix_id');         // Nama database
```

### 3. Import Database

```bash
# Buka MySQL
mysql -u root -p

# Create database
CREATE DATABASE tix_id;
USE tix_id;

# Import struktur dan data
SOURCE path/to/database.sql;
```

Atau via phpMyAdmin:
1. Buka `http://localhost/phpmyadmin`
2. Create database: `tix_id`
3. Import file `database.sql`

### 4. Setup Upload Folder

```bash
cd backend_api
mkdir uploads
chmod 777 uploads  # Linux/Mac
# Windows: Klik kanan > Properties > Security > Full Control
```

### 5. Test API

Buka browser dan test endpoint:
- `http://localhost/tix_id_api/` - Halaman test
- `http://localhost/tix_id_api/movies.php` - List movies
- `http://localhost/tix_id_api/test_api.php` - Test lengkap

---

## 📱 Setup Android App

### 1. Update Base URL

Edit file `app/src/main/java/com/pab/tixid/network/ApiConfig.kt`:

```kotlin
object ApiConfig {
    // Untuk testing lokal
    private const val BASE_URL = "http://192.168.1.2/tix_id_api/"
    
    // Untuk production (ganti dengan domain Anda)
    // private const val BASE_URL = "https://yourdomain.com/api/"
    
    const val REGISTER_URL = "${BASE_URL}register.php"
    const val LOGIN_URL = "${BASE_URL}login.php"
    const val MOVIES_URL = "${BASE_URL}movies.php"
}
```

**Cara mendapatkan IP Lokal:**

Windows:
```cmd
ipconfig
# Lihat IPv4 Address
```

Mac/Linux:
```bash
ifconfig
# Atau
ip addr show
```

### 2. Build & Run

```bash
# Clean & rebuild
./gradlew clean
./gradlew assembleDebug

# Atau via Android Studio:
# Build > Clean Project
# Build > Rebuild Project
```

---

## 🔐 Keamanan

### File yang TIDAK boleh dicommit ke Git:

- ❌ `backend_api/config.php` - Kredensial database
- ❌ `backend_api/uploads/*` - File upload user
- ❌ `*.sql` - Database backup
- ❌ `*.keystore` - Android signing key
- ❌ `google-services.json` - Firebase config

### File yang HARUS dicommit:

- ✅ `backend_api/config.example.php` - Template config
- ✅ `backend_api/*.php` - Semua file PHP API
- ✅ `app/src/` - Source code Android
- ✅ `.gitignore` - Daftar file yang diabaikan

---

## 🐛 Troubleshooting

### Error: "Connection failed"
- Cek kredensial database di `config.php`
- Pastikan MySQL sudah running
- Cek nama database sudah dibuat

### Error: "Failed to upload image"
- Cek folder `uploads/` ada dan punya permission write
- Cek ukuran file tidak melebihi `upload_max_filesize` di `php.ini`

### Error: "Failed to connect" di Android
- Pastikan HP dan PC di jaringan yang sama
- Cek IP address sudah benar di `ApiConfig.kt`
- Matikan firewall atau allow port 80
- Pastikan Laragon sudah running

### Database tidak update
- Jalankan file SQL update yang ada di `backend_api/`
- Cek versi database cocok dengan kode

---

## 📞 Support

Jika ada masalah:
1. Cek file log: `backend_api/error.log`
2. Cek Android Logcat di Android Studio
3. Test API manual via browser atau Postman

---

## 📝 Version History

- v1.0 - Initial release dengan fitur login/register
- v1.1 - Tambah fitur upload poster film
- v1.2 - Fix missing posters dan force close issues

---

**Happy Coding! 🎉**

