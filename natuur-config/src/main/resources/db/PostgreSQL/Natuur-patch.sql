/*
 * Copyright (c) 2022 Marco de Booij
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
﻿-- Patch van objecten voor het Natuur schema.
--
-- Project: Natuur
-- Author: Marco de Booij

GRANT SELECT ON SEDES.REGIOS TO NATUUR_SEL;
GRANT SELECT ON SEDES.REGIOS TO NATUUR_UPD;

CREATE TABLE NATUUR.REGIOLIJST_TAXA (
  REGIO_ID                        INTEGER         NOT NULL,
  STATUS                          CHAR(2),
  TAXON_ID                        INTEGER         NOT NULL,
  CONSTRAINT PK_REGIOLIJST_TAXA PRIMARY KEY (REGIO_ID, TAXON_ID)
);

CREATE TABLE NATUUR.REGIOLIJSTEN (
  DATUM                           DATE            NOT NULL,
  OMSCHRIJVING                    VARCHAR(2000),
  REGIO_ID                        INTEGER         NOT NULL,
  CONSTRAINT PK_REGIOLIJSTEN PRIMARY KEY (REGIO_ID)
);

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID AS KLASSE_ID,
         DET.PARENT_VOLGNUMMER AS KLASSE_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM AS KLASSE_LATIJNSENAAM,
         DET.TAXON_ID, DET.VOLGNUMMER, DET.LATIJNSENAAM,
         FOT.TAXON_SEQ, WNM.DATUM, FOT.FOTO_BESTAND, FOT.FOTO_DETAIL,
         GEB.GEBIED_ID, GEB.REGIO_ID, GEB.NAAM AS GEBIED,
         FOT.OPMERKING
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID
WHERE    DET.PARENT_RANG='kl';

ALTER TABLE NATUUR.FOTOS
  DROP CONSTRAINT FK_FOT_WAARNEMING_ID;

ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT FK_FOT_WAARNEMING_ID FOREIGN KEY (WAARNEMING_ID)
  REFERENCES NATUUR.WAARNEMINGEN (WAARNEMING_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.REGIOLIJST_TAXA
  ADD CONSTRAINT FK_RLT_REGIO_ID FOREIGN KEY (REGIO_ID)
  REFERENCES NATUUR.REGIOLIJSTEN (REGIO_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

ALTER TABLE NATUUR.REGIOLIJST_TAXA
  ADD CONSTRAINT FK_RLT_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

GRANT SELECT                         ON TABLE NATUUR.REGIOLIJST_TAXA  TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.REGIOLIJSTEN     TO NATUUR_SEL;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE NATUUR.REGIOLIJST_TAXA  TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE NATUUR.REGIOLIJSTEN     TO NATUUR_UPD;

COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.DATUM               IS 'De datum van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_BESTAND        IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_DETAIL         IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.OPMERKING           IS 'Een opmerking voor deze foto.';
COMMENT ON TABLE  NATUUR.REGIOLIJST_TAXA                    IS 'Deze tabel bevat de taxa die in het regio van de lijst voorkomen.';
COMMENT ON COLUMN NATUUR.REGIOLIJST_TAXA.REGIO_ID           IS 'De sleutel van de regio waarin deze taxon te vinden is.';
COMMENT ON COLUMN NATUUR.REGIOLIJST_TAXA.STATUS             IS 'De status van de taxon in de regio.';
COMMENT ON COLUMN NATUUR.REGIOLIJST_TAXA.TAXON_ID           IS 'De sleutel van de taxon.';
COMMENT ON TABLE  NATUUR.REGIOLIJSTEN                       IS 'Deze tabel bevat de regios waarvoor er een lijst is met de taxa die erin voorkomen.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.DATUM                 IS 'De datum van de samenstelling van de lijst.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.OMSCHRIJVING          IS 'De omschrijving van de lijst.';
COMMENT ON COLUMN NATUUR.REGIOLIJSTEN.REGIO_ID              IS 'De sleutel van de regio waarvoor deze lijst is.';

INSERT INTO DOOS.I18N_LIJSTEN
        (CODE, OMSCHRIJVING)
 VALUES ('natuur.taxon.status', 'Lijst met de statussen van de taxa.');
