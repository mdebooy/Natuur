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
         (Q.H).UITGESTORVEN, Q.LEVEL, Q.BREADCRUMB AS PATH
FROM     Q
ORDER BY Q.BREADCRUMB;

CREATE OR REPLACE VIEW NATUUR.DETAILS AS
SELECT   P.TAXON_ID AS PARENT_ID, P.VOLGNUMMER AS PARENT_VOLGNUMMER,
         P.RANG AS PARENT_RANG, P.LATIJNSENAAM AS PARENT_LATIJNSENAAM,
         R.NIVEAU, T.TAXON_ID, T.VOLGNUMMER, T.RANG, T.LATIJNSENAAM,
         T.OPMERKING, T.UITGESTORVEN,
         CASE WHEN F.AANTAL IS NULL THEN 0 ELSE 1 END OP_FOTO
FROM     NATUUR.TAXONOMIE T
           JOIN NATUUR.TAXA P
             ON  P.TAXON_ID =ANY(T.PATH)
           JOIN NATUUR.RANGEN R
             ON  T.RANG=R.RANG
           LEFT JOIN (SELECT   WNM.TAXON_ID, COUNT(*) AANTAL
                      FROM     NATUUR.FOTOS FOT
                               JOIN NATUUR.WAARNEMINGEN WNM ON FOT.WAARNEMING_ID = WNM.WAARNEMING_ID
                      GROUP BY WNM.TAXON_ID) F
             ON T.TAXON_ID=F.TAXON_ID;

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID, DET.PARENT_RANG, DET.PARENT_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM, DET.TAXON_ID, DET.RANG, DET.VOLGNUMMER,
         DET.LATIJNSENAAM, FOT.TAXON_SEQ, WNM.DATUM, FOT.FOTO_BESTAND,
         FOT.FOTO_DETAIL, GEB.GEBIED_ID, GEB.LAND_ID, GEB.NAAM AS GEBIED,
         FOT.OPMERKING
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID;

CREATE OR REPLACE VIEW NATUUR.GEEN_FOTO AS
WITH ZONDERFOTO AS (
  SELECT   W.TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W
  EXCEPT
  SELECT   W.TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W
             JOIN NATUUR.FOTOS F ON F.WAARNEMING_ID = W.WAARNEMING_ID)
SELECT   D.PARENT_ID, D.PARENT_RANG, D.TAXON_ID
FROM     NATUUR.DETAILS D JOIN ZONDERFOTO Z ON D.TAXON_ID=Z.TAXON_ID;

CREATE OR REPLACE VIEW NATUUR.OVERZICHT AS
WITH WNM AS (
  SELECT   DISTINCT W.TAXON_ID
  FROM     NATUUR.WAARNEMINGEN W)
SELECT   D.PARENT_ID, D.PARENT_VOLGNUMMER, D.PARENT_LATIJNSENAAM,
         D.PARENT_RANG, D.RANG, COUNT(D.TAXON_ID) AS TOTAAL,
         COUNT(WNM.TAXON_ID) AS WAARGENOMEN, SUM(D.OP_FOTO) AS OP_FOTO
FROM     NATUUR.DETAILS D
           LEFT JOIN WNM ON D.TAXON_ID = WNM.TAXON_ID
WHERE    D.RANG IN (SELECT R.RANG
                     FROM   NATUUR.RANGEN R
                       JOIN NATUUR.RANGEN R2 ON R.NIVEAU >= R2.NIVEAU
                         AND R2.RANG = 'so')
GROUP BY D.PARENT_ID, D.PARENT_VOLGNUMMER, D.PARENT_LATIJNSENAAM, D.PARENT_RANG,
         D.RANG;

ALTER TABLE NATUUR.RANGNAMEN
ADD CONSTRAINT CHK_RNM_TAAL  CHECK (TAAL = LOWER(TAAL));

ALTER TABLE NATUUR.TAXONNAMEN
 ADD CONSTRAINT CHK_TNM_TAAL  CHECK (TAAL = LOWER(TAAL));

CREATE INDEX IX_TAX_PARENT_ID
  ON NATUUR.TAXA USING (PARENT_ID);

GRANT SELECT                         ON TABLE NATUUR.DETAILS          TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT   TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.OVERZICHT        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.DETAILS          TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT   TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.GEEN_FOTO        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.OVERZICHT        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE        TO NATUUR_UPD;

COMMENT ON TABLE  NATUUR.FOTOS                              IS 'Deze tabel bevat alle foto''s gemaakt bij de waarnemingen.';
COMMENT ON TABLE  NATUUR.GEBIEDEN                           IS 'Deze tabel bevat alle gebieden waar de waarnemingen zijn gedaan.';

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
COMMENT ON COLUMN NATUUR.DETAILS.UITGESTORVEN               IS 'Is de taxon uitgestorven?';
COMMENT ON VIEW   NATUUR.FOTO_OVERZICHT                     IS 'Deze view bevat alle foto''s met gegevens uit meerdere tabellen.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.DATUM               IS 'De datum van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_BESTAND        IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_DETAIL         IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_ID             IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED              IS 'De naam van het gebied waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED_ID           IS 'De sleutel van het gebied.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LATIJNSENAAM        IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LAND_ID             IS 'De sleutel van het land waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.OPMERKING           IS 'Een opmerking voor deze foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_ID           IS 'De sleutel van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_LATIJNSENAAM IS 'De latijnse naam van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_RANG         IS 'De hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.PARENT_VOLGNUMMER   IS 'Het volgnummer van de hogere rang van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.RANG                IS 'Dit is de rang van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_SEQ           IS 'Dit is het volgnummer van de foto van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_ID            IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.VOLGNUMMER          IS 'Het volgnummer van de taxon.';
COMMENT ON VIEW   NATUUR.GEEN_FOTO                          IS 'Deze view bevat alle (onder)soorten, met een waarneming, waar geen foto van is.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.PARENT_RANG              IS 'De rang van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.GEEN_FOTO.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON VIEW   NATUUR.OVERZICHT                          IS 'Deze view bevat een overzicht van alle rangen met info over aantal soorten, waarnemingen en foto''s.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_ID                IS 'De sleutel van de taxon van de parent.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_LATIJNSENAAM      IS 'De latijnsenaam van de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_RANG              IS 'De parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.PARENT_VOLGNUMMER        IS 'Het volgnummer van de parent rang';
COMMENT ON COLUMN NATUUR.OVERZICHT.RANG                     IS 'De rang waarop de aantallen zijn berekend (>= so).';
COMMENT ON COLUMN NATUUR.OVERZICHT.TOTAAL                   IS 'Aantal soorten binnen de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.WAARGENOMEN              IS 'Aantal soorten waargenomen binnen de parent rang.';
COMMENT ON COLUMN NATUUR.OVERZICHT.OP_FOTO                  IS 'Aantal soorten gefotografeerd binnen de parent rang.';
COMMENT ON VIEW   NATUUR.TAXONOMIE                          IS 'Deze view bevat gegevens van de taxon en zijn parent.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LATIJNSENAAM             IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.LEVEL                    IS 'Het niveau rang binnen de taxa.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.OPMERKING                IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PARENT_ID                IS 'De sleutel van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.PATH                     IS 'Een array met alle hogere niveaus''s van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.RANG                     IS 'De sleutel van rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.TAXON_ID                 IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONOMIE.UITGESTORVEN             IS 'Is de taxon uitgestorven?';
COMMENT ON COLUMN NATUUR.TAXONOMIE.VOLGNUMMER               IS 'Het volgnummer van de taxon.';
