CREATE TABLE customer (
  id BINARY(16) PRIMARY KEY,
  full_name VARCHAR(120) NOT NULL,
  email VARCHAR(180) NOT NULL,
  CONSTRAINT uk_customer_email UNIQUE (email)
);
CREATE TABLE account (
  id BINARY(16) PRIMARY KEY,
  customer_id BINARY(16) NOT NULL,
  currency CHAR(3) NOT NULL,
  balance DECIMAL(19,2) NOT NULL,
  alias VARCHAR(80) NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  version BIGINT NOT NULL DEFAULT 0,
  CONSTRAINT fk_account_customer FOREIGN KEY(customer_id) REFERENCES customer(id),
  CONSTRAINT ck_account_balance CHECK(balance >= 0),
  CONSTRAINT ck_account_currency CHECK(currency REGEXP '^[A-Z]{3}$')
);
CREATE TABLE transfer (
  id BINARY(16) PRIMARY KEY,
  origin_account_id BINARY(16) NOT NULL,
  destination_account_id BINARY(16) NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
  currency CHAR(3) NOT NULL,
  status VARCHAR(24) NOT NULL,
  idempotency_key VARCHAR(100) NOT NULL,
  request_fingerprint CHAR(64) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT uk_transfer_idempotency UNIQUE(idempotency_key),
  CONSTRAINT fk_transfer_origin FOREIGN KEY(origin_account_id) REFERENCES account(id),
  CONSTRAINT fk_transfer_destination FOREIGN KEY(destination_account_id) REFERENCES account(id),
  CONSTRAINT ck_transfer_amount CHECK(amount > 0),
  CONSTRAINT ck_transfer_accounts CHECK(origin_account_id <> destination_account_id)
);
CREATE TABLE movement (
  id BINARY(16) PRIMARY KEY,
  transfer_id BINARY(16) NOT NULL,
  account_id BINARY(16) NOT NULL,
  amount DECIMAL(19,2) NOT NULL,
  currency CHAR(3) NOT NULL,
  occurred_at TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_movement_transfer FOREIGN KEY(transfer_id) REFERENCES transfer(id),
  CONSTRAINT fk_movement_account FOREIGN KEY(account_id) REFERENCES account(id)
);
CREATE INDEX ix_movement_account_time ON movement(account_id, occurred_at DESC);
CREATE TABLE outbox_event (
  id BINARY(16) PRIMARY KEY,
  aggregate_id BINARY(16) NOT NULL,
  event_type VARCHAR(80) NOT NULL,
  payload JSON NOT NULL,
  created_at TIMESTAMP(6) NOT NULL,
  published_at TIMESTAMP(6) NULL
);
CREATE INDEX ix_outbox_unpublished ON outbox_event(published_at, created_at);
