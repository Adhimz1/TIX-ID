<?php
require_once 'config.php';

// Get request parameters
$status = isset($_GET['status']) ? $_GET['status'] : null;

$conn = getDBConnection();

if ($status) {
    $stmt = $conn->prepare("SELECT * FROM movies WHERE status = ? ORDER BY release_date DESC, created_at DESC");
    $stmt->bind_param("s", $status);
} else {
    $stmt = $conn->prepare("SELECT * FROM movies ORDER BY release_date DESC, created_at DESC");
}

$stmt->execute();
$result = $stmt->get_result();
$movies = [];

while ($row = $result->fetch_assoc()) {
    $movies[] = $row;
}

$stmt->close();
$conn->close();

sendResponse(true, 'Movies retrieved successfully', ['movies' => $movies]);
?>

