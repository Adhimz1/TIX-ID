@echo off
echo ========================================
echo TIX-ID API - Quick Setup Script
echo ========================================
echo.

REM Check if Laragon www folder exists
if not exist "C:\laragon\www\" (
    echo [ERROR] Laragon tidak ditemukan!
    echo Pastikan Laragon sudah terinstall di C:\laragon\
    pause
    exit /b 1
)

echo [1/4] Membuat folder tix_id_api...
if not exist "C:\laragon\www\tix_id_api\" (
    mkdir "C:\laragon\www\tix_id_api"
    echo Folder berhasil dibuat!
) else (
    echo Folder sudah ada!
)

echo.
echo [2/4] Copying API files...
copy "%~dp0backend_api\*.php" "C:\laragon\www\tix_id_api\" >nul
copy "%~dp0backend_api\*.sql" "C:\laragon\www\tix_id_api\" >nul
copy "%~dp0backend_api\*.md" "C:\laragon\www\tix_id_api\" >nul
echo Files copied successfully!

echo.
echo [3/4] Checking Laragon status...
tasklist /FI "IMAGENAME eq httpd.exe" 2>NUL | find /I /N "httpd.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo Apache: RUNNING [OK]
) else (
    echo Apache: NOT RUNNING [!]
    echo Silakan start Laragon terlebih dahulu!
)

tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo MySQL: RUNNING [OK]
) else (
    echo MySQL: NOT RUNNING [!]
    echo Silakan start Laragon terlebih dahulu!
)

echo.
echo [4/4] Opening test page...
timeout /t 2 >nul
start http://localhost/tix_id_api/test_api.php

echo.
echo ========================================
echo Setup completed!
echo ========================================
echo.
echo Next steps:
echo 1. Buka HeidiSQL atau phpMyAdmin
echo 2. Jalankan query dari file: database.sql
echo 3. Test API di: http://192.168.1.2/tix_id_api/test_api.php
echo 4. Build dan run aplikasi Android
echo.
echo Panduan lengkap: PANDUAN_FIX_LOGIN_REGISTER.md
echo.
pause

