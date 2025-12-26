@echo off
echo ========================================
echo   COPY API FILES TO LARAGON
echo ========================================
echo.

set SOURCE=C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api
set DEST=C:\laragon\www\tix_id_api

echo Source: %SOURCE%
echo Destination: %DEST%
echo.

REM Check if source folder exists
if not exist "%SOURCE%" (
    echo [ERROR] Source folder not found!
    pause
    exit
)

REM Create destination folder if not exists
if not exist "%DEST%" (
    echo Creating destination folder...
    mkdir "%DEST%"
)

echo Copying files...
echo.

REM Copy all files
copy /Y "%SOURCE%\*.php" "%DEST%\"
copy /Y "%SOURCE%\*.sql" "%DEST%\"
copy /Y "%SOURCE%\*.html" "%DEST%\"
copy /Y "%SOURCE%\*.md" "%DEST%\"
copy /Y "%SOURCE%\*.bat" "%DEST%\"

echo.
echo ========================================
echo   FILES COPIED SUCCESSFULLY!
echo ========================================
echo.
echo Files copied to: %DEST%
echo.
echo IMPORTANT: Fix user data!
echo Open browser: http://localhost/tix_id_api/fix_users.php
echo.
echo Next steps:
echo 1. Open: http://localhost/tix_id_api/fix_users.php (FIX USER DATA)
echo 2. Then test: http://localhost/tix_id_api/test_api_lengkap.html
echo 3. Login should work!
echo.
pause

