
-- Dumping data for table `city`
INSERT INTO city (id,name, country_id) VALUES
                                        (1,'Gaza', 169),
                                        (2,'Al-Quds', 169),
                                        (3,'Mekkah', 194),
                                        (4,'Al-Madina', 194);
-- - Insert data into location table (assuming coordinates for Gaza)
INSERT INTO location (id, latitude, longitude)
VALUES (1, 31.524010, 34.368716),  -- Sample location 1 in Gaza
       (2, 31.496553, 34.397222),  -- Sample location 2 in Gaza
       (3, 31.509958, 34.419922),  -- Sample location 3 in Gaza
       (4, 31.537136, 34.348486); -- Sample location 4 in Gaza

-- Insert data into place table referencing locations
INSERT INTO place (id, name, description, opening_time, closing_time, location_id, country_id,city_id)
VALUES (1, 'Great Mosque of Gaza', 'Largest mosque in Gaza', '07:00:00', '17:00:00', 1, 169,1),  -- Reference country_id for Palestine (replace with actual ID)
       (2, 'Al-Bahr Park', 'Beautiful beachfront park', '08:00:00', '20:00:00', 2, 169,2),  -- Reference country_id for Palestine (replace with actual ID)
       (3, 'Khan al-Wakala', 'Historic caravanserai', '09:00:00', '18:00:00', 3, 169,1),  -- Reference country_id for Palestine (replace with actual ID)
       (4, 'Gaza War Museum', 'Museum dedicated to Gaza conflicts', '10:00:00', '16:00:00', 4, 169,1);  -- Reference country_id for Palestine (replace with actual ID)