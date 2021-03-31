-- Kreatie van alle objecten voor het Natuur schema.
--
-- Copyright 2020 Marco de Booij
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

REASSIGN OWNED BY NATUUR TO POSTGRES;
REVOKE ALL ON DATABASE :DBNAME FROM NATUUR;
DROP ROLE NATUUR;

CREATE TABLE NATUUR.RANGNAMEN (
  NAAM                            VARCHAR(255)    NOT NULL,
  RANG                            VARCHAR(3)      NOT NULL,
  TAAL                            CHARACTER(2)    NOT NULL,
  CONSTRAINT PK_TAXONNAMEN PRIMARY KEY (RANG, TAAL));

ALTER TABLE NATUUR.FOTOS
  ADD COLUMN DATUM                           DATE,
  ADD COLUMN FOTO_BESTAND                    VARCHAR(255),
  ADD COLUMN FOTO_DETAIL                     VARCHAR(20),
  ADD COLUMN OPMERKING                       VARCHAR(2000)
  ADD COLUMN WAARNEMING_ID                   INTEGER;

ALTER TABLE NATUUR.RANGNAMEN
  ADD CONSTRAINT FK_RNM_RANG FOREIGN KEY (RANG)
  REFERENCES NATUUR.RANGEN (RANG);

ALTER TABLE NATUUR.TAXA
  ADD COLUMN VOLGNUMMER                      SMALLINT        NOT NULL  DEFAULT 0;

DROP VIEW NATUUR.FOTO_OVERZICHT;
DROP VIEW NATUUR.GEEN_FOTO;
DROP VIEW NATUUR.DETAILS;
DROP VIEW NATUUR.TAXONOMIE;

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

GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.RANGNAMEN      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO      TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE NATUUR.RANGNAMEN      TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_UPD;

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
COMMENT ON COLUMN NATUUR.FOTOS.DATUM                        IS 'De datum van de foto (tijdelijk).';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_BESTAND                 IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.FOTO_DETAIL                  IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTOS.GEBIED_ID                    IS 'De sleutel van het gebied waarin de foto gemaakt is (deprecated).';
COMMENT ON COLUMN NATUUR.FOTOS.OPMERKING                    IS 'Een opmerking voor deze foto.';
COMMENT ON COLUMN NATUUR.FOTOS.TAXON_ID                     IS 'De sleutel van de taxon op de foto (deprecated).';
COMMENT ON COLUMN NATUUR.FOTOS.WAARNEMING_ID                IS 'De sleutel van de waarneming.';
COMMENT ON VIEW   NATUUR.GEEN_FOTO                          IS 'Deze view bevat alle waarnemingen waar nog geen foto van is.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_RANG              IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON TABLE  NATUUR.RANGNAMEN                          IS 'Deze tabel bevat de namen van de rangen.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.NAAM                     IS 'De naam van de rang.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.RANG                     IS 'De sleutel van de rang.';
COMMENT ON COLUMN NATUUR.RANGNAMEN.TAAL                     IS 'De taal.';
COMMENT ON COLUMN NATUUR.TAXA.VOLGNUMMER                    IS 'Het volgnummer dat gebruikt wordt in publicaties. Is 0 als er op (latijnse)naam gesorteerd wordt.';
COMMENT ON VIEW   NATUUR.TAXONOMIE                          IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LATIJNSENAAM             IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LEVEL                    IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.OPMERKING                IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PATH                     IS 'Een array met alle hogere niveaus''s van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.RANG                     IS 'De sleutel van rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.VOLGNUMMER               IS 'Het volgnummer van de taxon.';
