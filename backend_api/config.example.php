<?php
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: POST, GET, OPTIONS");
header("Access-Control-Allow-Headers: Content-Type");
header("Content-Type: application/json; charset=UTF-8");

// ========================================
// DATABASE CONFIGURATION TEMPLATE
// ========================================
//
// INSTRUKSI SETUP:
// 1. Copy file ini menjadi 'config.php'
// 2. Isi kredensial database Anda di bawah
// 3. JANGAN commit file config.php ke Git!
//
// ========================================

// Database configuration
define('DB_HOST', 'localhost');           // Host database (biasanya localhost)
define('DB_USER', 'your_username');       // Username database Anda
define('DB_PASS', 'your_password');       // Password database Anda
define('DB_NAME', 'your_database_name');  // Nama database (contoh: tix_id)

// Create connection
function getDBConnection() {
    $conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

    if ($conn->connect_error) {
        die(json_encode([
            'success' => false,
            'message' => 'Connection failed: ' . $conn->connect_error
        ]));
    }

    return $conn;
}

// Helper function to send JSON response
function sendResponse($success, $message, $data = null) {
    $response = [
        'success' => $success,
        'message' => $message
    ];

    if ($data !== null) {
        $response['data'] = $data;
    }

    echo json_encode($response);
    exit();
}
?>

