CREATE TABLE IF NOT EXISTS exposicao_financeira(
    id serial   PRIMARY KEY,
    ativo       VARCHAR(10),
    lado        VARCHAR(1),
    quantidade  INTEGER,
    preco       NUMERIC(10,2)

)