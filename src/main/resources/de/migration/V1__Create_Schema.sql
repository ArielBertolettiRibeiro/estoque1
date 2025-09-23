-- Tabela: users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Tabela: produtos
CREATE TABLE produtos (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    unit_price NUMERIC(19,2) NOT NULL,
    available_quantity INTEGER NOT NULL
);

-- Tabela: stock_movements
CREATE TABLE stock_movements (
    id SERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    moved_quantity INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL,
    movement_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES produtos(id)
);

-- Tabela: sales
CREATE TABLE sales (
    id SERIAL PRIMARY KEY,
    sold_quantity INTEGER NOT NULL,
    sale_date TIMESTAMP NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_sale_product FOREIGN KEY (product_id) REFERENCES produtos(id)
);