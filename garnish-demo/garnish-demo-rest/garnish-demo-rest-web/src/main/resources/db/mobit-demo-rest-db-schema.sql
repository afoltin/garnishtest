create table users (
    id        BIGINT        NOT NULL AUTO_INCREMENT,
    name      VARCHAR(250)  NOT NULL,
    username  VARCHAR(250)  NOT NULL,
    password  VARCHAR(250)  NOT NULL,

    CONSTRAINT PK_USERS          PRIMARY KEY(id),
    CONSTRAINT UK_USERS_USERNAME UNIQUE (username)
);

create table addresses (
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    user_id          BIGINT         NOT NULL,
    textual_address  VARCHAR(250)   NOT NULL,
    latitude         DECIMAL(12, 8) NOT NULL,
    longitude        DECIMAL(12, 8) NOT NULL,

    CONSTRAINT PK_ADDRESSES         PRIMARY KEY (id),
    CONSTRAINT UK_ADDRESSES_USER_ID UNIQUE      (user_id),
    CONSTRAINT FK_ADDRESSES_USER    FOREIGN KEY (user_id) references users(id)
);

create table auth_tokens (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    user_id      BIGINT       NOT NULL,
    auth_token        VARCHAR(250) NOT NULL,
    date_created TIMESTAMP    NOT NULL,

    CONSTRAINT PK_AUTH_TOKENS PRIMARY KEY(id),
    CONSTRAINT FK_AUTH_TOKENS_USER FOREIGN KEY(user_id) references users(id)
);

create table todo_lists (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    user_id   BIGINT       NOT NULL,
    title     VARCHAR(50)  NOT NULL,

    CONSTRAINT PK_TODO_LISTS      PRIMARY KEY(id),
    CONSTRAINT FK_TODO_LISTS_USER FOREIGN KEY(user_id) references users(id)
);

create table todo_items (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    list_id   BIGINT       NOT NULL,
    title     VARCHAR(50)  NOT NULL,

    CONSTRAINT PK_TODO_ITEMS      PRIMARY KEY(id),
    CONSTRAINT FK_TODO_ITEMS_LIST FOREIGN KEY(list_id) references todo_lists(id)
);
