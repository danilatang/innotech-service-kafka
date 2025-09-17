CREATE TABLE IF NOT EXISTS clients_accounts_schema.clients (
    client_id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    age INT NOT NULL,
    updated_at TIMESTAMP,
    department_id INT
);

CREATE TABLE IF NOT EXISTS clients_accounts_schema.accounts (
    number BIGINT PRIMARY KEY,
    client_id UUID REFERENCES clients_accounts_schema.clients(client_id),
    opening_date DATE,
    closing_date DATE,
    balance NUMERIC(19,2)
);