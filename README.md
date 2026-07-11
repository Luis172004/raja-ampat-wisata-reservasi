# Sistem Reservasi Wisata Raja Ampat

Aplikasi desktop Java (Swing) dengan koneksi database MySQL, untuk Tugas Akhir Mata Kuliah PBO.

## Struktur Tabel Database
1. `wisatawan` — data pengunjung
2. `destinasi` — objek wisata
3. `paket_wisata` — paket tur (relasi many-to-one ke `destinasi`)
4. `pemesanan` — transaksi booking (relasi many-to-one ke `wisatawan` dan `paket_wisata`)

## Cara Menjalankan

### 1. Siapkan Database
- Buka MySQL (Workbench/XAMPP/CLI), lalu jalankan file `database/schema.sql`.
- Ini akan otomatis membuat database `reservasi_wisata_raja_ampat`, membuat 4 tabel, dan mengisi beberapa data contoh.

### 2. Atur Koneksi Database
Buka `src/main/java/com/rajaampat/db/DatabaseConnection.java`, sesuaikan:
```java
private static final String USER = "root";
private static final String PASSWORD = ""; // isi sesuai password MySQL kamu
```

### 3. Jalankan dengan Maven
```bash
mvn clean package
java -jar target/reservasi-wisata-raja-ampat.jar
```

Atau buka project ini di IntelliJ IDEA / Eclipse sebagai Maven Project, lalu jalankan `Main.java`.

### 4. Login
- Username: `admin`
- Password: `admin123`

## Fitur
- CRUD Destinasi Wisata
- CRUD Paket Wisata (dropdown relasi ke Destinasi)
- CRUD Data Wisatawan
- Pemesanan (dropdown relasi ke Wisatawan & Paket, total bayar dihitung otomatis: harga paket × jumlah orang)
- Update status pemesanan (Pending / Confirmed / Cancelled)

## Catatan
- GUI dibuat manual dengan `GridBagLayout`/`BorderLayout` (tanpa GUI Builder/Design View NetBeans).
- Struktur mengikuti pola MVC sederhana: `model` (entity), `dao` (akses database), `gui` (tampilan).
