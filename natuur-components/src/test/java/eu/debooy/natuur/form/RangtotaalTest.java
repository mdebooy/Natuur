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
package eu.debooy.natuur.form;

import eu.debooy.natuur.NatuurTestConstants;
import eu.debooy.natuur.domain.OverzichtDto;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangtotaalTest {
  private static  Rangtotaal  leeg;
  private static  Rangtotaal  rangtotaal;

  private void leegRangtotaal(Rangtotaal rangtotaal) {
    assertNull(rangtotaal.getLatijnsenaam());
    assertNull(rangtotaal.getNaam());
    assertNull(rangtotaal.getOpFoto());
    assertEquals(0, rangtotaal.getPctOpFoto());
    assertNull(rangtotaal.getRang());
    assertNull(rangtotaal.getTaxonId());
    assertNull(rangtotaal.getTotaal());
    assertNull(rangtotaal.getVolgnummer());
    assertNull(rangtotaal.getWaargenomen());
  }

  @BeforeClass
  public static void setUpClass() {
    leeg  = new Rangtotaal();
    leeg.setLatijnsenaam("");
    leeg.setTaxonId(0L);
    leeg.setTotaal(0);
    leeg.setOpFoto(0);
    leeg.setVolgnummer(0L);
    leeg.setWaargenomen(0);

    rangtotaal  = new Rangtotaal();
    rangtotaal.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);
    rangtotaal.setNaam(NatuurTestConstants.RANGNAAM);
    rangtotaal.setOpFoto(NatuurTestConstants.OPFOTO);
    rangtotaal.setRang(NatuurTestConstants.RANG);
    rangtotaal.setTaxonId(NatuurTestConstants.TAXONID);
    rangtotaal.setTotaal(NatuurTestConstants.TOTAAL);
    rangtotaal.setVolgnummer(NatuurTestConstants.VOLGNUMMER);
    rangtotaal.setWaargenomen(NatuurTestConstants.WAARGENOMEN);
  }

  @Test
  public void testAddOpFoto() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getOpFoto());
    instance.addOpFoto(NatuurTestConstants.OPFOTO);
    assertEquals(NatuurTestConstants.OPFOTO, instance.getOpFoto());
  }

  @Test
  public void testAddTotaal() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getTotaal());
    instance.addTotaal(NatuurTestConstants.TOTAAL);
    assertEquals(NatuurTestConstants.TOTAAL, instance.getTotaal());
  }

  @Test
  public void testAddWaargenomen() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getWaargenomen());
    instance.addWaargenomen(NatuurTestConstants.WAARGENOMEN);
    assertEquals(NatuurTestConstants.WAARGENOMEN, instance.getWaargenomen());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Rangtotaal(leeg);
    var groter  = new Rangtotaal(leeg);
    var kleiner = new Rangtotaal(leeg);

    gelijk.setTaxonId(NatuurTestConstants.TAXONID);
    groter.setTaxonId(rangtotaal.getTaxonId() + 1);
    kleiner.setTaxonId(rangtotaal.getTaxonId() - 1);

    assertTrue(rangtotaal.compareTo(groter) < 0);
    assertEquals(0, rangtotaal.compareTo(gelijk));
    assertTrue(rangtotaal.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Rangtotaal  instance  = new Rangtotaal(leeg);

    assertEquals(rangtotaal, rangtotaal);
    assertNotEquals(rangtotaal, null);
    assertNotEquals(rangtotaal, NatuurTestConstants.RANGNAAM);
    assertNotEquals(rangtotaal, instance);

    instance.setTaxonId(rangtotaal.getTaxonId());
    assertEquals(rangtotaal, instance);

    instance.setTaxonId(rangtotaal.getTaxonId() - 1);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(NatuurTestConstants.LATIJNSENAAM, rangtotaal.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertEquals(NatuurTestConstants.RANGNAAM, rangtotaal.getNaam());
  }

  @Test
  public void testGetOpFoto() {
    assertEquals(NatuurTestConstants.OPFOTO, rangtotaal.getOpFoto());
  }

  @Test
  public void testGetPctOpFoto() {
    assertEquals(NatuurTestConstants.PCTOPFOTO, rangtotaal.getPctOpFoto());
  }

  @Test
  public void testGetRang() {
    assertEquals(NatuurTestConstants.RANG, rangtotaal.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, rangtotaal.getTaxonId());
  }

  @Test
  public void testGetTotaal() {
    assertEquals(NatuurTestConstants.TOTAAL, rangtotaal.getTotaal());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(NatuurTestConstants.VOLGNUMMER, rangtotaal.getVolgnummer());
  }

  @Test
  public void testGetWaargenomen() {
    assertEquals(NatuurTestConstants.WAARGENOMEN, rangtotaal.getWaargenomen());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.TAXONID_HASH, rangtotaal.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Rangtotaal();

    leegRangtotaal(instance);
  }

  @Test
  public void testInit2() {
    var instance  = new Rangtotaal(new OverzichtDto());

    leegRangtotaal(instance);
  }

  @Test
  public void testInit3() {
    var instance  = new Rangtotaal(new OverzichtDto(), NatuurTestConstants.TAAL);

    leegRangtotaal(instance);
  }

  @Test
  public void testInit4() {
    var instance  = new Rangtotaal(rangtotaal);

    assertEquals(rangtotaal.getLatijnsenaam(), instance.getLatijnsenaam());
    assertEquals(rangtotaal.getNaam(), instance.getNaam());
    assertEquals(rangtotaal.getOpFoto(), instance.getOpFoto());
    assertEquals(rangtotaal.getRang(), instance.getRang());
    assertEquals(rangtotaal.getTaxonId(), instance.getTaxonId());
    assertEquals(rangtotaal.getTotaal(), instance.getTotaal());
    assertEquals(rangtotaal.getVolgnummer(), instance.getVolgnummer());
    assertEquals(rangtotaal.getWaargenomen(), instance.getWaargenomen());
  }

  @Test
  public void testNullOverzichtDto() {
    OverzichtDto  overzicht = null;
    var           instance  = new Rangtotaal(overzicht);

    leegRangtotaal(instance);
  }

  @Test
  public void testPctOpFoto() {
    var instance  = new Rangtotaal();

    assertEquals(0, instance.getPctOpFoto());

    instance.setOpFoto(NatuurTestConstants.OPFOTO);
    assertEquals(0, instance.getPctOpFoto());

    instance.setWaargenomen(0);
    assertEquals(0, instance.getPctOpFoto());

    instance.setWaargenomen(NatuurTestConstants.WAARGENOMEN);
    assertEquals(NatuurTestConstants.PCTOPFOTO, instance.getPctOpFoto());
  }

  @Test
  public void testSetLatijnsenaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.LATIJNSENAAM,
                           instance.getLatijnsenaam());
    instance.setLatijnsenaam(NatuurTestConstants.LATIJNSENAAM);

    assertEquals(NatuurTestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
  }

  @Test
  public void testSetNaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.RANGNAAM, instance.getNaam());
    instance.setNaam(NatuurTestConstants.RANGNAAM);

    assertEquals(NatuurTestConstants.RANGNAAM, instance.getNaam());
  }

  @Test
  public void testSetOpFoto() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.OPFOTO, instance.getOpFoto());
    instance.setOpFoto(NatuurTestConstants.OPFOTO);

    assertEquals(NatuurTestConstants.OPFOTO, instance.getOpFoto());
  }

  @Test
  public void testSetTaxonId() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetTotaal() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.TOTAAL, instance.getTotaal());
    instance.setTotaal(NatuurTestConstants.TOTAAL);

    assertEquals(NatuurTestConstants.TOTAAL, instance.getTotaal());
  }

  @Test
  public void testSetVolgnummer() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(NatuurTestConstants.VOLGNUMMER);

    assertEquals(NatuurTestConstants.VOLGNUMMER, instance.getVolgnummer());
  }

  @Test
  public void testSetWaargenomen() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NatuurTestConstants.WAARGENOMEN,
                           instance.getWaargenomen());
    instance.setWaargenomen(NatuurTestConstants.WAARGENOMEN);

    assertEquals(NatuurTestConstants.WAARGENOMEN, instance.getWaargenomen());
  }
}
