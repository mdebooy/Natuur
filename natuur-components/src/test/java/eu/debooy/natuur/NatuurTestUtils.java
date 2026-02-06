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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.test.TestConstants;
import eu.debooy.doosutils.test.TestUtils;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.RangnaamDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Taxon;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Marco de Booij
 */
public final class NatuurTestUtils {
  private NatuurTestUtils() {}

  public static Map<Long, FotoDto> getFotos() {
    var                 fotoDto = new FotoDto();
    Map<Long, FotoDto>  fotos   = new HashMap<>();

    fotoDto.setFotoBestand(NatuurTestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(NatuurTestConstants.FOTODETAIL);
    fotoDto.setFotoId(NatuurTestConstants.FOTOID);
    fotoDto.setOpmerking(NatuurTestConstants.OPMERKING);
    fotoDto.setTaxonSeq(NatuurTestConstants.TAXONSEQ);
    fotoDto.setWaarnemingId(NatuurTestConstants.WAARNEMINGID);
    fotos.put(NatuurTestConstants.TAXONSEQ, fotoDto);

    fotoDto = new FotoDto();
    fotoDto.setFotoBestand(NatuurTestConstants.FOTOBESTAND);
    fotoDto.setFotoDetail(NatuurTestConstants.FOTODETAIL);
    fotoDto.setFotoId(NatuurTestConstants.FOTOID + 1);
    fotoDto.setOpmerking(NatuurTestConstants.OPMERKING);
    fotoDto.setTaxonSeq(NatuurTestConstants.TAXONSEQ + 1);
    fotoDto.setWaarnemingId(NatuurTestConstants.WAARNEMINGID);
    fotos.put(NatuurTestConstants.TAXONSEQ + 1, fotoDto);

    return fotos;
  }

  public static FotoOverzichtDto getFotoOverzichtDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException, ParseException {
    var fotoOverzichtDto  = new FotoOverzichtDto();

    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_DATUM,
                       Datum.toDate(TestConstants.RUSHDATUM,
                                    TestConstants.FORMAAT));
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_FOTOBESTAND,
                       NatuurTestConstants.FOTOBESTAND);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_FOTODETAIL,
                       NatuurTestConstants.FOTODETAIL);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_FOTOID,
                       NatuurTestConstants.FOTOID);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_GEBIEDID,
                       NatuurTestConstants.GEBIEDID);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_LANDID,
                       NatuurTestConstants.LANDID);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_LATIJNSENAAM,
                       NatuurTestConstants.LATIJNSENAAM);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_OPMERKING,
                       NatuurTestConstants.OPMERKING);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_PARENTID,
                       NatuurTestConstants.PARENTTAXONID);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_PARENTLATIJNSENAAM,
                       NatuurTestConstants.PARENTLATIJNSENAAM);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_PARENTRANG,
                       NatuurTestConstants.PARENTRANG);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_PARENTSTATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_PARENTVOLGNUMMER,
                       NatuurTestConstants.PARENTVOLGNUMMER);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_RANG,
                       NatuurTestConstants.RANG);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_STATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_TAXON,
                       getTaxonDto());
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_TAXONSEQ,
                       NatuurTestConstants.TAXONSEQ);
    TestUtils.setField(fotoOverzichtDto,
                       FotoOverzichtDto.COL_VOLGNUMMER,
                       NatuurTestConstants.VOLGNUMMER);

    return fotoOverzichtDto;
  }
  public static Gebied getGebied() {
    var gebied  = new Gebied();

    gebied.setGebiedId(NatuurTestConstants.GEBIEDID);
    gebied.setLandId(NatuurTestConstants.LANDID);
    gebied.setLatitude(NatuurTestConstants.LATITUDE);
    gebied.setLatitudeGraden(NatuurTestConstants.LATITUDE_GRADEN);
    gebied.setLatitudeMinuten(NatuurTestConstants.LATITUDE_MINUTEN);
    gebied.setLatitudeSeconden(NatuurTestConstants.LATITUDE_SECONDEN);
    gebied.setLongitude(NatuurTestConstants.LONGITUDE);
    gebied.setLongitudeGraden(NatuurTestConstants.LONGITUDE_GRADEN);
    gebied.setLongitudeMinuten(NatuurTestConstants.LONGITUDE_MINUTEN);
    gebied.setLongitudeSeconden(NatuurTestConstants.LONGITUDE_SECONDEN);
    gebied.setNaam(NatuurTestConstants.NAAM);

    return gebied;
  }

  public static GebiedDto getGebiedDto() {
    var gebied    = getGebied();
    var gebiedDto = new GebiedDto();

    gebied.persist(gebiedDto);

    return gebiedDto;
  }

  public static DetailDto getOndersoortDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       NatuurTestConstants.ONDERSOORTLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU,
                       NatuurTestConstants.ONDERSOORTNIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, NatuurTestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING, NatuurTestConstants.OPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID, NatuurTestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       NatuurTestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG, NatuurTestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTSTATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       NatuurTestConstants.VOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, NatuurTestConstants.ONDERSOORTRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_STATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID,
                       NatuurTestConstants.ONDERSOORTTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER,
                       NatuurTestConstants.ONDERSOORTVOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonOndersoortnamen());
    TestUtils.setField(detailDto, "taxon", getOndersoortTaxonDto());

    return detailDto;
  }

  public static Taxon getOndersoortTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.ONDERSOORTLATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.ONDERSOORTNAAM);
    taxon.setNiveau(NatuurTestConstants.ONDERSOORTNIVEAU);
    taxon.setOpmerking(NatuurTestConstants.ONDERSOORTOPMERKING);
    taxon.setParentId(NatuurTestConstants.TAXONID);
    taxon.setParentLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setParentNaam(NatuurTestConstants.TAXONNAAM);
    taxon.setParentNiveau(NatuurTestConstants.NIVEAU);
    taxon.setParentRang(NatuurTestConstants.RANG);
    taxon.setParentRangnaam(NatuurTestConstants.RANGNAAM);
    taxon.setParentStatus(NatuurTestConstants.STATUS);
    taxon.setParentVolgnummer(NatuurTestConstants.VOLGNUMMER);
    taxon.setRang(NatuurTestConstants.ONDERSOORTRANG);
    taxon.setRangnaam(NatuurTestConstants.ONDERSOORTRANGNAAM);
    taxon.setStatus(NatuurTestConstants.STATUS);
    taxon.setTaxonId(NatuurTestConstants.ONDERSOORTTAXONID);
    taxon.setVolgnummer(NatuurTestConstants.ONDERSOORTVOLGNUMMER);

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

    rang.setNaam(NatuurTestConstants.PARENTRANGNAAM);
    rang.setNiveau(NatuurTestConstants.PARENTNIVEAU);
    rang.setRang(NatuurTestConstants.PARENTRANG);

    return rang;
  }

  public static Taxon getParentTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.PARENTNAAM);
    taxon.setNiveau(NatuurTestConstants.PARENTNIVEAU);
    taxon.setOpmerking(NatuurTestConstants.PARENTOPMERKING);
    taxon.setParentId(NatuurTestConstants.GRANDPARENTTAXONID);
    taxon.setParentLatijnsenaam(NatuurTestConstants.GRANDPARENTLATIJNSENAAM);
    taxon.setParentNaam(NatuurTestConstants.GRANDPARENTNAAM);
    taxon.setParentNiveau(NatuurTestConstants.GRANDPARENTNIVEAU);
    taxon.setParentRang(NatuurTestConstants.GRANDPARENTRANG);
    taxon.setParentRangnaam(NatuurTestConstants.GRANDPARENTRANGNAAM);
    taxon.setParentStatus(NatuurTestConstants.STATUS);
    taxon.setParentVolgnummer(NatuurTestConstants.GRANDPARENTVOLGNUMMER);
    taxon.setRang(NatuurTestConstants.PARENTRANG);
    taxon.setRangnaam(NatuurTestConstants.PARENTRANGNAAM);
    taxon.setStatus(NatuurTestConstants.STATUS);
    taxon.setTaxonId(NatuurTestConstants.PARENTTAXONID);
    taxon.setVolgnummer(NatuurTestConstants.PARENTVOLGNUMMER);

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

    rang.setNaam(NatuurTestConstants.RANGNAAM);
    rang.setNiveau(NatuurTestConstants.NIVEAU);
    rang.setRang(NatuurTestConstants.RANG);

    return rang;
  }

  public static Map<String, RangnaamDto> getRangnamen() {
    var                       rangnaamDto = new RangnaamDto();
    Map<String, RangnaamDto>  rangnamen   = new HashMap<>();

    rangnaamDto.setTaal(NatuurTestConstants.TAAL);
    rangnaamDto.setRang(NatuurTestConstants.RANG);
    rangnaamDto.setNaam(NatuurTestConstants.RANGNAAM);
    rangnamen.put(NatuurTestConstants.TAAL, rangnaamDto);

    rangnaamDto = new RangnaamDto();
    rangnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    rangnaamDto.setRang(NatuurTestConstants.RANG);
    rangnaamDto.setNaam(NatuurTestConstants.RANGNAAM_KL);
    rangnamen.put(NatuurTestConstants.TAAL_KL, rangnaamDto);

    return rangnamen;
  }

  public static DetailDto getSoortDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       NatuurTestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU, NatuurTestConstants.NIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, NatuurTestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING, NatuurTestConstants.OPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID,
                       NatuurTestConstants.PARENTTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       NatuurTestConstants.PARENTLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG,
                       NatuurTestConstants.PARENTRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTSTATUS, NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       NatuurTestConstants.PARENTVOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, NatuurTestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_STATUS, NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID, NatuurTestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER,
                       NatuurTestConstants.VOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonParentnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonnamen());

    return detailDto;
  }

  public static Taxon getTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.NAAM);
    taxon.setNiveau(NatuurTestConstants.NIVEAU);
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setParentId(NatuurTestConstants.PARENTTAXONID);
    taxon.setParentLatijnsenaam(NatuurTestConstants.PARENTLATIJNSENAAM);
    taxon.setParentNaam(NatuurTestConstants.PARENTNAAM);
    taxon.setParentNiveau(NatuurTestConstants.PARENTNIVEAU);
    taxon.setParentRang(NatuurTestConstants.PARENTRANG);
    taxon.setParentRangnaam(NatuurTestConstants.PARENTRANGNAAM);
    taxon.setParentStatus(NatuurTestConstants.STATUS);
    taxon.setParentVolgnummer(NatuurTestConstants.PARENTVOLGNUMMER);
    taxon.setRang(NatuurTestConstants.RANG);
    taxon.setRangnaam(NatuurTestConstants.RANGNAAM);
    taxon.setStatus(NatuurTestConstants.STATUS);
    taxon.setTaxonId(NatuurTestConstants.TAXONID);
    taxon.setVolgnummer(NatuurTestConstants.VOLGNUMMER);

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

    taxonnaamDto.setTaal(NatuurTestConstants.TAAL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.TAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.TAXONNAAM);
    taxonnamen.put(NatuurTestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.TAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.TAXONNAAM_KL);
    taxonnamen.put(NatuurTestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Taxon getTaxonOndersoort() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.ONDERSOORTLATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.ONDERSOORTNAAM);
    taxon.setOpmerking(NatuurTestConstants.OPMERKING);
    taxon.setParent(getTaxon());
    taxon.setRang(NatuurTestConstants.ONDERSOORTRANG);
    taxon.setRangnaam(NatuurTestConstants.RANGNAAM);
    taxon.setTaxonId(NatuurTestConstants.ONDERSOORTTAXONID);
    taxon.setVolgnummer(NatuurTestConstants.ONDERSOORTVOLGNUMMER);

    return taxon;
  }

  public static Map<String, TaxonnaamDto> getTaxonOndersoortnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.ONDERSOORTTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.ONDERSOORTNAAM_KL);
    taxonnamen.put(NatuurTestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Map<String, TaxonnaamDto> getTaxonParentnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(NatuurTestConstants.TAAL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.PARENTTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.PARENTNAAM);
    taxonnamen.put(NatuurTestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.PARENTTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.PARENTNAAM_KL);
    taxonnamen.put(NatuurTestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Map<String, TaxonnaamDto> getTaxonVarieteitnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(NatuurTestConstants.TAAL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.VARIETEITTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.VARIETEITNAAM);
    taxonnamen.put(NatuurTestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.VARIETEITTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.VARIETEITNAAM_KL);
    taxonnamen.put(NatuurTestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static Map<String, TaxonnaamDto> getTaxonVormnamen() {
    var                       taxonnaamDto  = new TaxonnaamDto();
    Map<String, TaxonnaamDto> taxonnamen    = new HashMap<>();

    taxonnaamDto.setTaal(NatuurTestConstants.TAAL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.VORMTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.VORMNAAM);
    taxonnamen.put(NatuurTestConstants.TAAL, taxonnaamDto);

    taxonnaamDto = new TaxonnaamDto();
    taxonnaamDto.setTaal(NatuurTestConstants.TAAL_KL);
    taxonnaamDto.setTaxonId(NatuurTestConstants.VORMTAXONID);
    taxonnaamDto.setNaam(NatuurTestConstants.VORMNAAM_KL);
    taxonnamen.put(NatuurTestConstants.TAAL_KL, taxonnaamDto);

    return taxonnamen;
  }

  public static DetailDto getVarieteitDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       NatuurTestConstants.VARIETEITLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU,
                       NatuurTestConstants.VARIETEITNIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, NatuurTestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING,
                       NatuurTestConstants.VARIETEITOPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID, NatuurTestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       NatuurTestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG, NatuurTestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTSTATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       NatuurTestConstants.VOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, NatuurTestConstants.VARIETEITRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_STATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID,
                       NatuurTestConstants.VARIETEITTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER,
                       NatuurTestConstants.VARIETEITVOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonVarieteitnamen());
    TestUtils.setField(detailDto, "taxon", getVarieteitTaxonDto());

    return detailDto;
  }

  public static Taxon getVarieteitTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.VARIETEITLATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.VARIETEITNAAM);
    taxon.setNiveau(NatuurTestConstants.VARIETEITNIVEAU);
    taxon.setOpmerking(NatuurTestConstants.VARIETEITOPMERKING);
    taxon.setParentId(NatuurTestConstants.TAXONID);
    taxon.setParentLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setParentNaam(NatuurTestConstants.TAXONNAAM);
    taxon.setParentNiveau(NatuurTestConstants.NIVEAU);
    taxon.setParentRang(NatuurTestConstants.RANG);
    taxon.setParentRangnaam(NatuurTestConstants.RANGNAAM);
    taxon.setParentVolgnummer(NatuurTestConstants.VOLGNUMMER);
    taxon.setRang(NatuurTestConstants.VARIETEITRANG);
    taxon.setRangnaam(NatuurTestConstants.VARIETEITRANGNAAM);
    taxon.setTaxonId(NatuurTestConstants.VARIETEITTAXONID);
    taxon.setVolgnummer(NatuurTestConstants.VARIETEITVOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getVarieteitTaxonDto()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon     = getVarieteitTaxon();
    var taxonDto  = new TaxonDto();

    taxon.persist(taxonDto);
    getTaxonVarieteitnamen().forEach((taal, taxonnaam) -> {
      taxonnaam.setTaxonId(taxon.getTaxonId());
      taxonDto.addNaam(taxonnaam);
    });
    TestUtils.setField(taxonDto, "parentnamen", getTaxonVarieteitnamen());
    TestUtils.setField(taxonDto, "parent", getTaxonDto());

    return taxonDto;
  }

  public static DetailDto getVormDetailDto()
      throws IllegalAccessException, IllegalArgumentException,
             NoSuchFieldException {
    var detailDto = new DetailDto();

    TestUtils.setField(detailDto,
                       DetailDto.COL_LATIJNSENAAM,
                       NatuurTestConstants.VORMLATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_NIVEAU, NatuurTestConstants.VORMNIVEAU);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPFOTO, NatuurTestConstants.OPFOTO);
    TestUtils.setField(detailDto,
                       DetailDto.COL_OPMERKING,
                       NatuurTestConstants.VORMOPMERKING);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTID, NatuurTestConstants.TAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTLATIJNSENAAM,
                       NatuurTestConstants.LATIJNSENAAM);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTRANG, NatuurTestConstants.RANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTSTATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_PARENTVOLGNUMMER,
                       NatuurTestConstants.VOLGNUMMER);
    TestUtils.setField(detailDto,
                       DetailDto.COL_RANG, NatuurTestConstants.VORMRANG);
    TestUtils.setField(detailDto,
                       DetailDto.COL_STATUS,
                       NatuurTestConstants.STATUS);
    TestUtils.setField(detailDto,
                       DetailDto.COL_TAXONID, NatuurTestConstants.VORMTAXONID);
    TestUtils.setField(detailDto,
                       DetailDto.COL_VOLGNUMMER,
                       NatuurTestConstants.VORMVOLGNUMMER);

    TestUtils.setField(detailDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(detailDto, "taxonnamen", getTaxonVormnamen());
    TestUtils.setField(detailDto, "taxon", getVormTaxonDto());

    return detailDto;
  }

  public static Taxon getVormTaxon() {
    var taxon = new Taxon();

    taxon.setLatijnsenaam(NatuurTestConstants.VORMLATIJNSENAAM);
    taxon.setNaam(NatuurTestConstants.VORMNAAM);
    taxon.setNiveau(NatuurTestConstants.VORMNIVEAU);
    taxon.setOpmerking(NatuurTestConstants.VORMOPMERKING);
    taxon.setParentId(NatuurTestConstants.TAXONID);
    taxon.setParentLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    taxon.setParentNaam(NatuurTestConstants.TAXONNAAM);
    taxon.setParentNiveau(NatuurTestConstants.NIVEAU);
    taxon.setParentRang(NatuurTestConstants.RANG);
    taxon.setParentRangnaam(NatuurTestConstants.RANGNAAM);
    taxon.setParentVolgnummer(NatuurTestConstants.VOLGNUMMER);
    taxon.setRang(NatuurTestConstants.VORMRANG);
    taxon.setRangnaam(NatuurTestConstants.VORMRANGNAAM);
    taxon.setTaxonId(NatuurTestConstants.VORMTAXONID);
    taxon.setVolgnummer(NatuurTestConstants.VORMVOLGNUMMER);

    return taxon;
  }

  public static TaxonDto getVormTaxonDto()
      throws IllegalArgumentException, IllegalAccessException,
             NoSuchFieldException {
    var taxon     = getVormTaxon();
    var taxonDto  = new TaxonDto();

    taxon.persist(taxonDto);
    getTaxonVormnamen().forEach((taal, taxonnaam) -> {
      taxonnaam.setTaxonId(taxon.getTaxonId());
      taxonDto.addNaam(taxonnaam);
    });
    TestUtils.setField(taxonDto, "parentnamen", getTaxonnamen());
    TestUtils.setField(taxonDto, "parent", getTaxonDto());

    return taxonDto;
  }
}
