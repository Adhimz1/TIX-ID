# ⚠️ SOLUSI ERROR API

## 🔴 Masalah Yang Anda Alami:

1. ❌ `login.php` → Error: "Invalid JSON data"
2. ❌ `movies.php` → Error: "Not Found" (404)

## ✅ SOLUSI LENGKAP

### **Masalah Utama: File API Belum Di-Copy!**

File API ada di project folder tapi **BELUM di-copy** ke folder Laragon!

---

## 📂 Step 1: Copy File API ke Laragon

### **Cara Manual:**

1. Buka folder: `C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\`
2. **Select semua file** (Ctrl+A):
   - admin_movies.php
   - config.php
   - database.sql
   - login.php
   - movies.php
   - register.php
   - test_api.php
   - index.html
3. **Copy** (Ctrl+C)
4. Buka folder: `C:\laragon\www\tix_id_api\`
5. **Paste** (Ctrl+V)
6. **Replace/Overwrite** jika ada file yang sama

### **Atau Gunakan Script BAT:**

Buat file `copy_api.bat` di folder `backend_api`:

```batch
@echo off
echo Copying API files to Laragon...
xcopy /Y /E "C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\*.*" "C:\laragon\www\tix_id_api\"
echo.
echo Done! API files copied successfully.
pause
```

Double-click file tersebut untuk auto-copy.

---

## 🧪 Step 2: Test API dengan Benar

### **1. Test movies.php**

Setelah file di-copy, buka browser:

```
http://localhost/tix_id_api/movies.php
```

**Harus muncul:**
```json
{
  "success": true,
  "message": "Movies retrieved successfully",
  "data": {
    "movies": [...]
  }
}
```

Jika masih "Not Found" → file belum di-copy!

---

### **2. Test login.php**

**PENTING:** `login.php` harus diakses dengan **POST method**, tidak bisa lewat browser langsung!

#### **Cara A: Pakai Postman (Recommended)**

1. Download Postman: https://www.postman.com/downloads/
2. Install & buka Postman
3. New Request
4. Method: **POST**
5. URL: `http://localhost/tix_id_api/login.php`
6. Tab **Body** → pilih **raw** → pilih **JSON**
7. Isi:
```json
{
  "email": "admin@tixid.com",
  "password": "password123"
}
```
8. Klik **Send**

**Response yang benar:**
```json
{
  "success": true,
  "message": "Login berhasil",
  "data": {
    "id": 2,
    "name": "Admin TIX ID",
    "email": "admin@tixid.com",
    "phone": "081234567890",
    "role": "admin",
    "created_at": "..."
  }
}
```

#### **Cara B: Pakai cURL (Terminal/CMD)**

Buka CMD, jalankan:

```bash
curl -X POST http://localhost/tix_id_api/login.php -H "Content-Type: application/json" -d "{\"email\":\"admin@tixid.com\",\"password\":\"password123\"}"
```

#### **Cara C: Pakai test_api.php yang Sudah Ada**

Buka browser:
```
http://localhost/tix_id_api/test_api.php
```

File ini sudah ada dan bisa langsung test login!

---

## 📋 Checklist File yang Harus Ada di Laragon

Cek folder `C:\laragon\www\tix_id_api\` harus ada:

- ✅ `config.php` - Koneksi database
- ✅ `login.php` - Login API (updated dengan role)
- ✅ `register.php` - Register API
- ✅ `movies.php` - API ambil daftar film (NEW)
- ✅ `admin_movies.php` - API admin kelola film (NEW)
- ✅ `database.sql` - File database
- ✅ `test_api.php` - File test API
- ✅ `index.html` - Landing page (optional)

---

## 🔍 Cara Cek File Sudah Di-Copy

### **Cara 1: Lewat Browser**

Buka:
```
http://localhost/tix_id_api/
```

Harus muncul list file atau index.html.

### **Cara 2: Lewat File Explorer**

1. Buka: `C:\laragon\www\tix_id_api\`
2. Harus ada minimal 7 file di atas
3. Cek tanggal modified file `movies.php` dan `admin_movies.php` harus terbaru (hari ini)

---

## ⚡ QUICK FIX (Paling Cepat)

1. **Copy semua file** dari:
   ```
   C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\
   ```
   
2. **Paste ke**:
   ```
   C:\laragon\www\tix_id_api\
   ```

3. **Test movies.php**:
   ```
   http://localhost/tix_id_api/movies.php
   ```
   Harus muncul JSON data film.

4. **Test login via Postman** atau `test_api.php`:
   ```
   http://localhost/tix_id_api/test_api.php
   ```

5. **Jika semua OK**, buka aplikasi Android dan login dengan:
   ```
   Email: admin@tixid.com
   Password: password123
   ```

---

## 🐛 Troubleshooting

### **movies.php masih Not Found**
→ File belum di-copy atau nama folder salah
→ Pastikan: `C:\laragon\www\tix_id_api\movies.php` ada

### **login.php error "Invalid JSON data"**
→ Anda akses lewat browser (GET method)
→ Harus pakai POST method (Postman/test_api.php)

### **Semua API error "Connection refused"**
→ Laragon belum running
→ Start Laragon dulu

### **Error "Call to undefined function sendResponse()"**
→ File `config.php` belum di-copy
→ Copy ulang semua file

---

## ✅ Setelah Semua OK

Buka aplikasi Android:
1. Login dengan: `admin@tixid.com` / `password123`
2. Harus masuk ke **Admin Dashboard**
3. Bisa tambah/edit/hapus film
4. Film yang ditambah admin akan muncul di aplikasi user

---

## 🎯 Kesimpulan

**Masalahnya sederhana:** File API belum di-copy ke folder Laragon!

**Solusinya:** Copy semua file dari `backend_api` ke `C:\laragon\www\tix_id_api\`

Setelah di-copy, **pasti jalan**! 🚀

