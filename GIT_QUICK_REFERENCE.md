# 🚀 Quick Reference - Git Commit Guide

## ⚡ CARA TERCEPAT (Recommended)

### Double-click salah satu:
1. **`auto_commit.bat`** - Full automation dengan pilihan menu
2. **`safe_commit.bat`** - Basic safety check

Pilih `auto_commit.bat` untuk kemudahan maksimal!

---

## 📝 Manual Command (Jika paham Git)

```bash
# 1. Remove sensitive files
git rm --cached backend_api/config.php
git rm --cached -r backend_api/uploads

# 2. Add all files
git add .

# 3. Commit
git commit -m "Security: Setup .gitignore dan deployment guide"

# 4. Push
git push origin master
```

---

## ✅ File Yang AMAN Dicommit

| File/Folder | Status | Keterangan |
|-------------|--------|------------|
| `app/src/**/*.kt` | ✅ AMAN | Source code Android |
| `app/res/**` | ✅ AMAN | Resources (layout, drawable, values) |
| `backend_api/*.php` | ✅ AMAN | Semua PHP API files |
| `backend_api/config.example.php` | ✅ AMAN | Template config |
| `*.gradle*` | ✅ AMAN | Build scripts |
| `*.md` | ✅ AMAN | Documentation |
| `*.bat` | ✅ AMAN | Helper scripts |

---

## ❌ File Yang TIDAK Boleh Dicommit

| File/Folder | Status | Keterangan |
|-------------|--------|------------|
| `backend_api/config.php` | ❌ BAHAYA | Kredensial database |
| `backend_api/uploads/*` | ❌ BAHAYA | File upload user |
| `*.sql` | ❌ BAHAYA | Database backup |
| `*.keystore` | ❌ BAHAYA | Android signing key |
| `.idea/` | ❌ SKIP | IDE settings |
| `build/` | ❌ SKIP | Build output |
| `*.apk`, `*.aab` | ❌ SKIP | Compiled apps |

**Sudah diproteksi oleh `.gitignore`** ✅

---

## 🆘 Troubleshooting Cepat

### Problem: "config.php masih muncul di git status"
```bash
git rm --cached backend_api/config.php
```

### Problem: ".gitignore tidak bekerja"
```bash
git rm -r --cached .
git add .
```

### Problem: "Push rejected"
```bash
git pull origin master --rebase
git push origin master
```

---

## 📋 Checklist Sebelum Commit

- [ ] Jalankan `git status` - tidak ada file sensitif
- [ ] File `config.php` tidak muncul
- [ ] Folder `uploads/` tidak muncul
- [ ] Tidak ada file `*.sql` atau `*.db`
- [ ] Build masih berjalan normal
- [ ] Test app masih berfungsi

---

## 🎯 Good Commit Messages

### Format:
```
Type: Brief description

- Detail 1
- Detail 2
- Detail 3
```

### Types:
- `Security:` - Perubahan keamanan
- `Fix:` - Bug fixes
- `Feature:` - Fitur baru
- `Update:` - Update existing feature
- `Docs:` - Documentation only
- `Refactor:` - Code restructuring

### Contoh:
```bash
git commit -m "Security: Setup .gitignore dan deployment guide

- Add comprehensive .gitignore
- Add config.example.php template
- Add deployment documentation
- Remove sensitive files from tracking"
```

---

## 🔄 Daily Workflow

### Morning (Pull latest):
```bash
git pull origin master
```

### Evening (Push changes):
```bash
auto_commit.bat
# atau
git add .
git commit -m "Your message"
git push origin master
```

---

## 📞 Need Help?

1. **Baca:** `SECURITY_SETUP_COMPLETE.md`
2. **Panduan lengkap:** `DEPLOYMENT_GUIDE.md`
3. **API docs:** `backend_api/README.md`

---

## ✨ TL;DR (Too Long Didn't Read)

**Paling gampang:**
```
Double-click: auto_commit.bat
Pilih option 1
Done! 🎉
```

**Database dan kredensial AMAN!** ✅

