<?php
require_once 'config.php';

// Get POST data
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// Log request for debugging
error_log("Register request: " . $input);

if (!$data) {
    sendResponse(false, 'Invalid JSON data');
}

// Validate input
if (empty($data['name']) || empty($data['email']) || empty($data['password'])) {
    sendResponse(false, 'Nama, email, dan password harus diisi');
}

$name = trim($data['name']);
$email = trim($data['email']);
$password = $data['password'];
$phone = isset($data['phone']) ? trim($data['phone']) : '';

// Validate email format
if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
    sendResponse(false, 'Format email tidak valid');
}

// Validate password length
if (strlen($password) < 6) {
    sendResponse(false, 'Password minimal 6 karakter');
}

// Get database connection
$conn = getDBConnection();

// Check if email already exists
$stmt = $conn->prepare("SELECT id FROM users WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $stmt->close();
    $conn->close();
    sendResponse(false, 'Email sudah terdaftar');
}
$stmt->close();

// Hash password
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// Insert new user
$stmt = $conn->prepare("INSERT INTO users (name, email, password, phone, created_at) VALUES (?, ?, ?, ?, NOW())");
$stmt->bind_param("ssss", $name, $email, $hashedPassword, $phone);

if ($stmt->execute()) {
    error_log("User registered successfully: $email");
    sendResponse(true, 'Pendaftaran berhasil! Silakan login', [
        'id' => $stmt->insert_id,
        'name' => $name,
        'email' => $email
    ]);
} else {
    error_log("Registration failed: " . $stmt->error);
    sendResponse(false, 'Pendaftaran gagal: ' . $stmt->error);
}

$stmt->close();
$conn->close();
?>

