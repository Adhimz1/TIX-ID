# PANDUAN LENGKAP: Fix Login & Register TIX-ID

## ❌ MASALAH YANG TERJADI:
1. Saat daftar, data tidak masuk ke database
2. Saat login, selalu muncul "email dan password salah"
3. Kemungkinan API PHP belum dibuat atau ada error

## ✅ SOLUSI LENGKAP:

### LANGKAH 1: Setup Database di Laragon

1. **Buka Laragon** (pastikan Apache dan MySQL sudah running - lampu hijau)

2. **Buka HeidiSQL atau phpMyAdmin:**
   - Klik kanan icon Laragon di system tray
   - Pilih "MySQL" -> "HeidiSQL"
   
   Atau buka browser: `http://localhost/phpmyadmin`

3. **Buat database:**
   ```sql
   CREATE DATABASE IF NOT EXISTS tix_id;
   USE tix_id;
   
   CREATE TABLE IF NOT EXISTS users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       phone VARCHAR(20) DEFAULT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );
   
   CREATE INDEX idx_email ON users(email);
   ```

4. **Insert user test (opsional):**
   ```sql
   INSERT INTO users (name, email, password, phone) VALUES 
   ('Test User', 'test@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890');
   ```
   Password: `password123`

### LANGKAH 2: Copy File API ke Laragon

1. **Copy folder `backend_api`** yang sudah saya buat ke:
   ```
   C:\laragon\www\tix_id_api\
   ```

2. **Struktur folder harus seperti ini:**
   ```
   C:\laragon\www\tix_id_api\
   ├── config.php
   ├── register.php
   ├── login.php
   ├── database.sql
   └── README.md
   ```

3. **Atau manual:**
   - Buka File Explorer
   - Pergi ke `C:\laragon\www\`
   - Buat folder baru: `tix_id_api`
   - Copy semua file dari folder `backend_api` ke dalam folder `tix_id_api`

### LANGKAH 3: Test API

**A. Test dari Browser PC:**
1. Buka browser, ketik: `http://localhost/tix_id_api/register.php`
2. Harusnya muncul error JSON (ini normal, karena kita belum kirim data)
3. Kalau muncul "404 Not Found", berarti file belum di copy dengan benar

**B. Test dari Browser HP:**
1. Pastikan HP dan PC di WiFi yang sama (atau PC pakai LAN tapi 1 network)
2. Buka browser di HP, ketik: `http://192.168.1.2/tix_id_api/register.php`
3. Harusnya muncul error JSON

**C. Cek IP PC (jika perlu update):**
1. Buka CMD di PC
2. Ketik: `ipconfig`
3. Cari "IPv4 Address" di bagian "Ethernet adapter" atau "Wireless LAN adapter"
4. Contoh: `192.168.1.2`
5. Jika IP berbeda, update di `RetrofitClient.kt`

### LANGKAH 4: Update Kode Android (SUDAH SAYA LAKUKAN)

Saya sudah update:
- ✅ `MasukActivity.kt` - Tambah logging dan better error handling
- ✅ `DaftarActivity.kt` - Tambah logging dan better error handling
- ✅ File backend PHP sudah dibuat

### LANGKAH 5: Build & Test

1. **Build aplikasi:**
   - Di Android Studio, klik menu "Build" -> "Rebuild Project"
   - Tunggu sampai selesai

2. **Run di HP:**
   - Klik tombol Run (▶) di Android Studio
   - Pilih device HP Xiaomi M2101K6G

3. **Test Register:**
   - Buka aplikasi TIX-ID
   - Klik "Daftar"
   - Isi form:
     - Nama: `Ahmad Test`
     - Email: `ahmad@test.com`
     - Password: `123456`
     - Konfirmasi Password: `123456`
   - Klik "Daftar"
   - Lihat Logcat di Android Studio untuk detail error (jika ada)

4. **Cek di Database:**
   - Buka HeidiSQL
   - Klik database `tix_id`
   - Klik tabel `users`
   - Klik tab "Data"
   - Harusnya ada data user baru

5. **Test Login:**
   - Login dengan email dan password yang baru didaftarkan
   - Harusnya berhasil masuk ke Home

### LANGKAH 6: Debugging (Jika Masih Error)

**A. Cek Logcat di Android Studio:**
1. Buka tab "Logcat" di bawah
2. Filter: `DaftarActivity` atau `MasukActivity`
3. Cari baris log yang berisi error

**B. Cek Error Log PHP:**
1. Buka: `C:\laragon\bin\apache\apache-2.4.x\logs\error.log`
2. Scroll ke bawah, cari error terbaru

**C. Test API Manual dengan Postman atau cURL:**

Register:
```bash
curl -X POST http://192.168.1.2/tix_id_api/register.php -H "Content-Type: application/json" -d "{\"name\":\"Test\",\"email\":\"test2@test.com\",\"password\":\"123456\",\"phone\":\"081234567890\"}"
```

Login:
```bash
curl -X POST http://192.168.1.2/tix_id_api/login.php -H "Content-Type: application/json" -d "{\"email\":\"test2@test.com\",\"password\":\"123456\"}"
```

### TROUBLESHOOTING UMUM:

**❌ Error: "Connection failed"**
- ✅ Pastikan Laragon running (Apache & MySQL nyala)
- ✅ Cek database `tix_id` sudah dibuat
- ✅ Cek file config.php (user: root, password: kosong)

**❌ Error: "Failed to connect to 192.168.1.2"**
- ✅ HP dan PC harus 1 network
- ✅ Matikan firewall Windows sementara
- ✅ Test ping dari HP: buka CMD di HP (via Termux), ketik: `ping 192.168.1.2`

**❌ Error: "Email atau password salah" (padahal baru daftar)**
- ✅ Cek di database apakah user benar-benar terdaftar
- ✅ Cek password di-hash dengan benar (harusnya mulai dengan `$2y$`)
- ✅ Lihat Logcat untuk detail error

**❌ Layar putih saat daftar/login**
- ✅ Cek Logcat untuk stack trace error
- ✅ Kemungkinan crash karena response API tidak sesuai

### CARA MEMASTIKAN DATA MASUK DATABASE:

1. **Setelah klik Daftar di app:**
   - Buka HeidiSQL
   - Refresh tabel users (F5)
   - Lihat apakah ada baris baru

2. **Cek struktur response API:**
   - Response harus format JSON seperti ini:
   ```json
   {
     "success": true,
     "message": "Pendaftaran berhasil! Silakan login",
     "data": {
       "id": 1,
       "name": "Ahmad Test",
       "email": "ahmad@test.com"
     }
   }
   ```

### FITUR YANG SUDAH SAYA TAMBAHKAN:

✅ Logging detail di DaftarActivity dan MasukActivity
✅ Error handling yang lebih baik
✅ Toast message yang lebih informatif
✅ Backend API PHP lengkap (register.php, login.php, config.php)
✅ Database schema SQL
✅ Password hashing dengan bcrypt
✅ Validasi email format
✅ Check email duplicate saat register
✅ Session persistence (auto-login jika sudah pernah login)

---

## 📝 CHECKLIST SEBELUM TEST:

- [ ] Laragon running (Apache & MySQL hijau)
- [ ] Database `tix_id` sudah dibuat
- [ ] Tabel `users` sudah ada
- [ ] Folder `C:\laragon\www\tix_id_api\` sudah ada dan berisi 4 file PHP
- [ ] IP di RetrofitClient.kt sesuai dengan IP PC (`192.168.1.2`)
- [ ] HP dan PC di network yang sama
- [ ] Build project berhasil tanpa error
- [ ] App terinstall di HP

Jika semua sudah ✅, harusnya register dan login berfungsi normal!

Jika masih error, kirim screenshot Logcat dan saya akan bantu fix.

