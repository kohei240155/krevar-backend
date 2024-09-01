-- usersテーブルにダミーデータを挿入
INSERT INTO users (name, email, password, google_id, role)
VALUES
('John Doe', 'john.doe@example.com', 'password123', NULL, 'USER'),
('Jane Smith', 'jane.smith@example.com', 'securepass', NULL, 'USER'),
('Alice Johnson', 'alice.johnson@example.com', 'alicepassword', 'google-id-alice', 'USER');

-- review_intervalsテーブルにダミーデータを挿入
INSERT INTO review_intervals (interval_order, interval_days)
VALUES
(1, 0),    -- 0日後
(2, 1),    -- 1日後
(3, 3),    -- 3日後
(4, 7),    -- 7日後
(5, 14),   -- 14日後
(6, 30),   -- 30日後
(7, 60),   -- 60日後
(8, 90),   -- 90日後
(9, 180),  -- 180日後
(10, 300);  -- 300日後

-- decksテーブルにダミーデータを挿入
INSERT INTO decks (deck_name, user_id)
VALUES
('Basic Vocabulary', 1),  -- John Doeのデッキ1
('Intermediate Vocabulary', 1),  -- John Doeのデッキ2
('Advanced Vocabulary', 2),  -- Jane Smithのデッキ1
('Business English', 2),  -- Jane Smithのデッキ2
('Technical Terms', 3),  -- Alice Johnsonのデッキ1
('Daily Conversation', 3);  -- Alice Johnsonのデッキ2

-- wordsテーブルにダミーデータを挿入
INSERT INTO words (original_text, translated_text, nuance_text, image_url, deck_id)
VALUES
-- John Doe's Basic Vocabulary Deck
('apple', 'りんご', 'A common fruit.', 'http://example.com/apple.jpg', 1),
('banana', 'バナナ', 'A yellow fruit.', 'http://example.com/banana.jpg', 1),
('grape', 'ぶどう', 'A small round fruit.', 'http://example.com/default.jpg', 1),

-- John Doe's Intermediate Vocabulary Deck
('philosophy', '哲学', 'The study of the fundamental nature of knowledge.', 'http://example.com/default.jpg', 2),
('culture', '文化', 'The arts and other manifestations of human intellectual achievement.', 'http://example.com/default.jpg', 2),
('architecture', '建築', 'The art or practice of designing and constructing buildings.', 'http://example.com/architecture.jpg', 2),

-- Jane Smith's Advanced Vocabulary Deck
('infrastructure', 'インフラ', 'The basic physical and organizational structures and facilities.', 'http://example.com/default.jpg', 3),
('philanthropy', '慈善活動', 'The desire to promote the welfare of others.', 'http://example.com/philanthropy.jpg', 3),
('dichotomy', '二分法', 'A division or contrast between two things.', 'http://example.com/default.jpg', 3),

-- Jane Smith's Business English Deck
('synergy', '相乗効果', 'The interaction or cooperation of two or more organizations.', 'http://example.com/default.jpg', 4),
('paradigm', 'パラダイム', 'A typical example or pattern of something.', 'http://example.com/default.jpg', 4),
('leverage', 'レバレッジ', 'The exertion of force by means of a lever.', 'http://example.com/leverage.jpg', 4),

-- Alice Johnson's Technical Terms Deck
('algorithm', 'アルゴリズム', 'A step-by-step procedure for calculations.', 'http://example.com/default.jpg', 5),
('database', 'データベース', 'A structured set of data.', 'http://example.com/default.jpg', 5),
('encryption', '暗号化', 'The process of converting information or data into a code.', 'http://example.com/default.jpg', 5),

-- Alice Johnson's Daily Conversation Deck
('hello', 'こんにちは', 'A common greeting.', 'http://example.com/hello.jpg', 6),
('thank you', 'ありがとう', 'A polite expression of gratitude.', 'http://example.com/thankyou.jpg', 6),
('goodbye', 'さようなら', 'A farewell expression.', 'http://example.com/default.jpg', 6);

-- quiz_resultsテーブルにダミーデータを挿入
INSERT INTO quiz_results (word_id, review_interval_id, next_practice_date, correct_count, incorrect_count, is_extra_mode_correct)
VALUES
(1, 1, CURDATE(), 0, 0, FALSE),
(2, 1, CURDATE(), 0, 0, FALSE),
(3, 1, CURDATE(), 0, 0, FALSE),
(4, 1, CURDATE(), 0, 0, FALSE),
(5, 1, CURDATE(), 0, 0, FALSE),
(6, 1, CURDATE(), 0, 0, FALSE),
(7, 1, CURDATE(), 0, 0, FALSE),
(8, 1, CURDATE(), 0, 0, FALSE),
(9, 1, CURDATE(), 0, 0, FALSE),
(10, 1, CURDATE(), 0, 0, FALSE),
(11, 1, CURDATE(), 0, 0, FALSE),
(12, 1, CURDATE(), 0, 0, FALSE),
(13, 1, CURDATE(), 0, 0, FALSE),
(14, 1, CURDATE(), 0, 0, FALSE),
(15, 1, CURDATE(), 0, 0, FALSE),
(16, 1, CURDATE(), 0, 0, FALSE),
(17, 1, CURDATE(), 0, 0, FALSE),
(18, 1, CURDATE(), 0, 0, FALSE);
