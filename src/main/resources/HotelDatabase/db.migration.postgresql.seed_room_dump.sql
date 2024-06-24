INSERT INTO room_services (id,name) VALUES
                                    (1,'Room Cleaning'),
                                    (2,'Laundry Service'),
                                    (3,'Wake-up Call'),
                                    (4,'Minibar Refill'),
                                    (5,'Room Service'),
                                    (6,'Luggage Assistance'),
                                    (7,'Concierge Service'),
                                    (8,'Turn Down Service'),
                                    (9,'Daily Newspaper'),
                                    (10,'In-Room Safe'),
                                    (11,'High-Speed Internet'),
                                    (12,'Air Conditioning'),
                                    (13,'Television'),
                                    (14,'Complimentary Toiletries'),
                                    (15,'Coffee Maker'),
                                    (16,'Iron and Ironing Board'),
                                    (17,'Hair Dryer'),
                                    (18,'Bathrobe and Slippers'),
                                    (19,'Pillow Menu'),
                                    (20,'Soundproof Rooms');
-- Assuming the RoomServices table and RoomType enum are already created

DO $$
    DECLARE
        room_counter INTEGER := 1;
        hotel_details_id INTEGER;
        room_type VARCHAR(10);
    BEGIN
        FOR hotel_details_id IN 1..10 LOOP
                FOR i IN 1..2 LOOP
                        IF mod(room_counter, 3) = 1 THEN
                            room_type := 'SINGLE';
                        ELSIF mod(room_counter, 3) = 2 THEN
                            room_type := 'DOUBLE';
                        ELSE
                            room_type := 'SWEET';
                        END IF;

                        -- Insert a Room record
                        INSERT INTO Room (id,hotel_details_id, num_of_bed, space, max_num_of_people, price,type)
                        VALUES (room_counter,hotel_details_id, (RANDOM() * 4 + 1)::INTEGER, (RANDOM() * 100 + 20)::INTEGER, (RANDOM() * 5 + 1)::INTEGER, (RANDOM() * 300 + 50)::INTEGER, room_type);

                        room_counter := room_counter + 1;
                    END LOOP;
            END LOOP;
    END $$;
