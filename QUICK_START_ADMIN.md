# 🚀 Quick Start - Admin Panel TIX-ID

## Langkah Cepat (5 Menit)

### 1️⃣ Update Database
```bash
# Buka phpMyAdmin
# Import: backend_api/database.sql
# ATAU jalankan query manual (lihat ADMIN_SETUP_GUIDE.md)
```

### 2️⃣ Copy File API
Pastikan file ini ada di `C:\laragon\www\tix_id_api\`:
- ✅ `admin_movies.php` (baru)
- ✅ `movies.php` (baru)
- ✅ `login.php` (updated)
- ✅ `config.php`
- ✅ `register.php`

### 3️⃣ Build & Run Android
```bash
# Di Android Studio:
# 1. Sync Gradle
# 2. Build > Rebuild Project
# 3. Run di device
```

### 4️⃣ Login Admin
```
Email: admin@tixid.com
Password: password123
```

## ✨ Selesai!

Sekarang:
- **Admin** masuk → AdminDashboardActivity (kelola film)
- **User biasa** masuk → HomeActivity (lihat film)

---

## 📋 Checklist

- [ ] Database diupdate (tabel movies, kolom role)
- [ ] File API dicopy ke laragon/www/tix_id_api
- [ ] Laragon running (Apache + MySQL)
- [ ] Project di-build tanpa error
- [ ] Login admin berhasil
- [ ] Bisa tambah film baru
- [ ] Film muncul di user app

---

## 🆘 Bantuan

Lihat file lengkap: **ADMIN_SETUP_GUIDE.md**

IP Address saat ini: `192.168.1.2`
(Ganti di `ApiConfig.kt` jika IP berubah)

