@echo off
echo ========================================
echo   TIX ID - Git Commit Helper
echo ========================================
echo.

REM Remove config.php from git if exists
echo [1/5] Removing config.php from git tracking...
git rm --cached backend_api\config.php 2>NUL
if errorlevel 1 (
    echo    - config.php not in git ^(OK^)
) else (
    echo    - config.php removed from git tracking
)
echo.

REM Remove uploads folder from git if exists
echo [2/5] Removing uploads folder from git tracking...
git rm --cached -r backend_api\uploads 2>NUL
if errorlevel 1 (
    echo    - uploads folder not in git ^(OK^)
) else (
    echo    - uploads folder removed from git tracking
)
echo.

REM Check git status
echo [3/5] Checking git status...
git status
echo.

REM Add safe files
echo [4/5] Adding safe files to staging...
git add .gitignore
git add backend_api\config.example.php
git add backend_api\README.md
git add DEPLOYMENT_GUIDE.md
git add app\src\
echo    - Safe files added
echo.

REM Show what will be committed
echo [5/5] Files ready to commit:
git status --short
echo.

echo ========================================
echo   Ready to Commit!
echo ========================================
echo.
echo Next steps:
echo   1. Review the files above
echo   2. Run: git commit -m "Your commit message"
echo   3. Run: git push origin master
echo.
echo Example commit message:
echo   git commit -m "Fix: Perbaiki tampilan film dan setup deployment guide"
echo.
pause

