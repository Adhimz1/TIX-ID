# ⚡ INSTRUKSI COPY FILE MANUAL

## ❌ MASALAH ANDA:

Dari screenshot: `fix_users.php` error → **File belum di-copy ke Laragon!**

---

## ✅ SOLUSI (Copy Manual - 100% Berhasil):

### **Langkah 1: Buka Folder Source**

Buka folder ini di File Explorer:
```
C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api\
```

### **Langkah 2: Select Semua File**

Di folder `backend_api`, tekan **Ctrl+A** untuk select semua file.

Pastikan file ini terselect:
- ✅ admin_movies.php
- ✅ config.php  
- ✅ database.sql
- ✅ **fix_users.php** ← PENTING!
- ✅ fix_user_data.sql
- ✅ index.html
- ✅ login.php
- ✅ movies.php
- ✅ register.php
- ✅ test_api.php
- ✅ **test_api_lengkap.html** ← PENTING!

### **Langkah 3: Copy File**

Tekan **Ctrl+C** untuk copy.

### **Langkah 4: Buka Folder Tujuan**

Buka folder Laragon:
```
C:\laragon\www\tix_id_api\
```

**Jika folder `tix_id_api` belum ada:**
- Klik kanan di `www` → New → Folder
- Nama: `tix_id_api`

### **Langkah 5: Paste File**

Di folder `C:\laragon\www\tix_id_api\`, tekan **Ctrl+V** untuk paste.

Jika ada dialog "Replace files", klik **Replace** atau **Yes to All**.

### **Langkah 6: Verifikasi**

Cek folder `C:\laragon\www\tix_id_api\` harus ada minimal file ini:
- ✅ config.php
- ✅ login.php
- ✅ movies.php
- ✅ admin_movies.php
- ✅ **fix_users.php**
- ✅ **test_api_lengkap.html**

---

## 🧪 TEST SETELAH COPY:

### **Test 1: Fix Users**

Buka browser:
```
http://localhost/tix_id_api/fix_users.php
```

**Harus muncul:** Halaman HTML dengan tombol fix user (bukan error JSON!)

### **Test 2: Test API Lengkap**

Buka:
```
http://localhost/tix_id_api/test_api_lengkap.html
```

Klik semua tombol test, harus ✓ OK semua!

---

## 🎯 CHECKLIST:

- [ ] Buka folder: `backend_api`
- [ ] Select all (Ctrl+A)
- [ ] Copy (Ctrl+C)
- [ ] Buka folder: `C:\laragon\www\tix_id_api\`
- [ ] Paste (Ctrl+V)
- [ ] Replace jika ditanya
- [ ] Cek file `fix_users.php` ada
- [ ] Test: `http://localhost/tix_id_api/fix_users.php`
- [ ] Harus muncul halaman HTML (bukan JSON error)

---

## 🚀 SETELAH FILE TER-COPY:

1. Buka: `http://localhost/tix_id_api/fix_users.php`
2. Klik tombol untuk fix user data
3. Test login di: `http://localhost/tix_id_api/test_api_lengkap.html`
4. Semua harus ✓ OK
5. Build & Run Android
6. Login: `admin@tixid.com` / `password123`
7. **SELESAI!** ✅

---

**PENTING:** Jangan skip langkah copy! File harus di folder `C:\laragon\www\tix_id_api\` baru bisa diakses via browser!

