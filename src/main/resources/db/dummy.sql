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

-- wordsテーブルにダーデータを挿入（修正版）
INSERT INTO words (original_text, translated_text, nuance_text, image_url, review_interval_id, next_practice_date, correct_count, incorrect_count, is_normal_mode_correct, is_extra_mode_correct, deck_id)
VALUES
-- John Doe's Basic Vocabulary Deck
('apple', 'りんご', 'A common fruit.', 'http://example.com/apple.jpg', 1, CURDATE(), 3, 1, TRUE, FALSE, 1),
('banana', 'バナナ', 'A yellow fruit.', 'http://example.com/banana.jpg', 2, CURDATE(), 2, 2, FALSE, FALSE, 1),
('grape', 'ぶどう', 'A small round fruit.', NULL, 1, CURDATE(), 1, 1, TRUE, FALSE, 1),

-- John Doe's Intermediate Vocabulary Deck
('philosophy', '哲学', 'The study of the fundamental nature of knowledge.', NULL, 3, CURDATE(), 2, 0, TRUE, FALSE, 2),
('culture', '文化', 'The arts and other manifestations of human intellectual achievement.', NULL, 4, CURDATE(), 3, 1, TRUE, FALSE, 2),
('architecture', '建築', 'The art or practice of designing and constructing buildings.', 'http://example.com/architecture.jpg', 5, CURDATE(), 0, 3, FALSE, FALSE, 2),

-- Jane Smith's Advanced Vocabulary Deck
('infrastructure', 'インフラ', 'The basic physical and organizational structures and facilities.', NULL, 3, CURDATE(), 1, 2, TRUE, FALSE, 3),
('philanthropy', '慈善活動', 'The desire to promote the welfare of others.', 'http://example.com/philanthropy.jpg', 4, CURDATE(), 4, 0, TRUE, FALSE, 3),
('dichotomy', '二分法', 'A division or contrast between two things.', NULL, 2, CURDATE(), 0, 1, FALSE, FALSE, 3),

-- Jane Smith's Business English Deck
('synergy', '相乗効果', 'The interaction or cooperation of two or more organizations.', NULL, 2, CURDATE(), 3, 0, TRUE, FALSE, 4),
('paradigm', 'パラダイム', 'A typical example or pattern of something.', NULL, 1, CURDATE(), 2, 1, TRUE, FALSE, 4),
('leverage', 'レバレッジ', 'The exertion of force by means of a lever.', 'http://example.com/leverage.jpg', 4, CURDATE(), 1, 2, FALSE, FALSE, 4),

-- Alice Johnson's Technical Terms Deck
('algorithm', 'アルゴリズム', 'A step-by-step procedure for calculations.', NULL, 1, CURDATE(), 4, 1, TRUE, FALSE, 5),
('database', 'データベース', 'A structured set of data.', NULL, 5, CURDATE(), 5, 0, TRUE, FALSE, 5),
('encryption', '暗号化', 'The process of converting information or data into a code.', NULL, 3, CURDATE(), 2, 3, FALSE, FALSE, 5),

-- Alice Johnson's Daily Conversation Deck
('hello', 'こんにちは', 'A common greeting.', 'http://example.com/hello.jpg', 1, CURDATE(), 10, 0, TRUE, FALSE, 6),
('thank you', 'ありがとう', 'A polite expression of gratitude.', 'http://example.com/thankyou.jpg', 2, CURDATE(), 8, 1, TRUE, FALSE, 6),
('goodbye', 'さようなら', 'A farewell expression.', NULL, 3, CURDATE(), 6, 2, TRUE, FALSE, 6);