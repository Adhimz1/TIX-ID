# 🚀 QUICK START - TIX-ID Login/Register Fix

## ⚡ Langkah Cepat (5 Menit):

### 1️⃣ Setup Backend (1 menit)
```bash
# Double-click file ini:
setup_api.bat
```

### 2️⃣ Setup Database (2 menit)
1. Buka: http://localhost/phpmyadmin
2. Klik tab "SQL"
3. Copy-paste isi file `backend_api/database.sql`
4. Klik "Go"

### 3️⃣ Test API (1 menit)
Buka: http://localhost/tix_id_api/
- Klik "Check API Status" → harus OK semua ✅
- Klik "Test Register" → harus sukses ✅
- Klik "Test Login" → harus sukses ✅

### 4️⃣ Test di HP (1 menit)
1. Build & Run aplikasi
2. Klik "Daftar"
3. Isi form dan submit
4. Harusnya redirect ke Login dan berhasil login

---

## 🔧 Troubleshooting Cepat:

### ❌ "Connection failed"
```bash
# Cek Laragon:
- Pastikan Apache & MySQL hijau
- Restart Laragon
```

### ❌ "Failed to connect to 192.168.1.2"
```bash
# Update IP di RetrofitClient.kt:
1. Buka CMD, ketik: ipconfig
2. Lihat IPv4 Address
3. Update di RetrofitClient.kt line 13
```

### ❌ "Email atau password salah"
```sql
-- Cek di database:
SELECT * FROM users WHERE email = 'test@example.com';
-- Harusnya ada 1 row
```

---

## 📁 File Locations:

```
C:\laragon\www\tix_id_api\     ← Backend API (copy kesini)
├── config.php                  ← Database config
├── register.php                ← Register endpoint
├── login.php                   ← Login endpoint
├── test_api.php               ← API status checker
└── index.html                  ← Web tester

Database: tix_id               ← Buat di phpMyAdmin
Table: users                   ← Buat via database.sql
```

---

## 🧪 Test URLs:

- API Tester: http://localhost/tix_id_api/
- API Status: http://localhost/tix_id_api/test_api.php
- Register: http://localhost/tix_id_api/register.php
- Login: http://localhost/tix_id_api/login.php

Dari HP (ganti dengan IP PC):
- http://192.168.1.2/tix_id_api/

---

## ✅ Success Criteria:

✔️ Test API Status → semua hijau
✔️ Test Register di web → sukses
✔️ Test Login di web → sukses
✔️ Cek database → ada data user
✔️ Register di app → sukses
✔️ Login di app → masuk ke Home

---

## 📞 Need Help?

1. Cek Logcat: Filter "DaftarActivity" atau "MasukActivity"
2. Cek Error Log: `C:\laragon\bin\apache\apache-x.x.x\logs\error.log`
3. Screenshot error + kirim ke developer

---

## 🎯 What's Changed:

✅ Added detailed logging in MasukActivity & DaftarActivity
✅ Better error messages (shows full error details)
✅ Created PHP backend API (register, login, config)
✅ Created database schema
✅ Created web-based API tester
✅ Added setup script for quick installation

---

**Full Guide:** `PANDUAN_FIX_LOGIN_REGISTER.md`

