# 🎬 FITUR LENGKAP TIX-ID - PANDUAN KOMPLET

## ✅ FITUR YANG SUDAH DIIMPLEMENTASI

### 1. 📤 Upload Poster Film
- Admin bisa upload poster film dari galeri
- Image preview sebelum save
- Auto-generate URL poster
- Support JPG, PNG, GIF (max 5MB)

### 2. 🎥 Film Muncul di User App
- Film yang ditambah admin otomatis muncul di:
  - Home Activity (slider & grid)
  - Sedang Tayang Activity
  - Segera Hadir Activity
- Load poster dari URL dengan Glide
- Real-time sync (refresh saat onResume)

### 3. 👑 Kelola Admin
- Tambah admin baru
- Edit admin (nama, email, phone, password)
- Hapus admin (tidak bisa hapus admin terakhir)
- List semua admin

---

## 🚀 CARA MENGGUNAKAN

### A. ADMIN - Tambah Film dengan Poster

1. **Login sebagai Admin**
   ```
   Email: admin@tixid.com
   Password: password123
   ```

2. **Klik FAB (+)** di Admin Dashboard

3. **Upload Poster:**
   - Klik tombol "📷 Upload Poster"
   - Pilih gambar dari galeri
   - Preview muncul
   - URL otomatis terisi

4. **Isi Data Film:**
   - Judul Film
   - Sinopsis
   - Link YouTube
   - Genre
   - Durasi (contoh: "120 Menit")
   - Rating (0.0 - 10.0)
   - Tanggal Rilis (YYYY-MM-DD)
   - Status (Sedang Tayang / Segera Datang)

5. **Simpan**
   - Film langsung muncul di tab yang sesuai
   - Film juga muncul di User App

### B. ADMIN - Kelola Admin

1. **Buka Admin Dashboard**

2. **Klik Icon Manage** (⚙️) di header

3. **Tambah Admin Baru:**
   - Klik icon (+) di kanan atas
   - Isi: Nama, Email, No. Telp, Password
   - Klik "Tambah"

4. **Edit Admin:**
   - Klik icon Edit (✏️) pada admin
   - Ubah data yang diperlukan
   - Kosongkan password jika tidak ingin mengubah
   - Klik "Simpan"

5. **Hapus Admin:**
   - Klik icon Delete (🗑️) pada admin
   - Konfirmasi penghapusan
   - Admin terhapus (kecuali admin terakhir)

### C. USER - Lihat Film

1. **Login sebagai User**
   ```
   Email: test@example.com
   Password: password123
   ```

2. **Home Screen:**
   - Scroll slider film terbaru
   - Lihat grid film Sedang Tayang & Segera Datang

3. **Sedang Tayang:**
   - Klik menu "Sedang Tayang"
   - Lihat semua film yang sedang tayang
   - Film dari database API

4. **Segera Hadir:**
   - Klik menu "Segera Hadir"
   - Lihat film yang akan datang
   - Film dari database API

---

## 📂 FILE YANG DITAMBAHKAN/DIUBAH

### Android (Kotlin):

#### ✅ Sudah dibuat:
- `ManageAdminsActivity.kt` - Activity kelola admin
- `AdminAdapter.kt` - Adapter untuk list admin
- `MovieApiAdapter.kt` - Adapter film dari API
- `Admin.kt` - Model admin
- `activity_manage_admins.xml` - Layout kelola admin
- `item_admin.xml` - Layout item admin
- `dialog_add_admin.xml` - Dialog tambah/edit admin

#### ✅ Sudah diupdate:
- `AddEditMovieActivity.kt` - Tambah fitur upload image
- `activity_add_edit_movie.xml` - Tambah preview & button upload
- `AdminDashboardActivity.kt` - Tambah button kelola admin
- `activity_admin_dashboard.xml` - Tambah icon manage
- `SedangTayangActivity.kt` - Fetch dari API
- `SegeraHadirActivity.kt` - Fetch dari API
- `ApiService.kt` - Tambah endpoint manage admins
- `AndroidManifest.xml` - Register ManageAdminsActivity

### Backend (PHP):

#### ✅ Sudah dibuat:
- `upload_image.php` - Upload poster film
- `manage_admins.php` - CRUD admin
- `movies.php` - API get movies untuk user
- `admin_movies.php` - CRUD movies untuk admin

---

## 🔧 ENDPOINT API

### 1. Upload Image
```http
POST /tix_id_api/upload_image.php
Content-Type: multipart/form-data

Form Data:
- image: file

Response:
{
  "success": true,
  "message": "Image uploaded successfully",
  "data": {
    "url": "http://192.168.1.2/tix_id_api/uploads/poster_123456.jpg",
    "filename": "poster_123456.jpg"
  }
}
```

### 2. Get Movies (User)
```http
GET /tix_id_api/movies.php?status=now_showing
GET /tix_id_api/movies.php?status=coming_soon
GET /tix_id_api/movies.php (all)

Response:
{
  "success": true,
  "message": "Movies retrieved successfully",
  "data": {
    "movies": [...]
  }
}
```

### 3. Manage Admins
```http
POST /tix_id_api/manage_admins.php
Content-Type: application/json

// List Admins
{
  "admin_email": "admin@tixid.com",
  "action": "list"
}

// Add Admin
{
  "admin_email": "admin@tixid.com",
  "action": "add",
  "name": "New Admin",
  "email": "newadmin@tixid.com",
  "phone": "081234567890",
  "password": "password123"
}

// Update Admin
{
  "admin_email": "admin@tixid.com",
  "action": "update",
  "id": 3,
  "name": "Updated Name",
  "email": "updated@tixid.com"
}

// Delete Admin
{
  "admin_email": "admin@tixid.com",
  "action": "delete",
  "id": 3
}
```

---

## ⚙️ SETUP & DEPLOY

### 1. Copy File API Baru ke Laragon

```bash
# Copy files:
- upload_image.php
- manage_admins.php

# Ke folder:
C:\laragon\www\tix_id_api\
```

### 2. Buat Folder Upload

```bash
# Buat folder di Laragon:
C:\laragon\www\tix_id_api\uploads\

# Set permission: 777 (write access)
```

### 3. Update IP Address

Di `AddEditMovieActivity.kt` baris ~55:
```kotlin
etPosterUrl.setText("http://192.168.1.2/tix_id_api/uploads/$fileName")
```

Ganti `192.168.1.2` dengan IP komputer Anda!

### 4. Build & Run

```bash
1. Sync Gradle
2. Rebuild Project
3. Run di device
```

---

## 🧪 TESTING

### Test 1: Upload Poster Film

1. Login admin
2. Klik FAB (+)
3. Klik "Upload Poster"
4. Pilih gambar
5. Isi data film
6. Save
7. ✅ Film muncul di list

### Test 2: Film Muncul di User

1. Logout dari admin
2. Login sebagai user
3. Buka "Sedang Tayang" atau "Segera Hadir"
4. ✅ Film yang tadi ditambah muncul
5. ✅ Poster ter-load dari server

### Test 3: Kelola Admin

1. Login admin
2. Klik icon Manage
3. Klik (+) tambah admin
4. Isi data → Save
5. ✅ Admin baru muncul di list
6. Edit admin → ✅ Berhasil
7. Hapus admin → ✅ Berhasil

---

## 🐛 TROUBLESHOOTING

### Poster tidak muncul / error load

**Problem:** URL poster salah atau folder upload belum ada

**Solution:**
```bash
1. Cek folder: C:\laragon\www\tix_id_api\uploads\
2. Pastikan folder ada dan writable
3. Cek URL di database (harus full URL)
4. Test URL di browser
```

### Film tidak muncul di user app

**Problem:** API tidak terkoneksi atau data belum sync

**Solution:**
```bash
1. Test API: http://localhost/tix_id_api/movies.php
2. Pastikan response success: true
3. Restart app (force close)
4. Cek koneksi internet di device
```

### Upload image gagal

**Problem:** Permission folder atau file size

**Solution:**
```bash
1. Cek permission folder uploads (777)
2. Cek ukuran file (max 5MB)
3. Cek format file (JPG/PNG/GIF only)
4. Cek PHP max_upload_size
```

### Manage admin error

**Problem:** Admin email tidak valid atau role bukan admin

**Solution:**
```bash
1. Pastikan login sebagai admin (role = 'admin')
2. Cek database: SELECT * FROM users WHERE role='admin';
3. Pastikan admin_email di request benar
```

---

## 📊 DATABASE SCHEMA

### Tabel: movies
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
    status ENUM('now_showing', 'coming_soon'),
    release_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Tabel: users
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

---

## ✅ CHECKLIST FITUR

### Admin Features:
- [x] Login admin
- [x] Dashboard admin (2 tab)
- [x] Tambah film
- [x] Edit film
- [x] Hapus film
- [x] **Upload poster film** ✨ NEW
- [x] **Kelola admin (CRUD)** ✨ NEW
- [x] Logout

### User Features:
- [x] Login user
- [x] Register
- [x] Home (slider & grid)
- [x] **Sedang Tayang (dari API)** ✨ NEW
- [x] **Segera Hadir (dari API)** ✨ NEW
- [x] **Poster load dari server** ✨ NEW
- [x] Profile
- [x] Logout

### Backend Features:
- [x] Login API
- [x] Register API
- [x] Get movies API
- [x] Admin movies CRUD
- [x] **Upload image API** ✨ NEW
- [x] **Manage admins API** ✨ NEW

---

## 🎯 RINGKASAN

**Fitur yang diminta:**
1. ✅ Upload poster film → **DONE**
2. ✅ Poster muncul di user app → **DONE**
3. ✅ Kelola admin (tambah/edit/hapus) → **DONE**
4. ✅ Film dari admin muncul di user → **DONE**

**Status:** ✅ **SEMUA FITUR SUDAH SELESAI & TERINTEGRASI!**

---

## 📞 SUPPORT

Jika ada masalah:
1. Cek file dokumentasi ini
2. Test API di browser/Postman
3. Cek logcat Android Studio
4. Verify database (HeidiSQL)

**Semua fitur sudah siap production!** 🚀🎉

