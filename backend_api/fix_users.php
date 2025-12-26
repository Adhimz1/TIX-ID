<?php
/**
 * Script untuk fix user data dan generate password hash
 * Jalankan di browser: http://localhost/tix_id_api/fix_users.php
 */

require_once 'config.php';

// Password yang akan digunakan
$password = 'password123';
$password_hash = password_hash($password, PASSWORD_BCRYPT);

echo "<!DOCTYPE html>";
echo "<html><head>";
echo "<title>Fix User Data</title>";
echo "<style>
    body { font-family: Arial; padding: 20px; background: #f5f5f5; }
    .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
    h1 { color: #667eea; }
    .success { color: #4caf50; background: #e8f5e9; padding: 10px; border-radius: 5px; margin: 10px 0; }
    .error { color: #f44336; background: #ffebee; padding: 10px; border-radius: 5px; margin: 10px 0; }
    .info { color: #2196f3; background: #e3f2fd; padding: 10px; border-radius: 5px; margin: 10px 0; }
    pre { background: #f5f5f5; padding: 15px; border-radius: 5px; overflow-x: auto; }
    table { width: 100%; border-collapse: collapse; margin: 20px 0; }
    th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
    th { background: #667eea; color: white; }
    tr:hover { background: #f5f5f5; }
</style>";
echo "</head><body>";
echo "<div class='container'>";
echo "<h1>🔧 Fix User Data - TIX-ID</h1>";

try {
    $conn = getDBConnection();

    echo "<div class='info'>";
    echo "<strong>Password yang akan digunakan:</strong> $password<br>";
    echo "<strong>Password Hash:</strong> <code>$password_hash</code>";
    echo "</div>";

    // Hapus user lama
    echo "<h2>Step 1: Menghapus user lama...</h2>";
    $stmt = $conn->prepare("DELETE FROM users WHERE email IN (?, ?)");
    $email1 = 'test@example.com';
    $email2 = 'admin@tixid.com';
    $stmt->bind_param("ss", $email1, $email2);

    if ($stmt->execute()) {
        $deleted = $stmt->affected_rows;
        echo "<div class='success'>✓ User lama dihapus ($deleted user)</div>";
    }
    $stmt->close();

    // Insert user baru
    echo "<h2>Step 2: Menambahkan user baru...</h2>";

    $stmt = $conn->prepare("INSERT INTO users (name, email, password, phone, role) VALUES (?, ?, ?, ?, ?)");

    // User 1: Test User
    $name = 'Test User';
    $email = 'test@example.com';
    $phone = '081234567890';
    $role = 'user';
    $stmt->bind_param("sssss", $name, $email, $password_hash, $phone, $role);

    if ($stmt->execute()) {
        echo "<div class='success'>✓ User 'Test User' berhasil ditambahkan</div>";
    } else {
        echo "<div class='error'>✗ Gagal menambahkan Test User: " . $stmt->error . "</div>";
    }

    // User 2: Admin
    $name = 'Admin TIX ID';
    $email = 'admin@tixid.com';
    $phone = '081234567890';
    $role = 'admin';
    $stmt->bind_param("sssss", $name, $email, $password_hash, $phone, $role);

    if ($stmt->execute()) {
        echo "<div class='success'>✓ Admin 'Admin TIX ID' berhasil ditambahkan</div>";
    } else {
        echo "<div class='error'>✗ Gagal menambahkan Admin: " . $stmt->error . "</div>";
    }

    $stmt->close();

    // Tampilkan semua user
    echo "<h2>Step 3: Verifikasi User</h2>";
    $result = $conn->query("SELECT id, name, email, phone, role, created_at FROM users ORDER BY id");

    if ($result->num_rows > 0) {
        echo "<table>";
        echo "<tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Role</th><th>Created</th></tr>";

        while ($row = $result->fetch_assoc()) {
            $roleClass = $row['role'] == 'admin' ? 'style="background:#fff3cd; font-weight:bold;"' : '';
            echo "<tr $roleClass>";
            echo "<td>{$row['id']}</td>";
            echo "<td>{$row['name']}</td>";
            echo "<td>{$row['email']}</td>";
            echo "<td>{$row['phone']}</td>";
            echo "<td>{$row['role']}</td>";
            echo "<td>{$row['created_at']}</td>";
            echo "</tr>";
        }

        echo "</table>";
    }

    $conn->close();

    echo "<h2>✅ Selesai!</h2>";
    echo "<div class='success'>";
    echo "<strong>User siap digunakan:</strong><br><br>";
    echo "<strong>Admin Login:</strong><br>";
    echo "Email: admin@tixid.com<br>";
    echo "Password: password123<br><br>";
    echo "<strong>User Login:</strong><br>";
    echo "Email: test@example.com<br>";
    echo "Password: password123";
    echo "</div>";

    echo "<div class='info'>";
    echo "<strong>Langkah selanjutnya:</strong><br>";
    echo "1. Test login di: <a href='test_api_lengkap.html'>test_api_lengkap.html</a><br>";
    echo "2. Atau buka aplikasi Android dan login";
    echo "</div>";

} catch (Exception $e) {
    echo "<div class='error'>";
    echo "<strong>Error:</strong> " . $e->getMessage();
    echo "</div>";
}

echo "</div>";
echo "</body></html>";
?>

