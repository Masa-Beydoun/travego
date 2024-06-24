

INSERT INTO Hotel (id,name, num_of_rooms, description, stars, city_id, country_id, photo_id) VALUES
                                                                                                 (1,'Hotel A', 50, 'A luxury hotel in the city center.', 5, 1, 169, 1),
                                                                                                 (2,'Hotel B', 75, 'A modern hotel with a great view.', 4, 2, 169, 2),
                                                                                                 (3,'Hotel C', 100, 'A budget hotel for travelers.', 3, 3, 194, 3),
                                                                                                 (4,'Hotel D', 150, 'An elegant hotel with excellent service.', 5, 4, 194, 4),
                                                                                                 (5,'Hotel E', 200, 'A stylish hotel with great amenities.', 4, 1, 169, 5),
                                                                                                 (6,'Hotel F', 60, 'A charming hotel near the river.', 3, 2, 169, 6),
                                                                                                 (7,'Hotel G', 80, 'A cozy hotel in a quiet area.', 2, 3, 194, 7),
                                                                                                 (8,'Hotel H', 120, 'A grand hotel with historic significance.', 5, 4, 194, 8),
                                                                                                 (9,'Hotel I', 90, 'A convenient hotel for business travelers.', 4, 1, 169, 9),
                                                                                                 (10,'Hotel J', 110, 'A picturesque hotel with scenic views.', 5, 2, 169, 10);
-- Insert fake data into the HotelServices table
INSERT INTO hotel_services (id, name) VALUES
                                             (1,'Free WiFi'),
                                             (2,'Swimming Pool'),
                                             (3,'Gym'),
                                             (4,'Spa'),
                                             (5,'Restaurant'),
                                             (6,'Bar'),
                                             (7,'Airport Shuttle'),
                                             (9,'Pet Friendly');

-- Insert fake data into the HotelDetails table with averageRating calculated
WITH random_values AS (
    SELECT
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec1, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc1, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac1, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean1,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec2, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc2, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac2, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean2,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec3, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc3, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac3, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean3,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec4, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc4, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac4, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean4,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec5, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc5, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac5, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean5,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec6, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc6, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac6, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean6,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec7, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc7, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac7, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean7,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec8, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc8, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac8, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean8,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec9, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc9, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac9, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean9,
        ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS sec10, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS loc10, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS fac10, ROUND(CAST(RANDOM() * 10 AS numeric), 2) AS clean10
)
INSERT INTO hotel_details (id,start_time, end_time, price_for_extra_bed, distance_from_city, breakfast_price, photos, hotel_id, security, location, facilities, cleanliness, average_rating)
SELECT
    1,'08:00:12'::TIME, '22:00:12'::TIME, 50, 5.0, 15.0, ARRAY[11,12,13], 1, sec1, loc1, fac1, clean1, (sec1 + loc1 + fac1 + clean1) / 4 FROM random_values
UNION ALL SELECT
              2,'09:00:00'::TIME, '21:00:00'::TIME, 60, 3.5, 12.5, ARRAY[14,15,16], 2, sec2, loc2, fac2, clean2, (sec2 + loc2 + fac2 + clean2) / 4 FROM random_values
UNION ALL SELECT
              3,'07:00:00'::TIME, '23:00:00'::TIME, 55, 4.0, 10.0, ARRAY[17,18,19], 3, sec3, loc3, fac3, clean3, (sec3 + loc3 + fac3 + clean3) / 4 FROM random_values
UNION ALL SELECT
              4,'06:00:00'::TIME, '20:00:00'::TIME, 45, 2.5, 18.0, ARRAY[20,21,22], 4, sec4, loc4, fac4, clean4, (sec4 + loc4 + fac4 + clean4) / 4 FROM random_values
UNION ALL SELECT
              5,'10:00:00'::TIME, '19:00:00'::TIME, 70, 6.0, 20.0, ARRAY[23,24,25], 5, sec5, loc5, fac5, clean5, (sec5 + loc5 + fac5 + clean5) / 4 FROM random_values
UNION ALL SELECT
              6,'11:00:00'::TIME, '18:00:00'::TIME, 80, 7.5, 25.0, ARRAY[26,27,28], 6, sec6, loc6, fac6, clean6, (sec6 + loc6 + fac6 + clean6) / 4 FROM random_values
UNION ALL SELECT
              7,'12:00:00'::TIME, '17:00:00'::TIME, 90, 8.0, 30.0, ARRAY[29,30,31], 7, sec7, loc7, fac7, clean7, (sec7 + loc7 + fac7 + clean7) / 4 FROM random_values
UNION ALL SELECT
              8,'13:00:00'::TIME, '16:00:00'::TIME, 100, 9.5, 35.0, ARRAY[32,33,34], 8, sec8, loc8, fac8, clean8, (sec8 + loc8 + fac8 + clean8) / 4 FROM random_values
UNION ALL SELECT
              9,'14:00:00'::TIME, '15:00:00'::TIME, 110, 10.0, 40.0, ARRAY[35,36,37], 9, sec9, loc9, fac9, clean9, (sec9 + loc9 + fac9 + clean9) / 4 FROM random_values
UNION ALL SELECT
              10,'15:00:00'::TIME, '14:00:00'::TIME, 120, 11.5, 45.0, ARRAY[38,39,40], 10, sec10, loc10, fac10, clean10, (sec10 + loc10 + fac10 + clean10) / 4 FROM random_values;
-- Insert data into the hotel_services_hotel join table
-- Assuming each hotel has between 1 to 3 random services
DO $$
    DECLARE
        hotel_id INTEGER;
        service_id INTEGER;
    BEGIN
        FOR hotel_id IN 1..10 LOOP
                FOR service_id IN (SELECT id FROM hotel_services ORDER BY RANDOM() LIMIT 3) LOOP
                        INSERT INTO hotel_services_hotel (hotel_id, hotel_services_id) VALUES (hotel_id, service_id);
                    END LOOP;
            END LOOP;
    END $$;

