-- disable FK checks, otherwise truncate doesn't work
set referential_integrity false;

truncate table todo_items;
truncate table todo_lists;

truncate table auth_tokens;
truncate table users;

set referential_integrity true;
