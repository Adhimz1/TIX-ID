-- ========================================
-- ADD MISSING MOVIES TO DATABASE
-- ========================================
-- File: add_missing_movies.sql

USE tix_id;

-- Insert film-film yang hilang

-- NOW SHOWING (Sedang Tayang)
INSERT INTO movies (title, poster_url, synopsis, youtube_url, rating, duration, genre, status, release_date) VALUES
('WALL-E', 'https://i.pinimg.com/736x/6b/42/d0/6b42d0e7f415f4cf1f8e98a4f3e65b93.jpg',
'WALL-E, robot pembersih sampah terakhir di Bumi yang ditinggalkan, menemukan tujuan hidup baru ketika bertemu dengan robot pencari bernama EVE.',
'https://www.youtube.com/watch?v=CZ1CATNbXg0',
8.4, '98 Menit', 'Animation, Sci-Fi', 'now_showing', '2008-06-27'),

('The Good Dinosaur', 'https://lumiere-a.akamaihd.net/v1/images/p_thegooddinosaur_19870_3c5a8196.jpeg',
'Dalam dunia alternatif di mana dinosaurus tidak punah, Arlo, seekor dinosaurus muda yang penakut, harus menemukan jalan pulang setelah terpisah dari keluarganya.',
'https://www.youtube.com/watch?v=O-RgquKVTPE',
7.8, '93 Menit', 'Animation, Adventure', 'now_showing', '2015-11-25'),

('Stranger Things', 'https://m.media-amazon.com/images/M/MV5BMDZkYmVhNjMtNWU4MC00MDQxLWE3MjYtZGMzZWI1ZjhlOWJmXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg',
'Sekelompok anak-anak di Hawkins, Indiana menghadapi kekuatan supernatural dan eksperimen pemerintah rahasia saat mencari teman mereka yang hilang.',
'https://www.youtube.com/watch?v=b9EkMc79ZSU',
8.7, '50 Menit/Episode', 'Sci-Fi, Horror', 'now_showing', '2016-07-15'),

('Deadpool', 'https://m.media-amazon.com/images/M/MV5BYzE5MjY1ZDgtMTkyNC00MTMyLThhMjAtZGI5OTE1NzFlZGJjXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_.jpg',
'Wade Wilson, seorang mantan pasukan khusus yang menjadi tentara bayaran, menjalani eksperimen brutal yang memberinya kekuatan penyembuhan cepat dan mengubahnya menjadi Deadpool.',
'https://www.youtube.com/watch?v=ONHBaC-pfsk',
8.0, '108 Menit', 'Action, Comedy', 'now_showing', '2016-02-12');

-- COMING SOON (Segera Datang)
INSERT INTO movies (title, poster_url, synopsis, youtube_url, rating, duration, genre, status, release_date) VALUES
('The Last of Us', 'https://m.media-amazon.com/images/M/MV5BZGUzYTI3M2EtZmM0Yy00NGUyLWI4ODEtN2Q3ZGJlYzhhZjU3XkEyXkFqcGdeQXVyNTM0OTY1OQ@@._V1_.jpg',
'Joel, seorang penyelundup yang tangguh, ditugaskan untuk mengantar Ellie, gadis berusia 14 tahun, melintasi Amerika Serikat pasca-apokaliptik yang dipenuhi zombie.',
'https://www.youtube.com/watch?v=uLtkt8BonwM',
8.8, '55 Menit/Episode', 'Drama, Action', 'coming_soon', '2023-01-15');

-- Note: Zootopia sudah ada di database dengan status 'coming_soon'

-- Verify insertion
SELECT
    id,
    title,
    status,
    rating,
    duration,
    genre,
    LEFT(poster_url, 50) as poster_preview
FROM movies
ORDER BY status, title;

