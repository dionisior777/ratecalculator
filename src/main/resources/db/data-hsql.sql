INSERT INTO mynt.rule (rule_name, priority, type, min, max, rate, reject_flag) VALUES ('Reject', 1, 'WEIGHT', 50.01, null, null, true);
INSERT INTO mynt.rule (rule_name, priority, type, min, max, rate, reject_flag) VALUES ('Heavy Parcel', 2, 'WEIGHT', 10.01, 50.00, 20, false);
INSERT INTO mynt.rule (rule_name, priority, type, min, max, rate, reject_flag) VALUES ('Small Parcel', 3, 'VOLUME', 0.01, 1499.99, 0.03, false);
INSERT INTO mynt.rule (rule_name, priority, type, min, max, rate, reject_flag) VALUES ('Medium Parcel', 4, 'VOLUME', 1500.00, 2499.99, 0.04, false);
INSERT INTO mynt.rule (rule_name, priority, type, min, max, rate, reject_flag) VALUES ('Large Parcel', 5, 'VOLUME', 2500, null, 0.05, false);