# 🌐 CARA UPDATE IP ADDRESS DI APLIKASI

## 📍 Kenapa Harus Update IP?

Ketika aplikasi Android berjalan di **HP fisik**, dia tidak bisa menggunakan `localhost` untuk akses backend API. Harus menggunakan **IP address PC** tempat Laragon berjalan.

---

## 🔍 Langkah 1: Cek IP Address PC Anda

### Windows:

1. Tekan **Windows + R**
2. Ketik: `cmd` → Enter
3. Ketik: `ipconfig`
4. Cari baris **"IPv4 Address"**

**Contoh hasil:**
```
Wireless LAN adapter Wi-Fi:
   IPv4 Address. . . . . . . . . . . : 192.168.1.5
```

**IP Anda adalah:** `192.168.1.5`

---

## ✏️ Langkah 2: Update di Android Studio

### File yang Harus Diubah:

**File:** `app/src/main/java/com/pab/tixid/api/RetrofitClient.kt`

**Lokasi:** Line 31

**Sebelum:**
```kotlin
private const val BASE_URL = "http://192.168.1.2/tix_id_api/"
```

**Sesudah (ganti dengan IP Anda):**
```kotlin
private const val BASE_URL = "http://192.168.1.5/tix_id_api/"  // Ganti 192.168.1.5 dengan IP PC Anda
```

### Cara Edit di Android Studio:

1. Di Android Studio, tekan **Ctrl + Shift + N**
2. Ketik: `RetrofitClient`
3. Enter untuk membuka file
4. Cari baris `private const val BASE_URL`
5. Ganti IP address dengan IP PC Anda
6. Tekan **Ctrl + S** untuk save

---

## 🧪 Langkah 3: Test Koneksi dari HP

**Sebelum rebuild app, test dulu koneksinya:**

1. Buka **browser di HP** (Chrome/Firefox)
2. Ketik di address bar: `http://192.168.1.5/tix_id_api/test_api.php`
   (Ganti 192.168.1.5 dengan IP PC Anda)
3. Harusnya muncul halaman JSON dengan status "READY"

**Jika muncul "Cannot connect" atau "Timeout":**
- ✅ Pastikan HP dan PC di WiFi yang sama
- ✅ Pastikan Laragon running (Apache hijau)
- ✅ Matikan firewall Windows sementara
- ✅ Coba ping dari HP ke PC

---

## 🔨 Langkah 4: Rebuild & Run

Setelah IP sudah benar:

1. Di Android Studio: **Build** → **Rebuild Project**
2. Tunggu sampai selesai (tidak ada error)
3. Hubungkan HP via USB
4. Klik **Run** (▶️)
5. Pilih device HP Anda
6. Test daftar & login!

---

## 📊 Skenario Berbeda:

### Skenario 1: PC Pakai WiFi
```
IP PC: 192.168.1.5 (lihat di Wi-Fi adapter)
HP: Harus pakai WiFi yang sama
BASE_URL: "http://192.168.1.5/tix_id_api/"
```

### Skenario 2: PC Pakai LAN (Ethernet)
```
IP PC: 192.168.1.10 (lihat di Ethernet adapter)
HP: Pakai WiFi yang terhubung ke router yang sama
BASE_URL: "http://192.168.1.10/tix_id_api/"
```

### Skenario 3: PC Pakai Hotspot HP
```
IP PC: 192.168.43.x (biasanya hotspot range)
HP: Yang jadi hotspot
BASE_URL: "http://192.168.43.1/tix_id_api/" (cek dengan ipconfig)
```

### Skenario 4: Pakai Emulator (bukan HP fisik)
```
Tidak perlu IP PC, pakai IP khusus emulator
BASE_URL: "http://10.0.2.2/tix_id_api/"
```

---

## 🐛 Troubleshooting:

### Error: "Failed to connect to 192.168.1.x"

**Solusi 1: Cek Firewall**
```
1. Control Panel → Windows Defender Firewall
2. Turn off (sementara untuk testing)
3. Test lagi di app
```

**Solusi 2: Allow Port 80**
```
1. Windows Firewall → Advanced Settings
2. Inbound Rules → New Rule
3. Port → TCP → 80
4. Allow the connection
```

**Solusi 3: Test Ping**
```
Di HP (pakai Termux atau Ping Tools app):
ping 192.168.1.5

Harusnya reply, bukan timeout
```

### Error: "Connection timeout"

**Solusi:**
- ✅ Pastikan Laragon running
- ✅ Test di browser PC: http://localhost/tix_id_api/
- ✅ Test di browser HP: http://192.168.1.x/tix_id_api/
- ✅ Pastikan HP tidak pakai VPN

### Error: "UnknownHostException"

**Solusi:**
- ✅ IP address salah, cek lagi dengan ipconfig
- ✅ HP tidak tersambung ke jaringan
- ✅ Typo di BASE_URL (cek slash, http://, dll)

---

## ✅ Checklist Update IP:

- [ ] Cek IP PC dengan `ipconfig`
- [ ] Catat IP address (contoh: 192.168.1.5)
- [ ] Buka RetrofitClient.kt di Android Studio
- [ ] Update BASE_URL dengan IP yang benar
- [ ] Save file (Ctrl + S)
- [ ] Test koneksi dari browser HP
- [ ] Rebuild project di Android Studio
- [ ] Run app di HP
- [ ] Test daftar & login

---

## 📝 Catatan Penting:

1. **IP address bisa berubah** setiap kali PC restart atau reconnect WiFi
2. Jika tiba-tiba app tidak bisa connect, **cek IP lagi dengan ipconfig**
3. Untuk production, lebih baik pakai **domain** atau **IP static**
4. Jangan lupa **save file** setelah edit!
5. Harus **rebuild** setiap kali ganti IP (tidak cukup hanya Run)

---

## 🎯 Quick Command Reference:

```bash
# Cek IP PC
ipconfig

# Test koneksi dari CMD
ping 192.168.1.5

# Test API dari browser
http://192.168.1.5/tix_id_api/test_api.php

# Cek port 80 terbuka
netstat -an | findstr :80
```

---

**File Updated:** RetrofitClient.kt  
**Current IP:** 192.168.1.2 (ganti sesuai IP PC Anda)  
**Status:** Ready to update! ✅

---

Jika sudah update IP dan test berhasil, lanjut ke langkah berikutnya! 🚀

