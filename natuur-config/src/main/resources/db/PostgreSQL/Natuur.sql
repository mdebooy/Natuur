-- Kreatie van alle objecten voor het Natuur schema.
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

\prompt 'Database  : ' db_naam
\echo    Passwords
\prompt 'NATUUR    : ' natuur_pw
\prompt 'NATUUR_APP: ' natuur_app_pw
\set q_db_naam       '\"':db_naam'\"'
\set q_natuur_pw     '\'':natuur_pw'\''
\set q_natuur_app_pw '\'':natuur_app_pw'\''

-- Gebruikers en rollen.
CREATE ROLE NATUUR LOGIN
  PASSWORD :q_natuur_pw
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE ROLE NATUUR_APP LOGIN
  PASSWORD :q_natuur_app_pw
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE;

CREATE SCHEMA NATUUR AUTHORIZATION NATUUR;

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

GRANT CONNECT ON DATABASE :q_db_naam TO NATUUR;
GRANT CONNECT ON DATABASE :q_db_naam TO NATUUR_APP;

-- Connect als NATUUR om de objecten te maken
\c :db_naam natuur

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
  FOTO_ID                         INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_FOTOS'::REGCLASS),
  GEBIED_ID                       INTEGER         NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL,
  TAXON_SEQ                       NUMERIC(3)      NOT NULL  DEFAULT 0,
  CONSTRAINT PK_FOTOS PRIMARY KEY (FOTO_ID)
);

CREATE TABLE NATUUR.GEBIEDEN (
  GEBIED_ID                       INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_GEBIEDEN'::REGCLASS),
  LAND_ID                         INTEGER         NOT NULL,
  NAAM                            VARCHAR(255)    NOT NULL,
  CONSTRAINT PK_GEBIEDEN PRIMARY KEY (GEBIED_ID)
);

CREATE TABLE NATUUR.RANGEN (
  NIVEAU                          INTEGER         NOT NULL,
  RANG                            VARCHAR(3)      NOT NULL,
  CONSTRAINT PK_RANGEN PRIMARY KEY (RANG)
);

CREATE TABLE NATUUR.TAXA (
  LATIJNSENAAM                    VARCHAR(255)    NOT NULL,
  OPMERKING                       VARCHAR(2000),
  PARENT_ID                       INTEGER,
  RANG                            VARCHAR(3)      NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_TAXA'::REGCLASS),
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
CREATE OR REPLACE VIEW natuur.taxonomie AS
WITH RECURSIVE q AS (
  SELECT h.*::natuur.taxa AS h, 1 AS level, ARRAY[h.taxon_id] AS breadcrumb
  FROM   natuur.taxa h
  WHERE  h.parent_id IS NULL
  UNION ALL
  SELECT hi.*::natuur.taxa AS hi, q_1.level + 1 AS level, q_1.breadcrumb || hi.taxon_id
  FROM   q q_1
           JOIN natuur.taxa hi ON hi.parent_id = (q_1.h).taxon_id)
SELECT   (q.h).taxon_id AS taxon_id, (q.h).parent_id AS parent_id, (q.h).rang AS rang,
         (q.h).naam AS naam, (q.h).latijnsenaam AS latijnsenaam,
         (q.h).opmerking AS opmerking, q.level,
         q.breadcrumb AS path
FROM     q
ORDER BY q.breadcrumb;

CREATE OR REPLACE VIEW natuur.details AS
SELECT   p.taxon_id AS parent_id, p.rang AS parent_rang, p.naam AS parent_naam,
         p.latijnsenaam AS parent_latijnsenaam,
         r.niveau, t.taxon_id, t.rang, t.naam, t.latijnsenaam, t.opmerking,
         CASE WHEN f.aantal IS NULL THEN 0 ELSE 1 END op_foto
FROM     natuur.taxonomie t
           JOIN natuur.taxa p
             ON  p.taxon_id<>t.taxon_id
             AND (p.taxon_id =ANY(t.path))
           JOIN natuur.rangen r
             ON  t.rang=r.rang
           LEFT JOIN (SELECT   taxon_id, COUNT(*) aantal
                      FROM     natuur.fotos
                      GROUP BY taxon_id) f
             ON t.taxon_id=f.taxon_id;

CREATE OR REPLACE VIEW natuur.foto_overzicht AS 
 SELECT   fot.foto_id, det.parent_id AS klasse_id,
          det.parent_latijnsenaam AS klasse_latijnsenaam,
          det.taxon_id, det.latijnsenaam,
          fot.taxon_seq, geb.land_id, geb.naam AS gebied
 FROM     natuur.fotos fot
            JOIN natuur.details det ON fot.taxon_id = det.taxon_id
            JOIN natuur.gebieden geb ON fot.gebied_id = geb.gebied_id
 WHERE    det.parent_rang='kl'
 AND      det.rang in ('so', 'oso');

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

-- Grant rechten
GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTOS          TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEBIEDEN       TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.RANGEN         TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXA           TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONNAMEN     TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.WAARNEMINGEN   TO NATUUR_SEL;

GRANT SELECT                         ON TABLE    NATUUR.DETAILS           TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.FOTO_OVERZICHT    TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.FOTOS             TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.GEBIEDEN          TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.RANGEN            TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_FOTOS         TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_GEBIEDEN      TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_TAXA          TO NATUUR_UPD;
GRANT SELECT, UPDATE                 ON SEQUENCE NATUUR.SEQ_WAARNEMINGEN  TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.TAXA              TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.TAXONNAMEN        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.TAXONOMIE         TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.WAARNEMINGEN      TO NATUUR_UPD;

-- Commentaren
COMMENT ON TABLE  NATUUR.FOTOS                      IS 'Deze tabel bevat alle foto''s.';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_ID              IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.GEBIED_ID            IS 'De sleutel van het gebied waarin de foto gemaakt is.';
COMMENT ON COLUMN NATUUR.FOTOS.TAXON_ID             IS 'De sleutel van de taxon op de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.TAXON_SEQ            IS 'Een volgnummer voor de foto voor de betreffende taxon.';
COMMENT ON TABLE  NATUUR.GEBIEDEN                   IS 'Deze tabel bevat alle gebieden waar foto''s gemaakt zijn.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.GEBIED_ID         IS 'De sleutel van het gebied.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.LAND_ID           IS 'De sleutel van het land waarin dit gebied ligt.';
COMMENT ON COLUMN NATUUR.GEBIEDEN.NAAM              IS 'De naam van het gebied.';
COMMENT ON TABLE  NATUUR.RANGEN                     IS 'Deze tabel bevat alle rangen van de taxa met hun niveau.';
COMMENT ON COLUMN NATUUR.RANGEN.NIVEAU              IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.RANGEN.RANG                IS 'De rang van een taxon.';
COMMENT ON TABLE  NATUUR.TAXA                       IS 'Deze tabel bevat alle nodige TAXA (ev. TAXON).';
COMMENT ON COLUMN NATUUR.TAXA.LATIJNSENAAM          IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.NAAM                  IS 'De nederlandse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.OPMERKING             IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXA.PARENT_ID             IS 'De parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.RANG                  IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.TAXON_ID              IS 'De sleutel van de taxon.';
COMMENT ON TABLE  NATUUR.TAXONNAMEN                 IS 'Deze tabel bevat alle nodige TAXA (ev. TAXON).';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.NAAM            IS 'De naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAAL            IS 'De taal.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAXON_ID        IS 'De sleutel van de taxon.';
COMMENT ON TABLE  NATUUR.WAARNEMINGEN               IS 'Deze tabel bevat alle waarnemingen.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.AANTAL        IS 'Het aantal wat waargenomen is.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.DATUM         IS 'De datum van de waarneming.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.GEBIED_ID     IS 'Het gebied waar de waarneming is gedaan.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.OPMERKING     IS 'Een opmerking voor deze waarneming.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.TAXON_ID      IS 'De taxon die is waargenomen.';
COMMENT ON COLUMN NATUUR.WAARNEMINGEN.WAARNEMING_ID IS 'De sleutel van de waarneming.';

-- Default waardes
INSERT INTO NATUUR.GEBIEDEN VALUES (0,0,'Onbekend');

INSERT INTO rangen
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
