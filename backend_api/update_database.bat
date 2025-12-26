@echo off
echo ========================================
echo   UPDATE DATABASE TIX-ID dengan ADMIN
echo ========================================
echo.

REM Cek apakah MySQL running
echo [1/4] Cek MySQL Service...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe">NUL
if "%ERRORLEVEL%"=="0" (
    echo      ✓ MySQL sudah berjalan
) else (
    echo      ✗ MySQL belum berjalan!
    echo      Silakan start Laragon terlebih dahulu
    pause
    exit
)

echo.
echo [2/4] Import database...
echo      Lokasi: %~dp0database.sql

REM Path MySQL di Laragon (sesuaikan jika berbeda)
set MYSQL_PATH=C:\laragon\bin\mysql\mysql-8.0.30-winx64\bin\mysql.exe

REM Cek apakah file mysql.exe ada
if not exist "%MYSQL_PATH%" (
    echo      ✗ MySQL tidak ditemukan di: %MYSQL_PATH%
    echo      Silakan sesuaikan path di script ini
    pause
    exit
)

REM Import database
"%MYSQL_PATH%" -u root -p < "%~dp0database.sql"

if %ERRORLEVEL% EQU 0 (
    echo      ✓ Database berhasil diimport
) else (
    echo      ✗ Gagal import database
    pause
    exit
)

echo.
echo [3/4] Verifikasi database...
echo      Cek tabel users dan movies...

"%MYSQL_PATH%" -u root -e "USE tix_id; SHOW TABLES;" > temp_tables.txt
findstr /C:"users" temp_tables.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo      ✓ Tabel users OK
) else (
    echo      ✗ Tabel users tidak ditemukan
)

findstr /C:"movies" temp_tables.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo      ✓ Tabel movies OK
) else (
    echo      ✗ Tabel movies tidak ditemukan
)
del temp_tables.txt

echo.
echo [4/4] Cek akun admin...
"%MYSQL_PATH%" -u root -e "USE tix_id; SELECT name, email, role FROM users WHERE role='admin';"

echo.
echo ========================================
echo   DATABASE SIAP DIGUNAKAN!
echo ========================================
echo.
echo Login Admin:
echo   Email    : admin@tixid.com
echo   Password : password123
echo.
pause

