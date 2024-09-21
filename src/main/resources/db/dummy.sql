-- subscription_statusesテーブルにダミーデータを挿入
INSERT INTO subscription_statuses (subscription_status, max_image_generation)
VALUES
('free', 1),   -- 無料プラン、1日1枚
('normal', 5), -- 通常プラン、1日5枚
('pro', 10);   -- プロプラン、1日10枚

-- languagesテーブルにダミーデータを挿入
INSERT INTO languages (language_code, language_name)
VALUES
('ja', 'Japanese'), -- Japanese
('en', 'English'), -- English
('es', 'Spanish'); -- Spanish

-- usersテーブルにダミーデータを挿入
INSERT INTO users (
    name,
    email,
    google_id,
    role,
    default_native_language_id,
    default_learning_language_id,
    subscription_status_id,
    image_generation_remaining,
    image_generation_reset_date,
    highlight_color
)
VALUES
('John Doe', 'john.doe@example.com', 'google-id-john', 'USER', 1, 2, 1, 1, CURDATE(), '#FFFF00'),
('Jane Smith', 'jane.smith@example.com', 'google-id-jane', 'USER', 1, 2, 1, 1, CURDATE(), '#FFFF00'),
('Alice Johnson', 'alice.johnson@example.com', 'google-id-alice', 'USER', 1, 2, 1, 1, CURDATE(), '#FFFF00');

-- review_intervalsテーブルにダミーデータを挿入
INSERT INTO review_intervals (interval_days)
VALUES
(0),    -- 0日後
(1),    -- 1日後
(3),    -- 3日後
(7),    -- 7日後
(14),   -- 14日後
(30),   -- 30日後
(60),   -- 60日後
(90),   -- 90日後
(180),  -- 180日後
(300); -- 300日後

-- decksテーブルにダミーデータを挿入
INSERT INTO decks (
    deck_name,
    user_id,
    native_language_id,
    learning_language_id
)
VALUES
('Basic Vocabulary', 1, 1, 2),  -- John Doeのデッキ1
('Intermediate Vocabulary', 1, 1, 2),  -- John Doeのデッキ2
('Advanced Vocabulary', 2, 1, 2),  -- Jane Smithのデッキ1
('Business English', 2, 1, 2),  -- Jane Smithのデッキ2
('Technical Terms', 3, 1, 2),  -- Alice Johnsonのデッキ1
('Daily Conversation', 3, 1, 2);  -- Alice Johnsonのデッキ2

-- wordsテーブルにダミーデータを挿入
INSERT INTO words (
    original_text,
    translated_text,
    nuance_text,
    image_url,
    deck_id,
    review_interval_id,
    next_practice_date,
    correct_count,
    incorrect_count,
    is_extra_mode_correct
)
VALUES
-- John Doe's Basic Vocabulary Deck
('apple', 'りんご', 'A common fruit.', 'http://example.com/apple.jpg', 1, 1, CURDATE(), 0, 0, 0),
('banana', 'バナナ', 'A yellow fruit.', 'http://example.com/banana.jpg', 1, 1, CURDATE(), 0, 0, 0),
('grape', 'ぶどう', 'A small round fruit.', 'http://example.com/default.jpg', 1, 1, CURDATE(), 0, 0, 0),

-- John Doe's Intermediate Vocabulary Deck
('philosophy', '哲学', 'The study of the fundamental nature of knowledge.', 'http://example.com/default.jpg', 2, 1, CURDATE(), 0, 0, 0),
('culture', '文化', 'The arts and other manifestations of human intellectual achievement.', 'http://example.com/default.jpg', 2, 1, CURDATE(), 0, 0, 0),
('architecture', '建築', 'The art or practice of designing and constructing buildings.', 'http://example.com/architecture.jpg', 2, 1, CURDATE(), 0, 0, 0),

-- Jane Smith's Advanced Vocabulary Deck
('infrastructure', 'インフラ', 'The basic physical and organizational structures and facilities.', 'http://example.com/default.jpg', 3, 1, CURDATE(), 0, 0, 0),
('philanthropy', '慈善活動', 'The desire to promote the welfare of others.', 'http://example.com/philanthropy.jpg', 3, 1, CURDATE(), 0, 0, 0),
('dichotomy', '二分法', 'A division or contrast between two things.', 'http://example.com/default.jpg', 3, 1, CURDATE(), 0, 0, 0),

-- Jane Smith's Business English Deck
('synergy', '相乗効果', 'The interaction or cooperation of two or more organizations.', 'http://example.com/default.jpg', 4, 1, CURDATE(), 0, 0, 0),
('paradigm', 'パラダイム', 'A typical example or pattern of something.', 'http://example.com/default.jpg', 4, 1, CURDATE(), 0, 0, 0),
('leverage', 'レバレッジ', 'The exertion of force by means of a lever.', 'http://example.com/leverage.jpg', 4, 1, CURDATE(), 0, 0, 0),

-- Alice Johnson's Technical Terms Deck
('algorithm', 'アルゴリズム', 'A step-by-step procedure for calculations.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, 0),
('database', 'データベース', 'A structured set of data.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, 0),
('encryption', '暗号化', 'The process of converting information or data into a code.', 'http://example.com/default.jpg', 5, 1, CURDATE(), 0, 0, 0),

-- Alice Johnson's Daily Conversation Deck
('hello', 'こんにちは', 'A common greeting.', 'http://example.com/hello.jpg', 6, 1, CURDATE(), 0, 0, 0),
('thank you', 'ありがとう', 'A polite expression of gratitude.', 'http://example.com/thankyou.jpg', 6, 1, CURDATE(), 0, 0, 0),
('goodbye', 'さようなら', 'A farewell expression.', 'http://example.com/default.jpg', 6, 1, CURDATE(), 0, 0, 0);
