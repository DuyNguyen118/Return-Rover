-- Reset sequences to start after the inserted data
SELECT setval('users_user_id_seq', (SELECT MAX(user_id) FROM users));
SELECT setval('admins_admin_id_seq', (SELECT MAX(admin_id) FROM admins));
SELECT setval('lost_items_lost_item_id_seq', (SELECT MAX(lost_item_id) FROM lost_items));
SELECT setval('found_items_found_item_id_seq', (SELECT MAX(found_item_id) FROM found_items));
SELECT setval('item_matches_match_id_seq', (SELECT MAX(match_id) FROM item_matches));
SELECT setval('admin_actions_action_id_seq', (SELECT MAX(action_id) FROM admin_actions));

-- Sample data for users table 
INSERT INTO users (user_id, student_id, fullname, email, phone_number, profile_picture, password, merit_point, created_at) VALUES
(1, 'ITCSIU23006', 'Nguyen Van Bao Duy', 'baoduy.song@gmail.com', '0933132794', 'profile1.jpg', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 10, '2025-05-28 15:00:00'),
(2, 'ITCSIU23050', 'Nguyen Ngoc Van Anh', 'nnvanh.ityu@gmail.com', '0912345679', 'profile2.jpg', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 5, '2025-05-27 14:30:00'),
(3, 'SE12347', 'Bob Johnson', 'bob.johnson@example.com', '0912345680', 'profile3.jpg', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 20, '2025-05-26 10:15:00'),
(4, 'SE12348', 'Alice Brown', 'alice.brown@example.com', '0912345681', 'profile4.jpg', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 2, '2025-05-25 09:45:00'),
(5, 'SE12349', 'Charlie Davis', 'charlie.davis@example.com', '0912345682', 'profile5.jpg', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 15, '2025-05-24 16:20:00')
ON CONFLICT (user_id) DO NOTHING;

-- Sample data for admins table 
INSERT INTO admins (admin_id, name, email, password, student_id) VALUES
(1, 'Admin One', 'admin1@example.com', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 'ADM001'),
(2, 'Admin Two', 'admin2@example.com', '$2a$12$ze8FwxJ3hgE8F.NVVi3FCuM8o5tx2kuZznhtSXFOZo1nlrW/SPtj2', 'ADM002')
ON CONFLICT (admin_id) DO NOTHING;

-- Sample data for lost_items table
INSERT INTO lost_items (lost_item_id, user_id, title, description, category, type, location, lost_date, image_url, created_at) VALUES
(1, 1, 'Black Wallet', 'Lost my black leather wallet with student ID and some cash', 'Accessories', 'lost', 'A1.109', '2025-05-20', 'wallet.jpg', '2025-05-20 10:30:00'),
(2, 2, 'Silver Laptop Charger', 'Dell 65W charger, lost near the library', 'Electronics', 'lost', 'Library 2nd floor', '2025-05-21', 'charger.jpg', '2025-05-21 14:15:00'),
(3, 3, 'Blue Water Bottle', 'Blue Hydro Flask with stickers on it', 'Other', 'lost', 'Canteen', '2025-05-22', 'bottle.jpg', '2025-05-22 09:45:00'),
(4, 4, 'Wireless Earbuds', 'White Samsung Galaxy Buds in a case', 'Electronics', 'lost', 'A1.208', '2025-05-23', 'earbuds.jpg', '2025-05-23 12:30:00'),
(5, 5, 'Textbook: Advanced Math', 'Calculus 2 textbook with notes inside', 'Documents', 'lost', 'A2.104', '2025-05-24', 'book.jpg', '2025-05-24 16:20:00')
ON CONFLICT (lost_item_id) DO NOTHING;

-- Sample data for found_items table
INSERT INTO found_items (found_item_id, user_id, title, description, category, type, location, found_date, image_url, created_at) VALUES
(1, 2, 'Black Wallet', 'Found in the cafeteria with some cash and cards', 'Accessories', 'found', 'Canteen', '2025-05-20', 'found_wallet.jpg', '2025-05-20 11:30:00'),
(2, 3, 'Silver Laptop Charger', 'Found plugged in the library study area', 'Electronics', 'found', 'Library 2nd floor', '2025-05-21', 'found_charger.jpg', '2025-05-21 15:30:00'),
(3, 4, 'Blue Water Bottle', 'Left in the gym locker room', 'Other', 'found', 'Canteen', '2025-05-22', 'found_bottle.jpg', '2025-05-22 10:30:00'),
(4, 5, 'Wireless Earbuds', 'Found on a bench outside Building C', 'Electronics', 'found', 'A1.612', '2025-05-23', 'found_earbuds.jpg', '2025-05-23 13:45:00'),
(5, 1, 'Math Textbook', 'Found in the study area, has notes inside', 'Documents', 'found', 'A2.104', '2025-05-24', 'found_book.jpg', '2025-05-24 17:30:00')
ON CONFLICT (found_item_id) DO NOTHING;

-- No sample data for item_matches and admin_actions - starting with empty tables

-- Reset sequences to start after the inserted data
SELECT setval('users_user_id_seq', (SELECT MAX(user_id) FROM users));
SELECT setval('admins_admin_id_seq', (SELECT MAX(admin_id) FROM admins));
SELECT setval('lost_items_lost_item_id_seq', (SELECT MAX(lost_item_id) FROM lost_items));
SELECT setval('found_items_found_item_id_seq', (SELECT MAX(found_item_id) FROM found_items));
SELECT setval('item_matches_match_id_seq', 1, false);  -- Start from 1 for new matches
SELECT setval('admin_actions_action_id_seq', 1, false); -- Start from 1 for new actions