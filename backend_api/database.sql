-- Create database
CREATE DATABASE IF NOT EXISTS tix_id;
USE tix_id;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) DEFAULT NULL,
    role ENUM('user', 'admin') DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample user for testing (password: password123)
INSERT INTO users (name, email, password, phone, role) VALUES
('Test User', 'test@example.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'user'),
('Admin TIX ID', 'admin@tixid.com', '$2y$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', '081234567890', 'admin');

-- Create movies table
CREATE TABLE IF NOT EXISTS movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    poster_url VARCHAR(500) NOT NULL,
    synopsis TEXT NOT NULL,
    youtube_url VARCHAR(500),
    rating DECIMAL(2,1) DEFAULT 0.0,
    duration VARCHAR(20),
    genre VARCHAR(100),
    status ENUM('now_showing', 'coming_soon') DEFAULT 'now_showing',
    release_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample movies
INSERT INTO movies (title, poster_url, synopsis, youtube_url, rating, duration, genre, status, release_date) VALUES
-- NOW SHOWING (Sedang Tayang)
('Spider-Man: No Way Home', 'https://m.media-amazon.com/images/M/MV5BZWMyYzFjYTYtNTRjYi00OGExLWE2YzgtOGRmYjAxZTU3NzBiXkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg', 'Peter Parker menghadapi konsekuensi setelah identitasnya terbongkar. Ia mencari bantuan Doctor Strange, namun hal ini membuka pintu multiverse yang berbahaya.', 'https://www.youtube.com/watch?v=JfVOs4VSpmA', 8.5, '148 Menit', 'Action, Adventure', 'now_showing', '2021-12-15'),
('The Lion King', 'https://m.media-amazon.com/images/M/MV5BMjIwMjE1Nzc4NV5BMl5BanBnXkFtZTgwNDg4OTA1NzM@._V1_.jpg', 'Simba, seekor singa muda yang ditakdirkan untuk menjadi raja, harus menghadapi masa lalunya dan mengambil tempatnya yang sebenarnya di Circle of Life.', 'https://www.youtube.com/watch?v=4sj1MT05lAA', 8.0, '118 Menit', 'Animation, Adventure', 'now_showing', '2019-07-19'),
('Harry Potter and the Deathly Hallows', 'https://m.media-amazon.com/images/M/MV5BMjIyZGU4YzUtNDkzYi00ZDRhLTljYzctYTMxMDQ4M2E0Y2YxXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg', 'Harry, Ron, dan Hermione mencari Horcrux yang tersisa untuk menghancurkan Voldemort. Pertempuran terakhir di Hogwarts menentukan takdir dunia sihir.', 'https://www.youtube.com/watch?v=5NYt1qirBWg', 8.7, '130 Menit', 'Fantasy, Adventure', 'now_showing', '2011-07-15'),
('WALL-E', 'https://i.pinimg.com/736x/6b/42/d0/6b42d0e7f415f4cf1f8e98a4f3e65b93.jpg', 'WALL-E, robot pembersih sampah terakhir di Bumi yang ditinggalkan, menemukan tujuan hidup baru ketika bertemu dengan robot pencari bernama EVE.', 'https://www.youtube.com/watch?v=CZ1CATNbXg0', 8.4, '98 Menit', 'Animation, Sci-Fi', 'now_showing', '2008-06-27'),
('The Good Dinosaur', 'https://lumiere-a.akamaihd.net/v1/images/p_thegooddinosaur_19870_3c5a8196.jpeg', 'Dalam dunia alternatif di mana dinosaurus tidak punah, Arlo, seekor dinosaurus muda yang penakut, harus menemukan jalan pulang setelah terpisah dari keluarganya.', 'https://www.youtube.com/watch?v=O-RgquKVTPE', 7.8, '93 Menit', 'Animation, Adventure', 'now_showing', '2015-11-25'),
('Stranger Things', 'https://m.media-amazon.com/images/M/MV5BMDZkYmVhNjMtNWU4MC00MDQxLWE3MjYtZGMzZWI1ZjhlOWJmXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg', 'Sekelompok anak-anak di Hawkins, Indiana menghadapi kekuatan supernatural dan eksperimen pemerintah rahasia saat mencari teman mereka yang hilang.', 'https://www.youtube.com/watch?v=b9EkMc79ZSU', 8.7, '50 Menit/Episode', 'Sci-Fi, Horror', 'now_showing', '2016-07-15'),
('Deadpool', 'https://m.media-amazon.com/images/M/MV5BYzE5MjY1ZDgtMTkyNC00MTMyLThhMjAtZGI5OTE1NzFlZGJjXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg', 'Wade Wilson, seorang mantan pasukan khusus yang menjadi tentara bayaran, menjalani eksperimen brutal yang memberinya kekuatan penyembuhan cepat dan mengubahnya menjadi Deadpool.', 'https://www.youtube.com/watch?v=ONHBaC-pfsk', 8.0, '108 Menit', 'Action, Comedy', 'now_showing', '2016-02-12'),

-- COMING SOON (Segera Datang)
('Zootopia', 'https://m.media-amazon.com/images/M/MV5BOTMyMjEyNzIzMV5BMl5BanBnXkFtZTgwNzIyNjU0NzE@._V1_.jpg', 'Judy Hopps, kelinci pertama yang menjadi polisi, bekerja sama dengan rubah penipu Nick Wilde untuk mengungkap konspirasi besar di kota Zootopia.', 'https://www.youtube.com/watch?v=jWM0ct-OLsM', 8.3, '108 Menit', 'Animation, Comedy', 'coming_soon', '2016-03-04'),
('Star Wars: The Rise of Skywalker', 'https://m.media-amazon.com/images/M/MV5BMDljNTQ5ODItZmQwMy00M2ExLTljOTQtZTVjNGE2NTg0NGIxXkEyXkFqcGdeQXVyODkzNTgxMDg@._V1_.jpg', 'Rey, Finn, dan Poe Dameron memimpin perlawanan melawan First Order dalam pertempuran terakhir melawan Kaisar Palpatine.', 'https://www.youtube.com/watch?v=8Qn_spdM5Zg', 7.5, '142 Menit', 'Sci-Fi, Action', 'coming_soon', '2019-12-20'),
('Mulan', 'https://m.media-amazon.com/images/M/MV5BYzcxYjNhMTktYjRmYi00NjM1LWI0NDctNWViMTI1MzEyYmFhXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg', 'Hua Mulan menyamar sebagai pria untuk menggantikan ayahnya yang sakit dalam perang melawan invasi dari utara.', 'https://www.youtube.com/watch?v=KK8FHdFluOQ', 7.8, '115 Menit', 'Action, Drama', 'coming_soon', '2020-09-04'),
('The Last of Us', 'https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_.jpg', 'Joel, seorang penyelundup yang tangguh, ditugaskan untuk mengantar Ellie, gadis berusia 14 tahun, melintasi Amerika Serikat pasca-apokaliptik yang dipenuhi zombie.', 'https://www.youtube.com/watch?v=uLtkt8BonwM', 8.8, '55 Menit/Episode', 'Drama, Action', 'coming_soon', '2023-01-15');

-- Create index on email for faster queries
CREATE INDEX idx_email ON users(email);
CREATE INDEX idx_movie_status ON movies(status);
CREATE INDEX idx_movie_title ON movies(title);

