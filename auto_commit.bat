@echo off
chcp 65001 >nul
echo.
echo ╔════════════════════════════════════════════════════════╗
echo ║       TIX ID - FULL GIT COMMIT AUTOMATION             ║
echo ╚════════════════════════════════════════════════════════╝
echo.

REM Change to project directory
cd /d "%~dp0"

echo [STEP 1] Checking Git Repository...
git status >nul 2>&1
if errorlevel 1 (
    echo ❌ Error: Not a git repository!
    echo    Run: git init
    pause
    exit /b 1
)
echo ✅ Git repository found
echo.

echo [STEP 2] Removing Sensitive Files from Tracking...
git rm --cached backend_api\config.php 2>nul
git rm --cached -r backend_api\uploads 2>nul
git rm --cached *.sql 2>nul
git rm --cached *.db 2>nul
echo ✅ Sensitive files removed
echo.

echo [STEP 3] Adding Security Files...
git add .gitignore
git add backend_api\config.example.php
git add backend_api\README.md
git add DEPLOYMENT_GUIDE.md
git add SECURITY_SETUP_COMPLETE.md
echo ✅ Security files added
echo.

echo [STEP 4] Adding Application Files...
git add app\src\
git add app\build.gradle.kts
git add build.gradle.kts
git add settings.gradle.kts
git add gradle\
echo ✅ Application files added
echo.

echo [STEP 5] Adding Backend PHP Files (except config.php)...
git add backend_api\*.php
git rm --cached backend_api\config.php 2>nul
echo ✅ Backend files added
echo.

echo [STEP 6] Adding Documentation...
git add *.md
git add README.md
echo ✅ Documentation added
echo.

echo ╔════════════════════════════════════════════════════════╗
echo ║              FILES READY TO COMMIT                     ║
echo ╚════════════════════════════════════════════════════════╝
echo.
git status --short
echo.

echo ╔════════════════════════════════════════════════════════╗
echo ║                 COMMIT OPTIONS                         ║
echo ╚════════════════════════════════════════════════════════╝
echo.
echo Choose commit type:
echo   1. Security Setup (First time)
echo   2. Bug Fix
echo   3. New Feature
echo   4. Update Documentation
echo   5. Custom Message
echo   0. Cancel
echo.

set /p choice="Enter choice (0-5): "

if "%choice%"=="0" (
    echo.
    echo ❌ Commit cancelled
    pause
    exit /b 0
)

if "%choice%"=="1" (
    set "message=Security: Setup .gitignore dan deployment guide^

- Add comprehensive .gitignore untuk protect sensitive files
- Add config.example.php template
- Add DEPLOYMENT_GUIDE.md untuk setup instructions
- Add backend API documentation
- Remove config.php, uploads/, dan database files dari git tracking
- Add security checklist dan troubleshooting guide"
)

if "%choice%"=="2" (
    set "message=Fix: Perbaiki tampilan film dan force close issues^

- Fix missing posters di admin panel
- Fix force close pada lihat semua
- Fix missing films di sedang tayang dan segera hadir
- Update upload image API handling
- Improve error handling"
)

if "%choice%"=="3" (
    set "message=Feature: Add new feature^

- Describe your feature here"
)

if "%choice%"=="4" (
    set "message=Docs: Update documentation^

- Update README and guides"
)

if "%choice%"=="5" (
    set /p "message=Enter commit message: "
)

echo.
echo ╔════════════════════════════════════════════════════════╗
echo ║                   COMMITTING...                        ║
echo ╚════════════════════════════════════════════════════════╝
echo.

git commit -m "%message%"

if errorlevel 1 (
    echo.
    echo ❌ Commit failed!
    pause
    exit /b 1
)

echo.
echo ✅ Commit successful!
echo.

echo ╔════════════════════════════════════════════════════════╗
echo ║                     PUSH TO REMOTE?                    ║
echo ╚════════════════════════════════════════════════════════╝
echo.
set /p push="Push to remote repository? (y/n): "

if /i "%push%"=="y" (
    echo.
    echo [PUSHING] Uploading to remote...
    git push origin master

    if errorlevel 1 (
        echo.
        echo ❌ Push failed! Try:
        echo    git push origin main
        echo    or
        echo    git push origin [your-branch-name]
        pause
        exit /b 1
    )

    echo.
    echo ✅ Push successful!
    echo.
    echo ╔════════════════════════════════════════════════════════╗
    echo ║              🎉 ALL DONE! 🎉                          ║
    echo ╚════════════════════════════════════════════════════════╝
    echo.
    echo Your code is now safely backed up on GitHub!
    echo Database credentials and sensitive files are protected.
    echo.
) else (
    echo.
    echo ℹ️  Commit saved locally. Push later with:
    echo    git push origin master
    echo.
)

pause

