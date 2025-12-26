# 🎬 TIX-ID - Cinema Ticket Booking App

Aplikasi pemesanan tiket bioskop berbasis Android dengan Kotlin.

## 📋 Fitur

- ✅ **Authentication**
  - Register dengan validasi lengkap
  - Login dengan session persistence
  - Auto-login jika sudah pernah login
  - Logout

- ✅ **Movie Booking**
  - Pilih bioskop
  - Pilih jadwal film
  - Pilih kursi interaktif
  - Ringkasan pesanan

- ✅ **Payment**
  - Metode pembayaran (GoPay, DANA, dll)
  - Detail transaksi

## 🛠️ Tech Stack

- **Frontend:** Kotlin + XML
- **Backend:** PHP + MySQL
- **Architecture:** MVVM Pattern
- **Networking:** Retrofit + OkHttp
- **Storage:** DataStore (Preferences)
- **Server:** Laragon (Apache + MySQL)

## 🚀 Quick Setup

### Prerequisites
- Android Studio (latest version)
- JDK 11 or higher
- Laragon (untuk backend)
- HP Android atau Emulator

### Setup Backend

1. **Install Laragon**
   - Download dari: https://laragon.org/download/
   - Install dan jalankan

2. **Copy API Files**
   ```bash
   # Jalankan script setup otomatis:
   setup_api.bat
   ```

3. **Setup Database**
   - Buka: http://localhost/phpmyadmin
   - Klik "SQL"
   - Copy-paste isi `backend_api/database.sql`
   - Klik "Go"

4. **Test API**
   - Buka: http://localhost/tix_id_api/
   - Test register & login

### Setup Android App

1. **Clone Repository**
   ```bash
   git clone <repo-url>
   cd TIX-ID
   ```

2. **Open in Android Studio**
   - Open project
   - Wait for Gradle sync

3. **Update IP Address (jika perlu)**
   - Buka `RetrofitClient.kt`
   - Update `BASE_URL` dengan IP komputer Anda
   - Cek IP dengan command: `ipconfig`

4. **Build & Run**
   - Klik Run (▶️)
   - Pilih device
   - Tunggu sampai app terinstall

## 📱 Usage

1. **Register**
   - Buka app → Klik "Daftar"
   - Isi form (nama, email, password)
   - Klik "Daftar"

2. **Login**
   - Masukkan email dan password
   - Klik "Masuk"

3. **Booking Tiket**
   - Pilih bioskop
   - Pilih film
   - Pilih jadwal
   - Pilih kursi
   - Pilih metode pembayaran
   - Konfirmasi pesanan

## 📁 Project Structure

```
TIX-ID/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/pab/tixid/
│           │   ├── api/              # Retrofit API services
│           │   ├── models/           # Data models
│           │   ├── utils/            # Helper classes
│           │   ├── MasukActivity.kt  # Login screen
│           │   ├── DaftarActivity.kt # Register screen
│           │   ├── HomeActivity.kt   # Home screen
│           │   └── ...               # Other activities
│           └── res/                  # Resources (layouts, drawables, etc)
├── backend_api/
│   ├── config.php                    # Database configuration
│   ├── register.php                  # Register endpoint
│   ├── login.php                     # Login endpoint
│   ├── test_api.php                  # API status checker
│   ├── index.html                    # Web-based API tester
│   └── database.sql                  # Database schema
├── setup_api.bat                     # Quick setup script
├── QUICK_START.md                    # Quick start guide
└── PANDUAN_FIX_LOGIN_REGISTER.md    # Detailed troubleshooting guide
```

## 🔧 Configuration

### RetrofitClient.kt
```kotlin
private const val BASE_URL = "http://192.168.1.2/tix_id_api/"
```
**Note:** Ganti IP sesuai dengan IP komputer Anda

### config.php
```php
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'tix_id');
```

## 🐛 Troubleshooting

### Error: "Connection failed"
- ✅ Pastikan Laragon running
- ✅ Cek database `tix_id` sudah dibuat
- ✅ Test API di browser: http://localhost/tix_id_api/test_api.php

### Error: "Failed to connect to API"
- ✅ Pastikan HP dan PC di network yang sama
- ✅ Update IP di `RetrofitClient.kt`
- ✅ Matikan firewall sementara
- ✅ Test ping dari HP ke PC

### Error: "Email atau password salah"
- ✅ Cek di database apakah user terdaftar
- ✅ Lihat Logcat untuk detail error
- ✅ Test API manual via web tester

**Full troubleshooting guide:** `PANDUAN_FIX_LOGIN_REGISTER.md`

## 📊 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/register.php` | POST | Register user baru |
| `/login.php` | POST | Login user |
| `/test_api.php` | GET | Check API status |

### Register Request
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "081234567890"
}
```

### Register Response
```json
{
  "success": true,
  "message": "Pendaftaran berhasil! Silakan login",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com"
  }
}
```

### Login Request
```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

### Login Response
```json
{
  "success": true,
  "message": "Login berhasil",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "081234567890",
    "created_at": "2025-12-22 10:00:00"
  }
}
```

## 🧪 Testing

### Test API Manual
```bash
# Test Register
curl -X POST http://192.168.1.2/tix_id_api/register.php \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@test.com","password":"123456","phone":"081234567890"}'

# Test Login
curl -X POST http://192.168.1.2/tix_id_api/login.php \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"123456"}'
```

### Test via Web
- Buka: http://localhost/tix_id_api/
- Gunakan form yang tersedia untuk test

## 📝 Recent Changes (22 Dec 2025)

### Fixed Issues:
- ✅ Register tidak masuk database
- ✅ Login selalu error "email dan password salah"
- ✅ Layar putih saat test API

### Added Features:
- ✅ Detailed logging di MasukActivity dan DaftarActivity
- ✅ Better error messages with full details
- ✅ PHP backend API (register, login, config)
- ✅ Database schema SQL
- ✅ Web-based API tester
- ✅ Setup script untuk quick installation
- ✅ Comprehensive documentation

### Improved:
- ✅ Error handling di semua API calls
- ✅ Toast messages lebih informatif
- ✅ Validasi input lebih ketat
- ✅ Password hashing dengan bcrypt
- ✅ Session persistence (auto-login)

## 👨‍💻 Development

### Build Project
```bash
./gradlew build
```

### Run Tests
```bash
./gradlew test
```

### Clean Build
```bash
./gradlew clean
```

## 📄 License

This project is created for educational purposes.

## 👥 Contributors

- Ahmad - Developer

## 🆘 Support

Jika ada masalah:
1. Cek `QUICK_START.md` untuk solusi cepat
2. Cek `PANDUAN_FIX_LOGIN_REGISTER.md` untuk troubleshooting detail
3. Lihat Logcat di Android Studio
4. Test API via web tester: http://localhost/tix_id_api/

---

**Happy Coding! 🚀**

