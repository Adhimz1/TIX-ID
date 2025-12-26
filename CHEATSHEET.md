# 🚀 TIX-ID CHEATSHEET - Commands & Tips

## ⚡ QUICK COMMANDS

### Setup Awal (Sekali Saja):
```bash
# 1. Copy file API
cd backend_api
copy_all_new_files.bat

# 2. Fix user data
Open: http://localhost/tix_id_api/fix_users.php

# 3. Build Android
Android Studio → Sync Gradle → Rebuild → Run
```

---

## 🔑 LOGIN CREDENTIALS

### Admin:
```
Email: admin@tixid.com
Password: password123
Role: admin
```

### User:
```
Email: test@example.com
Password: password123
Role: user
```

---

## 🌐 IMPORTANT URLS

### API Testing:
```
http://localhost/tix_id_api/test_api_lengkap.html
http://localhost/tix_id_api/movies.php
http://localhost/tix_id_api/fix_users.php
```

### Database:
```
phpMyAdmin: http://localhost/phpmyadmin
HeidiSQL: Via Laragon menu
Database: tix_id
```

---

## 📝 COMMON TASKS

### Tambah Film dengan Poster (Admin):
```
1. Login admin
2. FAB (+)
3. "Upload Poster" → Pilih gambar
4. Isi data film
5. Save
```

### Lihat Film (User):
```
1. Login user
2. Home → "Sedang Tayang" / "Segera Hadir"
3. Film muncul dengan poster
```

### Tambah Admin Baru:
```
1. Login admin
2. Icon ⚙️ (Manage Admins)
3. (+) → Isi data
4. Save
```

### Edit Admin:
```
1. Manage Admins
2. ✏️ pada admin
3. Ubah data
4. Save
```

### Hapus Admin:
```
1. Manage Admins
2. 🗑️ pada admin
3. Konfirmasi
```

---

## 🐛 QUICK FIXES

### Build Error:
```
File → Invalidate Caches / Restart
File → Sync Project with Gradle Files
Build → Clean Project
Build → Rebuild Project
```

### API Error:
```
1. Cek Laragon running (lampu hijau)
2. Test: http://localhost/tix_id_api/movies.php
3. Cek IP di RetrofitClient.kt
4. Cek device & PC satu network
```

### Login Error:
```
1. Buka: http://localhost/tix_id_api/fix_users.php
2. Klik execute
3. Login ulang
```

### Poster tidak muncul:
```
1. Cek folder: C:\laragon\www\tix_id_api\uploads\
2. Pastikan folder ada
3. Test upload ulang
4. Cek URL di database
```

### Film tidak muncul:
```
1. Pull to refresh
2. Restart app
3. Cek API: /movies.php?status=now_showing
4. Verify database (HeidiSQL)
```

---

## 🔧 FILE LOCATIONS

### Android:
```
Project: C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\
Main code: app\src\main\java\com\pab\tixid\
Layouts: app\src\main\res\layout\
```

### Backend:
```
Source: backend_api\
Deployed: C:\laragon\www\tix_id_api\
Uploads: C:\laragon\www\tix_id_api\uploads\
```

### Database:
```
SQL file: backend_api\database.sql
Database name: tix_id
Tables: users, movies
```

---

## 📊 DATABASE QUICK QUERIES

### Lihat semua admin:
```sql
SELECT * FROM users WHERE role = 'admin';
```

### Lihat semua film:
```sql
SELECT id, title, status, rating FROM movies;
```

### Tambah admin manual:
```sql
INSERT INTO users (name, email, password, phone, role) VALUES
('New Admin', 'newadmin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '08123456789', 'admin');
```

### Reset password:
```sql
UPDATE users 
SET password = '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi'
WHERE email = 'admin@tixid.com';
-- Password: password123
```

### Hapus semua film:
```sql
DELETE FROM movies;
```

### Count movies:
```sql
SELECT status, COUNT(*) as total FROM movies GROUP BY status;
```

---

## 🎯 TESTING CHECKLIST

### Backend:
- [ ] Laragon running
- [ ] Database tix_id ada
- [ ] Users table ada (dengan admin)
- [ ] Movies table ada
- [ ] Folder uploads/ ada
- [ ] Test API: movies.php ✓
- [ ] Test API: login.php ✓

### Android:
- [ ] Gradle sync berhasil
- [ ] Build tanpa error
- [ ] Run berhasil
- [ ] Login admin ✓
- [ ] Upload poster ✓
- [ ] Film muncul di admin ✓
- [ ] Film muncul di user ✓
- [ ] Manage admin ✓

---

## 🚀 DEPLOYMENT CHECKLIST

### Pre-deployment:
- [ ] Test semua fitur
- [ ] Verify database
- [ ] Check API endpoints
- [ ] Test di multiple devices
- [ ] Fix all bugs

### Deployment:
- [ ] Update IP di code
- [ ] Build release APK
- [ ] Test production database
- [ ] Deploy backend to server
- [ ] Test in production

---

## 📱 KEYBOARD SHORTCUTS

### Android Studio:
```
Shift+F10       = Run app
Ctrl+F9         = Make project
Ctrl+Shift+F10  = Run current file
Alt+Shift+F10   = Run...
Ctrl+Alt+L      = Reformat code
Ctrl+/          = Comment line
Ctrl+Shift+/    = Block comment
Alt+Enter       = Quick fix
```

---

## 💡 TIPS & TRICKS

### Faster Development:
1. Use Hot Reload (Ctrl+F10)
2. Keep test_api_lengkap.html open
3. Monitor logcat untuk errors
4. Use HeidiSQL untuk verify database

### Debugging:
1. Check logcat first
2. Test API independently
3. Verify database data
4. Check network connectivity

### Best Practices:
1. Always test API before Android
2. Commit setelah fitur selesai
3. Test di real device
4. Backup database regularly

---

## 📞 QUICK HELP

### File Issue:
```
1. Cek SOLUSI_ERROR_API.md
2. Cek FIX_LOGIN_ERROR.md
3. Test API via test_api_lengkap.html
4. Check logcat
```

### Need Reference:
```
1. README.md - Overview
2. QUICK_START_FITUR_BARU.md - Quick start
3. FITUR_LENGKAP_PANDUAN.md - Complete guide
4. FINAL_SUMMARY_COMPLETE.md - Summary
```

---

## ⭐ MUST REMEMBER

### Important IPs:
```
Localhost: http://localhost/tix_id_api/
Your PC: http://192.168.1.2/tix_id_api/  (ganti sesuai IP Anda!)
Device: http://192.168.1.8 (IP device Anda)
```

### Important Ports:
```
Apache: 80
MySQL: 3306
Android Emulator: 10.0.2.2
```

### Important Passwords:
```
MySQL root: (kosong)
Admin: password123
User: password123
Hash: $2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi
```

---

## 🎉 DONE!

**Semua command & tips ada di sini!**

**Save this file untuk referensi cepat! 📌**

