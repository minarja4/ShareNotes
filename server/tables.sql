-- token, password SHA256
CREATE TABLE User (
    uid      INT UNSIGNED NOT NULL AUTO_INCREMENT,
    email       VARCHAR(100),
    password    CHAR(64),
    token       CHAR(64),
    PRIMARY KEY(uid)
) ENGINE=InnoDB;

CREATE TABLE Note (
    nid         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100),
    note        TEXT,
    version     INT UNSIGNED,
    owner       INT UNSIGNED NOT NULL,
    PRIMARY KEY(nid),
    CONSTRAINT FOREIGN KEY(owner) REFERENCES User(uid)
) ENGINE=InnoDB;

CREATE TABLE Share (
    user        INT UNSIGNED NOT NULL,
    note        INT UNSIGNED NOT NULL,
    readonly    TINYINT(1) DEFAULT '0',
    PRIMARY KEY(user, note)
) ENGINE=InnoDB;

ALTER TABLE Share ADD CONSTRAINT fk_user FOREIGN KEY(user) REFERENCES User(uid),
                  ADD CONSTRAINT fk_note FOREIGN KEY(note) REFERENCES Note(nid);
