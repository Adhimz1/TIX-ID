# TIX ID - Database Integration Guide

## Setup Instruksi

### 1. Setup Database MySQL di Laragon

1. Buka Laragon dan start Apache + MySQL
2. Buka phpMyAdmin: `http://localhost/phpmyadmin`
3. Buat database baru bernama `tix_id`
4. Jalankan query SQL berikut:

```sql
CREATE DATABASE tix_id;

USE tix_id;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert dummy user untuk testing
INSERT INTO users (name, email, password, phone) 
VALUES ('Admin', 'admin@tixid.com', MD5('admin123'), '081234567890');
```

### 2. Setup API PHP

File API sudah dibuat di: `C:\laragon\www\tix_id_api\`

Files yang perlu ada:
- `config.php` - Konfigurasi database
- `login.php` - API endpoint login
- `register.php` - API endpoint registrasi

### 3. Testing API

**Test Login API:**
```
URL: http://localhost/tix_id_api/login.php
Method: POST
Body (JSON):
{
    "email": "admin@tixid.com",
    "password": "admin123"
}
```

**Test Register API:**
```
URL: http://localhost/tix_id_api/register.php
Method: POST
Body (JSON):
{
    "name": "Test User",
    "email": "test@example.com",
    "password": "test123",
    "phone": "081234567890"
}
```

### 4. Konfigurasi Android

#### Update BASE_URL di RetrofitClient.kt:

**Untuk Emulator:**
```kotlin
private const val BASE_URL = "http://10.0.2.2/tix_id_api/"
```

**Untuk Device Fisik:**
1. Cek IP komputer dengan `ipconfig` di CMD
2. Cari "IPv4 Address" (contoh: 192.168.1.100)
3. Update BASE_URL:
```kotlin
private const val BASE_URL = "http://192.168.1.100/tix_id_api/"
```

**Pastikan komputer dan device di jaringan WiFi yang sama!**

### 5. Testing di Android

#### Test Registrasi:
1. Buka app TIX ID
2. Klik "Daftar" atau buka DaftarActivity
3. Isi form:
   - Nama: Test User
   - Email: test@tixid.com
   - Password: test123
   - Konfirmasi Password: test123
4. Klik tombol "Daftar"
5. Jika berhasil, akan redirect ke MasukActivity

#### Test Login:
1. Buka MasukActivity
2. Isi form:
   - Email: admin@tixid.com
   - Password: admin123
3. Klik tombol "Masuk"
4. Jika berhasil, akan redirect ke HomeActivity

#### Test Logout:
1. Di HomeActivity, klik menu "Akun" (bottom navigation)
2. Klik tombol "Keluar"
3. Confirm dialog
4. Akan redirect ke SelamatDatang/Welcome screen

#### Test Session Persistence:
1. Login dengan user
2. Close app (swipe dari recent apps)
3. Buka app lagi
4. **Seharusnya langsung masuk ke HomeActivity tanpa perlu login lagi**

## Troubleshooting

### Error: "Unable to resolve host"
- Pastikan Laragon running
- Pastikan BASE_URL sudah benar
- Test API dengan Postman dulu

### Error: "Duplicate resources logo_dana/logo_gopay"
- Hapus file `logo_dana.xml` dan `logo_gopay.xml`
- Atau rename menjadi `logo_dana_backup.xml`

### Error: "Connection refused"
- Pastikan Apache di Laragon running
- Cek firewall Windows tidak block Laragon
- Untuk device fisik, pastikan satu WiFi dengan komputer

### Gradle Sync Failed
- File > Invalidate Caches / Restart
- Sync Project with Gradle Files

## File Structure

```
TIX-ID/
├── app/src/main/java/com/pab/tixid/
│   ├── api/
│   │   ├── ApiService.kt
│   │   └── RetrofitClient.kt
│   ├── models/
│   │   ├── User.kt
│   │   ├── LoginRequest.kt
│   │   ├── RegisterRequest.kt
│   │   └── ApiResponse.kt
│   ├── utils/
│   │   └── UserPreferences.kt
│   ├── MasukActivity.kt
│   ├── DaftarActivity.kt
│   └── ProfileActivity.kt
└── build.gradle.kts (with Retrofit dependencies)
```

## Dependencies yang Ditambahkan

```kotlin
// Retrofit
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

// Lifecycle
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")
```

## Fitur Yang Sudah Diimplementasi

✅ Registrasi user ke database MySQL
✅ Login user dari database
✅ Session management dengan DataStore
✅ Auto-login (persistent session)
✅ Logout dengan clear session
✅ Profile display dengan data dari database

## Next Steps (Opsional)

- [ ] Tambah field phone number di form registrasi
- [ ] Implementasi forgot password
- [ ] Upload profile picture ke server
- [ ] Update profile information
- [ ] Change password feature

