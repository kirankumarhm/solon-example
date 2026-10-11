-- ====================================================================
-- Initial Seed Data for Product Catalog
-- ====================================================================
MERGE INTO tb_product (id, sku, name, description, category, price, stock_quantity, status, created_at, updated_at) 
KEY(id) 
VALUES 
(1, 'PROD-LAPTOP-001', 'Developer Pro Laptop 16-inch', 'High-performance workstation laptop with 32GB RAM, 1TB NVMe SSD', 'Electronics', 1899.99, 45, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'PROD-PHONE-002', 'NextGen 5G Smartphone', 'Flagship smartphone with OLED display, triple camera and 5000mAh battery', 'Electronics', 899.50, 120, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'PROD-HEADPHONES-003', 'Noise-Canceling Wireless Headphones', 'Studio-grade over-ear wireless headphones with 40-hour battery life', 'Audio', 249.99, 85, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'PROD-KEYBOARD-004', 'Mechanical Gaming Keyboard', 'RGB backlight with hot-swappable mechanical tactile switches', 'Accessories', 129.00, 200, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'PROD-MOUSE-005', 'Ergonomic Wireless Mouse', 'Precision wireless mouse with customizable thumb buttons and USB-C recharge', 'Accessories', 79.99, 150, 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
