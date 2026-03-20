CREATE TABLE IF NOT EXISTS session (
    id            BIGSERIAL    PRIMARY KEY,
    account_id    BIGINT       NOT NULL,
    token         VARCHAR(255) NOT NULL,
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at    TIMESTAMP    NOT NULL,
    last_used_at  TIMESTAMP    NULL,
    CONSTRAINT uq_session_token UNIQUE (token),
    CONSTRAINT fk_session_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_session_account_id ON session (account_id);
CREATE INDEX IF NOT EXISTS idx_session_expires_at ON session (expires_at);
