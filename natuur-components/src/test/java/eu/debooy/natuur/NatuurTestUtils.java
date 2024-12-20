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

import eu.debooy.doosutils.test.TestUtils;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Taxon;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marco de Booij
 */
public final class NatuurTestUtils {
  private NatuurTestUtils() {}

  public static DetailDto getOndersoortDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       TestConstants.ONDERSOORTLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU, TestConstants.ONDERSOORTNIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, TestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING, TestConstants.OPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID, TestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       TestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG, TestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTUITGESTORVEN,
                       TestConstants.UITGESTORVEN);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       TestConstants.VOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, TestConstants.ONDERSOORTRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID, TestConstants.ONDERSOORTTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_UITGESTORVEN, TestConstants.UITGESTORVEN);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER,
                       TestConstants.ONDERSOORTVOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonOndersoortnamen());
    TestUtils.setField(detailDto, "taxon", getTaxonDto());

    return detailDto;
  }

  public static DetailDto getSoortDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       TestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU, TestConstants.NIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, TestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING, TestConstants.OPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID, TestConstants.PARENTTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       TestConstants.PARENTLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG, TestConstants.PARENTRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTUITGESTORVEN,
                       TestConstants.PARENTUITGESTORVEN);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       TestConstants.PARENTVOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, TestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID, TestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_UITGESTORVEN, TestConstants.UITGESTORVEN);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER, TestConstants.VOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonParentnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonnamen());

    return detailDto;
  }

  public static Map<Long, FotoDto> getFotos() {
    var                 fotoDto = new FotoDto();
    Map<Long, FotoDto>  fotos   = new HashMap<>();

    fotoDto.setFotoBestand(TestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(TestConstants.FOTODETAIL);
    fotoDto.setFotoId(TestConstants.FOTOID);
    fotoDto.setOpmerking(TestConstants.OPMERKING);
    fotoDto.setTaxonSeq(TestConstants.TAXONSEQ);
    fotoDto.setWaarnemingId(TestConstants.WAARNEMINGID);
    fotos.put(TestConstants.TAXONSEQ, fotoDto);

    fotoDto = new FotoDto();
    fotoDto.setFotoBestand(TestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(TestConstants.FOTODETAIL);
    fotoDto.setFotoId(TestConstants.FOTOID + 1);
    fotoDto.setOpmerking(TestConstants.OPMERKING);
    fotoDto.setTaxonSeq(TestConstants.TAXONSEQ + 1);
    fotoDto.setWaarnemingId(TestConstants.WAARNEMINGID);
    fotos.put(TestConstants.TAXONSEQ + 1, fotoDto);

    return fotos;
  }

  public static Gebied getGebied() {
    var gebied  = new Gebied();
    gebied.setGebiedId(TestConstants.GEBIEDID);
    gebied.setLandId(TestConstants.LANDID);
    gebied.setLatitude(TestConstants.LATITUDE);
    gebied.setLatitudeGraden(TestConstants.LATITUDE_GRADEN);
    gebied.setLatitudeMinuten(TestConstants.LATITUDE_MINUTEN);
    gebied.setLatitudeSeconden(TestConstants.LATITUDE_SECONDEN);
    gebied.setLongitude(TestConstants.LONGITUDE);
    gebied.setLongitudeGraden(TestConstants.LONGITUDE_GRADEN);
    gebied.setLongitudeMinuten(TestConstants.LONGITUDE_MINUTEN);
    gebied.setLongitudeSeconden(TestConstants.LONGITUDE_SECONDEN);
    gebied.setNaam(TestConstants.NAAM);

    return gebied;
  }

  public static GebiedDto getGebiedDto() {
    var gebied    = getGebied();
    var gebiedDto = new GebiedDto();

    gebied.persist(gebiedDto);

    return gebiedDto;
  }

  public static Taxon getOndersoortTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(TestConstants.ONDERSOORTLATIJNSENAAM);
    taxon.setNaam(TestConstants.ONDERSOORTNAAM);
    taxon.setNiveau(TestConstants.ONDERSOORTNIVEAU);
    taxon.setOpmerking(TestConstants.ONDERSOORTOPMERKING);
    taxon.setParentId(TestConstants.TAXONID);
    taxon.setParentLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setParentNaam(TestConstants.TAXONNAAM);
    taxon.setParentNiveau(TestConstants.NIVEAU);
    taxon.setParentRang(TestConstants.RANG);
    taxon.setParentRangnaam(TestConstants.RANGNAAM);
    taxon.setParentVolgnummer(TestConstants.VOLGNUMMER);
    taxon.setRang(TestConstants.ONDERSOORTRANG);
    taxon.setRangnaam(TestConstants.ONDERSOORTRANGNAAM);
    taxon.setTaxonId(TestConstants.ONDERSOORTTAXONID);
    taxon.setVolgnummer(TestConstants.ONDERSOORTVOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getOndersoortTaxonDto()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon     = getOndersoortTaxon();
    var taxonDto  = new TaxonDto();

    taxon.persist(taxonDto);
    getTaxonOndersoortnamen().forEach((taal, taxonnaam) -> {
      taxonnaam.setTaxonId(taxon.getTaxonId());
      taxonDto.addNaam(taxonnaam);
    });
    TestUtils.setField(taxonDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(taxonDto, "parent", getTaxonDto());

    return taxonDto;
  }

  public static Rang getParentRang() {
    var rang  = new Rang();

    rang.setNaam(TestConstants.PARENTRANGNAAM);
    rang.setNiveau(TestConstants.PARENTNIVEAU);
    rang.setRang(TestConstants.PARENTRANG);

    return rang;
  }

  public static Taxon getParentTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(TestConstants.PARENTLATIJNSENAAM);
    taxon.setNaam(TestConstants.PARENTNAAM);
    taxon.setNiveau(TestConstants.PARENTNIVEAU);
    taxon.setOpmerking(TestConstants.PARENTOPMERKING);
    taxon.setParentId(TestConstants.GRANDPARENTTAXONID);
    taxon.setParentLatijnsenaam(TestConstants.GRANDPARENTLATIJNSENAAM);
    taxon.setParentNaam(TestConstants.GRANDPARENTNAAM);
    taxon.setParentNiveau(TestConstants.GRANDPARENTNIVEAU);
    taxon.setParentRang(TestConstants.GRANDPARENTRANG);
    taxon.setParentRangnaam(TestConstants.GRANDPARENTRANGNAAM);
    taxon.setParentVolgnummer(TestConstants.GRANDPARENTVOLGNUMMER);
    taxon.setRang(TestConstants.PARENTRANG);
    taxon.setRangnaam(TestConstants.PARENTRANGNAAM);
    taxon.setTaxonId(TestConstants.PARENTTAXONID);
    taxon.setVolgnummer(TestConstants.PARENTVOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getParentTaxonDto() {
    var taxon     = getParentTaxon();
    var taxonDto  = new TaxonDto();

    taxon.persist(taxonDto);
    getTaxonParentnamen().forEach((taal, taxonnaam) -> {
      taxonnaam.setTaxonId(taxon.getTaxonId());
      taxonDto.addNaam(taxonnaam);
    });

    return taxonDto;
  }

  public static Rang getRang() {
    var rang  = new Rang();

    rang.setNaam(TestConstants.RANGNAAM);
    rang.setNiveau(TestConstants.NIVEAU);
    rang.setRang(TestConstants.RANG);

    return rang;
  }

  public static Map<String, RangnaamDto> getRangnamen() {
    var                       rangnaamDto = new RangnaamDto();
    Map<String, RangnaamDto>  rangnamen   = new HashMap<>();

    rangnaamDto.setTaal(TestConstants.TAAL);
    rangnaamDto.setRang(TestConstants.RANG);
    rangnaamDto.setNaam(TestConstants.RANGNAAM);
    rangnamen.put(TestConstants.TAAL, rangnaamDto);

    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(TestConstants.TAAL_KL);
    rangnaamDto.setRang(TestConstants.RANG);
    rangnaamDto.setNaam(TestConstants.RANGNAAM_KL);
    rangnamen.put(TestConstants.TAAL_KL, rangnaamDto);

    return rangnamen;
  }

  public static Taxon getTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    taxon.setNaam(TestConstants.NAAM);
    taxon.setNiveau(TestConstants.NIVEAU);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setParentId(TestConstants.PARENTTAXONID);
    taxon.setParentLatijnsenaam(TestConstants.PARENTLATIJNSENAAM);
    taxon.setParentNaam(TestConstants.PARENTNAAM);
    taxon.setParentNiveau(TestConstants.PARENTNIVEAU);
    taxon.setParentRang(TestConstants.PARENTRANG);
    taxon.setParentRangnaam(TestConstants.PARENTRANGNAAM);
    taxon.setParentVolgnummer(TestConstants.PARENTVOLGNUMMER);
    taxon.setRang(TestConstants.RANG);
    taxon.setRangnaam(TestConstants.RANGNAAM);
    taxon.setTaxonId(TestConstants.TAXONID);
    taxon.setVolgnummer(TestConstants.VOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getTaxonDto() {
    var taxon     = getTaxon();
    var taxonDto  = new TaxonDto();

    taxon.persist(taxonDto);
    getTaxonnamen().forEach((taal, taxonnaam) -> {
      taxonnaam.setTaxonId(taxon.getTaxonId());
      taxonDto.addNaam(taxonnaam);
    });

    return taxonDto;
  }

  public static Map<String, TaxonnaamDto> getTaxonnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(TestConstants.TAAL);
    taxonnaamDto.setTaxonId(TestConstants.TAXONID);
    taxonnaamDto.setNaam(TestConstants.TAXONNAAM);
    taxonnamen.put(TestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(TestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(TestConstants.TAXONID);
    taxonnaamDto.setNaam(TestConstants.TAXONNAAM_KL);
    taxonnamen.put(TestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Taxon getTaxonOndersoort() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(TestConstants.ONDERSOORTLATIJNSENAAM);
    taxon.setNaam(TestConstants.ONDERSOORTNAAM);
    taxon.setOpmerking(TestConstants.OPMERKING);
    taxon.setParent(getTaxon());
    taxon.setRang(TestConstants.ONDERSOORTRANG);
    taxon.setRangnaam(TestConstants.RANGNAAM);
    taxon.setTaxonId(TestConstants.ONDERSOORTTAXONID);
    taxon.setVolgnummer(TestConstants.ONDERSOORTVOLGNUMMER);

    return taxon;
  }

  public static Map<String, TaxonnaamDto> getTaxonOndersoortnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(TestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(TestConstants.ONDERSOORTTAXONID);
    taxonnaamDto.setNaam(TestConstants.ONDERSOORTNAAM_KL);
    taxonnamen.put(TestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Map<String, TaxonnaamDto> getTaxonParentnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(TestConstants.TAAL);
    taxonnaamDto.setTaxonId(TestConstants.PARENTTAXONID);
    taxonnaamDto.setNaam(TestConstants.PARENTNAAM);
    taxonnamen.put(TestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(TestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(TestConstants.PARENTTAXONID);
    taxonnaamDto.setNaam(TestConstants.PARENTNAAM_KL);
    taxonnamen.put(TestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }
}
