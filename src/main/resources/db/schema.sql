-- users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- review_intervals table
CREATE TABLE review_intervals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    interval_order INT NOT NULL,
    interval_days INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL
);

-- decks table
CREATE TABLE decks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deck_name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    last_practiced_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- words table
CREATE TABLE words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_text TEXT NOT NULL,
    translated_text TEXT NOT NULL,
    nuance_text TEXT NOT NULL,
    image_url VARCHAR(255),
    review_interval_id INT NOT NULL,
    next_practice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    correct_count INT DEFAULT 0,
    incorrect_count INT DEFAULT 0,
    is_correct BOOLEAN DEFAULT FALSE,
    deck_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (deck_id) REFERENCES decks(id),
    FOREIGN KEY (review_interval_id) REFERENCES review_intervals(id)
);