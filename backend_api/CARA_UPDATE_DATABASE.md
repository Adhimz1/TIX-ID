# 📊 Cara Update Database - LENGKAP

## ⚡ Cara TERCEPAT (Otomatis)

### **Opsi 1: Pakai Script BAT**
1. Buka Laragon, pastikan MySQL running
2. Double-click file: `backend_api/update_database.bat`
3. Masukkan password MySQL (biasanya kosong, langsung Enter)
4. Selesai! ✅

---

## 🖱️ Cara MUDAH (Manual via phpMyAdmin)

### **Opsi 2: Import via phpMyAdmin**

1. **Buka phpMyAdmin**
   - Buka browser
   - Ketik: `http://localhost/phpmyadmin`
   - Login (username: root, password: biasanya kosong)

2. **Drop Database Lama (Opsional)**
   - Klik database `tix_id` di sidebar kiri
   - Tab "Operations"
   - Scroll ke bawah, klik "Drop the database"
   - Konfirmasi

3. **Import Database Baru**
   - Klik tab "Import" di atas
   - Klik "Choose File"
   - Pilih: `C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\database.sql`
   - Scroll ke bawah, klik "Go"
   - Tunggu sampai selesai

4. **Verifikasi**
   - Klik database `tix_id` di sidebar
   - Pastikan ada 2 tabel:
     - ✅ `users`
     - ✅ `movies`
   - Klik tabel `users`
   - Tab "Browse"
   - Pastikan ada user dengan email: `admin@tixid.com` dan role: `admin`

---

## 💻 Cara MANUAL (Query SQL)

### **Opsi 3: Jalankan Query Manual**

Jika sudah punya database `tix_id` dan tidak mau hapus data yang ada:

#### **Step 1: Tambah Kolom `role` ke Tabel `users`**
```sql
-- Buka SQL tab di phpMyAdmin
-- Pilih database tix_id
-- Jalankan query ini:

ALTER TABLE users ADD COLUMN role ENUM('user', 'admin') DEFAULT 'user' AFTER phone;
```

#### **Step 2: Buat Tabel `movies`**
```sql
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
```

#### **Step 3: Tambah Admin Account**
```sql
-- Cek apakah email admin@tixid.com sudah ada
SELECT * FROM users WHERE email = 'admin@tixid.com';

-- Jika belum ada, insert:
INSERT INTO users (name, email, password, phone, role) 
VALUES ('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin');

-- Jika sudah ada, update role-nya:
UPDATE users SET role = 'admin' WHERE email = 'admin@tixid.com';
```

#### **Step 4: Update User Existing jadi Admin (Opsional)**
```sql
-- Misalnya kamu mau user dengan email tertentu jadi admin:
UPDATE users SET role = 'admin' WHERE email = 'emailkamu@example.com';
```

#### **Step 5: Tambah Index**
```sql
CREATE INDEX IF NOT EXISTS idx_movie_status ON movies(status);
CREATE INDEX IF NOT EXISTS idx_movie_title ON movies(title);
```

#### **Step 6: Insert Sample Movies (Opsional)**
```sql
INSERT INTO movies (title, poster_url, synopsis, youtube_url, rating, duration, genre, status, release_date) VALUES
('Spider-Man: No Way Home', 'https://image.tmdb.org/t/p/w500/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg', 'Peter Parker menghadapi konsekuensi setelah identitasnya terbongkar. Ia mencari bantuan Doctor Strange, namun hal ini membuka pintu multiverse yang berbahaya.', 'https://www.youtube.com/watch?v=JfVOs4VSpmA', 8.5, '148 Menit', 'Action, Adventure', 'now_showing', '2021-12-15'),
('The Lion King', 'https://image.tmdb.org/t/p/w500/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg', 'Simba, seekor singa muda yang ditakdirkan untuk menjadi raja, harus menghadapi masa lalunya dan mengambil tempatnya yang sebenarnya di Circle of Life.', 'https://www.youtube.com/watch?v=4sj1MT05lAA', 8.0, '118 Menit', 'Animation, Adventure', 'now_showing', '2019-07-19');
```

---

## ✅ Verifikasi Database Sudah OK

### **Cek via phpMyAdmin:**
1. Buka database `tix_id`
2. Klik tabel `users` → Browse
   - Pastikan ada kolom `role`
   - Pastikan ada user dengan role = `admin`
3. Klik tabel `movies` → Browse
   - Pastikan tabel ada (boleh kosong atau ada data)

### **Cek via SQL Query:**
```sql
-- Cek struktur tabel users
DESCRIBE users;
-- Harus ada kolom: id, name, email, password, phone, role, created_at, updated_at

-- Cek admin account
SELECT id, name, email, role FROM users WHERE role = 'admin';
-- Harus muncul minimal 1 admin

-- Cek tabel movies
SHOW TABLES LIKE 'movies';
-- Harus muncul: movies

-- Cek isi movies (boleh kosong)
SELECT COUNT(*) FROM movies;
```

---

## 🎯 Test Login Admin

### **Via Browser (Test API):**
1. Buka: `http://localhost/tix_id_api/login.php`
2. Method: POST
3. Body (JSON):
```json
{
  "email": "admin@tixid.com",
  "password": "password123"
}
```

4. Response harus ada: `"role": "admin"`

### **Via Android App:**
1. Buka aplikasi TIX-ID
2. Login dengan:
   - Email: `admin@tixid.com`
   - Password: `password123`
3. Harus masuk ke halaman **Admin Dashboard** (bukan Home)
4. Bisa tambah film baru

---

## 🚨 Troubleshooting

### **Error: "Table 'tix_id.users' doesn't exist"**
- Database belum diimport
- Jalankan ulang import database.sql

### **Error: "Unknown column 'role' in 'field list'"**
- Kolom role belum ditambahkan
- Jalankan: `ALTER TABLE users ADD COLUMN role ENUM('user', 'admin') DEFAULT 'user' AFTER phone;`

### **Error: "Email atau password salah"**
- Admin account belum dibuat
- Jalankan: `INSERT INTO users (name, email, password, phone, role) VALUES ('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin');`

### **Login berhasil tapi masuk ke Home (bukan Admin Dashboard)**
- Role tidak 'admin'
- Jalankan: `UPDATE users SET role = 'admin' WHERE email = 'admin@tixid.com';`

### **Error: "Table 'tix_id.movies' doesn't exist"**
- Tabel movies belum dibuat
- Jalankan query CREATE TABLE movies di atas

---

## 📝 Catatan Penting

1. **Password Default:** `password123` (hash: `$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi`)
2. **Role:** Hanya ada 2 nilai: `user` atau `admin`
3. **Admin Email:** Bisa diganti, tapi pastikan role = 'admin'
4. **Backup:** Selalu backup database sebelum import/drop

---

## ✨ Selesai!

Database sekarang sudah siap dengan:
- ✅ Kolom `role` di tabel `users`
- ✅ Tabel `movies` untuk menyimpan data film
- ✅ Admin account siap pakai
- ✅ Index untuk performa

Langkah selanjutnya: Build & Run aplikasi Android! 🚀

