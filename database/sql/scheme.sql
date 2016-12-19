--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.1
-- Dumped by pg_dump version 9.6.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: board_items; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE board_items (
    item_id bigint NOT NULL,
    task_id bigint,
    file_id bigint,
    data text
);


ALTER TABLE board_items OWNER TO pmadmin;

--
-- Name: board_items_item_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE board_items_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE board_items_item_id_seq OWNER TO pmadmin;

--
-- Name: board_items_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE board_items_item_id_seq OWNED BY board_items.item_id;


--
-- Name: boards; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE boards (
    board_id bigint NOT NULL,
    title text,
    data text,
    meet_id bigint,
    editor_id bigint
);


ALTER TABLE boards OWNER TO pmadmin;

--
-- Name: board_pages_page_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE board_pages_page_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE board_pages_page_id_seq OWNER TO pmadmin;

--
-- Name: board_pages_page_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE board_pages_page_id_seq OWNED BY boards.board_id;


--
-- Name: card_files; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE card_files (
    card_id bigint NOT NULL,
    file_id bigint NOT NULL
);


ALTER TABLE card_files OWNER TO pmadmin;

--
-- Name: cards; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE cards (
    card_id bigint NOT NULL,
    meet_id bigint,
    user_id bigint,
    title text,
    content text,
    image_id bigint,
    "time" bigint
);


ALTER TABLE cards OWNER TO pmadmin;

--
-- Name: cards_card_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE cards_card_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cards_card_id_seq OWNER TO pmadmin;

--
-- Name: cards_card_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE cards_card_id_seq OWNED BY cards.card_id;


--
-- Name: chats; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE chats (
    chat_id bigint NOT NULL,
    title text,
    image_id bigint,
    group_id bigint
);


ALTER TABLE chats OWNER TO pmadmin;

--
-- Name: chats_chat_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE chats_chat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE chats_chat_id_seq OWNER TO pmadmin;

--
-- Name: chats_chat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE chats_chat_id_seq OWNED BY chats.chat_id;


--
-- Name: files; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE files (
    file_id bigint NOT NULL,
    url text,
    name text,
    original_url text,
    small text,
    medium text,
    large text,
    original text,
    "time" bigint
);


ALTER TABLE files OWNER TO pmadmin;

--
-- Name: files_file_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE files_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE files_file_id_seq OWNER TO pmadmin;

--
-- Name: files_file_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE files_file_id_seq OWNED BY files.file_id;


--
-- Name: group_types; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE group_types (
    type_id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE group_types OWNER TO pmadmin;

--
-- Name: group_types_type_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE group_types_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE group_types_type_id_seq OWNER TO pmadmin;

--
-- Name: group_types_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE group_types_type_id_seq OWNED BY group_types.type_id;


--
-- Name: groups; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE groups (
    group_id bigint NOT NULL,
    title text NOT NULL,
    status text,
    created_time timestamp without time zone,
    type_id bigint,
    admin_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    image_id bigint,
    "time" bigint
);


ALTER TABLE groups OWNER TO pmadmin;

--
-- Name: groups_group_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE groups_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE groups_group_id_seq OWNER TO pmadmin;

--
-- Name: groups_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE groups_group_id_seq OWNED BY groups.group_id;


--
-- Name: meet_notes; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE meet_notes (
    note_id bigint NOT NULL,
    meet_id bigint NOT NULL,
    task_id bigint,
    value text NOT NULL,
    user_id bigint NOT NULL
);


ALTER TABLE meet_notes OWNER TO pmadmin;

--
-- Name: meet_notes_note_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE meet_notes_note_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE meet_notes_note_id_seq OWNER TO pmadmin;

--
-- Name: meet_notes_note_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE meet_notes_note_id_seq OWNED BY meet_notes.note_id;


--
-- Name: meet_tasks; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE meet_tasks (
    task_id bigint NOT NULL,
    meet_id bigint NOT NULL,
    value text NOT NULL,
    checked boolean,
    user_id bigint
);


ALTER TABLE meet_tasks OWNER TO pmadmin;

--
-- Name: meet_targets_target_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE meet_targets_target_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE meet_targets_target_id_seq OWNER TO pmadmin;

--
-- Name: meet_targets_target_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE meet_targets_target_id_seq OWNED BY meet_tasks.task_id;


--
-- Name: meet_types; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE meet_types (
    type_id bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE meet_types OWNER TO pmadmin;

--
-- Name: meet_types_type_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE meet_types_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE meet_types_type_id_seq OWNER TO pmadmin;

--
-- Name: meet_types_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE meet_types_type_id_seq OWNED BY meet_types.type_id;


--
-- Name: meets; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE meets (
    meet_id bigint NOT NULL,
    title text NOT NULL,
    admin_id bigint,
    image_id bigint,
    location text,
    description text,
    type_id bigint,
    group_id bigint NOT NULL,
    "time" bigint NOT NULL
);


ALTER TABLE meets OWNER TO pmadmin;

--
-- Name: meets_meet_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE meets_meet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE meets_meet_id_seq OWNER TO pmadmin;

--
-- Name: meets_meet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE meets_meet_id_seq OWNED BY meets.meet_id;


--
-- Name: messages; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE messages (
    message_id bigint NOT NULL,
    chat_id bigint NOT NULL,
    text text NOT NULL,
    user_id bigint,
    "time" bigint,
    viewed boolean DEFAULT false
);


ALTER TABLE messages OWNER TO pmadmin;

--
-- Name: messages_message_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE messages_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE messages_message_id_seq OWNER TO pmadmin;

--
-- Name: messages_message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE messages_message_id_seq OWNED BY messages.message_id;


--
-- Name: test; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE test (
    id bigint NOT NULL,
    value text
);


ALTER TABLE test OWNER TO pmadmin;

--
-- Name: test_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE test_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE test_id_seq OWNER TO pmadmin;

--
-- Name: test_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE test_id_seq OWNED BY test.id;


--
-- Name: user_chats; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_chats (
    chat_id bigint NOT NULL,
    user_id bigint NOT NULL,
    new_messages integer,
    last_message_id bigint
);


ALTER TABLE user_chats OWNER TO pmadmin;

--
-- Name: user_files; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_files (
    user_id bigint NOT NULL,
    file_id bigint NOT NULL
);


ALTER TABLE user_files OWNER TO pmadmin;

--
-- Name: user_group_invites; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_group_invites (
    user_id bigint NOT NULL,
    inviter_id bigint NOT NULL,
    group_id bigint NOT NULL,
    accepted boolean
);


ALTER TABLE user_group_invites OWNER TO pmadmin;

--
-- Name: user_groups; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_groups (
    user_id bigint NOT NULL,
    group_id bigint NOT NULL,
    create_meet_permission smallint NOT NULL,
    invite_permission smallint NOT NULL
);


ALTER TABLE user_groups OWNER TO pmadmin;

--
-- Name: user_info; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_info (
    user_id bigint NOT NULL,
    "position" text,
    company text,
    address text,
    show_email boolean,
    phone text
);


ALTER TABLE user_info OWNER TO pmadmin;

--
-- Name: user_meet_info; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_meet_info (
    user_id bigint NOT NULL,
    meet_id bigint NOT NULL,
    online boolean,
    connected boolean
);


ALTER TABLE user_meet_info OWNER TO pmadmin;

--
-- Name: user_meets; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_meets (
    user_id bigint NOT NULL,
    meet_id bigint NOT NULL,
    edit_board_permission smallint NOT NULL,
    viewed boolean
);


ALTER TABLE user_meets OWNER TO pmadmin;

--
-- Name: user_messages; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE user_messages (
    message_id bigint NOT NULL,
    viewed boolean DEFAULT false,
    user_id bigint NOT NULL
);


ALTER TABLE user_messages OWNER TO pmadmin;

--
-- Name: users; Type: TABLE; Schema: public; Owner: pmadmin
--

CREATE TABLE users (
    user_id bigint NOT NULL,
    email text NOT NULL,
    phone text,
    password text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    address text,
    company text,
    image_id bigint,
    "position" text
);


ALTER TABLE users OWNER TO pmadmin;

--
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: pmadmin
--

CREATE SEQUENCE users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_user_id_seq OWNER TO pmadmin;

--
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: pmadmin
--

ALTER SEQUENCE users_user_id_seq OWNED BY users.user_id;


--
-- Name: board_items item_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY board_items ALTER COLUMN item_id SET DEFAULT nextval('board_items_item_id_seq'::regclass);


--
-- Name: boards board_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY boards ALTER COLUMN board_id SET DEFAULT nextval('board_pages_page_id_seq'::regclass);


--
-- Name: cards card_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY cards ALTER COLUMN card_id SET DEFAULT nextval('cards_card_id_seq'::regclass);


--
-- Name: chats chat_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY chats ALTER COLUMN chat_id SET DEFAULT nextval('chats_chat_id_seq'::regclass);


--
-- Name: files file_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY files ALTER COLUMN file_id SET DEFAULT nextval('files_file_id_seq'::regclass);


--
-- Name: group_types type_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY group_types ALTER COLUMN type_id SET DEFAULT nextval('group_types_type_id_seq'::regclass);


--
-- Name: groups group_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups ALTER COLUMN group_id SET DEFAULT nextval('groups_group_id_seq'::regclass);


--
-- Name: meet_notes note_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_notes ALTER COLUMN note_id SET DEFAULT nextval('meet_notes_note_id_seq'::regclass);


--
-- Name: meet_tasks task_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_tasks ALTER COLUMN task_id SET DEFAULT nextval('meet_targets_target_id_seq'::regclass);


--
-- Name: meet_types type_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_types ALTER COLUMN type_id SET DEFAULT nextval('meet_types_type_id_seq'::regclass);


--
-- Name: meets meet_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets ALTER COLUMN meet_id SET DEFAULT nextval('meets_meet_id_seq'::regclass);


--
-- Name: messages message_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY messages ALTER COLUMN message_id SET DEFAULT nextval('messages_message_id_seq'::regclass);


--
-- Name: test id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY test ALTER COLUMN id SET DEFAULT nextval('test_id_seq'::regclass);


--
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY users ALTER COLUMN user_id SET DEFAULT nextval('users_user_id_seq'::regclass);


--
-- Name: board_items board_items_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY board_items
    ADD CONSTRAINT board_items_pk PRIMARY KEY (item_id);


--
-- Name: boards board_pages_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY boards
    ADD CONSTRAINT board_pages_pk PRIMARY KEY (board_id);


--
-- Name: card_files card_file_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY card_files
    ADD CONSTRAINT card_file_pk PRIMARY KEY (card_id, file_id);


--
-- Name: cards cards_pk1; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY cards
    ADD CONSTRAINT cards_pk1 PRIMARY KEY (card_id);


--
-- Name: user_chats chat_user_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_chats
    ADD CONSTRAINT chat_user_pk PRIMARY KEY (chat_id, user_id);


--
-- Name: chats chats_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY chats
    ADD CONSTRAINT chats_pk PRIMARY KEY (chat_id);


--
-- Name: files files_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY files
    ADD CONSTRAINT files_pk PRIMARY KEY (file_id);


--
-- Name: group_types group_types_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY group_types
    ADD CONSTRAINT group_types_pk PRIMARY KEY (type_id);


--
-- Name: groups groups_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_pk PRIMARY KEY (group_id);


--
-- Name: user_meet_info meet_info_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meet_info
    ADD CONSTRAINT meet_info_pk PRIMARY KEY (user_id, meet_id);


--
-- Name: meet_notes meet_notes_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_notes
    ADD CONSTRAINT meet_notes_pk PRIMARY KEY (note_id);


--
-- Name: meet_tasks meet_targets_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_tasks
    ADD CONSTRAINT meet_targets_pk PRIMARY KEY (task_id);


--
-- Name: meet_types meet_types_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_types
    ADD CONSTRAINT meet_types_pk PRIMARY KEY (type_id);


--
-- Name: meets meets_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets
    ADD CONSTRAINT meets_pk PRIMARY KEY (meet_id);


--
-- Name: messages messages_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_pk PRIMARY KEY (message_id);


--
-- Name: users un_1; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY users
    ADD CONSTRAINT un_1 UNIQUE (email);


--
-- Name: user_files user_files_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_files
    ADD CONSTRAINT user_files_pk PRIMARY KEY (user_id, file_id);


--
-- Name: user_group_invites user_group_invites_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_group_invites
    ADD CONSTRAINT user_group_invites_pk PRIMARY KEY (user_id, group_id);


--
-- Name: user_groups user_groups_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_pk PRIMARY KEY (user_id, group_id);


--
-- Name: user_info user_info_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_info
    ADD CONSTRAINT user_info_pk PRIMARY KEY (user_id);


--
-- Name: user_meets user_meets_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meets
    ADD CONSTRAINT user_meets_pk PRIMARY KEY (user_id, meet_id);


--
-- Name: user_messages user_message_pk1; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_messages
    ADD CONSTRAINT user_message_pk1 PRIMARY KEY (user_id, message_id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pk PRIMARY KEY (user_id);


--
-- Name: fki_meet_notes_fk2; Type: INDEX; Schema: public; Owner: pmadmin
--

CREATE INDEX fki_meet_notes_fk2 ON meet_notes USING btree (user_id);


--
-- Name: board_items board_items_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY board_items
    ADD CONSTRAINT board_items_fk1 FOREIGN KEY (task_id) REFERENCES meet_tasks(task_id);


--
-- Name: board_items board_items_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY board_items
    ADD CONSTRAINT board_items_fk2 FOREIGN KEY (file_id) REFERENCES files(file_id);


--
-- Name: boards boards_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY boards
    ADD CONSTRAINT boards_fk1 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: boards boards_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY boards
    ADD CONSTRAINT boards_fk2 FOREIGN KEY (editor_id) REFERENCES users(user_id);


--
-- Name: card_files card_file_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY card_files
    ADD CONSTRAINT card_file_fk1 FOREIGN KEY (card_id) REFERENCES cards(card_id);


--
-- Name: card_files card_file_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY card_files
    ADD CONSTRAINT card_file_fk2 FOREIGN KEY (file_id) REFERENCES files(file_id);


--
-- Name: cards cards_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY cards
    ADD CONSTRAINT cards_fk1 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: cards cards_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY cards
    ADD CONSTRAINT cards_fk2 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: cards cards_fk3; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY cards
    ADD CONSTRAINT cards_fk3 FOREIGN KEY (image_id) REFERENCES files(file_id);


--
-- Name: user_chats chat_user_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_chats
    ADD CONSTRAINT chat_user_fk0 FOREIGN KEY (chat_id) REFERENCES chats(chat_id);


--
-- Name: user_chats chat_user_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_chats
    ADD CONSTRAINT chat_user_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: chats chats_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY chats
    ADD CONSTRAINT chats_fk0 FOREIGN KEY (image_id) REFERENCES files(file_id);


--
-- Name: chats chats_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY chats
    ADD CONSTRAINT chats_fk1 FOREIGN KEY (group_id) REFERENCES groups(group_id);


--
-- Name: groups groups_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_fk0 FOREIGN KEY (type_id) REFERENCES group_types(type_id);


--
-- Name: groups groups_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_fk1 FOREIGN KEY (admin_id) REFERENCES users(user_id);


--
-- Name: groups groups_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_fk2 FOREIGN KEY (chat_id) REFERENCES chats(chat_id);


--
-- Name: groups groups_fk3; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY groups
    ADD CONSTRAINT groups_fk3 FOREIGN KEY (image_id) REFERENCES files(file_id);


--
-- Name: user_meet_info meet_info_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meet_info
    ADD CONSTRAINT meet_info_fk0 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_meet_info meet_info_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meet_info
    ADD CONSTRAINT meet_info_fk1 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: meet_notes meet_notes_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_notes
    ADD CONSTRAINT meet_notes_fk0 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: meet_notes meet_notes_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_notes
    ADD CONSTRAINT meet_notes_fk1 FOREIGN KEY (task_id) REFERENCES meet_tasks(task_id);


--
-- Name: meet_notes meet_notes_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_notes
    ADD CONSTRAINT meet_notes_fk2 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: meet_tasks meet_targets_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_tasks
    ADD CONSTRAINT meet_targets_fk0 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: meet_tasks meet_tasks_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meet_tasks
    ADD CONSTRAINT meet_tasks_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: meets meets_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets
    ADD CONSTRAINT meets_fk0 FOREIGN KEY (admin_id) REFERENCES users(user_id);


--
-- Name: meets meets_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets
    ADD CONSTRAINT meets_fk2 FOREIGN KEY (image_id) REFERENCES files(file_id);


--
-- Name: meets meets_fk3; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets
    ADD CONSTRAINT meets_fk3 FOREIGN KEY (type_id) REFERENCES meet_types(type_id);


--
-- Name: meets meets_fk4; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY meets
    ADD CONSTRAINT meets_fk4 FOREIGN KEY (group_id) REFERENCES groups(group_id);


--
-- Name: messages messages_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_fk0 FOREIGN KEY (chat_id) REFERENCES chats(chat_id);


--
-- Name: messages messages_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY messages
    ADD CONSTRAINT messages_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_files user_files_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_files
    ADD CONSTRAINT user_files_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_files user_files_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_files
    ADD CONSTRAINT user_files_fk2 FOREIGN KEY (file_id) REFERENCES files(file_id);


--
-- Name: user_group_invites user_group_invites_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_group_invites
    ADD CONSTRAINT user_group_invites_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_group_invites user_group_invites_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_group_invites
    ADD CONSTRAINT user_group_invites_fk2 FOREIGN KEY (inviter_id) REFERENCES users(user_id);


--
-- Name: user_group_invites user_group_invites_fk3; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_group_invites
    ADD CONSTRAINT user_group_invites_fk3 FOREIGN KEY (group_id) REFERENCES groups(group_id);


--
-- Name: user_groups user_groups_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_fk0 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_groups user_groups_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_groups
    ADD CONSTRAINT user_groups_fk1 FOREIGN KEY (group_id) REFERENCES groups(group_id);


--
-- Name: user_info user_info_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_info
    ADD CONSTRAINT user_info_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_meets user_meets_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meets
    ADD CONSTRAINT user_meets_fk0 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_meets user_meets_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_meets
    ADD CONSTRAINT user_meets_fk1 FOREIGN KEY (meet_id) REFERENCES meets(meet_id);


--
-- Name: user_messages user_message_fk1; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_messages
    ADD CONSTRAINT user_message_fk1 FOREIGN KEY (user_id) REFERENCES users(user_id);


--
-- Name: user_messages user_message_fk2; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY user_messages
    ADD CONSTRAINT user_message_fk2 FOREIGN KEY (message_id) REFERENCES messages(message_id);


--
-- Name: users users_fk0; Type: FK CONSTRAINT; Schema: public; Owner: pmadmin
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_fk0 FOREIGN KEY (image_id) REFERENCES files(file_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA public TO pmadmin;


--
-- PostgreSQL database dump complete
--

