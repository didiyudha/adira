INSERT INTO users (id, username, password, active) VALUES
('001', 'didiyudha@gmail.com', '$2a$08$loVHA0yyeipzJNhHCisr9.40NH.NCh8.Sfk57zQBrOocE3fSA7vxq', TRUE),
('002', 'yudhadidi@yahoo.com', '$2a$08$loVHA0yyeipzJNhHCisr9.40NH.NCh8.Sfk57zQBrOocE3fSA7vxq', TRUE);

INSERT INTO roles (id, name) VALUES
('1', 'USER'),
('2', 'ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES
('001', '1'),
('002', '1');