@echo off
echo ========================================
echo    UPDATE DATABASE WITH ALL MOVIES
echo ========================================
echo.

REM Define paths
set LARAGON_MYSQL="C:\laragon\bin\mysql\mysql-8.0.30-winx64\bin\mysql.exe"
set DB_NAME=tix_id
set SQL_FILE=database.sql

echo [1/3] Connecting to MySQL...
echo.

REM Check if MySQL exists
if not exist %LARAGON_MYSQL% (
    echo ERROR: MySQL not found at %LARAGON_MYSQL%
    echo Please check your Laragon installation path.
    pause
    exit /b 1
)

echo [2/3] Dropping and recreating database...
%LARAGON_MYSQL% -u root -e "DROP DATABASE IF EXISTS %DB_NAME%; CREATE DATABASE %DB_NAME%;"
if errorlevel 1 (
    echo ERROR: Failed to recreate database
    pause
    exit /b 1
)

echo [3/3] Importing database with all movies...
%LARAGON_MYSQL% -u root %DB_NAME% < %SQL_FILE%
if errorlevel 1 (
    echo ERROR: Failed to import database
    pause
    exit /b 1
)

echo.
echo ========================================
echo    DATABASE UPDATED SUCCESSFULLY!
echo ========================================
echo.
echo Movies added:
echo.
echo NOW SHOWING (7 films):
echo   1. Spider-Man: No Way Home
echo   2. The Lion King
echo   3. Harry Potter
echo   4. WALL-E
echo   5. The Good Dinosaur
echo   6. Stranger Things
echo   7. Deadpool
echo.
echo COMING SOON (4 films):
echo   1. Zootopia
echo   2. Star Wars
echo   3. Mulan
echo   4. The Last of Us
echo.
echo Total: 11 films
echo.
echo Next steps:
echo 1. Run the Android app
echo 2. Check "Sedang Tayang" - should have 7 films
echo 3. Check "Segera Hadir" - should have 4 films
echo.
pause

