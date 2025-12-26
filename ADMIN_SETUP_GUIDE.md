# Panduan Setup Admin TIX-ID

## 🎯 Fitur Admin yang Telah Dibuat

Admin dapat:
- ✅ Login dengan akun admin (role: admin)
- ✅ Mengelola film (CRUD):
  - Menambah film baru
  - Mengedit film
  - Menghapus film
- ✅ Membedakan film "Sedang Tayang" dan "Segera Datang"
- ✅ Menginput: Judul, Poster URL, Sinopsis, Link YouTube, Genre, Durasi, Rating, Tanggal Rilis
- ✅ Logout

---

## 📦 Database Setup

### 1. Import Database Baru
```bash
# Di phpMyAdmin atau MySQL console
# Import file: backend_api/database.sql
```

Database baru sudah include:
- Tabel `users` dengan kolom `role` (user/admin)
- Tabel `movies` untuk menyimpan data film
- Default admin account:
  - Email: `admin@tixid.com`
  - Password: `password123`
  - Role: admin

### 2. Atau Update Database Manual
Jika sudah punya database, jalankan query ini:

```sql
-- Tambah kolom role ke tabel users
ALTER TABLE users ADD COLUMN role ENUM('user', 'admin') DEFAULT 'user' AFTER phone;

-- Update user existing menjadi admin (ganti email sesuai kebutuhan)
UPDATE users SET role = 'admin' WHERE email = 'admin@tixid.com';

-- Buat tabel movies
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

-- Index untuk performa
CREATE INDEX idx_movie_status ON movies(status);
CREATE INDEX idx_movie_title ON movies(title);
```

---

## 🔧 Backend API Files

File baru yang ditambahkan:
1. **`admin_movies.php`** - API untuk admin mengelola film (CRUD)
2. **`movies.php`** - API untuk user mengambil daftar film
3. **Update `login.php`** - Mengembalikan role user

Pastikan semua file ada di: `C:\laragon\www\tix_id_api\`

---

## 📱 Android Files yang Ditambahkan

### Models:
- `Movie.kt` - Model data film
- `MovieRequest.kt` - Request untuk API film
- Update `User.kt` - Tambah field role

### Activities:
- `AdminDashboardActivity.kt` - Dashboard admin dengan tabs
- `AddEditMovieActivity.kt` - Form tambah/edit film

### Fragments:
- `AdminMoviesFragment.kt` - Fragment untuk list film di admin

### Adapters:
- `AdminMovieAdapter.kt` - Adapter untuk RecyclerView film
- `AdminViewPagerAdapter.kt` - Adapter untuk ViewPager2

### Layouts:
- `activity_admin_dashboard.xml` - Layout dashboard admin
- `activity_add_edit_movie.xml` - Layout form film
- `fragment_admin_movies.xml` - Layout fragment list
- `item_admin_movie.xml` - Layout item film di admin

### API Service:
- Update `ApiService.kt` - Tambah endpoint film
- Update `UserPreferences.kt` - Tambah support role

---

## 🚀 Cara Menggunakan

### 1. Login sebagai Admin
```
Email: admin@tixid.com
Password: password123
```

Setelah login, jika role = admin, akan langsung masuk ke **AdminDashboardActivity**.

### 2. Menambah Film Baru
1. Klik tombol **FAB (+)** di kanan bawah
2. Isi form:
   - Judul Film (required)
   - URL Poster (required) - Link gambar poster
   - Sinopsis (required)
   - Link YouTube Trailer (optional)
   - Genre (optional)
   - Durasi (optional) - Format: "148 Menit"
   - Rating (optional) - 0.0 - 10.0
   - Tanggal Rilis (optional) - Format: YYYY-MM-DD
   - Status: Sedang Tayang / Segera Datang
3. Klik **Simpan Film**

### 3. Mengedit Film
1. Klik film di list
2. Edit data yang ingin diubah
3. Klik **Simpan Film**

### 4. Menghapus Film
1. Klik film yang ingin dihapus
2. Klik tombol **Hapus Film**
3. Konfirmasi penghapusan

### 5. Logout
Klik icon power di header, konfirmasi logout.

---

## 🔐 Login User Biasa

User biasa tetap akan masuk ke **HomeActivity** seperti biasa.
Nanti film-film yang ditambahkan admin akan muncul di:
- Halaman "Sedang Tayang" (status: now_showing)
- Halaman "Segera Datang" (status: coming_soon)

---

## 🧪 Testing

### Test API Admin (via Postman/Browser):

1. **Login Admin**
```
POST: http://192.168.1.2/tix_id_api/login.php
Body: {
  "email": "admin@tixid.com",
  "password": "password123"
}
```

2. **Get Movies (Admin)**
```
POST: http://192.168.1.2/tix_id_api/admin_movies.php
Body: {
  "admin_email": "admin@tixid.com",
  "status": "now_showing"
}
```

3. **Add Movie**
```
POST: http://192.168.1.2/tix_id_api/admin_movies.php
Body: {
  "admin_email": "admin@tixid.com",
  "title": "Judul Film",
  "poster_url": "https://example.com/poster.jpg",
  "synopsis": "Sinopsis film...",
  "youtube_url": "https://youtube.com/watch?v=xxx",
  "genre": "Action",
  "duration": "120 Menit",
  "rating": 8.5,
  "release_date": "2025-12-25",
  "status": "now_showing"
}
```

4. **Update Movie**
```
PUT: http://192.168.1.2/tix_id_api/admin_movies.php
Body: {
  "admin_email": "admin@tixid.com",
  "id": 1,
  "title": "Judul Baru"
}
```

5. **Delete Movie**
```
DELETE: http://192.168.1.2/tix_id_api/admin_movies.php
Body: {
  "admin_email": "admin@tixid.com",
  "id": 1
}
```

---

## 🎨 Customize

### Menambah Admin Baru
Jalankan query SQL:
```sql
UPDATE users SET role = 'admin' WHERE email = 'email@example.com';
```

### Mengubah Warna Admin Dashboard
Edit file: `app/src/main/res/values/colors.xml`
```xml
<color name="purple_500">#7700FF</color>  <!-- Warna utama -->
```

---

## 🐛 Troubleshooting

### Error: "Unauthorized access"
- Pastikan email admin benar
- Cek di database apakah role = 'admin'

### Error: "Connection failed"
- Pastikan Laragon running
- Cek IP address di `ApiConfig.kt` sudah benar
- Test API di browser dulu

### Film tidak muncul di User
- Cek status film sudah sesuai (now_showing/coming_soon)
- Refresh aplikasi user

---

## 📝 Catatan Penting

1. **Poster URL**: Gunakan link langsung ke gambar (jpg/png). Contoh:
   - ✅ `https://image.tmdb.org/t/p/w500/poster.jpg`
   - ❌ `https://website.com/movie-page`

2. **YouTube URL**: Gunakan link video YouTube. Contoh:
   - ✅ `https://www.youtube.com/watch?v=abc123`
   - ✅ `https://youtu.be/abc123`

3. **Format Tanggal**: Gunakan format `YYYY-MM-DD`
   - ✅ `2025-12-25`
   - ❌ `25-12-2025`

4. **Rating**: Angka desimal 0.0 - 10.0
   - ✅ `8.5`
   - ❌ `85`

---

## 🎉 Selesai!

Sekarang Anda punya dashboard admin lengkap untuk mengelola film di aplikasi TIX-ID!

Admin dapat menambah, edit, hapus film dengan mudah, dan film akan otomatis muncul di aplikasi user. 🎬🍿

