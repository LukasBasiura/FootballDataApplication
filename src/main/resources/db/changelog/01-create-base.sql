-- liquibase formatted sql
-- changeset lbasiura:1
CREATE TABLE country
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    code VARCHAR(255)          NULL,
    flag VARCHAR(255)          NULL,
    CONSTRAINT pk_country PRIMARY KEY (id)
);
ALTER TABLE country
    ADD CONSTRAINT uc_country_name UNIQUE (name);

CREATE TABLE league
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NULL,
    type       VARCHAR(255)          NULL,
    logo       VARCHAR(255)          NULL,
    country_id BIGINT                NULL,
    CONSTRAINT pk_league PRIMARY KEY (id)
);

ALTER TABLE league
    ADD CONSTRAINT uc_4bdef86273534cfa8d729b80e UNIQUE (name, country_id);

ALTER TABLE league
    ADD CONSTRAINT FK_LEAGUE_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);

CREATE TABLE season
(
    id      BIGINT AUTO_INCREMENT NOT NULL,
    year    VARCHAR(255)          NULL,
    current BIT(1)                NULL,
    CONSTRAINT pk_season PRIMARY KEY (id)
);
ALTER TABLE season
    ADD CONSTRAINT uc_season_year UNIQUE (year);

CREATE TABLE season_leagues(
    seasons_id BIGINT,
    leagues_id BIGINT,
    FOREIGN KEY (seasons_id) REFERENCES season(id),
    FOREIGN KEY (leagues_id) REFERENCES league(id)
);

CREATE TABLE team
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(255)          NULL,
    logo       VARCHAR(255)          NULL,
    national   BIT(1)                NULL,
    country_id BIGINT                NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);
ALTER TABLE team
    ADD CONSTRAINT uc_team_name UNIQUE (name);
ALTER TABLE team
    ADD CONSTRAINT FK_TEAM_ON_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id);
CREATE TABLE statistic
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    shots_on_goal     VARCHAR(255)          NULL,
    shots_off_goal    VARCHAR(255)          NULL,
    total_shots       VARCHAR(255)          NULL,
    blocked_shots     VARCHAR(255)          NULL,
    shots_inside_box  VARCHAR(255)          NULL,
    shots_outside_box VARCHAR(255)          NULL,
    fouls             VARCHAR(255)          NULL,
    corner_kicks      VARCHAR(255)          NULL,
    offsides          VARCHAR(255)          NULL,
    ball_possession   VARCHAR(255)          NULL,
    yellow_cards      VARCHAR(255)          NULL,
    red_cards         VARCHAR(255)          NULL,
    goalkeeper_saves  VARCHAR(255)          NULL,
    total_passes      VARCHAR(255)          NULL,
    passes_accurate   VARCHAR(255)          NULL,
    CONSTRAINT pk_statistic PRIMARY KEY (id)
);
CREATE TABLE `match`
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    date         date                  NOT NULL,
    league_id    BIGINT                NULL,
    home_id      BIGINT                NULL,
    away_id      BIGINT                NULL,
    home_stat_id BIGINT                NULL,
    away_stat_id BIGINT                NULL,
    home_full    VARCHAR(255)          NULL,
    away_full    VARCHAR(255)          NULL,
    home_half    VARCHAR(255)          NULL,
    away_half    VARCHAR(255)          NULL,
    home_extra   VARCHAR(255)          NULL,
    away_extra   VARCHAR(255)          NULL,
    home_pen     VARCHAR(255)          NULL,
    away_pen     VARCHAR(255)          NULL,
    CONSTRAINT pk_match PRIMARY KEY (id)
);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_AWAY FOREIGN KEY (away_id) REFERENCES team (id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_AWAYSTAT FOREIGN KEY (away_stat_id) REFERENCES statistic (id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_HOME FOREIGN KEY (home_id) REFERENCES team (id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_HOMESTAT FOREIGN KEY (home_stat_id) REFERENCES statistic (id);

ALTER TABLE `match`
    ADD CONSTRAINT FK_MATCH_ON_LEAGUE FOREIGN KEY (league_id) REFERENCES league (id);

INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2008', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2012', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2016', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2020', true);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2009', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2013', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2017', true);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2010', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2011', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2014', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2015', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2018', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2019', false);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2021', true);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2022', true);
INSERT INTO PUBLIC.SEASON (YEAR, CURRENT) VALUES ('2023', true);

