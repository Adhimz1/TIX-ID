<?php
require_once 'config.php';

// Get request method
$method = $_SERVER['REQUEST_METHOD'];

// Get POST/PUT data
$input = file_get_contents('php://input');
$data = json_decode($input, true);

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
    case 'POST':
        if (isset($data['action'])) {
            switch ($data['action']) {
                case 'list':
                    listAdmins($conn);
                    break;
                case 'add':
                    addAdmin($conn, $data);
                    break;
                case 'update':
                    updateAdmin($conn, $data);
                    break;
                case 'delete':
                    deleteAdmin($conn, $data);
                    break;
                default:
                    sendResponse(false, 'Invalid action');
            }
        } else {
            listAdmins($conn);
        }
        break;
    default:
        sendResponse(false, 'Invalid request method');
}

function listAdmins($conn) {
    $stmt = $conn->prepare("SELECT id, name, email, phone, created_at FROM users WHERE role = 'admin' ORDER BY created_at DESC");
    $stmt->execute();
    $result = $stmt->get_result();

    $admins = [];
    while ($row = $result->fetch_assoc()) {
        $admins[] = $row;
    }

    $stmt->close();
    $conn->close();

    sendResponse(true, 'Admins retrieved successfully', ['admins' => $admins]);
}

function addAdmin($conn, $data) {
    // Validate required fields
    if (empty($data['name']) || empty($data['email']) || empty($data['password'])) {
        $conn->close();
        sendResponse(false, 'Name, email, and password are required');
    }

    // Check if email already exists
    $stmt = $conn->prepare("SELECT id FROM users WHERE email = ?");
    $stmt->bind_param("s", $data['email']);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Email already exists');
    }
    $stmt->close();

    // Hash password
    $hashedPassword = password_hash($data['password'], PASSWORD_BCRYPT);
    $phone = isset($data['phone']) ? $data['phone'] : null;

    // Insert new admin
    $stmt = $conn->prepare("INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, 'admin')");
    $stmt->bind_param("ssss", $data['name'], $data['email'], $hashedPassword, $phone);

    if ($stmt->execute()) {
        $newAdminId = $stmt->insert_id;
        $stmt->close();
        $conn->close();

        sendResponse(true, 'Admin added successfully', ['id' => $newAdminId]);
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to add admin');
    }
}

function updateAdmin($conn, $data) {
    if (empty($data['id'])) {
        $conn->close();
        sendResponse(false, 'Admin ID is required');
    }

    // Build update query dynamically
    $updates = [];
    $types = '';
    $values = [];

    if (isset($data['name'])) {
        $updates[] = 'name = ?';
        $types .= 's';
        $values[] = $data['name'];
    }

    if (isset($data['email'])) {
        $updates[] = 'email = ?';
        $types .= 's';
        $values[] = $data['email'];
    }

    if (isset($data['phone'])) {
        $updates[] = 'phone = ?';
        $types .= 's';
        $values[] = $data['phone'];
    }

    if (isset($data['password'])) {
        $updates[] = 'password = ?';
        $types .= 's';
        $values[] = password_hash($data['password'], PASSWORD_BCRYPT);
    }

    if (empty($updates)) {
        $conn->close();
        sendResponse(false, 'No fields to update');
    }

    $updateStr = implode(', ', $updates);
    $sql = "UPDATE users SET $updateStr WHERE id = ? AND role = 'admin'";
    $types .= 'i';
    $values[] = $data['id'];

    $stmt = $conn->prepare($sql);
    $stmt->bind_param($types, ...$values);

    if ($stmt->execute()) {
        $affected = $stmt->affected_rows;
        $stmt->close();
        $conn->close();

        if ($affected > 0) {
            sendResponse(true, 'Admin updated successfully');
        } else {
            sendResponse(false, 'Admin not found or no changes made');
        }
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to update admin');
    }
}

function deleteAdmin($conn, $data) {
    if (empty($data['id'])) {
        $conn->close();
        sendResponse(false, 'Admin ID is required');
    }

    // Check if it's the last admin
    $stmt = $conn->prepare("SELECT COUNT(*) as count FROM users WHERE role = 'admin'");
    $stmt->execute();
    $result = $stmt->get_result();
    $row = $result->fetch_assoc();
    $stmt->close();

    if ($row['count'] <= 1) {
        $conn->close();
        sendResponse(false, 'Cannot delete the last admin');
    }

    // Delete admin
    $stmt = $conn->prepare("DELETE FROM users WHERE id = ? AND role = 'admin'");
    $stmt->bind_param("i", $data['id']);

    if ($stmt->execute()) {
        $affected = $stmt->affected_rows;
        $stmt->close();
        $conn->close();

        if ($affected > 0) {
            sendResponse(true, 'Admin deleted successfully');
        } else {
            sendResponse(false, 'Admin not found');
        }
    } else {
        $stmt->close();
        $conn->close();
        sendResponse(false, 'Failed to delete admin');
    }
}
?>

