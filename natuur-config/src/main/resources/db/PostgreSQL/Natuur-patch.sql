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

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID AS KLASSE_ID,
         DET.PARENT_VOLGNUMMER AS KLASSE_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM AS KLASSE_LATIJNSENAAM,
         DET.TAXON_ID, DET.VOLGNUMMER, DET.LATIJNSENAAM,
         FOT.TAXON_SEQ, WNM.DATUM, FOT.FOTO_BESTAND, FOT.FOTO_DETAIL,
         GEB.GEBIED_ID, GEB.LAND_ID, GEB.NAAM AS GEBIED,
         FOT.OPMERKING
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID
WHERE    DET.PARENT_RANG='kl';

ALTER  TABLE NATUUR.GEBIEDEN DROP CONSTRAINT CHK_GEB_LATITUDE;
ALTER  TABLE NATUUR.GEBIEDEN DROP CONSTRAINT CHK_GEB_LONGITUDE;
ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LATITUDE CHECK(LATITUDE = ANY (ARRAY['N'::bpchar, 'S'::bpchar, NULL]));
ALTER TABLE NATUUR.GEBIEDEN
  ADD CONSTRAINT CHK_GEB_LONGITUDE CHECK(LONGITUDE = ANY (ARRAY['E'::bpchar, 'W'::bpchar, NULL]));

COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.DATUM               IS 'De datum van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_BESTAND        IS 'Het bestand met de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_DETAIL         IS 'Detail van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.OPMERKING           IS 'Een opmerking voor deze foto.';
