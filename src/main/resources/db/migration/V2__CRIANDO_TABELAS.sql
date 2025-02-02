SET search_path TO core;

CREATE TABLE IF NOT EXISTS veiculo (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(150) NOT NULL,
    fabricante VARCHAR(150) NOT NULL,
    cor VARCHAR(20) NOT NULL,
    ano INT NOT NULL,
    preco DOUBLE PRECISION NOT NULL
);

SET search_path TO comum;

CREATE TABLE IF NOT EXISTS carro (
    id_carro SERIAL PRIMARY KEY,
    fk_veiculo INT NOT NULL,
    quantidade_portas INT NOT NULL,
    tipo_combustivel VARCHAR(10),
    CONSTRAINT fk_carro_veiculo FOREIGN KEY (fk_veiculo) REFERENCES core.veiculo(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS moto (
    id_moto SERIAL PRIMARY KEY,
    fk_veiculo INT NOT NULL,
    cilindrada VARCHAR(10) NOT NULL,
    CONSTRAINT fk_moto_veiculo FOREIGN KEY (fk_veiculo) REFERENCES core.veiculo(id) ON DELETE CASCADE
);
