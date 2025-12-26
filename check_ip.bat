@echo off
echo ========================================
echo TIX-ID - IP Address Checker
echo ========================================
echo.

echo Mengecek IP Address PC Anda...
echo.

for /f "tokens=2 delims=:" %%a in ('ipconfig ^| findstr /c:"IPv4"') do (
    set IP=%%a
    set IP=!IP:~1!
    echo [FOUND] IPv4 Address: !IP!
)

echo.
echo ========================================
echo Instruksi:
echo ========================================
echo.
echo 1. Catat IP address di atas
echo 2. Buka Android Studio
echo 3. Buka file: RetrofitClient.kt
echo 4. Cari baris: private const val BASE_URL
echo 5. Ganti IP dengan IP di atas
echo.
echo Contoh:
echo private const val BASE_URL = "http://[IP_ANDA]/tix_id_api/"
echo.
echo ========================================
echo Test URL:
echo ========================================
echo.

for /f "tokens=2 delims=:" %%a in ('ipconfig ^| findstr /c:"IPv4"') do (
    set IP=%%a
    set IP=!IP:~1!
    echo Browser PC: http://localhost/tix_id_api/
    echo Browser HP: http://!IP!/tix_id_api/
    echo API Test:   http://!IP!/tix_id_api/test_api.php
)

echo.
echo ========================================
echo.
pause

