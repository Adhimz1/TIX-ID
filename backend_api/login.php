<?php
require_once 'config.php';

// Get POST data
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// Log request for debugging
error_log("Login request: " . $input);

if (!$data) {
    sendResponse(false, 'Invalid JSON data');
}

// Validate input
if (empty($data['email']) || empty($data['password'])) {
    sendResponse(false, 'Email dan password harus diisi');
}

$email = trim($data['email']);
$password = $data['password'];

// Get database connection
$conn = getDBConnection();

// Find user by email
$stmt = $conn->prepare("SELECT id, name, email, password, phone, role, created_at FROM users WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    $stmt->close();
    $conn->close();
    error_log("Login failed: Email not found - $email");
    sendResponse(false, 'Email atau password salah');
}

$user = $result->fetch_assoc();
$stmt->close();
$conn->close();

// Verify password
if (password_verify($password, $user['password'])) {
    // Remove password from response
    unset($user['password']);

    error_log("Login successful: $email");
    sendResponse(true, 'Login berhasil', $user);
} else {
    error_log("Login failed: Wrong password for $email");
    sendResponse(false, 'Email atau password salah');
}
?>

