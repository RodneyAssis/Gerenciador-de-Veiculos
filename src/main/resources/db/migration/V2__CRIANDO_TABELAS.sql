SET search_path TO core;

CREATE TABLE IF NOT EXISTS veiculo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    modelo VARCHAR(150) NOT NULL ,
    fabricante VARCHAR(150) NOT NULL ,
    ano INT NOT NULL ,
    preco DOUBLE PRECISION NOT NULL
    );

SET search_path TO comum;

CREATE TABLE IF NOT EXISTS carro (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    qtd_portas INT NOT NULL,
    tipo_compustivel VARCHAR(2)
);

CREATE TABLE IF NOT EXISTS moto (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cilindrada INT NOT NULL
);

