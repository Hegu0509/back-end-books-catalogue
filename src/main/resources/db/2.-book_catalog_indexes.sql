-- ============================================================
-- Índices de optimización para book_catalog_db
-- Compatible con el esquema corregido del catálogo de libros
-- ============================================================

USE book_catalog_db;

-- ============================================================
-- ÍNDICES PARA TABLA books
-- ============================================================

-- Búsqueda rápida por título.
-- Útil para consultas tipo:
-- WHERE title LIKE 'Texto%'
CREATE INDEX idx_books_title_search
ON books (title);

-- Filtro principal de catálogo visible por categoría.
-- Útil para:
-- WHERE visible = 'S' AND category_id = ?
CREATE INDEX idx_books_visible_category
ON books (visible, category_id);

-- Filtro de catálogo visible por autor.
-- Útil para:
-- WHERE visible = 'S' AND author_id = ?
CREATE INDEX idx_books_visible_author
ON books (visible, author_id);

-- Filtro de catálogo visible por editorial.
-- Útil para:
-- WHERE visible = 'S' AND publisher_id = ?
CREATE INDEX idx_books_visible_publisher
ON books (visible, publisher_id);

-- Ordenamiento por fecha de publicación.
-- Útil para:
-- WHERE visible = 'S'
-- ORDER BY publication_date DESC
CREATE INDEX idx_books_visible_publication_date
ON books (visible, publication_date DESC);

-- Filtro y ordenamiento por precio.
-- Útil para:
-- WHERE visible = 'S' AND price BETWEEN ? AND ?
-- ORDER BY price ASC
CREATE INDEX idx_books_visible_price
ON books (visible, price);

-- Ordenamiento por valoración.
-- Útil para:
-- WHERE visible = 'S'
-- ORDER BY valoracion DESC
CREATE INDEX idx_books_visible_valoracion
ON books (visible, valoracion DESC);

-- Consulta de libros disponibles.
-- Útil para:
-- WHERE visible = 'S' AND stock > 0
CREATE INDEX idx_books_visible_stock
ON books (visible, stock);

-- Índice combinado para búsquedas frecuentes de catálogo:
-- categoría + formato + visibilidad + precio.
-- Útil para filtros de tienda:
-- WHERE visible = 'S'
--   AND category_id = ?
--   AND format = ?
--   AND price BETWEEN ? AND ?
CREATE INDEX idx_books_catalog_filter
ON books (visible, category_id, format, price);

-- Índice para búsqueda por idioma y formato.
-- Útil para:
-- WHERE visible = 'S'
--   AND language = ?
--   AND format = ?
CREATE INDEX idx_books_language_format
ON books (visible, language, format);

-- ============================================================
-- ÍNDICES FULLTEXT PARA BÚSQUEDA TEXTUAL
-- Requiere MySQL 5.6+ con InnoDB.
-- Útil para búsquedas por título y descripción.
-- Ejemplo:
-- SELECT *
-- FROM books
-- WHERE MATCH(title, short_description, description)
-- AGAINST ('silencio narrativa' IN NATURAL LANGUAGE MODE);
-- ============================================================

CREATE FULLTEXT INDEX ftx_books_search
ON books (title, short_description, description);

-- ============================================================
-- ÍNDICES PARA TABLA authors
-- ============================================================

-- Búsqueda textual por nombre y biografía.
CREATE FULLTEXT INDEX ftx_authors_search
ON authors (name, biography);

-- ============================================================
-- ÍNDICES PARA TABLA publishers
-- ============================================================

-- Búsqueda por nombre de editorial.
-- Nota: uq_publishers_name ya ayuda para igualdad exacta.
-- Este índice puede ayudar en ordenamiento/listados.
CREATE INDEX idx_publishers_name
ON publishers (name);

-- ============================================================
-- ÍNDICES PARA TABLA categories
-- ============================================================

-- Búsqueda por nombre de categoría.
-- Nota: uq_categories_name ya ayuda para igualdad exacta.
CREATE INDEX idx_categories_name
ON categories (name);

-- ============================================================
-- ÍNDICES PARA TABLA images
-- ============================================================

-- Obtención rápida de la cubierta de un libro.
-- Útil para:
-- WHERE book_id = ? AND image_type = 'cover' AND image_order = 1
CREATE INDEX idx_images_book_cover
ON images (book_id, image_type, image_order);

-- Obtención ordenada de galería.
-- Útil para:
-- WHERE book_id = ? AND image_type = 'gallery'
-- ORDER BY image_order
CREATE INDEX idx_images_book_gallery_order
ON images (book_id, image_type, image_order);

-- ============================================================
-- CONSULTAS DE VERIFICACIÓN DE ÍNDICES
-- ============================================================

SHOW INDEX FROM books;
SHOW INDEX FROM authors;
SHOW INDEX FROM publishers;
SHOW INDEX FROM categories;
SHOW INDEX FROM images;
