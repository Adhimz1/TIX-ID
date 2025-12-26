<?php
require_once 'config.php';

// Get request method
$method = $_SERVER['REQUEST_METHOD'];

// Get POST/PUT data
$input = file_get_contents('php://input');
$data = json_decode($input, true);

// For GET requests, use query parameters
if ($method === 'GET' && empty($data)) {
    $data = $_GET;
}

// Verify admin
if (empty($data['admin_email'])) {
    sendResponse(false, 'Unauthorized access');
}

$conn = getDBConnection();
$stmt = $conn->prepare("SELECT role FROM users WHERE email = ?");
$stmt->bind_param("s", $data['admin_email']);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows === 0) {
    $stmt->close();
    $conn->close();
    sendResponse(false, 'User not found');
}

$user = $result->fetch_assoc();
$stmt->close();

if ($user['role'] !== 'admin') {
    $conn->close();
    sendResponse(false, 'Unauthorized: Admin access required');
}

// Handle different methods
switch ($method) {
    case 'GET':
        getMovies($conn, $data);
        break;
    case 'POST':
        // Check if it's a request to get movies or add movie
        if (isset($data['id']) || (isset($data['status']) && !isset($data['title']))) {
            getMovies($conn, $data);
        } else {
            addMovie($conn, $data);
        }
        break;
    case 'PUT':
        updateMovie($conn, $data);
        break;
    case 'DELETE':
        deleteMovie($conn, $data);
        break;
    default:
        sendResponse(false, 'Invalid request method');
}

function getMovies($conn, $data) {
    $status = isset($data['status']) ? $data['status'] : null;

    if ($status) {
        $stmt = $conn->prepare("SELECT * FROM movies WHERE status = ? ORDER BY created_at DESC");
        $stmt->bind_param("s", $status);
    } else {
        $stmt = $conn->prepare("SELECT * FROM movies ORDER BY created_at DESC");
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
}

function addMovie($conn, $data) {
    // Validate input
    if (empty($data['title']) || empty($data['poster_url']) || empty($data['synopsis']) || empty($data['status'])) {
        $conn->close();
        sendResponse(false, 'All required fields must be filled');
    }

    $title = trim($data['title']);
    $poster_url = trim($data['poster_url']);
    $synopsis = trim($data['synopsis']);
    $youtube_url = isset($data['youtube_url']) ? trim($data['youtube_url']) : null;
    $rating = isset($data['rating']) ? floatval($data['rating']) : 0.0;
    $duration = isset($data['duration']) ? trim($data['duration']) : null;
    $genre = isset($data['genre']) ? trim($data['genre']) : null;
    $status = trim($data['status']);
    $release_date = isset($data['release_date']) ? trim($data['release_date']) : null;

    $stmt = $conn->prepare("INSERT INTO movies (title, poster_url, synopsis, youtube_url, rating, duration, genre, status, release_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    $stmt->bind_param("ssssdssss", $title, $poster_url, $synopsis, $youtube_url, $rating, $duration, $genre, $status, $release_date);

    if ($stmt->execute()) {
        $movie_id = $stmt->insert_id;
        $stmt->close();
        $conn->close();
        sendResponse(true, 'Movie added successfully', ['movie_id' => $movie_id]);
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to add movie');
    }
}

function updateMovie($conn, $data) {
    // Validate input
    if (empty($data['id'])) {
        $conn->close();
        sendResponse(false, 'Movie ID is required');
    }

    $id = intval($data['id']);
    $updates = [];
    $types = "";
    $values = [];

    if (isset($data['title'])) {
        $updates[] = "title = ?";
        $types .= "s";
        $values[] = trim($data['title']);
    }
    if (isset($data['poster_url'])) {
        $updates[] = "poster_url = ?";
        $types .= "s";
        $values[] = trim($data['poster_url']);
    }
    if (isset($data['synopsis'])) {
        $updates[] = "synopsis = ?";
        $types .= "s";
        $values[] = trim($data['synopsis']);
    }
    if (isset($data['youtube_url'])) {
        $updates[] = "youtube_url = ?";
        $types .= "s";
        $values[] = trim($data['youtube_url']);
    }
    if (isset($data['rating'])) {
        $updates[] = "rating = ?";
        $types .= "d";
        $values[] = floatval($data['rating']);
    }
    if (isset($data['duration'])) {
        $updates[] = "duration = ?";
        $types .= "s";
        $values[] = trim($data['duration']);
    }
    if (isset($data['genre'])) {
        $updates[] = "genre = ?";
        $types .= "s";
        $values[] = trim($data['genre']);
    }
    if (isset($data['status'])) {
        $updates[] = "status = ?";
        $types .= "s";
        $values[] = trim($data['status']);
    }
    if (isset($data['release_date'])) {
        $updates[] = "release_date = ?";
        $types .= "s";
        $values[] = trim($data['release_date']);
    }

    if (empty($updates)) {
        $conn->close();
        sendResponse(false, 'No fields to update');
    }

    $sql = "UPDATE movies SET " . implode(", ", $updates) . " WHERE id = ?";
    $types .= "i";
    $values[] = $id;

    $stmt = $conn->prepare($sql);
    $stmt->bind_param($types, ...$values);

    if ($stmt->execute()) {
        $stmt->close();
        $conn->close();
        sendResponse(true, 'Movie updated successfully');
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to update movie');
    }
}

function deleteMovie($conn, $data) {
    if (empty($data['id'])) {
        $conn->close();
        sendResponse(false, 'Movie ID is required');
    }

    $id = intval($data['id']);

    $stmt = $conn->prepare("DELETE FROM movies WHERE id = ?");
    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        $stmt->close();
        $conn->close();
        sendResponse(true, 'Movie deleted successfully');
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to delete movie');
    }
}
?>

