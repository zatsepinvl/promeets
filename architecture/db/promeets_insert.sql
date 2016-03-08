INSERT INTO meet_types(
            type_id, name)
    VALUES (1, 'real');
INSERT INTO meet_types(
            type_id, name)
    VALUES (2, 'remote');

INSERT INTO group_types(
            type_id, name)
    VALUES (1, 'open');
INSERT INTO group_types(
            type_id, name)
    VALUES (2, 'close');

INSERT INTO files(
            file_id, url)
    VALUES (1, 'http://file.txt');
    
INSERT INTO files(
            file_id, url)
    VALUES (2, 'http://image.png');
    
INSERT INTO users(
            user_id, email, phone, password, first_name, last_name, address, 
            company, position, image_id)
    VALUES (1, 'some@mail.com', '88002004040','qwerty' , 'Jone', 'Connor', 'USA West Coast', 
            'Google', 'CEO', 1);
            
INSERT INTO users(
            user_id, email, phone, password, first_name, last_name, address, 
            company, position, image_id)
    VALUES (2, 'some@mail.com', '88002002020','qwerty' , 'Sarah', 'Connor', 'USA West Coast', 
            'Apple', 'CEO', 1);
            INSERT INTO chats(
            chat_id, name)
    VALUES (1, 'chat X');
    
INSERT INTO chats(
            chat_id, name)
    VALUES (2, 'group chat');

INSERT INTO user_chats(
            chat_id, user_id)
    VALUES (1, 1);
    
INSERT INTO user_chats(
            chat_id, user_id)
    VALUES (1, 2);
    
INSERT INTO messages(
            message_id, user_id,chat_id, text)
    VALUES (1,1, 1, 'Hello, Sarah!');  
    
 INSERT INTO messages(
            message_id, user_id,chat_id, text)
    VALUES (2,2, 1, 'Hello, Jone!');
    
INSERT INTO groups(
            group_id, title, status, created_time, type_id, admin_id, chat_id, 
            image_id)
    VALUES (1, 'NetCracker', null, '12.12.2012', 1, 1, 2, 
            2);
            
INSERT INTO boards(
            board_id, title)
    VALUES (1,'board_for_meet_1');
    
INSERT INTO boards(
            board_id, title)
    VALUES (2,'board_for_meet_2');

INSERT INTO board_pages(
            page_id, board_id, number, title)
    VALUES (1, 1, 1, null);
    
INSERT INTO board_pages(
            page_id, board_id, number, title)
    VALUES (2, 2, 1, null);

INSERT INTO board_items(
            item_id, board_page_id, aim_id, file_id, data)
    VALUES (1, 1, null, null, 'some data 1');
    
INSERT INTO board_items(
            item_id, board_page_id, aim_id, file_id, data)
    VALUES (2, 2, null, null, 'some data 2');

INSERT INTO meets(
            meet_id, title, admin_id, time, board_id, image_id, location, 
            description, type_id, group_id)
    VALUES (1, 'The first meet', 1, '12.12.2015', 1, null, null, 
            null, 2, 1);
            
INSERT INTO meets(
            meet_id, title, admin_id, time, board_id, image_id, location, 
            description, type_id, group_id)
    VALUES (2, 'The second meet', 1, '12.12.2016', 2, null, null, 
            null, 2, 1);

INSERT INTO meet_aims(
            aim_id, meet_id, value)
    VALUES (1, 1, 'To discusse the meaning of life');

INSERT INTO meet_aims(
            aim_id, meet_id, value)
    VALUES (2, 2, 'To find out why everything sucks');
    
INSERT INTO meet_notes(
            note_id, meet_id, aim_id, value)
    VALUES (1, 1, 1, 'Everything sucks');
    
INSERT INTO meet_notes(
            note_id, meet_id, aim_id, value)
    VALUES (2, 2, 2, 'Nobody knows');
    
INSERT INTO user_groups(
            user_id, group_id, create_meet_permission, invite_permission)
    VALUES (1, 1, 1, 1);
    
INSERT INTO user_groups(
            user_id, group_id, create_meet_permission, invite_permission)
    VALUES (2, 1, 1, 1);
    
INSERT INTO user_meets(
            user_id, meet_id, edit_board_permission)
    VALUES (1, 1, 1);
    
INSERT INTO user_meets(
            user_id, meet_id, edit_board_permission)
    VALUES (2, 1, 1);

INSERT INTO user_meets(
            user_id, meet_id, edit_board_permission)
    VALUES (1, 2, 1);
    
INSERT INTO user_meets(
            user_id, meet_id, edit_board_permission)
    VALUES (2, 2, 1);
