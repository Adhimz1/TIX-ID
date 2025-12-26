# PowerShell Script - Copy API Files
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   COPY API FILES KE LARAGON" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$source = "C:\Users\ahmad\Documents\ADHIM\Kotlin\TIX-ID\backend_api"
$dest = "C:\laragon\www\tix_id_api"

# Check if source exists
if (-not (Test-Path $source)) {
    Write-Host "ERROR: Source folder tidak ditemukan!" -ForegroundColor Red
    Write-Host "Path: $source" -ForegroundColor Yellow
    pause
    exit
}

# Create destination if not exists
if (-not (Test-Path $dest)) {
    Write-Host "Membuat folder tujuan..." -ForegroundColor Yellow
    New-Item -ItemType Directory -Path $dest -Force | Out-Null
    Write-Host "✓ Folder dibuat: $dest" -ForegroundColor Green
}

Write-Host "[1/3] Copying PHP files..." -ForegroundColor Yellow
Copy-Item "$source\*.php" -Destination $dest -Force
Write-Host "      ✓ PHP files copied" -ForegroundColor Green

Write-Host "[2/3] Copying other files..." -ForegroundColor Yellow
Copy-Item "$source\*.sql" -Destination $dest -Force -ErrorAction SilentlyContinue
Copy-Item "$source\*.html" -Destination $dest -Force -ErrorAction SilentlyContinue
Copy-Item "$source\*.md" -Destination $dest -Force -ErrorAction SilentlyContinue
Write-Host "      ✓ Other files copied" -ForegroundColor Green

Write-Host "[3/3] Creating uploads folder..." -ForegroundColor Yellow
$uploadsPath = Join-Path $dest "uploads"
if (-not (Test-Path $uploadsPath)) {
    New-Item -ItemType Directory -Path $uploadsPath -Force | Out-Null
    Write-Host "      ✓ Uploads folder created" -ForegroundColor Green
} else {
    Write-Host "      ✓ Uploads folder already exists" -ForegroundColor Green
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "   FILES BERHASIL DI-COPY!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Files copied to: $dest" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Test API: http://localhost/tix_id_api/test_api_lengkap.html"
Write-Host "2. Build Android app"
Write-Host "3. Login & test features"
Write-Host ""
pause

