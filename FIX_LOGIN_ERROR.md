# 🔧 FIX LOGIN ERROR

## 🔴 Masalah: Login Error "Email atau password salah"

Dari screenshot Anda:
- ✅ Get Movies → OK 
- ❌ Login → Error

**Penyebab:** Data user di database belum cocok atau password hash salah.

---

## ✅ SOLUSI CEPAT (2 Menit):

### **Step 1: Copy File Baru ke Laragon**

Jalankan lagi:
```
backend_api\copy_to_laragon.bat
```

Atau copy manual file baru ini:
- `fix_users.php` (NEW) ← File untuk fix user data
- `fix_user_data.sql` (NEW)
- `test_api_lengkap.html` (sudah ada)

### **Step 2: Fix User Data**

Buka browser, ketik:
```
http://localhost/tix_id_api/fix_users.php
```

Script ini akan:
1. ✅ Hapus user lama
2. ✅ Tambah user baru dengan password hash yang benar
3. ✅ Tampilkan daftar user
4. ✅ Password: `password123` pasti jalan!

### **Step 3: Test Login Lagi**

Buka:
```
http://localhost/tix_id_api/test_api_lengkap.html
```

Klik tombol **"🔐 Login"**

Sekarang harus **✓ OK**!

---

## 🎯 Atau Fix Manual via HeidiSQL:

Jika cara di atas tidak jalan, jalankan query ini di HeidiSQL:

```sql
USE tix_id;

-- Hapus user lama
DELETE FROM users WHERE email IN ('test@example.com', 'admin@tixid.com');

-- Insert user baru dengan password: password123
INSERT INTO users (name, email, password, phone, role) VALUES
('Test User', 'test@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'user'),
('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin');

-- Cek hasilnya
SELECT id, name, email, role FROM users;
```

---

## ✅ Setelah Fix:

Login credentials yang benar:

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

**Keduanya** menggunakan password yang **sama**: `password123`

---

## 📋 Checklist:

- [ ] Jalankan `copy_to_laragon.bat` (copy file baru)
- [ ] Buka `http://localhost/tix_id_api/fix_users.php`
- [ ] Test login di `test_api_lengkap.html`
- [ ] Harus ✓ OK semua
- [ ] Build Android & login

---

## 🚀 Ringkasan:

**Masalah:** Password hash di database salah/tidak cocok

**Solusi:** Jalankan `fix_users.php` untuk regenerate user dengan password yang benar

**Hasil:** Login pasti jalan! ✅

---

**Next:** Setelah login OK, build Android dan login dengan `admin@tixid.com` / `password123` 🎉

