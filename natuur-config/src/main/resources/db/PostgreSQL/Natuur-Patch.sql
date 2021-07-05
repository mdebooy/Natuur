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

DROP VIEW NATUUR.FOTO_OVERZICHT;

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID AS KLASSE_ID,
         DET.PARENT_VOLGNUMMER AS KLASSE_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM AS KLASSE_LATIJNSENAAM,
         DET.TAXON_ID, DET.VOLGNUMMER, DET.LATIJNSENAAM,
         FOT.TAXON_SEQ, WNM.DATUM, FOT.FOTO_BESTAND, FOT.FOTO_DETAIL,
         GEB.LAND_ID, GEB.NAAM AS GEBIED
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID
WHERE    DET.PARENT_RANG='kl';

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
           LEFT JOIN (SELECT   WNM.TAXON_ID, COUNT(*) AANTAL
                      FROM     NATUUR.FOTOS FOT
                               JOIN NATUUR.WAARNEMINGEN WNM ON FOT.WAARNEMING_ID = WNM.WAARNEMING_ID
                      GROUP BY WNM.TAXON_ID) F
             ON T.TAXON_ID=F.TAXON_ID;

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

ALTER TABLE NATUUR.FOTOS DROP CONSTRAINT  FK_FOT_GEBIED_ID;
ALTER TABLE NATUUR.FOTOS DROP CONSTRAINT  FK_FOT_TAXON_ID;
ALTER TABLE NATUUR.FOTOS DROP CONSTRAINT  UK_FOT_NIVEAU;
ALTER TABLE NATUUR.FOTOS DROP COLUMN      DATUM;
ALTER TABLE NATUUR.FOTOS DROP COLUMN      GEBIED_ID;
ALTER TABLE NATUUR.FOTOS DROP COLUMN      TAXON_ID;
ALTER TABLE NATUUR.FOTOS ALTER COLUMN     WAARNEMING_ID     SET NOT NULL;
ALTER TABLE NATUUR.TAXA  ADD COLUMN UITGESTORVEN  CHAR(1) NOT NULL DEFAULT 'N';

ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT CHK_TAX_UITGESTORVEN CHECK(UITGESTORVEN = ANY (ARRAY['N'::bpchar, 'J'::bpchar]));

GRANT SELECT ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_UPD;

COMMENT ON VIEW   NATUUR.FOTO_OVERZICHT                     IS 'Deze view bevat alle foto''s met gegevens uit meerdere tabellen.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.DATUM               IS 'De datum van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_BESTAND        IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_DETAIL         IS 'Detail van de foto.';
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
COMMENT ON COLUMN NATUUR.TAXA.UITGESTORVEN                  IS 'Is de taxon uitgestorven?';

\set autocommit to off

UPDATE NATUUR.RANGEN
SET    NIVEAU = NIVEAU+2
WHERE  NIVEAU > 9;

UPDATE NATUUR.RANGEN
SET    NIVEAU = NIVEAU+1
WHERE  NIVEAU > 15;

COMMIT;

\set autocommit to on

INSERT INTO NATUUR.RANGEN (NIVEAU, RANG)
  VALUES (10, 'ikl'),
         (11, 'pkl'),
         (16, 'por');

INSERT INTO NATUUR.RANGNAMEN (RANG, NAAM, TAAL)
  VALUES ('do' , 'Domein',        'nl'),
         ('fa' , 'Familie',       'nl'),
         ('ge' , 'Geslacht',      'nl'),
         ('ikl', 'Infraklasse',   'nl'),
         ('ior', 'Infraorde',     'nl'),
         ('ist', 'Infrastam',     'nl'),
         ('kl' , 'Klasse',        'nl'),
         ('le' , 'Leven',         'nl'),
         ('ofa', 'Onderfamilie',  'nl'),
         ('oge', 'Ondergeslacht', 'nl'),
         ('okl', 'Onderklasse',   'nl'),
         ('oor', 'Onderorde',     'nl'),
         ('or' , 'Orde',          'nl'),
         ('ori', 'Onderrijk',     'nl'),
         ('oso', 'Ondersoort',    'nl'),
         ('ost', 'Onderstam',     'nl'),
         ('ota', 'Ondertak',      'nl'),
         ('pkl', 'Parvklasse',    'nl'),
         ('por', 'Parvorde',      'nl'),
         ('ri' , 'Rijk',          'nl'),
         ('sfa', 'Superfamilie',  'nl'),
         ('so' , 'Soort',         'nl'),
         ('sor', 'Superorde',     'nl'),
         ('st' , 'Stam',          'nl'),
         ('ta' , 'Tak',           'nl');

INSERT INTO NATUUR.RANGNAMEN (RANG, NAAM, TAAL)
  VALUES ('do' , 'Domain',      'en'),
         ('fa' , 'Family',      'en'),
         ('ge' , 'Genus',       'en'),
         ('ikl', 'Infraclass',  'en'),
         ('ior', 'Infraorder',  'en'),
         ('ist', 'Infraphylum', 'en'),
         ('kl' , 'Class',       'en'),
         ('le' , 'Life',        'en'),
         ('ofa', 'Subfamily',   'en'),
         ('oge', 'Subgenus',    'en'),
         ('okl', 'Subclass',    'en'),
         ('oor', 'Suborder',    'en'),
         ('or' , 'Order',       'en'),
         ('ori', 'Subkingdom',  'en'),
         ('oso', 'Subspecies',  'en'),
         ('ost', 'Subphylum',   'en'),
         ('ota', 'Subtribe',    'en'),
         ('pkl', 'Parvclass',   'en'),
         ('por', 'Parvorder',   'en'),
         ('ri' , 'Kingdom',     'en'),
         ('sfa', 'Superfamily', 'en'),
         ('so' , 'Species',     'en'),
         ('sor', 'Superorder',  'en'),
         ('st' , 'Phylum',      'en'),
         ('ta' , 'Tribe',       'en');

INSERT INTO NATUUR.RANGNAMEN (RANG, NAAM, TAAL)
  VALUES ('do' , 'Domäne',        'de'),
         ('fa' , 'Familie',       'de'),
         ('ge' , 'Gattung',       'de'),
         ('ikl', 'Infraklasse',   'de'),
         ('ior', 'Infraordnung',  'de'),
         ('ist', 'Infrastamm',    'de'),
         ('kl' , 'Klasse',        'de'),
         ('le' , 'Lebewesen',     'de'),
         ('ofa', 'Subfamilie',    'de'),
         ('oge', 'Subgattung',    'de'),
         ('okl', 'Subklasse',     'de'),
         ('oor', 'Subordnung',    'de'),
         ('or' , 'Ordnung',       'de'),
         ('ori', 'Subreich',      'de'),
         ('oso', 'Subart',        'de'),
         ('ost', 'Substamm',      'de'),
         ('ota', 'Subtribus',     'de'),
         ('pkl', 'Parvklasse',    'de'),
         ('por', 'Parvordnung',   'de'),
         ('ri' , 'Reich',         'de'),
         ('sfa', 'Superfamilie',  'de'),
         ('so' , 'Art',           'de'),
         ('sor', 'Superordnung',  'de'),
         ('st' , 'Stamm',         'de'),
         ('ta' , 'Tribus',        'de');

DELETE FROM DOOS.I18N_CODE_TEKSTEN
WHERE  CODE_ID IN (SELECT CODE_ID
                   FROM   DOOS.I18N_CODES
                   WHERE  CODE LIKE 'biologie.rang.%');

DELETE FROM DOOS.I18N_LIJST_CODES
WHERE  LIJST_ID IN (SELECT LIJST_ID
                    FROM   DOOS.I18N_LIJSTEN
                    WHERE  CODE='biologie.rang');

DELETE FROM DOOS.I18N_LIJSTEN
WHERE  CODE='biologie.rang';

DELETE FROM DOOS.I18N_CODES
WHERE  CODE LIKE 'biologie.rang.%';
