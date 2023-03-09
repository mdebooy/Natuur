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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import static eu.debooy.natuur.TestConstants.ERR_OPMERKING;
import static eu.debooy.natuur.TestConstants.OPMERKING;
import static eu.debooy.natuur.TestConstants.REQ_GEBIEDID;
import static eu.debooy.natuur.TestConstants.REQ_TAXONID;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Waarneming;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class WaarnemingValidatorTest {
  public static final Message ERR_AANTAL  =
      new Message.Builder()
                 .setAttribute(WaarnemingDto.COL_AANTAL)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.ISKLEINER)
                 .setParams(new Object[]{WaarnemingValidator.LBL_AANTAL, 1})
                 .build();

  public static final Message REQ_DATUM =
      new Message.Builder()
                 .setAttribute(WaarnemingDto.COL_DATUM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{WaarnemingValidator.LBL_DATUM})
                 .build();

  private static  Message   errDatum;
  private static  Date      morgen;
  private static  Gebied    gebied;
  private static  GebiedDto gebiedDto;
  private static  Taxon     taxon;
  private static  TaxonDto  taxonDto;

  @BeforeClass
  public static void setUpClass() {
    Calendar  kalender  = Calendar.getInstance();
    kalender.add(Calendar.DAY_OF_YEAR, 1);
    morgen    = kalender.getTime();

    errDatum  = new Message.Builder()
                           .setAttribute(WaarnemingDto.COL_DATUM)
                           .setSeverity(Message.ERROR)
                           .setMessage(PersistenceConstants.FUTURE)
                           .setParams(new Object[]{Datum.fromDate(morgen)})
                           .build();

    gebied    = TestUtils.getGebied();
    gebiedDto = TestUtils.getGebiedDto();
    taxon     = TestUtils.getTaxon();
    taxonDto  = TestUtils.getTaxonDto();
  }

  private void setFouten(List<Message> expResult) {
    expResult.add(ERR_AANTAL);
    expResult.add(errDatum);
    expResult.add(REQ_GEBIEDID);
    expResult.add(ERR_OPMERKING);
    expResult.add(REQ_TAXONID);
  }

  private void setLeeg(List<Message> expResult) {
    expResult.add(REQ_DATUM);
    expResult.add(REQ_GEBIEDID);
    expResult.add(REQ_TAXONID);
  }

  @Test
  public void testValideerFouteWaarneming() {
    Waarneming    waarneming  = new Waarneming();
    List<Message> expResult   = new ArrayList<>();

    waarneming.setAantal(0);
    waarneming.setDatum(morgen);
    waarneming.setOpmerking(DoosUtils.stringMetLengte(OPMERKING, 2001, "X"));

    setFouten(expResult);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeWaarneming() {
    Waarneming    waarneming  = new Waarneming();
    List<Message> expResult   = new ArrayList<>();

    waarneming.setAantal(1);
    waarneming.setDatum(new Date());
    waarneming.setGebied(gebied);
    waarneming.setOpmerking(OPMERKING);
    waarneming.setTaxon(taxon);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeWaarneming() {
    Waarneming    waarneming  = new Waarneming();
    List<Message> expResult   = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteWaarnemingDto() {
    WaarnemingDto waarneming  = new WaarnemingDto();
    List<Message> expResult   = new ArrayList<>();

    waarneming.setAantal(-1);
    waarneming.setDatum(morgen);
    waarneming.setOpmerking(DoosUtils.stringMetLengte(OPMERKING, 2001, "X"));

    setFouten(expResult);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeWaarnemingDto() {
    WaarnemingDto waarneming  = new WaarnemingDto();
    List<Message> expResult   = new ArrayList<>();

    waarneming.setAantal(1);
    waarneming.setDatum(new Date());
    waarneming.setGebied(gebiedDto);
    waarneming.setOpmerking(OPMERKING);
    waarneming.setTaxon(taxonDto);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeWaarnemingDto() {
    WaarnemingDto waarneming  = new WaarnemingDto();
    List<Message> expResult   = new ArrayList<>();

    setLeeg(expResult);

    List<Message> result      = WaarnemingValidator.valideer(waarneming);
    assertEquals(expResult.toString(), result.toString());
  }
}
