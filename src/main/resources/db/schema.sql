-- users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('admin', 'user') NOT NULL,
    subscription_type ENUM('free', 'basic', 'value', 'premium') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- mastery_statuses table
CREATE TABLE mastery_statuses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i') NOT NULL,
    days INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- decks table
CREATE TABLE decks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deck_name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- words table
CREATE TABLE words (
    id INT AUTO_INCREMENT PRIMARY KEY,
    original_text TEXT NOT NULL,
    translated_text TEXT,
    original_image_url VARCHAR(255),
    image_url VARCHAR(255),
    mastery_status_id INT NOT NULL,
    last_practiced_date DATE NOT NULL,
    next_practice_date DATE NOT NULL,
    deck_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (deck_id) REFERENCES decks(id),
    FOREIGN KEY (mastery_status_id) REFERENCES mastery_statuses(id)
);

-- 初期データの挿入
INSERT INTO mastery_statuses (status, days) VALUES
('a', 1),
('b', 2),
('c', 4),
('d', 7),
('e', 15),
('f', 30),
('g', 60),
('h', 120),
('i', 240);