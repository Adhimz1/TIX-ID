@echo off
echo ====================================
echo   CEK DATABASE TIX-ID
echo ====================================
echo.

REM Path MySQL di Laragon
set MYSQL_PATH=C:\laragon\bin\mysql\mysql-8.0.30-winx64\bin\mysql.exe

REM Cek apakah MySQL running
tasklist /FI "IMAGENAME eq mysqld.exe" 2>NUL | find /I /N "mysqld.exe">NUL
if NOT "%ERRORLEVEL%"=="0" (
    echo [✗] MySQL belum berjalan!
    echo     Silakan start Laragon dulu
    echo.
    pause
    exit
)
echo [✓] MySQL running

REM Cek database tix_id
echo.
echo Cek database tix_id...
"%MYSQL_PATH%" -u root -e "SHOW DATABASES LIKE 'tix_id';" > temp_db.txt
findstr /C:"tix_id" temp_db.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Database tix_id ada
) else (
    echo [✗] Database tix_id TIDAK ada!
    echo     Silakan import database.sql dulu
    del temp_db.txt
    pause
    exit
)
del temp_db.txt

REM Cek tabel users
echo.
echo Cek tabel users...
"%MYSQL_PATH%" -u root -e "USE tix_id; SHOW TABLES LIKE 'users';" > temp_users.txt
findstr /C:"users" temp_users.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Tabel users ada
) else (
    echo [✗] Tabel users TIDAK ada!
    del temp_users.txt
    pause
    exit
)
del temp_users.txt

REM Cek kolom role di users
echo.
echo Cek kolom role di tabel users...
"%MYSQL_PATH%" -u root -e "USE tix_id; DESCRIBE users;" > temp_role.txt
findstr /C:"role" temp_role.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Kolom role ada
) else (
    echo [✗] Kolom role TIDAK ada!
    echo     Database belum diupdate!
    del temp_role.txt
    pause
    exit
)
del temp_role.txt

REM Cek tabel movies
echo.
echo Cek tabel movies...
"%MYSQL_PATH%" -u root -e "USE tix_id; SHOW TABLES LIKE 'movies';" > temp_movies.txt
findstr /C:"movies" temp_movies.txt >nul
if %ERRORLEVEL% EQU 0 (
    echo [✓] Tabel movies ada
) else (
    echo [✗] Tabel movies TIDAK ada!
    echo     Database belum diupdate!
    del temp_movies.txt
    pause
    exit
)
del temp_movies.txt

REM Cek admin account
echo.
echo Cek admin account...
echo.
"%MYSQL_PATH%" -u root -e "USE tix_id; SELECT id, name, email, role FROM users WHERE role='admin';"
echo.

REM Cek jumlah movies
echo Cek jumlah film...
"%MYSQL_PATH%" -u root -e "USE tix_id; SELECT COUNT(*) as 'Total Film' FROM movies;"
echo.

echo ====================================
echo   DATABASE SUDAH OK! ✓
echo ====================================
echo.
echo Login Admin:
echo   Email    : admin@tixid.com
echo   Password : password123
echo.
echo Silakan build dan run aplikasi Android
echo.
pause

