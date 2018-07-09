INSERT into app_user (version,deleted, email, name, active, password) values (1,b'0','alex@ghb-software.com','Alexandru Gheboianu', b'1','$2a$10$zhlLopDUMrE0NR/aMfejA.Krv2qDQ.hc8K6d1wql3H0HySOYykSFy');


INSERT INTO groups (version, name) VALUES ('1', 'group.administrators');
INSERT INTO wms.groups (version, name) VALUES ('1', 'group.superadmin');
INSERT INTO wms.groups (version, name) VALUES ('1', 'group.warehouseManager');



INSERT INTO role (description, label, name) VALUES ('role.desc.users.read', 'role.users.read', 'USERS_READ')
INSERT INTO role (description, label, name) VALUES ('role.desc.users.write', 'role.users.write', 'USERS_WRITE')
INSERT INTO role (description, label, name) VALUES ('role.desc.superadmin', 'role.superadmin', 'SUPER_ADMIN')

INSERT INTO group_roles (group_id, role_id) VALUES ('1', '1');
INSERT INTO group_roles (group_id, role_id) VALUES ('1', '2');
INSERT INTO group_roles (group_id, role_id) VALUES ('2', '3');

INSERT INTO group_users (group_id) VALUES ('1');


--Required for 'Remember Me' token:
CREATE TABLE IF NOT EXISTS persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);