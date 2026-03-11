CREATE TABLE IF NOT EXISTS account (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(50)  NOT NULL,
    last_name    VARCHAR(50)  NOT NULL,
    email        VARCHAR(150) NOT NULL,
    phone        VARCHAR(50)  NULL,
    password     VARCHAR(255) NOT NULL,
    api_key      VARCHAR(150) NOT NULL,
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_admin     TINYINT(1)   NOT NULL DEFAULT 0,
    is_verified  TINYINT(1)   NOT NULL DEFAULT 0,
    CONSTRAINT pk_account PRIMARY KEY (id),
    CONSTRAINT uq_account_email UNIQUE (email),
    CONSTRAINT uq_account_api_key UNIQUE (api_key)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

