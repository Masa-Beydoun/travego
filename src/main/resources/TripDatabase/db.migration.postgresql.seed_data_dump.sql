
-- Dumping data for table `trip-price`
INSERT INTO trip_price (id, hotel_price, flight_price, services_price) VALUES
                                                                           (1, 100, 200, 300),
                                                                           (2, 150, 250, 350),
                                                                           (3, 200, 300, 400),
                                                                           (4, 250, 350, 450);

INSERT Into trip_services(id, name) VALUES
                                        (1,'VIP'),
                                        (2,'Med');

-- Dumping data for table `trip`
INSERT INTO trip (id,name, description, trip_category, start_date, end_date, country_id,
                  flight_company, min_passengers, max_passengers, status, is_private, price_id ) VALUES
                                                                                                    (1,'Trip 1', 'Description 1', 'Adventure_Travel', '2022-01-01', '2022-01-10', 1, 'Qatar_Airways', 1, 10, 'Available', true, 1),
                                                                                                    (2,'Trip 2', 'Description 2', 'Adventure_Travel', '2022-02-01', '2022-02-10', 2, 'Qatar_Airways', 5, 15, 'Completed', false, 2),
                                                                                                    (3,'Trip 3', 'Description 3', 'Cultural', '2022-03-01', '2022-03-10', 3, 'Qatar_Airways', 10, 20, 'InProgress', true, 3),
                                                                                                    (4,'Trip 4', 'Description 4', 'Family_Travel', '2022-04-01', '2022-04-10', 4, 'Qatar_Airways', 15, 25, 'Finished', false, 4);

INSERT INTO trip_service_trip(trip_id, trip_services_id) VALUES
                                                             (1 ,1),
                                                             (1,2),
                                                             (2,1);
INSERT INTO trip_hotel(trip_id, hotel_id) VALUES
                                              (1,1),
                                              (2,2),
                                              (3,3);
-- Dumping data for table `trip-plan`
INSERT INTO trip_plan (id,description, trip_id, place_id, start_date, end_date) VALUES
                                                                                 (1,'Plan 1', 1, 1, '2022-01-01', '2022-01-05'),
                                                                                 (2,'Plan 2', 2, 2, '2022-02-01', '2022-02-05'),
                                                                                 (3,'Plan 3', 3, 3, '2022-03-01', '2022-03-05'),
                                                                                 (4,'Plan 4', 4, 4, '2022-04-01', '2022-04-05');
