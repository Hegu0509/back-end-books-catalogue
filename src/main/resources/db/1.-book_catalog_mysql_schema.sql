-- ============================================================
-- Base de datos relacional MySQL para catálogo de libros
-- Versión corregida y más compatible con MySQL:
--   - Un solo autor por libro
--   - Una sola categoría por libro
--   - Campo valoracion en books
--   - Tabla images para cubierta y galería
--   - Sin columna generada, para evitar errores de compatibilidad
-- ============================================================

DROP DATABASE IF EXISTS book_catalog_db;
CREATE DATABASE book_catalog_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE book_catalog_db;

-- ============================================================
-- Tabla: publishers
-- ============================================================
CREATE TABLE publishers (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_publishers_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabla: authors
-- ============================================================
CREATE TABLE authors (
    id VARCHAR(40) NOT NULL,
    name VARCHAR(150) NOT NULL,
    biography TEXT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_authors_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabla: categories
-- ============================================================
CREATE TABLE categories (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(255) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_categories_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabla: books
-- Cada libro tiene un autor, una editorial y una categoría.
-- ============================================================
CREATE TABLE books (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NULL,
    short_description VARCHAR(500) NULL,

    author_id VARCHAR(40) NOT NULL,
    publisher_id BIGINT UNSIGNED NOT NULL,
    category_id BIGINT UNSIGNED NOT NULL,

    publication_date DATE NULL,
    edition VARCHAR(80) NULL,
    language VARCHAR(80) NOT NULL DEFAULT 'Español',
    format VARCHAR(80) NOT NULL DEFAULT 'digital',
    pages INT UNSIGNED NULL,
    visible CHAR(1) NOT NULL DEFAULT 'S',
    price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    stock INT UNSIGNED NOT NULL DEFAULT 0,
    valoracion DECIMAL(3,2) NOT NULL DEFAULT 0.00,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    UNIQUE KEY uq_books_isbn (isbn),
    KEY idx_books_title (title),
    KEY idx_books_author_id (author_id),
    KEY idx_books_publisher_id (publisher_id),
    KEY idx_books_category_id (category_id),
    KEY idx_books_visible (visible),

    CONSTRAINT fk_books_authors
        FOREIGN KEY (author_id)
        REFERENCES authors(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_books_publishers
        FOREIGN KEY (publisher_id)
        REFERENCES publishers(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_books_categories
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT chk_books_price_non_negative CHECK (price >= 0),
    CONSTRAINT chk_books_stock_non_negative CHECK (stock >= 0),
    CONSTRAINT chk_books_pages_positive CHECK (pages IS NULL OR pages > 0),
    CONSTRAINT chk_books_valoracion_range CHECK (valoracion >= 0 AND valoracion <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- Tabla: images
-- Un libro puede tener:
--   - Una cubierta: image_type = 'cover', image_order = 1
--   - Varias imágenes de galería: image_type = 'gallery', image_order = 1, 2, 3...
--
-- Regla aplicada:
--   UNIQUE(book_id, image_type, image_order)
--   + CHECK para que cover siempre use image_order = 1.
-- Esto permite una sola cubierta por libro y múltiples imágenes de galería.
-- ============================================================
CREATE TABLE images (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    book_id BIGINT UNSIGNED NOT NULL,
    image_url VARCHAR(1000) NOT NULL,
    image_type VARCHAR(80) NOT NULL DEFAULT 'cover',
    image_order INT UNSIGNED NOT NULL DEFAULT 1,
    alt_text VARCHAR(255) NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    KEY idx_images_book_id (book_id),
    KEY idx_images_type (image_type),
    UNIQUE KEY uq_images_book_type_order (book_id, image_type, image_order),

    CONSTRAINT fk_images_books
        FOREIGN KEY (book_id)
        REFERENCES books(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT chk_images_order_positive CHECK (image_order > 0),
    CONSTRAINT chk_images_cover_order CHECK (
        image_type <> 'cover' OR image_order = 1
    )
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

