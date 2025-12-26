<?php
require_once 'config.php';

header('Content-Type: application/json');

// Check if image was uploaded
if (!isset($_FILES['image'])) {
    sendResponse(false, 'No image uploaded');
}

$file = $_FILES['image'];
$uploadDir = 'uploads/';

// Create uploads directory if not exists
if (!file_exists($uploadDir)) {
    mkdir($uploadDir, 0777, true);
}

// Validate file type
$allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
$fileType = mime_content_type($file['tmp_name']);

if (!in_array($fileType, $allowedTypes)) {
    sendResponse(false, 'Invalid file type. Only JPG, PNG, and GIF allowed');
}

// Validate file size (max 5MB)
$maxSize = 5 * 1024 * 1024; // 5MB
if ($file['size'] > $maxSize) {
    sendResponse(false, 'File too large. Maximum size is 5MB');
}

// Generate unique filename
$extension = pathinfo($file['name'], PATHINFO_EXTENSION);
$filename = 'poster_' . time() . '_' . uniqid() . '.' . $extension;
$destination = $uploadDir . $filename;

// Move uploaded file
if (move_uploaded_file($file['tmp_name'], $destination)) {
    // Return full URL
    $protocol = isset($_SERVER['HTTPS']) && $_SERVER['HTTPS'] === 'on' ? 'https' : 'http';
    $host = $_SERVER['HTTP_HOST'];
    $baseUrl = $protocol . '://' . $host . '/tix_id_api/';
    $imageUrl = $baseUrl . $destination;

    sendResponse(true, 'Image uploaded successfully', [
        'url' => $imageUrl,
        'filename' => $filename
    ]);
} else {
    sendResponse(false, 'Failed to upload image');
}
?>

