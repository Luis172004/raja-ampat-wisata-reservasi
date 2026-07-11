-- ============================================================
-- Database: reservasi_wisata_raja_ampat
-- Deskripsi: Skema database untuk Sistem Reservasi Wisata Raja Ampat
-- ============================================================

CREATE DATABASE IF NOT EXISTS reservasi_wisata_raja_ampat;
USE reservasi_wisata_raja_ampat;

-- Tabel 1: wisatawan
CREATE TABLE IF NOT EXISTS wisatawan (
    id_wisatawan INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    no_hp VARCHAR(20),
    email VARCHAR(100),
    alamat VARCHAR(255)
);

-- Tabel 2: destinasi
CREATE TABLE IF NOT EXISTS destinasi (
    id_destinasi INT AUTO_INCREMENT PRIMARY KEY,
    nama_destinasi VARCHAR(100) NOT NULL,
    lokasi VARCHAR(150),
    deskripsi TEXT,
    harga_tiket DECIMAL(12,2) NOT NULL DEFAULT 0,
    kuota_harian INT NOT NULL DEFAULT 0
);

-- Tabel 3: paket_wisata (relasi many-to-one ke destinasi)
CREATE TABLE IF NOT EXISTS paket_wisata (
    id_paket INT AUTO_INCREMENT PRIMARY KEY,
    nama_paket VARCHAR(100) NOT NULL,
    id_destinasi INT NOT NULL,
    durasi_hari INT NOT NULL DEFAULT 1,
    harga_paket DECIMAL(12,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_paket_destinasi FOREIGN KEY (id_destinasi)
        REFERENCES destinasi(id_destinasi)
        ON DELETE CASCADE
);

-- Tabel 4: pemesanan (relasi many-to-one ke wisatawan & paket_wisata)
CREATE TABLE IF NOT EXISTS pemesanan (
    id_pemesanan INT AUTO_INCREMENT PRIMARY KEY,
    id_wisatawan INT NOT NULL,
    id_paket INT NOT NULL,
    tanggal_pesan DATE NOT NULL,
    tanggal_kunjungan DATE NOT NULL,
    jumlah_orang INT NOT NULL DEFAULT 1,
    total_bayar DECIMAL(12,2) NOT NULL DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    CONSTRAINT fk_pemesanan_wisatawan FOREIGN KEY (id_wisatawan)
        REFERENCES wisatawan(id_wisatawan)
        ON DELETE CASCADE,
    CONSTRAINT fk_pemesanan_paket FOREIGN KEY (id_paket)
        REFERENCES paket_wisata(id_paket)
        ON DELETE CASCADE
);

-- ============================================================
-- Data contoh (opsional, untuk memudahkan uji coba aplikasi)
-- ============================================================

INSERT INTO destinasi (nama_destinasi, lokasi, deskripsi, harga_tiket, kuota_harian) VALUES
('Pulau Wayag', 'Distrik Waigeo Barat', 'Gugusan karst ikonik dengan spot foto dari ketinggian.', 150000, 50),
('Pulau Piaynemo', 'Distrik Waigeo Barat', 'Gerbang selamat datang Raja Ampat dengan viewpoint terkenal.', 150000, 60),
('Kepulauan Kri', 'Distrik Meos Mansar', 'Spot snorkeling dan diving dengan terumbu karang berwarna.', 100000, 40),
('Teluk Kabui', 'Distrik Salawati Utara', 'Teluk tenang dikelilingi tebing karst untuk kayak dan susur teluk.', 75000, 30);

INSERT INTO paket_wisata (nama_paket, id_destinasi, durasi_hari, harga_paket) VALUES
('Explore Wayag 3D2N', 1, 3, 3500000),
('Piaynemo Sunrise Trip', 2, 2, 2200000),
('Snorkeling Kri Island', 3, 1, 850000),
('Kabui Bay Kayaking', 4, 1, 650000);

INSERT INTO wisatawan (nama, no_hp, email, alamat) VALUES
('Budi Santoso', '081234567890', 'budi.santoso@email.com', 'Jakarta'),
('Siti Aminah', '082345678901', 'siti.aminah@email.com', 'Bandung');

INSERT INTO pemesanan (id_wisatawan, id_paket, tanggal_pesan, tanggal_kunjungan, jumlah_orang, total_bayar, status) VALUES
(1, 1, '2026-06-01', '2026-07-15', 2, 7000000, 'Confirmed'),
(2, 3, '2026-06-10', '2026-07-20', 4, 3400000, 'Pending');
