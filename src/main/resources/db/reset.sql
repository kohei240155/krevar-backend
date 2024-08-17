SET SQL_SAFE_UPDATES = 0;
UPDATE `iruka_db`.`words` SET `is_normal_mode_correct` = '0';
UPDATE `iruka_db`.`words` SET `next_practice_date` = '2024-08-17 00:00:00';
UPDATE `iruka_db`.`words` SET `correct_count` = '0', `incorrect_count` = '0';
UPDATE `iruka_db`.`words` SET `review_interval_id` = '1';
SET SQL_SAFE_UPDATES = 1;


select * from words;