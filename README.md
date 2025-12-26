# 🎬 TIX-ID - Cinema Ticket Booking App

## 📱 Aplikasi Pemesanan Tiket Bioskop dengan Admin Panel

---

## ✨ FITUR TERBARU (December 23, 2025)

### 🎉 3 Fitur Baru Telah Ditambahkan:

1. **📤 Upload Poster Film**
   - Admin bisa upload gambar poster langsung dari galeri
   - Preview image sebelum save
   - Auto-generate URL

2. **🎥 Film Tampil di User App**
   - Film yang ditambah admin otomatis muncul di user app
   - Load poster dari server dengan Glide
   - Real-time sync

3. **👑 Kelola Admin (CRUD)**
   - Tambah admin baru
   - Edit admin existing
   - Hapus admin (proteksi last admin)

---

## 🚀 QUICK START

### 1. Copy File API ke Laragon

```bash
cd backend_api
copy_all_new_files.bat
```

### 2. Build & Run Android

```bash
1. Buka Android Studio
2. Sync Gradle
3. Rebuild Project
4. Run
```

### 3. Login & Test

**Admin:**
```
Email: admin@tixid.com
Password: password123
```

**User:**
```
Email: test@example.com
Password: password123
```

---

## 📖 DOKUMENTASI

### Untuk Mulai Cepat:
- **[QUICK_START_FITUR_BARU.md](QUICK_START_FITUR_BARU.md)** ← Mulai dari sini!

### Panduan Lengkap:
- **[FITUR_LENGKAP_PANDUAN.md](FITUR_LENGKAP_PANDUAN.md)** - Semua fitur detail
- **[FINAL_SUMMARY_COMPLETE.md](FINAL_SUMMARY_COMPLETE.md)** - Ringkasan final

### Troubleshooting:
- **[SOLUSI_ERROR_API.md](SOLUSI_ERROR_API.md)** - Fix API errors
- **[FIX_LOGIN_ERROR.md](FIX_LOGIN_ERROR.md)** - Fix login issues

### Setup Database:
- **[MULAI_SINI_DATABASE.md](MULAI_SINI_DATABASE.md)** - Setup database
- **[CARA_UPDATE_DATABASE.md](CARA_UPDATE_DATABASE.md)** - Update database

---

## 🎯 FITUR UTAMA

### 👤 User Features:
- ✅ Register & Login
- ✅ Browse film (Sedang Tayang & Segera Hadir)
- ✅ Lihat detail film dengan poster
- ✅ Pilih kursi bioskop
- ✅ Pilih jadwal & lokasi
- ✅ Pembayaran (Gopay, Dana, Mandiri)
- ✅ Profile management

### 👑 Admin Features:
- ✅ Login admin
- ✅ Dashboard dengan 2 tab (Sedang Tayang & Segera Datang)
- ✅ **Upload poster film** 📸 NEW
- ✅ Tambah film baru
- ✅ Edit film
- ✅ Hapus film
- ✅ **Kelola admin (Tambah/Edit/Hapus)** 👥 NEW

### 🖥️ Backend Features:
- ✅ RESTful API dengan PHP
- ✅ MySQL Database
- ✅ Authentication & Authorization
- ✅ Role-based access (User & Admin)
- ✅ **Image upload handling** 📤 NEW
- ✅ **Admin management API** ⚙️ NEW

---

## 🛠️ TECH STACK

### Frontend (Android):
- **Language:** Kotlin
- **UI:** Material Design 3
- **Architecture:** MVVM-like with Coroutines
- **Networking:** Retrofit + OkHttp
- **Image Loading:** Glide
- **Data Storage:** DataStore (Preferences)

### Backend (API):
- **Language:** PHP 7.4+
- **Database:** MySQL 5.7+
- **Server:** Laragon (Apache + MySQL)
- **Authentication:** BCrypt password hashing
- **File Upload:** Multipart/form-data

---

## 📂 STRUKTUR PROJECT

```
TIX-ID/
├── app/                          # Android App
│   ├── src/main/
│   │   ├── java/com/pab/tixid/
│   │   │   ├── adapters/        # RecyclerView Adapters
│   │   │   ├── api/             # Retrofit API Service
│   │   │   ├── models/          # Data Models
│   │   │   ├── utils/           # Helper classes
│   │   │   ├── ManageAdminsActivity.kt  ⭐ NEW
│   │   │   ├── AddEditMovieActivity.kt  ⭐ UPDATED
│   │   │   └── ...activities
│   │   └── res/                 # Resources (layouts, drawables, etc)
│
├── backend_api/                 # PHP Backend
│   ├── upload_image.php         ⭐ NEW - Upload poster
│   ├── manage_admins.php        ⭐ NEW - Kelola admin
│   ├── movies.php               # Get movies (user)
│   ├── admin_movies.php         # CRUD movies (admin)
│   ├── login.php                # Login API
│   ├── register.php             # Register API
│   ├── config.php               # DB config
│   ├── database.sql             # Database schema
│   └── uploads/                 ⭐ NEW - Folder upload poster
│
└── Documentation/               # All documentation files
```

---

## 🔧 SETUP & INSTALLATION

### Prerequisites:
- ✅ Android Studio (latest version)
- ✅ Laragon (Apache + MySQL)
- ✅ Android Device atau Emulator
- ✅ PC dan Device dalam 1 network

### Setup Steps:

**1. Clone Repository**
```bash
git clone <repository-url>
cd TIX-ID
```

**2. Setup Database**
```bash
1. Start Laragon
2. Open HeidiSQL
3. Create database: tix_id
4. Import: backend_api/database.sql
5. Run: backend_api/fix_users.php
```

**3. Setup Backend**
```bash
1. Copy backend_api ke: C:\laragon\www\tix_id_api\
2. Atau jalankan: copy_all_new_files.bat
3. Buat folder: uploads/
4. Test: http://localhost/tix_id_api/test_api_lengkap.html
```

**4. Setup Android**
```bash
1. Buka project di Android Studio
2. Update BASE_URL di RetrofitClient.kt (ganti IP)
3. Sync Gradle
4. Rebuild Project
5. Run
```

---

## 🧪 TESTING

### Test API:
```bash
1. Buka: http://localhost/tix_id_api/test_api_lengkap.html
2. Test semua endpoint
3. Pastikan semua ✓ OK
```

### Test Android:

**Test Upload Poster:**
```
1. Login admin
2. Klik FAB (+)
3. Upload gambar
4. Save → Film muncul ✅
```

**Test Film di User:**
```
1. Logout
2. Login user
3. Buka "Sedang Tayang"
4. Film muncul dengan poster ✅
```

**Test Kelola Admin:**
```
1. Login admin
2. Klik icon ⚙️
3. Tambah/Edit/Hapus admin ✅
```

---

## 📊 API ENDPOINTS

### Authentication:
- `POST /login.php` - Login user/admin
- `POST /register.php` - Register user baru

### Movies (Public):
- `GET /movies.php` - Get all movies
- `GET /movies.php?status=now_showing` - Sedang Tayang
- `GET /movies.php?status=coming_soon` - Segera Datang

### Admin - Movies:
- `POST /admin_movies.php` - Add movie
- `PUT /admin_movies.php` - Update movie
- `DELETE /admin_movies.php` - Delete movie

### Admin - Image Upload: ⭐ NEW
- `POST /upload_image.php` - Upload poster image

### Admin - Manage Admins: ⭐ NEW
- `POST /manage_admins.php` - List/Add/Update/Delete admins

---

## 🐛 TROUBLESHOOTING

### Build Error:
```
Fix: Invalidate Caches / Restart
     Sync Gradle → Clean → Rebuild
```

### API Connection Error:
```
Fix: Cek BASE_URL (IP harus sama)
     Cek Laragon running
     Cek device & PC 1 network
```

### Poster tidak muncul:
```
Fix: Buat folder uploads/
     Set permission 777
     Test URL di browser
```

### Login Error:
```
Fix: Jalankan fix_users.php
     Test di test_api_lengkap.html
     Verify database
```

**Detail troubleshooting:** Lihat file `SOLUSI_ERROR_API.md`

---

## 📜 LICENSE

This project is for educational purposes.

---

## 👥 CONTRIBUTORS

- **Developer:** Your Team
- **Last Update:** December 23, 2025
- **Version:** 2.0 (dengan Upload Poster & Kelola Admin)

---

## 🎉 CHANGELOG

### Version 2.0 (December 23, 2025)
- ✅ **Added:** Upload poster film feature
- ✅ **Added:** Manage admins (CRUD)
- ✅ **Improved:** Film fetch dari API (user app)
- ✅ **Improved:** Real-time sync admin ↔ user
- ✅ **Fixed:** Various UI/UX improvements

### Version 1.0 (Previous)
- ✅ Basic user authentication
- ✅ Admin dashboard
- ✅ Movie CRUD (admin)
- ✅ Seat selection
- ✅ Payment integration

---

## 📞 SUPPORT

**Need Help?**
1. Baca dokumentasi lengkap
2. Test API via test_api_lengkap.html
3. Check logcat untuk Android errors
4. Verify database via HeidiSQL

**Documentation Files:**
- `QUICK_START_FITUR_BARU.md` - Quick start guide
- `FITUR_LENGKAP_PANDUAN.md` - Complete features guide
- `FINAL_SUMMARY_COMPLETE.md` - Final summary
- `SOLUSI_ERROR_API.md` - API troubleshooting

---

## ⭐ FEATURES SUMMARY

| Feature | Status | User | Admin |
|---------|--------|------|-------|
| Login/Register | ✅ | ✅ | ✅ |
| Browse Movies | ✅ | ✅ | ✅ |
| Upload Poster | ⭐ NEW | - | ✅ |
| CRUD Movies | ✅ | - | ✅ |
| Manage Admins | ⭐ NEW | - | ✅ |
| Seat Selection | ✅ | ✅ | - |
| Payment | ✅ | ✅ | - |
| Profile | ✅ | ✅ | ✅ |

---

## 🚀 READY TO GO!

**Semua fitur sudah siap production!**

1. Copy file API → ✅
2. Build Android → ✅
3. Test fitur → ✅
4. **Deploy & Enjoy!** 🎉

**Happy Coding! 🎬🍿✨**

