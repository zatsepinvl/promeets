CREATE TABLE Users (
	user_id bigint NOT NULL,
	email TEXT NOT NULL,
	phone TEXT NOT NULL,
	password TEXT NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	address TEXT NOT NULL,
	company TEXT NOT NULL,
	position TEXT NOT NULL,
	image_id bigint NOT NULL,
	CONSTRAINT Users_pk PRIMARY KEY (user_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Files (
	file_id bigint NOT NULL,
	url TEXT NOT NULL,
	CONSTRAINT Files_pk PRIMARY KEY (file_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Chats (
	chat_id bigint NOT NULL,
	name TEXT NOT NULL,
	CONSTRAINT Chats_pk PRIMARY KEY (chat_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Chat_user (
	chat_id bigint NOT NULL,
	user_id bigint NOT NULL,
	CONSTRAINT Chat_user_pk PRIMARY KEY (chat_id,user_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Messages (
	message_id bigint NOT NULL,
	chat_id bigint NOT NULL,
	text TEXT NOT NULL,
	CONSTRAINT Messages_pk PRIMARY KEY (message_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Groups (
	group_id bigint NOT NULL,
	name TEXT NOT NULL,
	status TEXT NOT NULL,
	created_time TIMESTAMP NOT NULL,
	type_id bigint NOT NULL,
	admin_id bigint NOT NULL,
	chat_id bigint NOT NULL,
	image_id bigint NOT NULL,
	CONSTRAINT Groups_pk PRIMARY KEY (group_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Group_types (
	type_id bigint NOT NULL,
	name TEXT NOT NULL,
	CONSTRAINT Group_types_pk PRIMARY KEY (type_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Meets (
	meet_id bigint NOT NULL,
	name TEXT NOT NULL,
	admin_id bigint NOT NULL,
	time TIMESTAMP NOT NULL,
	board_id bigint NOT NULL,
	CONSTRAINT Meets_pk PRIMARY KEY (meet_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Boards (
	board_id bigint NOT NULL,
	name TEXT NOT NULL,
	CONSTRAINT Boards_pk PRIMARY KEY (board_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Meet_targets (
	target_id bigint NOT NULL,
	meet_id bigint NOT NULL,
	text TEXT NOT NULL,
	CONSTRAINT Meet_targets_pk PRIMARY KEY (target_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Meet_notes (
	note_id bigint NOT NULL,
	meet_id bigint NOT NULL,
	target_id bigint,
	text TEXT NOT NULL,
	CONSTRAINT Meet_notes_pk PRIMARY KEY (note_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Board_pages (
	page_id bigint NOT NULL,
	board_id bigint NOT NULL,
	number smallint NOT NULL,
	name TEXT NOT NULL,
	CONSTRAINT Board_pages_pk PRIMARY KEY (page_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE Board_items (
	item_id bigint NOT NULL,
	board_page_id bigint NOT NULL,
	target_id bigint,
	file_id bigint,
	data TEXT,
	CONSTRAINT Board_items_pk PRIMARY KEY (item_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE User_groups (
	user_id bigint NOT NULL,
	group_id bigint NOT NULL,
	create_meet_permission smallint NOT NULL,
	invite_permission smallint NOT NULL,
	CONSTRAINT User_groups_pk PRIMARY KEY (user_id,group_id)
) WITH (
  OIDS=FALSE
);



CREATE TABLE User_meets (
	user_id bigint NOT NULL,
	meet_id bigint NOT NULL,
	edit_board_permission smallint NOT NULL,
	CONSTRAINT User_meets_pk PRIMARY KEY (user_id,meet_id)
) WITH (
  OIDS=FALSE
);



ALTER TABLE Users ADD CONSTRAINT Users_fk0 FOREIGN KEY (image_id) REFERENCES Files(file_id);



ALTER TABLE Chat_user ADD CONSTRAINT Chat_user_fk0 FOREIGN KEY (chat_id) REFERENCES Chats(chat_id);
ALTER TABLE Chat_user ADD CONSTRAINT Chat_user_fk1 FOREIGN KEY (user_id) REFERENCES Users(user_id);

ALTER TABLE Messages ADD CONSTRAINT Messages_fk0 FOREIGN KEY (chat_id) REFERENCES Chats(chat_id);

ALTER TABLE Groups ADD CONSTRAINT Groups_fk0 FOREIGN KEY (type_id) REFERENCES Group_types(type_id);
ALTER TABLE Groups ADD CONSTRAINT Groups_fk1 FOREIGN KEY (admin_id) REFERENCES Users(user_id);
ALTER TABLE Groups ADD CONSTRAINT Groups_fk2 FOREIGN KEY (chat_id) REFERENCES Chats(chat_id);
ALTER TABLE Groups ADD CONSTRAINT Groups_fk3 FOREIGN KEY (image_id) REFERENCES Files(file_id);


ALTER TABLE Meets ADD CONSTRAINT Meets_fk0 FOREIGN KEY (admin_id) REFERENCES Users(user_id);
ALTER TABLE Meets ADD CONSTRAINT Meets_fk1 FOREIGN KEY (board_id) REFERENCES Boards(board_id);


ALTER TABLE Meet_targets ADD CONSTRAINT Meet_targets_fk0 FOREIGN KEY (meet_id) REFERENCES Meets(meet_id);

ALTER TABLE Meet_notes ADD CONSTRAINT Meet_notes_fk0 FOREIGN KEY (meet_id) REFERENCES Meets(meet_id);
ALTER TABLE Meet_notes ADD CONSTRAINT Meet_notes_fk1 FOREIGN KEY (target_id) REFERENCES Meet_targets(target_id);

ALTER TABLE Board_pages ADD CONSTRAINT Board_pages_fk0 FOREIGN KEY (board_id) REFERENCES Boards(board_id);

ALTER TABLE Board_items ADD CONSTRAINT Board_items_fk0 FOREIGN KEY (board_page_id) REFERENCES Board_pages(page_id);
ALTER TABLE Board_items ADD CONSTRAINT Board_items_fk1 FOREIGN KEY (target_id) REFERENCES Meet_targets(target_id);
ALTER TABLE Board_items ADD CONSTRAINT Board_items_fk2 FOREIGN KEY (file_id) REFERENCES Files(file_id);

ALTER TABLE User_groups ADD CONSTRAINT User_groups_fk0 FOREIGN KEY (user_id) REFERENCES Users(user_id);
ALTER TABLE User_groups ADD CONSTRAINT User_groups_fk1 FOREIGN KEY (group_id) REFERENCES Groups(group_id);

ALTER TABLE User_meets ADD CONSTRAINT User_meets_fk0 FOREIGN KEY (user_id) REFERENCES Users(user_id);
ALTER TABLE User_meets ADD CONSTRAINT User_meets_fk1 FOREIGN KEY (meet_id) REFERENCES Meets(meet_id);


