# ⚡ MULAI DARI SINI - UPDATE DATABASE

## Pilih Salah Satu:

### 🔵 CARA 1: Otomatis (Paling Mudah)
1. Pastikan Laragon running
2. Double-click: **`backend_api/update_database.bat`**
3. Enter (password kosong)
4. Selesai!

### 🟢 CARA 2: Manual via phpMyAdmin
1. Buka: `http://localhost/phpmyadmin`
2. Klik database `tix_id` (jika ada) → Operations → Drop database
3. Tab Import → Choose File → Pilih `backend_api/database.sql`
4. Klik Go
5. Selesai!

---

## ✅ Cek Apakah Sudah Berhasil

Double-click: **`backend_api/cek_database.bat`**

Jika semua ✓ → Database sudah OK!

---

## 🚀 Langkah Selanjutnya

1. **Copy file API** (jika belum):
   - Copy semua file dari `backend_api`
   - Paste ke `C:\laragon\www\tix_id_api\`

2. **Build & Run Android**:
   - Buka Android Studio
   - Sync Gradle
   - Build → Rebuild Project
   - Run di device

3. **Login Admin**:
   ```
   Email    : admin@tixid.com
   Password : password123
   ```

---

## 📚 Dokumentasi Lengkap

Kalau ada masalah, baca file ini:
- **`STEP_BY_STEP_DATABASE.md`** ← Panduan lengkap semua cara
- **`CARA_UPDATE_DATABASE.md`** ← Detail query SQL manual
- **`ADMIN_SETUP_GUIDE.md`** ← Panduan fitur admin

---

## 💬 Status Saat Ini

✅ File database.sql sudah diupdate  
✅ File API sudah siap (admin_movies.php, movies.php, dll)  
✅ Android code sudah dibuat  
⏳ **TINGGAL:** Import database & build Android!

---

**Password default admin:** `password123`  
**Role:** admin (bisa kelola film)

Selamat mencoba! 🎉

