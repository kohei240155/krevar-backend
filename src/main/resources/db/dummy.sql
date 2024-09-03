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

-- wordsテーブルにダミーデータを挿入 (quiz_resultsテーブルのデータを統合)
INSERT INTO words (original_text, translated_text, nuance_text, image_url, deck_id, review_interval_id, next_practice_date, correct_count, incorrect_count, is_extra_mode_correct)
VALUES
-- John Doe's Basic Vocabulary Deck
('apple', 'りんご', 'A common fruit.', 'http://example.com/apple.jpg', 1, 1, CURDATE(), 0, 0, FALSE),
('banana', 'バナナ', 'A yellow fruit.', 'http://example.com/banana.jpg', 1, 1, CURDATE(), 0, 0, FALSE),
('grape', 'ぶどう', 'A small round fruit.', 'http://example.com/default.jpg', 1, 1, CURDATE(), 0, 0, FALSE),

-- John Doe's Intermediate Vocabulary Deck
('philosophy', '哲学', 'The study of the fundamental nature of knowledge.', 'http://example.com/default.jpg', 2, 1, CURDATE(), 0, 0, FALSE),
('culture', '文化', 'The arts and other manifestations of human intellectual achievement.', 'http://example.com/default.jpg', 2, 1, CURDATE(), 0, 0, FALSE),
('architecture', '建築', 'The art or practice of designing and constructing buildings.', 'http://example.com/architecture.jpg', 2, 1, CURDATE(), 0, 0, FALSE),

-- Jane Smith's Advanced Vocabulary Deck
('infrastructure', 'インフラ', 'The basic physical and organizational structures and facilities.', 'http://example.com/default.jpg', 3, 1, CURDATE(), 0, 0, FALSE),
('philanthropy', '慈善活動', 'The desire to promote the welfare of others.', 'http://example.com/philanthropy.jpg', 3, 1, CURDATE(), 0, 0, FALSE),
('dichotomy', '二分法', 'A division or contrast between two things.', 'http://example.com/default.jpg', 3, 1, CURDATE(), 0, 0, FALSE),

-- Jane Smith's Business English Deck
('synergy', '相乗効果', 'The interaction or cooperation of two or more organizations.', 'http://example.com/default.jpg', 4, 1, CURDATE(), 0, 0, FALSE),
('paradigm', 'パラダイム', 'A typical example or pattern of something.', 'http://example.com/default.jpg', 4, 1, CURDATE(), 0, 0, FALSE),
('leverage', 'レバレッジ', 'The exertion of force by means of a lever.', 'http://example.com/leverage.jpg', 4, 1, CURDATE(), 0, 0, FALSE),

-- Alice Johnson's Technical Terms Deck
('algorithm', 'アルゴリズム', 'A step-by-step procedure for calculations.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, FALSE),
('database', 'データベース', 'A structured set of data.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, FALSE),
('encryption', '暗号化', 'The process of converting information or data into a code.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, FALSE),

-- Alice Johnson's Daily Conversation Deck
('hello', 'こんにちは', 'A common greeting.', 'http://example.com/hello.jpg', 6, 1, CURDATE(), 0, 0, FALSE),
('thank you', 'ありがとう', 'A polite expression of gratitude.', 'http://example.com/thankyou.jpg', 6, 1, CURDATE(), 0, 0, FALSE),
('goodbye', 'さようなら', 'A farewell expression.', 'http://example.com/default.jpg', 6, 1, CURDATE(), 0, 0, FALSE);
