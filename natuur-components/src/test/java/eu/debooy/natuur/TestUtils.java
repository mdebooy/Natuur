/*
 * Copyright (c) 2020 Marco de Booij
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
package eu.debooy.natuur;

import static eu.debooy.natuur.TestConstants.GEBIEDID;
import static eu.debooy.natuur.TestConstants.LANDID;
import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.LATITUDE;
import static eu.debooy.natuur.TestConstants.LATITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE;
import static eu.debooy.natuur.TestConstants.LONGITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTLATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.PARENTNAAM;
import static eu.debooy.natuur.TestConstants.PARENTVOLGNUMMER;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANGNAAM;
import static eu.debooy.natuur.TestConstants.RANGNAAM_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.VOLGNUMMER;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marco de Booij
 */
public final class TestUtils {
  private TestUtils() {}

  public static Gebied getGebied() {
    Gebied  gebied  = new Gebied();
    gebied.setGebiedId(GEBIEDID);
    gebied.setLandId(LANDID);
    gebied.setLatitude(LATITUDE);
    gebied.setLatitudeGraden(LATITUDE_GRADEN);
    gebied.setLatitudeMinuten(LATITUDE_MINUTEN);
    gebied.setLatitudeSeconden(LATITUDE_SECONDEN);
    gebied.setLongitude(LONGITUDE);
    gebied.setLongitudeGraden(LONGITUDE_GRADEN);
    gebied.setLongitudeMinuten(LONGITUDE_MINUTEN);
    gebied.setLongitudeSeconden(LONGITUDE_SECONDEN);
    gebied.setNaam(NAAM);

    return gebied;
  }

  public static GebiedDto getGebiedDto() {
    GebiedDto gebiedDto = new GebiedDto();
    gebiedDto.setGebiedId(GEBIEDID);
    gebiedDto.setLandId(LANDID);
    gebiedDto.setLatitude(LATITUDE);
    gebiedDto.setLatitudeGraden(LATITUDE_GRADEN);
    gebiedDto.setLatitudeMinuten(LATITUDE_MINUTEN);
    gebiedDto.setLatitudeSeconden(LATITUDE_SECONDEN);
    gebiedDto.setLongitude(LONGITUDE);
    gebiedDto.setLongitudeGraden(LONGITUDE_GRADEN);
    gebiedDto.setLongitudeMinuten(LONGITUDE_MINUTEN);
    gebiedDto.setLongitudeSeconden(LONGITUDE_SECONDEN);
    gebiedDto.setNaam(NAAM);

    return gebiedDto;
  }

  public static Map<String, RangnaamDto> getRangnamen() {
    var                       rangnaamDto = new RangnaamDto();
    Map<String, RangnaamDto>  rangnamen   = new HashMap<>();

    rangnaamDto.setTaal(TAAL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam(RANGNAAM);
    rangnamen.put(TAAL, rangnaamDto);

    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TAAL_KL);
    rangnaamDto.setRang(RANG);
    rangnaamDto.setNaam(RANGNAAM_KL);
    rangnamen.put(TAAL_KL, rangnaamDto);

    return rangnamen;
  }

  public static Taxon getTaxon() {
    Taxon taxon = new Taxon();
    taxon.setLatijnsenaam(LATIJNSENAAM);
    taxon.setNaam(NAAM);
    taxon.setOpmerking(OPMERKING);
    taxon.setParentId(PARENTID);
    taxon.setParentLatijnsenaam(PARENTLATIJNSENAAM);
    taxon.setParentNaam(PARENTNAAM);
    taxon.setParentVolgnummer(PARENTVOLGNUMMER);
    taxon.setRang(RANG);
    taxon.setTaxonId(TAXONID);
    taxon.setVolgnummer(VOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getTaxonDto() {
    TaxonDto  taxonDto  = new TaxonDto();
    taxonDto.setLatijnsenaam(LATIJNSENAAM);
    taxonDto.setOpmerking(OPMERKING);
    taxonDto.setParentId(PARENTID);
    taxonDto.setRang(RANG);
    taxonDto.setTaxonId(TAXONID);
    taxonDto.setVolgnummer(VOLGNUMMER);

    return taxonDto;
  }
}
