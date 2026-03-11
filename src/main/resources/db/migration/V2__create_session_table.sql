CREATE TABLE IF NOT EXISTS session (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    account_id    BIGINT       NOT NULL,
    token         VARCHAR(255) NOT NULL,
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at    DATETIME     NOT NULL,
    last_used_at  DATETIME     NULL,
    CONSTRAINT pk_session PRIMARY KEY (id),
    CONSTRAINT uq_session_token UNIQUE (token),
    CONSTRAINT fk_session_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    INDEX idx_session_account_id (account_id),
    INDEX idx_session_expires_at (expires_at)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
