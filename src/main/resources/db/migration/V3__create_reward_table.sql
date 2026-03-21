CREATE TABLE IF NOT EXISTS reward (
    id          BIGSERIAL   PRIMARY KEY,
    account_id  BIGINT       NOT NULL,
    cost        INT          NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    expires_at  TIMESTAMP    NULL DEFAULT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reward_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_reward_account_id ON reward (account_id);
