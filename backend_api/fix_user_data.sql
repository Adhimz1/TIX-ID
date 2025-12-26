-- ========================================
-- FIX USER DATA - PASTIKAN ADMIN & USER ADA
-- ========================================

USE tix_id;

-- Hapus user lama jika ada
DELETE FROM users WHERE email IN ('test@example.com', 'admin@tixid.com');

-- Insert ulang dengan password yang benar (password123)
INSERT INTO users (name, email, password, phone, role) VALUES
('Test User', 'test@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'user'),
('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin');

-- Cek hasilnya
SELECT id, name, email, role, LEFT(password, 20) as password_hash FROM users;

