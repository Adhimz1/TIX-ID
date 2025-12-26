# 🎯 LANGKAH-LANGKAH UPDATE DATABASE

## Cek Apakah Database Sudah Diupdate

### **Cara Cepat:**
1. Buka folder: `backend_api`
2. Double-click: **`cek_database.bat`**
3. Lihat hasilnya:
   - Jika semua ✓ → Database sudah OK, skip ke langkah "Build Android"
   - Jika ada ✗ → Lanjut ke cara update di bawah

---

## Cara 1: AUTO (Script BAT) ⚡

1. Pastikan Laragon running (MySQL nyala)
2. Double-click: **`update_database.bat`**
3. Enter password MySQL (biasanya kosong, langsung Enter)
4. Tunggu sampai selesai
5. Selesai! ✅

---

## Cara 2: MANUAL (phpMyAdmin) 🖱️

### A. Import Fresh Database (Recommended)

1. Buka browser → `http://localhost/phpmyadmin`
2. Login (root, password kosong)
3. **Drop database lama:**
   - Klik `tix_id` di sidebar
   - Tab "Operations"
   - Scroll ke bawah → "Drop the database"
   - Konfirmasi
4. **Import database baru:**
   - Tab "Import"
   - Choose File → Pilih `backend_api/database.sql`
   - Klik "Go"
   - Tunggu selesai
5. Verifikasi:
   - Klik database `tix_id`
   - Pastikan ada tabel: `users` dan `movies`

### B. Update Database Existing (Jika Ada Data Penting)

1. Buka phpMyAdmin → database `tix_id`
2. Tab "SQL"
3. Copy-paste dan jalankan query ini:

```sql
-- 1. Tambah kolom role
ALTER TABLE users ADD COLUMN role ENUM('user', 'admin') DEFAULT 'user' AFTER phone;

-- 2. Buat tabel movies
CREATE TABLE IF NOT EXISTS movies (
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

-- 3. Tambah admin account (jika belum ada)
INSERT INTO users (name, email, password, phone, role) 
VALUES ('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin')
ON DUPLICATE KEY UPDATE role = 'admin';

-- 4. Tambah index
CREATE INDEX IF NOT EXISTS idx_movie_status ON movies(status);
CREATE INDEX IF NOT EXISTS idx_movie_title ON movies(title);
```

4. Klik "Go"
5. Selesai!

---

## Verifikasi Database Sudah OK ✅

### Cek di phpMyAdmin:
1. Buka database `tix_id`
2. Klik tabel `users` → Browse
   - Harus ada kolom `role`
   - Harus ada user dengan email `admin@tixid.com` dan role `admin`
3. Klik tabel `movies`
   - Tabel harus ada (boleh kosong)

### Atau jalankan: `cek_database.bat`

---

## Setelah Database OK

### Copy File API ke Laragon

Pastikan file ini ada di `C:\laragon\www\tix_id_api\`:
- ✅ config.php
- ✅ login.php *(updated)*
- ✅ register.php
- ✅ admin_movies.php *(NEW)*
- ✅ movies.php *(NEW)*

**Cara tercepat:** Copy semua file dari folder `backend_api` ke `C:\laragon\www\tix_id_api\`

---

## Build & Run Android

1. Buka Android Studio
2. **Sync Gradle**: File → Sync Project with Gradle Files
3. **Rebuild**: Build → Rebuild Project
4. **Run**: Run → Run 'app' (atau Shift+F10)

---

## Test Login Admin

1. Jalankan aplikasi di HP/Emulator
2. Klik "Masuk"
3. Login dengan:
   ```
   Email    : admin@tixid.com
   Password : password123
   ```
4. Harus masuk ke **Admin Dashboard** (tampilan ungu dengan 2 tab)
5. Bisa tambah film dengan klik tombol + di kanan bawah

---

## 🆘 Kalau Ada Masalah

### Database tidak update
- Jalankan: `update_database.bat`
- Atau import manual via phpMyAdmin

### File API belum dicopy
- Copy semua dari `backend_api` ke `C:\laragon\www\tix_id_api\`

### Login admin masuk ke Home (bukan Admin Dashboard)
```sql
-- Jalankan di phpMyAdmin:
UPDATE users SET role = 'admin' WHERE email = 'admin@tixid.com';
```

### Build error
- Clean Project: Build → Clean Project
- Rebuild: Build → Rebuild Project
- Invalidate Caches: File → Invalidate Caches / Restart

---

## 📄 Dokumentasi Lengkap

- **Setup Database:** `CARA_UPDATE_DATABASE.md`
- **Admin Guide:** `ADMIN_SETUP_GUIDE.md`
- **Quick Start:** `QUICK_START_ADMIN.md`

---

## ✨ Selesai!

Sekarang kamu punya:
- ✅ Database dengan tabel users (role) dan movies
- ✅ Admin account siap pakai
- ✅ API lengkap untuk admin
- ✅ Dashboard admin di aplikasi

Selamat mengelola film! 🎬🍿

