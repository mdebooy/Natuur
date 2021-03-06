﻿-- Kreatie van alle objecten voor het Natuur schema.
--
-- Copyright 2015 Marco de Booij
--
-- Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
-- the European Commission - subsequent versions of the EUPL (the "Licence");
-- you may not use this work except in compliance with the Licence. You may
-- obtain a copy of the Licence at:
--
-- http://www.osor.eu/eupl
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the Licence for the specific language governing permissions and
-- limitations under the Licence.
--
-- Project: Natuur
-- Author: Marco de Booij

\echo    Passwords
\prompt 'NATUUR_APP: ' natuur_app_pw
\set q_natuur_app_pw '\'':natuur_app_pw'\''

CREATE SCHEMA NATUUR;

-- Gebruikers en rollen.
CREATE ROLE NATUUR_APP LOGIN
  PASSWORD :q_natuur_app_pw
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE ROLE NATUUR_SEL NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;
CREATE ROLE NATUUR_UPD NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

GRANT USAGE ON SCHEMA NATUUR  TO NATUUR_SEL;
GRANT USAGE ON SCHEMA NATUUR  TO NATUUR_UPD;

-- Rechten op andere schema's
GRANT USAGE ON SCHEMA SEDES   TO NATUUR_SEL;
GRANT USAGE ON SCHEMA SEDES   TO NATUUR_UPD;
GRANT SELECT ON SEDES.LANDEN, SEDES.LANDNAMEN TO NATUUR_SEL;
GRANT SELECT ON SEDES.LANDEN, SEDES.LANDNAMEN TO NATUUR_UPD;

GRANT NATUUR_UPD TO NATUUR_APP;

GRANT CONNECT ON DATABASE :DBNAME TO NATUUR_APP;

-- Sequences
CREATE SEQUENCE NATUUR.SEQ_FOTOS
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE NATUUR.SEQ_GEBIEDEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE NATUUR.SEQ_TAXA
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE SEQUENCE NATUUR.SEQ_WAARNEMINGEN
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

-- Tabellen
CREATE TABLE NATUUR.FOTOS (
  DATUM                           DATE,
  FOTO_BESTAND                    VARCHAR(255),
  FOTO_DETAIL                     VARCHAR(20),
  FOTO_ID                         INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_FOTOS'::REGCLASS),
  GEBIED_ID                       INTEGER,
  OPMERKING                       VARCHAR(2000),
  TAXON_ID                        INTEGER,
  TAXON_SEQ                       NUMERIC(3)      NOT NULL  DEFAULT 0,
  WAARNEMING_ID                   INTEGER,
  CONSTRAINT PK_FOTOS PRIMARY KEY (FOTO_ID)
);

CREATE TABLE NATUUR.GEBIEDEN (
  GEBIED_ID                       INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_GEBIEDEN'::REGCLASS),
  LAND_ID                         INTEGER         NOT NULL,
  LATITUDE                        CHAR(1),
  LATITUDE_GRADEN                 NUMERIC(2),
  LATITUDE_MINUTEN                NUMERIC(2),
  LATITUDE_SECONDEN               NUMERIC(5,3),
  LONGITUDE                       CHAR(1),
  LONGITUDE_GRADEN                NUMERIC(3),
  LONGITUDE_MINUTEN               NUMERIC(2),
  LONGITUDE_SECONDEN              NUMERIC(5,3),
  NAAM                            VARCHAR(255)    NOT NULL,
  CONSTRAINT PK_GEBIEDEN PRIMARY KEY (GEBIED_ID)
);

CREATE TABLE NATUUR.RANGEN (
  NIVEAU                          INTEGER         NOT NULL,
  RANG                            VARCHAR(3)      NOT NULL,
  CONSTRAINT PK_RANGEN PRIMARY KEY (RANG)
);

CREATE TABLE NATUUR.RANGNAMEN (
  NAAM                            VARCHAR(255)    NOT NULL,
  RANG                            VARCHAR(3)      NOT NULL,
  TAAL                            CHARACTER(2)    NOT NULL,
  CONSTRAINT PK_RANGNAMEN PRIMARY KEY (RANG, TAAL));

CREATE TABLE NATUUR.TAXA (
  LATIJNSENAAM                    VARCHAR(255)    NOT NULL,
  OPMERKING                       VARCHAR(2000),
  PARENT_ID                       INTEGER,
  RANG                            VARCHAR(3)      NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_TAXA'::REGCLASS),
  VOLGNUMMER                      SMALLINT        NOT NULL  DEFAULT 0,
  CONSTRAINT PK_TAXA PRIMARY KEY (TAXON_ID)
);

CREATE TABLE NATUUR.TAXONNAMEN (
  NAAM                            VARCHAR(255)    NOT NULL,
  TAAL                            CHARACTER(2)    NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL,
  CONSTRAINT PK_TAXONNAMEN PRIMARY KEY (TAXON_ID, TAAL)
);

CREATE TABLE NATUUR.WAARNEMINGEN (
  AANTAL                          SMALLINT,
  DATUM                           DATE            NOT NULL,
  GEBIED_ID                       INTEGER         NOT NULL,
  OPMERKING                       VARCHAR(2000),
  TAXON_ID                        INTEGER         NOT NULL,
  WAARNEMING_ID                   INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_WAARNEMINGEN'::REGCLASS),
  CONSTRAINT PK_WAARNEMINGEN PRIMARY KEY (WAARNEMING_ID)
);

-- Views
CREATE OR REPLACE VIEW NATUUR.TAXONOMIE AS
WITH RECURSIVE Q AS (
  SELECT H.*::NATUUR.TAXA AS H, 1 AS LEVEL, ARRAY[H.TAXON_ID] AS BREADCRUMB
  FROM   NATUUR.TAXA H
  WHERE  H.PARENT_ID IS NULL
  UNION ALL
  SELECT HI.*::NATUUR.TAXA AS HI, Q_1.LEVEL + 1 AS LEVEL,
         Q_1.BREADCRUMB || HI.TAXON_ID
  FROM   Q Q_1
           JOIN NATUUR.TAXA HI ON HI.PARENT_ID = (Q_1.H).TAXON_ID)
SELECT   (Q.H).TAXON_ID AS TAXON_ID, (Q.H).VOLGNUMMER AS VOLGNUMMER,
         (Q.H).PARENT_ID AS PARENT_ID, (Q.H).RANG AS RANG,
         (Q.H).LATIJNSENAAM AS LATIJNSENAAM, (Q.H).OPMERKING AS OPMERKING,
         Q.LEVEL, Q.BREADCRUMB AS PATH
FROM     Q
ORDER BY Q.BREADCRUMB;

CREATE OR REPLACE VIEW NATUUR.DETAILS AS
SELECT   P.TAXON_ID AS PARENT_ID, P.VOLGNUMMER AS PARENT_VOLGNUMMER,
         P.RANG AS PARENT_RANG, P.LATIJNSENAAM AS PARENT_LATIJNSENAAM,
         R.NIVEAU, T.TAXON_ID, T.VOLGNUMMER, T.RANG, T.LATIJNSENAAM,
         T.OPMERKING, CASE WHEN F.AANTAL IS NULL THEN 0 ELSE 1 END OP_FOTO
FROM     NATUUR.TAXONOMIE T
           JOIN NATUUR.TAXA P
             ON  P.TAXON_ID<>T.TAXON_ID
             AND (P.TAXON_ID =ANY(T.PATH))
           JOIN NATUUR.RANGEN R
             ON  T.RANG=R.RANG
           LEFT JOIN (SELECT   TAXON_ID, COUNT(*) AANTAL
                      FROM     NATUUR.FOTOS
                      GROUP BY TAXON_ID) F
             ON T.TAXON_ID=F.TAXON_ID;

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID AS KLASSE_ID,
         DET.PARENT_VOLGNUMMER AS KLASSE_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM AS KLASSE_LATIJNSENAAM,
         DET.TAXON_ID, DET.VOLGNUMMER, DET.LATIJNSENAAM,
         FOT.TAXON_SEQ, GEB.LAND_ID, GEB.NAAM AS GEBIED
FROM     NATUUR.FOTOS FOT
           JOIN NATUUR.DETAILS DET  ON FOT.TAXON_ID  = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON FOT.GEBIED_ID = GEB.GEBIED_ID
WHERE    DET.PARENT_RANG='kl';

CREATE OR REPLACE VIEW NATUUR.GEEN_FOTO AS
WITH ZONDERFOTO AS (
  SELECT   W.TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W
  EXCEPT
  SELECT   F.TAXON_ID
  FROM     NATUUR.FOTOS F)
SELECT   D.PARENT_ID, D.PARENT_RANG, D.TAXON_ID
FROM     NATUUR.DETAILS D JOIN ZONDERFOTO Z ON D.TAXON_ID=Z.TAXON_ID;

-- Constraints
ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT UK_FOT_NIVEAU UNIQUE(TAXON_ID, TAXON_SEQ);

ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT FK_FOT_GEBIED_ID FOREIGN KEY (GEBIED_ID)
  REFERENCES NATUUR.GEBIEDEN (GEBIED_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT FK_FOT_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT FK_FOT_WAARNEMING_ID FOREIGN KEY (WAARNEMING_ID)
  REFERENCES NATUUR.WAARNEMINGEN (WAARNEMING_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.RANGNAMEN
  ADD CONSTRAINT FK_RNM_RANG FOREIGN KEY (RANG)
  REFERENCES NATUUR.RANGEN (RANG);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LATITUDE CHECK(LATITUDE = ANY (ARRAY['N'::bpchar, 'S'::bpchar]));

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LATITUDE_GRADEN CHECK(LATITUDE_GRADEN >= 0 AND LATITUDE_GRADEN < 90);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LATITUDE_MINUTEN CHECK(LATITUDE_MINUTEN >= 0 AND LATITUDE_MINUTEN < 60);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LATITUDE_SECONDEN CHECK(LATITUDE_SECONDEN >= 0 AND LATITUDE_SECONDEN < 60);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LONGITUDE CHECK(LONGITUDE = ANY (ARRAY['E'::bpchar, 'W'::bpchar]));

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LONGITUDE_GRADEN CHECK(LONGITUDE_GRADEN >= 0 AND LONGITUDE_GRADEN < 180);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LONGITUDE_MINUTEN CHECK(LONGITUDE_MINUTEN >= 0 AND LONGITUDE_MINUTEN < 60);

ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LONGITUDE_SECONDEN CHECK(LONGITUDE_SECONDEN >= 0 AND LONGITUDE_SECONDEN < 60);

ALTER TABLE NATUUR.RANGEN
  ADD CONSTRAINT UK_RAN_NIVEAU UNIQUE(NIVEAU);

ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT UK_TAX_LATIJNSENAAM UNIQUE(LATIJNSENAAM);

ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT FK_TAX_PARENT_ID FOREIGN KEY (PARENT_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT FK_TAX_RANG FOREIGN KEY (RANG)
  REFERENCES NATUUR.RANGEN (RANG)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.TAXONNAMEN
  ADD CONSTRAINT FK_TNM_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.WAARNEMINGEN
  ADD CONSTRAINT FK_WNM_GEBIED_ID FOREIGN KEY (GEBIED_ID)
  REFERENCES NATUUR.GEBIEDEN (GEBIED_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.WAARNEMINGEN
  ADD CONSTRAINT FK_WNM_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

-- Indexen
CREATE UNIQUE INDEX UK_WNM_DATUM_GEBIED_TAXON
  ON NATUUR.WAARNEMINGEN (DATUM, GEBIED_ID, TAXON_ID);

-- Grant rechten
GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTOS          TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEBIEDEN       TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.RANGEN         TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.RANGNAMEN      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXA           TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONNAMEN     TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.WAARNEMINGEN   TO NATUUR_SEL;

GRANT SELECT                         ON TABLE    NATUUR.DETAILS           TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.FOTO_OVERZICHT    TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.FOTOS             TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.GEBIEDEN          TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.GEEN_FOTO         TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.RANGEN            TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.RANGNAMEN         TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_FOTOS         TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_GEBIEDEN      TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_TAXA          TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_WAARNEMINGEN  TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.TAXA              TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.TAXONNAMEN        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.TAXONOMIE         TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.WAARNEMINGEN      TO NATUUR_UPD;

-- Commentaren
COMMENT ON VIEW   NATUUR.DETAILS                            IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_ID                  IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_VOLGNUMMER          IS 'Het volgnummer van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_RANG                IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_LATIJNSENAAM        IS 'De latijnse naam van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.NIVEAU                     IS 'Het niveau van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.TAXON_ID                   IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.VOLGNUMMER                 IS 'Het volgnummer van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.RANG                       IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.LATIJNSENAAM               IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.OPMERKING                  IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.OP_FOTO                    IS 'Geeft aan of de taxon op foto staat (1) of niet (0).';
COMMENT ON VIEW   NATUUR.FOTO_OVERZICHT                     IS 'Deze view bevat alle foto''s met gegevens uit meerdere tabellen.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_ID             IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_ID           IS 'De sleutel van de klasse van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_VOLGNUMMER   IS 'Het volgnummer van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_LATIJNSENAAM IS 'De latijnse naam van de klasse van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_ID            IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.VOLGNUMMER          IS 'Het volgnummer van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LATIJNSENAAM        IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_SEQ           IS 'Dit is het volgnummer van de foto van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LAND_ID             IS 'De sleutel van het land waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED              IS 'De naam van het gebied waar de foto genomen is.';
COMMENT ON TABLE  NATUUR.FOTOS                              IS 'Deze tabel bevat alle foto''s.';
COMMENT ON COLUMN NATUUR.FOTOS.DATUM                        IS 'De datum van de foto (tijdelijk).';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_BESTAND                 IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_DETAIL                  IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_ID                      IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.GEBIED_ID                    IS 'De sleutel van het gebied waarin de foto gemaakt is (deprecated).';
COMMENT ON COLUMN NATUUR.FOTOS.OPMERKING                    IS 'Een opmerking voor deze foto.';
COMMENT ON COLUMN NATUUR.FOTOS.TAXON_ID                     IS 'De sleutel van de taxon op de foto (deprecated).';
COMMENT ON COLUMN NATUUR.FOTOS.TAXON_SEQ                    IS 'Een volgnummer voor de foto voor de betreffende taxon.';
COMMENT ON COLUMN NATUUR.FOTOS.WAARNEMING_ID                IS 'De sleutel van de waarneming.';
COMMENT ON TABLE  NATUUR.GEBIEDEN                           IS 'Deze tabel bevat alle gebieden waar foto''s gemaakt zijn.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.GEBIED_ID                 IS 'De sleut      el van het gebied.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LAND_ID                   IS 'De sleutel van het land waarin dit gebied ligt.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LATITUDE                  IS 'De latitude (N of S).';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LATITUDE_GRADEN           IS 'De latitude. Graden deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LATITUDE_MINUTEN          IS 'De latitude. Minuten deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LATITUDE_SECONDEN         IS 'De latitude. Seconden deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LONGITUDE                 IS 'De longitude (E of W)';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LONGITUDE_GRADEN          IS 'De longitude. Graden deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LONGITUDE_MINUTEN         IS 'De longitude. Minuten deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LONGITUDE_SECONDEN        IS 'De longitude. Seconden deel.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.NAAM                      IS 'De naam van het gebied.';
COMMENT ON VIEW   NATUUR.GEEN_FOTO                          IS 'Deze view bevat alle waarnemingen waar nog geen foto van is.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_RANG              IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON TABLE  NATUUR.RANGEN                             IS 'Deze tabel bevat alle rangen van de taxa met hun niveau.';
COMMENT ON COLUMN NATUUR.RANGEN.NIVEAU                      IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.RANGEN.RANG                        IS 'De rang van een taxon.';
COMMENT ON TABLE  NATUUR.RANGNAMEN                          IS 'Deze tabel bevat de namen van de rangen.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.NAAM                     IS 'De naam van de rang.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.RANG                     IS 'De sleutel van de rang.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.TAAL                     IS 'De taal.';
COMMENT ON TABLE  NATUUR.TAXA                               IS 'Deze tabel bevat alle nodige TAXA (ev. TAXON).';
COMMENT ON COLUMN NATUUR.TAXA.LATIJNSENAAM                  IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.OPMERKING                     IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXA.PARENT_ID                     IS 'De parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.RANG                          IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.TAXON_ID                      IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.VOLGNUMMER                    IS 'Het volgnummer dat gebruikt wordt in publicaties. Is 0 als er op (latijnse)naam gesorteerd wordt.';
COMMENT ON TABLE  NATUUR.TAXONNAMEN                         IS 'Deze tabel bevat de namen van de TAXA in verschillende talen.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.NAAM                    IS 'De naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAAL                    IS 'De taal.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAXON_ID                IS 'De sleutel van de taxon.';
COMMENT ON VIEW   NATUUR.TAXONOMIE                          IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LATIJNSENAAM             IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LEVEL                    IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.OPMERKING                IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PATH                     IS 'Een array met alle hogere niveaus''s van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.RANG                     IS 'De sleutel van rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.VOLGNUMMER               IS 'Het volgnummer van de taxon.';
COMMENT ON TABLE  NATUUR.WAARNEMINGEN                       IS 'Deze tabel bevat alle waarnemingen.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.AANTAL                IS 'Het aantal wat waargenomen is.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.DATUM                 IS 'De datum van de waarneming.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.GEBIED_ID             IS 'Het gebied waar de waarneming is gedaan.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.OPMERKING             IS 'Een opmerking voor deze waarneming.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.TAXON_ID              IS 'De taxon die is waargenomen.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.WAARNEMING_ID         IS 'De sleutel van de waarneming.';

-- Default waardes
INSERT INTO NATUUR.GEBIEDEN
        (GEBIED_ID, LAND_ID, NAAM)
 VALUES (0,0,'Onbekend');

INSERT INTO NATUUR.RANGEN
  VALUES (1 , 'le'),
         (2 , 'do'),
         (3 , 'ri'),
         (4 , 'ori'),
         (5 , 'st'),
         (6 , 'ost'),
         (7 , 'ist'),
         (8 , 'kl'),
         (9 , 'okl'),
         (10, 'sor'),
         (11, 'or'),
         (12, 'oor'),
         (13, 'ior'),
         (14, 'sfa'),
         (15, 'fa'),
         (16, 'ofa'),
         (17, 'ta'),
         (18, 'ota'),
         (19, 'ge'),
         (20, 'oge'),
         (21, 'so'),
         (22, 'oso');
