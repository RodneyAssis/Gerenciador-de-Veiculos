-- Inserindo dados na tabela veiculo
SET search_path TO core;

INSERT INTO veiculo (marca, modelo, fabricante, cor, ano, preco) VALUES
    ('Toyota', 'Corolla', 'Toyota Motor Corporation', 'Prata', 2022, 120000.00),
    ('Honda', 'Civic', 'Honda Motor Company', 'Preto', 2021, 130000.00),
    ('Ford', 'Mustang', 'Ford Motor Company', 'Vermelho', 2020, 250000.00),
    ('Yamaha', 'MT-07', 'Yamaha Motor Company', 'Azul', 2023, 45000.00),
    ('Honda', 'CB 500X', 'Honda Motor Company', 'Branco', 2022, 38000.00),
    ('Chevrolet', 'Onix', 'Chevrolet', 'Branco', 2023, 75000.00),
    ('Volkswagen', 'Gol', 'Volkswagen', 'Prata', 2022, 68000.00),
    ('Fiat', 'Argo', 'Fiat', 'Vermelho', 2021, 72000.00),
    ('Renault', 'Kwid', 'Renault', 'Preto', 2023, 59000.00),
    ('Jeep', 'Compass', 'Jeep', 'Azul', 2022, 160000.00),
    ('BMW', '320i', 'BMW', 'Cinza', 2021, 230000.00),
    ('Mercedes-Benz', 'C180', 'Mercedes-Benz', 'Branco', 2020, 270000.00),
    ('Audi', 'A3', 'Audi', 'Preto', 2023, 250000.00),
    ('Nissan', 'Versa', 'Nissan', 'Azul', 2022, 85000.00),
    ('Hyundai', 'HB20', 'Hyundai', 'Vermelho', 2023, 70000.00),
    ('Suzuki', 'GSX-S750', 'Suzuki', 'Azul', 2021, 50000.00),
    ('Ducati', 'Monster', 'Ducati', 'Vermelho', 2023, 75000.00),
    ('Kawasaki', 'Ninja 400', 'Kawasaki', 'Verde', 2022, 42000.00),
    ('Triumph', 'Tiger 900', 'Triumph', 'Preto', 2021, 65000.00),
    ('Harley-Davidson', 'Iron 883', 'Harley-Davidson', 'Laranja', 2020, 90000.00);

-- Inserindo dados na tabela carro
SET search_path TO comum;

INSERT INTO carro (fk_veiculo, quantidade_portas, tipo_combustivel) VALUES
    (1, 4, 'Flex'),
    (2, 4, 'Gasolina'),
    (3, 2, 'Gasolina'),
    (6, 4, 'Flex'),
    (7, 4, 'Flex'),
    (8, 4, 'Flex'),
    (9, 4, 'Flex'),
    (10, 4, 'Flex'),
    (11, 4, 'Gasolina'),
    (12, 4, 'Gasolina'),
    (13, 4, 'Gasolina'),
    (14, 4, 'Flex'),
    (15, 4, 'Flex');

-- Inserindo dados na tabela moto
INSERT INTO moto (fk_veiculo, cilindrada) VALUES
    (4, '689'),
    (5, '471'),
    (16, '749'),
    (17, '937'),
    (18, '399'),
    (19, '888'),
    (20, '883');
