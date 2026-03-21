CREATE TABLE IF NOT EXISTS task (
    id              BIGSERIAL    PRIMARY KEY,
    account_id      BIGINT       NOT NULL,
    title           VARCHAR(255) NOT NULL,
    description     TEXT         NOT NULL,
    reward_amount   INT          NOT NULL DEFAULT 0,
    status          INT          NOT NULL DEFAULT 0,
    completed_at    TIMESTAMP    NULL DEFAULT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_account FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_task_account_id ON task (account_id);
CREATE INDEX IF NOT EXISTS idx_task_status ON task (status);

CREATE OR REPLACE FUNCTION set_task_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_task_updated_at
BEFORE UPDATE ON task
FOR EACH ROW
EXECUTE FUNCTION set_task_updated_at();
