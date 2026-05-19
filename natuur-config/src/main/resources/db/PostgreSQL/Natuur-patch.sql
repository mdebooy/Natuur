/*
 * Copyright (c) 2023 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

/**
 * Author:  Marco de Booij
 * Created: 6 apr. 2023
 */

ALTER TABLE NATUUR.TAXA
  ADD STATUS VARCHAR(2) NULL;

ALTER TABLE NATUUR.RANGEN
  ADD INDIVIDU CHAR(1) NOT NULL DEFAULT 'N';

ALTER TABLE NATUUR.RANGEN
  ADD CONSTRAINT CHK_RAN_INDIVIDU CHECK(INDIVIDU = ANY (ARRAY['J', 'N']));

ALTER TABLE NATUUR.REGIOLIJST_TAXA
  DROP CONSTRAINT PK_REGIOLIJST_TAXA,
  DROP CONSTRAINT FK_RLT_REGIO_ID;

ALTER TABLE NATUUR.REGIOLIJSTEN
  DROP CONSTRAINT PK_REGIOLIJSTEN;

ALTER TABLE NATUUR.REGIOLIJSTEN
  ADD REGIOLIJST_ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY;
ALTER TABLE NATUUR.REGIOLIJSTEN
  ADD CONSTRAINT PK_REGIOLIJSTEN PRIMARY KEY (REGIOLIJST_ID),
  ADD CONSTRAINT UK_RLS_NIVEAU UNIQUE(REGIO_ID, DATUM);
ALTER TABLE NATUUR.REGIOLIJSTEN
  RENAME COLUMN DATUM TO EINDDATUM;
ALTER TABLE
  NATUUR.REGIOLIJSTEN ADD STARTDATUM DATE NULL;
UPDATE NATUUR.REGIOLIJSTEN
  SET STARTDATUM = EINDDATUM;
ALTER TABLE NATUUR.REGIOLIJSTEN
  ALTER COLUMN EINDDATUM DROP NOT NULL;
UPDATE NATUUR.REGIOLIJSTEN
  SET EINDDATUM = NULL;

UPDATE NATUUR.REGIOLIJST_TAXA RLT
  SET  REGIO_ID = RLS.REGIOLIJST_ID
FROM   NATUUR.REGIOLIJSTEN RLS
WHERE  RLT.REGIO_ID = RLS.REGIO_ID;

ALTER TABLE NATUUR.REGIOLIJST_TAXA
  RENAME COLUMN REGIO_ID TO REGIOLIJST_ID;
ALTER TABLE NATUUR.REGIOLIJST_TAXA
  ADD CONSTRAINT PK_REGIOLIJST_TAXA PRIMARY KEY (REGIOLIJST_ID, TAXON_ID),
  ADD CONSTRAINT FK_RLT_REGIOLIJST_ID FOREIGN KEY (REGIOLIJST_ID)
  REFERENCES NATUUR.REGIOLIJSTEN (REGIOLIJST_ID);

ALTER TABLE NATUUR.REGIOLIJST_TAXA
  ADD CONSTRAINT CHK_RLT_STATUS CHECK (STATUS = LOWER(STATUS));

CREATE TABLE NATUUR.TAXONBESCHRIJVINGEN (
  BESCHRIJVING                    VARCHAR(4000)   NOT NULL,
  BESCHRIJVINGTYPE                VARCHAR(10)     NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL,
  CONSTRAINT PK_TAXONBESCHRIJVINGEN PRIMARY KEY (TAXON_ID, BESCHRIJVINGTYPE)
);
ALTER TABLE NATUUR.TAXONBESCHRIJVINGEN
  ADD CONSTRAINT FK_TXB_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

DROP VIEW NATUUR.FOTO_OVERZICHT;
DROP VIEW NATUUR.GEEN_FOTO;
DROP VIEW NATUUR.OVERZICHT;
DROP VIEW NATUUR.DETAILS;
DROP VIEW NATUUR.TAXONOMIE;

ALTER TABLE NATUUR.TAXA ALTER COLUMN VOLGNUMMER TYPE INTEGER;
ALTER TABLE NATUUR.TAXONNAMEN ALTER COLUMN TAAL TYPE CHAR(3);
ALTER TABLE NATUUR.RANGNAMEN ALTER COLUMN TAAL TYPE CHAR(3);

UPDATE NATUUR.TAXONNAMEN TN
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1 = TN.TAAL;

UPDATE NATUUR.RANGNAMEN RN
SET    TAAL = T.ISO_639_2T
FROM   DOOS.TALEN T
WHERE  T.ISO_639_1 = RN.TAAL;

-- Views
CREATE OR REPLACE VIEW NATUUR.SOORT AS
SELECT   R.NIVEAU, R.RANG
FROM     NATUUR.RANGEN R
WHERE    R.INDIVIDU = 'J'
ORDER BY R.NIVEAU ASC LIMIT 1;

CREATE OR REPLACE VIEW NATUUR.TAXONOMIE AS
WITH RECURSIVE Q AS (
  SELECT H.*, 1 AS LEVEL, ARRAY[H.TAXON_ID] AS BREADCRUMB
  FROM   NATUUR.TAXA H
  WHERE  H.PARENT_ID IS NULL
  UNION ALL
  SELECT HI.*, Q_1.LEVEL + 1 AS LEVEL,
         Q_1.BREADCRUMB || HI.TAXON_ID
  FROM   Q Q_1
           JOIN NATUUR.TAXA HI ON HI.PARENT_ID = Q_1.TAXON_ID)
SELECT   TAXON_ID, VOLGNUMMER, PARENT_ID, RANG, STATUS, LATIJNSENAAM,
         OPMERKING, LEVEL, Q.BREADCRUMB AS PATH
FROM     Q;

CREATE OR REPLACE VIEW NATUUR.DETAILS AS
WITH FOTO_COUNTS AS (
SELECT   DISTINCT WNM.TAXON_ID
FROM     NATUUR.FOTOS FOT
         JOIN NATUUR.WAARNEMINGEN WNM ON FOT.WAARNEMING_ID = WNM.WAARNEMING_ID)
SELECT   P.TAXON_ID AS PARENT_ID, P.VOLGNUMMER AS PARENT_VOLGNUMMER,
         P.RANG AS PARENT_RANG, P.STATUS as PARENT_STATUS,
         P.LATIJNSENAAM AS PARENT_LATIJNSENAAM,
         R.INDIVIDU, R.NIVEAU,
         T.TAXON_ID, T.VOLGNUMMER, T.RANG, T.STATUS, T.LATIJNSENAAM,
         T.OPMERKING, LEAST(COALESCE(F.TAXON_ID, 0), 1) OP_FOTO
FROM     NATUUR.TAXONOMIE T
            JOIN NATUUR.TAXA P      ON  P.TAXON_ID = ANY(T.PATH)
            JOIN NATUUR.RANGEN R    ON  T.RANG=R.RANG
            LEFT JOIN FOTO_COUNTS F ON T.TAXON_ID = F.TAXON_ID;

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID, DET.PARENT_RANG, DET.PARENT_STATUS,
         DET.PARENT_VOLGNUMMER, DET.PARENT_LATIJNSENAAM, DET.TAXON_ID, DET.RANG,
         DET.STATUS, DET.VOLGNUMMER, DET.LATIJNSENAAM, FOT.TAXON_SEQ, WNM.DATUM,
         FOT.FOTO_BESTAND, FOT.FOTO_DETAIL, GEB.GEBIED_ID, GEB.LAND_ID,
         GEB.NAAM AS GEBIED, FOT.OPMERKING
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID;

CREATE OR REPLACE VIEW NATUUR.GEEN_FOTO AS
WITH ZONDERFOTO AS (
  SELECT   W.TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W
             LEFT JOIN NATUUR.FOTOS F ON F.WAARNEMING_ID = W.WAARNEMING_ID
  GROUP BY W.TAXON_ID
  HAVING   COUNT(F.FOTO_ID) = 0)
SELECT   D.PARENT_ID, D.PARENT_RANG, D.TAXON_ID
FROM     NATUUR.DETAILS D JOIN ZONDERFOTO Z ON D.TAXON_ID=Z.TAXON_ID;

CREATE OR REPLACE VIEW NATUUR.SOORTENLIJST AS
WITH GEZIEN AS (
  SELECT   DISTINCT TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W)
SELECT   D.PARENT_LATIJNSENAAM AS LATIJNSENAAM, MAX(D.OP_FOTO) AS OP_FOTO,
         D.PARENT_RANG AS RANG, D.PARENT_ID AS TAXON_ID,
         D.PARENT_VOLGNUMMER AS VOLGNUMMER
FROM     NATUUR.DETAILS D JOIN GEZIEN G ON G.TAXON_ID = D.TAXON_ID
                          JOIN NATUUR.SOORT S ON S.RANG = D.PARENT_RANG
GROUP BY D.PARENT_ID, D.PARENT_LATIJNSENAAM ,D.PARENT_RANG, D.PARENT_VOLGNUMMER;

CREATE OR REPLACE VIEW NATUUR.OVERZICHT AS
SELECT   D.PARENT_ID, D.PARENT_VOLGNUMMER, D.PARENT_LATIJNSENAAM,
         D.PARENT_RANG, D.PARENT_STATUS, D.RANG, D.STATUS,
         COUNT(D.TAXON_ID) AS TOTAAL, COUNT(L.TAXON_ID) AS WAARGENOMEN,
         SUM(D.OP_FOTO) AS OP_FOTO
FROM     NATUUR.DETAILS D
           JOIN NATUUR.SOORT S ON D.RANG = S.RANG
           LEFT JOIN NATUUR.SOORTENLIJST L ON D.TAXON_ID = L.TAXON_ID AND D.RANG= S.RANG
GROUP BY D.PARENT_ID, D.PARENT_VOLGNUMMER, D.PARENT_LATIJNSENAAM, D.PARENT_RANG,
         D.PARENT_STATUS, D.RANG, D.STATUS;

-- Constraints
ALTER TABLE NATUUR.RANGNAMEN
  ADD CONSTRAINT CHK_RNM_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT CHK_TAX_STATUS CHECK (STATUS = LOWER(STATUS));

ALTER TABLE NATUUR.TAXONNAMEN
  ADD CONSTRAINT CHK_TNM_TAAL  CHECK (TAAL = LOWER(TAAL));

-- Indexen

-- Grant rechten
GRANT SELECT                         ON TABLE NATUUR.DETAILS              TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT       TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO            TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.OVERZICHT            TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.SOORT                TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.SOORTENLIJST         TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONBESCHRIJVINGEN  TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE            TO NATUUR_SEL;

GRANT SELECT                         ON TABLE NATUUR.DETAILS              TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT       TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO            TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.OVERZICHT            TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.SOORT                TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.SOORTENLIJST         TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE NATUUR.TAXONBESCHRIJVINGEN  TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE            TO NATUUR_UPD;

-- Commentaren
COMMENT ON COLUMN NATUUR.RANGEN.INDIVIDU                      IS 'Is het een rang van individuen.';
COMMENT ON VIEW   NATUUR.DETAILS                              IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_ID                    IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_LATIJNSENAAM          IS 'De wetenschappelijke naam van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_RANG                  IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_STATUS                IS 'De status van de parent taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.PARENT_VOLGNUMMER            IS 'Het volgnummer van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.LATIJNSENAAM                 IS 'De wetenschappelijke naam van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.INDIVIDU                     IS 'Is het een rang van individuen?';
COMMENT ON COLUMN NATUUR.DETAILS.NIVEAU                       IS 'Het niveau van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.OP_FOTO                      IS 'Geeft aan of de taxon op foto staat (1) of niet (0).';
COMMENT ON COLUMN NATUUR.DETAILS.OPMERKING                    IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.RANG                         IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.STATUS                       IS 'De status van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.TAXON_ID                     IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.DETAILS.VOLGNUMMER                   IS 'Het volgnummer van de taxon.';
COMMENT ON VIEW   NATUUR.FOTO_OVERZICHT                       IS 'Deze view bevat alle foto''s met gegevens uit meerdere tabellen.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.DATUM                 IS 'De datum van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_BESTAND          IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_DETAIL           IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_ID               IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED                IS 'De naam van het gebied waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED_ID             IS 'De sleutel van het gebied.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LATIJNSENAAM          IS 'De wetenschappelijke naam van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LAND_ID               IS 'De sleutel van het land waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.OPMERKING             IS 'Een opmerking voor deze foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_ID             IS 'De sleutel van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_LATIJNSENAAM   IS 'De wetenschappelijke naam van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_RANG           IS 'De hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_STATUS         IS 'De status van de parent taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_VOLGNUMMER     IS 'Het volgnummer van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.RANG                  IS 'Dit is de rang van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.STATUS                IS 'De status van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_SEQ             IS 'Dit is het volgnummer van de foto van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_ID              IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.VOLGNUMMER            IS 'Het volgnummer van de taxon.';
COMMENT ON TABLE  NATUUR.FOTOS                                IS 'Deze tabel bevat alle foto''s gemaakt bij de waarnemingen.';
COMMENT ON TABLE  NATUUR.GEBIEDEN                             IS 'Deze tabel bevat alle gebieden waar de waarnemingen zijn gedaan.';
COMMENT ON VIEW   NATUUR.GEEN_FOTO                            IS 'Deze view bevat alle (onder)soorten, met een waarneming, waar geen foto van is.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_ID                  IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_RANG                IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.TAXON_ID                   IS 'De sleutel van de taxon.';
COMMENT ON VIEW   NATUUR.OVERZICHT                            IS 'Deze view bevat een overzicht van alle rangen met info over aantal soorten, waarnemingen en foto''s.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_ID                  IS 'De sleutel van de taxon van de parent.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_LATIJNSENAAM        IS 'De wetenschappelijke naam van de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_RANG                IS 'De parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_STATUS              IS 'De status van de parent taxon.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_VOLGNUMMER          IS 'Het volgnummer van de parent rang';
COMMENT ON COLUMN NATUUR.OVERZICHT.RANG                       IS 'De rang waarop de aantallen zijn berekend (>= so).';
COMMENT ON COLUMN NATUUR.OVERZICHT.STATUS                     IS 'De status van de taxon.';
COMMENT ON COLUMN NATUUR.OVERZICHT.TOTAAL                     IS 'Aantal soorten binnen de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.WAARGENOMEN                IS 'Aantal soorten waargenomen binnen de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.OP_FOTO                    IS 'Aantal soorten gefotografeerd binnen de parent rang.';
COMMENT ON COLUMN NATUUR.REGIOLIJST_TAXA.REGIOLIJST_ID        IS 'De sleutel van de regiolijst.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.EINDDATUM               IS 'De datum tot wanneer de lijst geldig is.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.REGIOLIJST_ID           IS 'De sleutel van de regiolijst.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.STARTDATUM              IS 'De datum vanaf wanneer de lijst geldig is.';
COMMENT ON VIEW   NATUUR.SOORT                                IS 'Deze view geeft de rang voor een soortenlijst.';
COMMENT ON COLUMN NATUUR.SOORT.NIVEAU                         IS 'Het niveau rang.';
COMMENT ON COLUMN NATUUR.SOORT.RANG                           IS 'De rang.';
COMMENT ON VIEW   NATUUR.SOORTENLIJST                         IS 'Deze view bevat taxa, samengevat op het hoogste individu niveau, die gezien zijn.';
COMMENT ON COLUMN NATUUR.SOORTENLIJST.LATIJNSENAAM            IS 'De wetenschappelijke naam van de taxon.';
COMMENT ON COLUMN NATUUR.SOORTENLIJST.OP_FOTO                 IS 'Geeft aan of de taxon op foto staat (1) of niet (0).';
COMMENT ON COLUMN NATUUR.SOORTENLIJST.RANG                    IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.SOORTENLIJST.TAXON_ID                IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.SOORTENLIJST.VOLGNUMMER              IS 'Het volgnummer dat gebruikt wordt in publicaties. Is 0 als er op (wetenschappelijke)naam gesorteerd wordt.';
COMMENT ON COLUMN NATUUR.TAXA.LATIJNSENAAM                    IS 'De wetenschappelijke naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.STATUS                          IS 'De status van de taxon.';
COMMENT ON TABLE  NATUUR.TAXONBESCHRIJVINGEN                  IS 'Deze tabel bevat de beschrijvingen van de TAXA in verschillende types.';
COMMENT ON COLUMN NATUUR.TAXONBESCHRIJVINGEN.BESCHRIJVING     IS 'De beschrijving van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONBESCHRIJVINGEN.BESCHRIJVINGTYPE IS 'Het type van de beschrijving van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONBESCHRIJVINGEN.TAXON_ID         IS 'De sleutel van de taxon.';
COMMENT ON VIEW   NATUUR.TAXONOMIE                            IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LATIJNSENAAM               IS 'De wetenschappelijke naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LEVEL                      IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.OPMERKING                  IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PARENT_ID                  IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PATH                       IS 'Een array met alle hogere niveaus''s van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.RANG                       IS 'De sleutel van rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.STATUS                     IS 'De status van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.TAXON_ID                   IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.VOLGNUMMER                 IS 'Het volgnummer van de taxon.';

-- Default waardes
INSERT INTO DOOS.I18N_LIJSTEN
        (CODE, OMSCHRIJVING)
 VALUES ('natuur.taxon.beschrijving.type', 'Lijst met verschillende beschrijvingtypes.');

-- Aanpassingen
UPDATE NATUUR.TAXA T
  SET STATUS = 'ex'
 WHERE T.UITGESTORVEN = 'J';

ALTER TABLE NATUUR.TAXA
  DROP CONSTRAINT CHK_TAX_UITGESTORVEN;

ALTER TABLE NATUUR.TAXA
  DROP COLUMN UITGESTORVEN;

INSERT INTO NATUUR.RANGEN
  VALUES (26, 'var', 'J'),
         (27, 'frm', 'J');

INSERT INTO NATUUR.RANGNAMEN
         (RANG, NAAM, TAAL)
  VALUES ('frm', 'Vorm',          'nld'),
         ('var', 'Variëteit',     'nld');

INSERT INTO NATUUR.RANGNAMEN
         (RANG, NAAM, TAAL)
  VALUES ('frm', 'Form',        'eng'),
         ('var', 'Variety',     'eng');

INSERT INTO NATUUR.RANGNAMEN
         (RANG, NAAM, TAAL)
  VALUES ('frm', 'Form',          'deu'),
         ('var', 'Varietät',      'deu');

UPDATE NATUUR.RANGEN
SET    INDIVIDU='J'
WHERE  RANG IN ('so', 'oso');