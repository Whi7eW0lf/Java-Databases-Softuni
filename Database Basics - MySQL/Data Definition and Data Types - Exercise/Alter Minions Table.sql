ALTER TABLE minions add (town_id int );

ALTER TABLE minions
ADD FOREIGN KEY (town_id) REFERENCES towns(id);