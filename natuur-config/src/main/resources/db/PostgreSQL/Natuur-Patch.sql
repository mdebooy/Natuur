/*
 * Copyright (c) 2021 Marco de Booij
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
 * Project: Natuur
 */

DROP VIEW NATUUR.FOTO_OVERZICHT;

CREATE OR REPLACE VIEW NATUUR.FOTO_OVERZICHT AS
SELECT   FOT.FOTO_ID, DET.PARENT_ID AS KLASSE_ID,
         DET.PARENT_VOLGNUMMER AS KLASSE_VOLGNUMMER,
         DET.PARENT_LATIJNSENAAM AS KLASSE_LATIJNSENAAM,
         DET.TAXON_ID, DET.VOLGNUMMER, DET.LATIJNSENAAM,
         FOT.TAXON_SEQ, WNM.DATUM, FOT.FOTO_BESTAND, FOT.FOTO_DETAIL,
         GEB.GEBIED_ID, GEB.LAND_ID, GEB.NAAM AS GEBIED
FROM     NATUUR.WAARNEMINGEN WNM
           JOIN NATUUR.FOTOS FOT    ON WNM.WAARNEMING_ID = FOT.WAARNEMING_ID
           JOIN NATUUR.DETAILS DET  ON WNM.TAXON_ID      = DET.TAXON_ID
           JOIN NATUUR.GEBIEDEN GEB ON WNM.GEBIED_ID     = GEB.GEBIED_ID
WHERE    DET.PARENT_RANG='kl';

GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_UPD;

COMMENT ON VIEW   NATUUR.FOTO_OVERZICHT                     IS 'Deze view bevat alle foto''s met gegevens uit meerdere tabellen.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.FOTO_ID             IS 'De sleutel van de foto.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED              IS 'De naam van het gebied waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.GEBIED_ID           IS 'De sleutel van het gebied.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_ID           IS 'De sleutel van de klasse van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_VOLGNUMMER   IS 'Het volgnummer van de parent van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.KLASSE_LATIJNSENAAM IS 'De latijnse naam van de klasse van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LATIJNSENAAM        IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.LAND_ID             IS 'De sleutel van het land waar de foto genomen is.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_SEQ           IS 'Dit is het volgnummer van de foto van deze taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.TAXON_ID            IS 'De sleutel van de taxon.';
COMMENT ON COLUMN NATUUR.FOTO_OVERZICHT.VOLGNUMMER          IS 'Het volgnummer van de taxon.';
