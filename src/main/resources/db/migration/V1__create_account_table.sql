CREATE TABLE IF NOT EXISTS account (
    id           BIGSERIAL    PRIMARY KEY,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(150) NOT NULL,
    phone        VARCHAR(50)  NULL,
    password     VARCHAR(255) NOT NULL,
    api_key      VARCHAR(150) NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_admin     BOOLEAN      NOT NULL DEFAULT FALSE,
    is_verified  BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_account_email UNIQUE (email),
    CONSTRAINT uq_account_api_key UNIQUE (api_key)
);

CREATE OR REPLACE FUNCTION set_account_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_account_updated_at ON account;
CREATE TRIGGER trg_account_updated_at
BEFORE UPDATE ON account
FOR EACH ROW
EXECUTE FUNCTION set_account_updated_at();
