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

import eu.debooy.natuur.TestConstants;
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
    rangtotaal.setLatijnsenaam(TestConstants.LATIJNSENAAM);
    rangtotaal.setNaam(TestConstants.RANGNAAM);
    rangtotaal.setOpFoto(TestConstants.OPFOTO);
    rangtotaal.setRang(TestConstants.RANG);
    rangtotaal.setTaxonId(TestConstants.TAXONID);
    rangtotaal.setTotaal(TestConstants.TOTAAL);
    rangtotaal.setVolgnummer(TestConstants.VOLGNUMMER);
    rangtotaal.setWaargenomen(TestConstants.WAARGENOMEN);
  }

  @Test
  public void testAddOpFoto() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getOpFoto());
    instance.addOpFoto(TestConstants.OPFOTO);
    assertEquals(TestConstants.OPFOTO, instance.getOpFoto());
  }

  @Test
  public void testAddTotaal() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getTotaal());
    instance.addTotaal(TestConstants.TOTAAL);
    assertEquals(TestConstants.TOTAAL, instance.getTotaal());
  }

  @Test
  public void testAddWaargenomen() {
    var instance  = new Rangtotaal(leeg);

    assertEquals(Integer.valueOf(0), instance.getWaargenomen());
    instance.addWaargenomen(TestConstants.WAARGENOMEN);
    assertEquals(TestConstants.WAARGENOMEN, instance.getWaargenomen());
  }

  @Test
  public void testCompareTo() {
    var gelijk  = new Rangtotaal(leeg);
    var groter  = new Rangtotaal(leeg);
    var kleiner = new Rangtotaal(leeg);

    gelijk.setTaxonId(TestConstants.TAXONID);
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
    assertNotEquals(rangtotaal, TestConstants.RANGNAAM);
    assertNotEquals(rangtotaal, instance);

    instance.setTaxonId(rangtotaal.getTaxonId());
    assertEquals(rangtotaal, instance);

    instance.setTaxonId(rangtotaal.getTaxonId() - 1);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(TestConstants.LATIJNSENAAM, rangtotaal.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertEquals(TestConstants.RANGNAAM, rangtotaal.getNaam());
  }

  @Test
  public void testGetOpFoto() {
    assertEquals(TestConstants.OPFOTO, rangtotaal.getOpFoto());
  }

  @Test
  public void testGetPctOpFoto() {
    assertEquals(TestConstants.PCTOPFOTO, rangtotaal.getPctOpFoto());
  }

  @Test
  public void testGetRang() {
    assertEquals(TestConstants.RANG, rangtotaal.getRang());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TestConstants.TAXONID, rangtotaal.getTaxonId());
  }

  @Test
  public void testGetTotaal() {
    assertEquals(TestConstants.TOTAAL, rangtotaal.getTotaal());
  }

  @Test
  public void testGetVolgnummer() {
    assertEquals(TestConstants.VOLGNUMMER, rangtotaal.getVolgnummer());
  }

  @Test
  public void testGetWaargenomen() {
    assertEquals(TestConstants.WAARGENOMEN, rangtotaal.getWaargenomen());
  }

  @Test
  public void testHashCode() {
    assertEquals(TestConstants.TAXONID_HASH, rangtotaal.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new Rangtotaal();

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpFoto());
    assertNull(instance.getRang());
    assertNull(instance.getTaxonId());
    assertNull(instance.getTotaal());
    assertNull(instance.getVolgnummer());
    assertNull(instance.getWaargenomen());
  }

  @Test
  public void testInit2() {
    var instance  = new Rangtotaal(new OverzichtDto());

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpFoto());
    assertNull(instance.getTaxonId());
    assertNull(instance.getTotaal());
    assertNull(instance.getVolgnummer());
    assertNull(instance.getWaargenomen());
  }

  @Test
  public void testInit3() {
    var instance  = new Rangtotaal(new OverzichtDto(), TestConstants.TAAL);

    assertNull(instance.getLatijnsenaam());
    assertNull(instance.getNaam());
    assertNull(instance.getOpFoto());
    assertNull(instance.getTaxonId());
    assertNull(instance.getTotaal());
    assertNull(instance.getVolgnummer());
    assertNull(instance.getWaargenomen());
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
  public void testPctOpFoto() {
    var instance  = new Rangtotaal();

    assertEquals(0, instance.getPctOpFoto());

    instance.setOpFoto(TestConstants.OPFOTO);
    assertEquals(0, instance.getPctOpFoto());

    instance.setWaargenomen(0);
    assertEquals(0, instance.getPctOpFoto());

    instance.setWaargenomen(TestConstants.WAARGENOMEN);
    assertEquals(TestConstants.PCTOPFOTO, instance.getPctOpFoto());
  }

  @Test
  public void testSetLatijnsenaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.LATIJNSENAAM,
                           instance.getLatijnsenaam());
    instance.setLatijnsenaam(TestConstants.LATIJNSENAAM);

    assertEquals(TestConstants.LATIJNSENAAM, instance.getLatijnsenaam());
  }

  @Test
  public void testSetNaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.RANGNAAM, instance.getNaam());
    instance.setNaam(TestConstants.RANGNAAM);

    assertEquals(TestConstants.RANGNAAM, instance.getNaam());
  }

  @Test
  public void testSetOpFoto() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.OPFOTO, instance.getOpFoto());
    instance.setOpFoto(TestConstants.OPFOTO);

    assertEquals(TestConstants.OPFOTO, instance.getOpFoto());
  }

  @Test
  public void testSetTaxonId() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.TAXONID, instance.getTaxonId());
    instance.setTaxonId(TestConstants.TAXONID);

    assertEquals(TestConstants.TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetTotaal() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.TOTAAL, instance.getTotaal());
    instance.setTotaal(TestConstants.TOTAAL);

    assertEquals(TestConstants.TOTAAL, instance.getTotaal());
  }

  @Test
  public void testSetVolgnummer() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.VOLGNUMMER, instance.getVolgnummer());
    instance.setVolgnummer(TestConstants.VOLGNUMMER);

    assertEquals(TestConstants.VOLGNUMMER, instance.getVolgnummer());
  }

  @Test
  public void testSetWaargenomen() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TestConstants.WAARGENOMEN,
                           instance.getWaargenomen());
    instance.setWaargenomen(TestConstants.WAARGENOMEN);

    assertEquals(TestConstants.WAARGENOMEN, instance.getWaargenomen());
  }
}
