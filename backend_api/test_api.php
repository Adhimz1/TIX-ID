<?php
// Test file untuk memastikan API berjalan dengan baik

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

$tests = [];

// Test 1: PHP Version
$tests['php_version'] = [
    'status' => version_compare(PHP_VERSION, '7.0.0', '>=') ? 'OK' : 'FAIL',
    'message' => 'PHP Version: ' . PHP_VERSION
];

// Test 2: Database Connection
try {
    $conn = new mysqli('localhost', 'root', '', 'tix_id');
    if ($conn->connect_error) {
        $tests['database'] = [
            'status' => 'FAIL',
            'message' => 'Connection failed: ' . $conn->connect_error
        ];
    } else {
        $tests['database'] = [
            'status' => 'OK',
            'message' => 'Database connected successfully'
        ];

        // Test 3: Check if users table exists
        $result = $conn->query("SHOW TABLES LIKE 'users'");
        if ($result->num_rows > 0) {
            $tests['users_table'] = [
                'status' => 'OK',
                'message' => 'Users table exists'
            ];

            // Test 4: Count users
            $result = $conn->query("SELECT COUNT(*) as count FROM users");
            $row = $result->fetch_assoc();
            $tests['users_count'] = [
                'status' => 'OK',
                'message' => 'Total users: ' . $row['count']
            ];
        } else {
            $tests['users_table'] = [
                'status' => 'FAIL',
                'message' => 'Users table does not exist. Please run database.sql'
            ];
        }

        $conn->close();
    }
} catch (Exception $e) {
    $tests['database'] = [
        'status' => 'FAIL',
        'message' => 'Exception: ' . $e->getMessage()
    ];
}

// Test 5: Check if required files exist
$requiredFiles = ['config.php', 'register.php', 'login.php'];
$missingFiles = [];
foreach ($requiredFiles as $file) {
    if (!file_exists($file)) {
        $missingFiles[] = $file;
    }
}

if (empty($missingFiles)) {
    $tests['api_files'] = [
        'status' => 'OK',
        'message' => 'All API files exist'
    ];
} else {
    $tests['api_files'] = [
        'status' => 'FAIL',
        'message' => 'Missing files: ' . implode(', ', $missingFiles)
    ];
}

// Calculate overall status
$allOk = true;
foreach ($tests as $test) {
    if ($test['status'] !== 'OK') {
        $allOk = false;
        break;
    }
}

// Send response
echo json_encode([
    'api_status' => $allOk ? 'READY' : 'NOT READY',
    'timestamp' => date('Y-m-d H:i:s'),
    'tests' => $tests
], JSON_PRETTY_PRINT);
?>

