# ⚡ QUICK START - FITUR BARU

## 🎯 3 Fitur Baru yang Ditambahkan:

1. **📤 Upload Poster Film** - Admin bisa upload gambar poster
2. **🎥 Film Muncul di User** - Film dari admin otomatis tampil di user app
3. **👑 Kelola Admin** - Tambah, edit, hapus admin

---

## 🚀 CARA PAKAI (3 LANGKAH):

### Step 1: Copy File API Baru

```bash
Double-click: backend_api/copy_all_new_files.bat
```

Ini akan copy:
- ✅ `upload_image.php` (upload poster)
- ✅ `manage_admins.php` (kelola admin)
- ✅ Buat folder `uploads/`

### Step 2: Build Android

```bash
1. Buka Android Studio
2. Sync Gradle
3. Rebuild Project
4. Run
```

### Step 3: Test Fitur!

**A. Test Upload Poster:**
1. Login admin (`admin@tixid.com` / `password123`)
2. Klik FAB (+)
3. Klik "📷 Upload Poster"
4. Pilih gambar
5. Isi data film
6. Save
7. ✅ Film muncul!

**B. Test Film di User App:**
1. Logout
2. Login user (`test@example.com` / `password123`)
3. Buka "Sedang Tayang"
4. ✅ Film yang tadi ditambah muncul!

**C. Test Kelola Admin:**
1. Login admin
2. Klik icon ⚙️ (Manage) di header
3. Klik (+) tambah admin
4. Isi data → Save
5. ✅ Admin baru muncul!

---

## 📋 CHECKLIST:

- [ ] Copy file API baru (`copy_all_new_files.bat`)
- [ ] Folder `uploads/` sudah ada
- [ ] Build Android berhasil
- [ ] Login admin berhasil
- [ ] Upload poster berhasil
- [ ] Film muncul di user app
- [ ] Kelola admin berhasil

---

## 🔧 Troubleshooting Cepat:

### Poster tidak muncul:
```
Cek: C:\laragon\www\tix_id_api\uploads\
Harus ada dan writable!
```

### Film tidak muncul di user:
```
Test: http://localhost/tix_id_api/movies.php
Harus return JSON success: true
```

### Upload gagal:
```
Cek ukuran gambar (max 5MB)
Format: JPG, PNG, GIF
```

---

## 📖 Dokumentasi Lengkap:

Lihat: **`FITUR_LENGKAP_PANDUAN.md`**

---

## ✅ Status:

**SEMUA FITUR SUDAH SIAP!** 🎉

Tinggal:
1. Copy file (`copy_all_new_files.bat`)
2. Build Android
3. Test & pakai!

**Selamat menggunakan! 🚀🎬**

