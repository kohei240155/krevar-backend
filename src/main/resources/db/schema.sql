-- DROP TABLE `iruka_db`.`words`;
-- DROP TABLE `iruka_db`.`review_intervals`;
-- DROP TABLE `iruka_db`.`decks`;
-- DROP TABLE `iruka_db`.`users`;
-- DROP TABLE `iruka_db`.`subscription_statuses`;
-- DROP TABLE `iruka_db`.`languages`;

-- languages table
CREATE TABLE languages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    language_code VARCHAR(50) NOT NULL UNIQUE,
    language_name VARCHAR(50) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

-- subscription_statuses table
CREATE TABLE subscription_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    plan VARCHAR(50) NOT NULL,
    max_image_generation INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0
);

-- users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    google_id VARCHAR(100) NOT NULL,
    role VARCHAR(100) NOT NULL,
    default_native_language_id INT NOT NULL DEFAULT 1,
    default_learning_language_id INT NOT NULL DEFAULT 2,
    subscription_id VARCHAR(100),
    image_generation_remaining INT DEFAULT 1,
    image_generation_reset_date DATE DEFAULT (CURRENT_DATE) ,
    subscription_status_id INT NOT NULL DEFAULT 1,
    is_trial_expired INT DEFAULT 0,
    highlight_color VARCHAR(20) DEFAULT '#FFFF00',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (default_native_language_id) REFERENCES languages(id),
    FOREIGN KEY (default_learning_language_id) REFERENCES languages(id),
    FOREIGN KEY (subscription_status_id) REFERENCES subscription_statuses(id)
);

-- review_intervals table
CREATE TABLE review_intervals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    interval_days INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    UNIQUE (interval_days)
);

-- decks table
CREATE TABLE decks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deck_name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    last_practiced_date DATETIME DEFAULT NULL,
    native_language_id INT NOT NULL,
    learning_language_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (native_language_id) REFERENCES languages(id),
    FOREIGN KEY (learning_language_id) REFERENCES languages(id)
);

-- words table
CREATE TABLE words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_text VARCHAR(255) NOT NULL,
    translated_text VARCHAR(255) NOT NULL,
    nuance_text VARCHAR(255) NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    deck_id INT NOT NULL,
    review_interval_id INT DEFAULT 1,
    next_practice_date DATE DEFAULT (CURRENT_DATE),
    correct_count INT DEFAULT 0,
    incorrect_count INT DEFAULT 0,
    is_extra_mode_correct INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted INT DEFAULT 0,
    FOREIGN KEY (deck_id) REFERENCES decks(id),
    FOREIGN KEY (review_interval_id) REFERENCES review_intervals(id)
);