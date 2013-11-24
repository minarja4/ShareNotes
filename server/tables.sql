-- token, password SHA256
CREATE TABLE User (
    uid         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    username    VARCHAR(64) NOT NULL,
    email       VARCHAR(100) NOT NULL,
    password    CHAR(64) NOT NULL,
    token       CHAR(64) NOT NULL,
    PRIMARY KEY(uid)
) ENGINE=InnoDB;

CREATE TABLE Note (
    nid         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(100) NOT NULL,
    note        TEXT,
    version     INT UNSIGNED,
    shared      TINYINT(1) DEFAULT '0',
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
