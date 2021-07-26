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

import static eu.debooy.natuur.TestConstants.LATIJNSENAAM;
import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OPFOTO;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static eu.debooy.natuur.TestConstants.TAXONID_HASH;
import static eu.debooy.natuur.TestConstants.TOTAAL;
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

    rangtotaal  = new Rangtotaal();
    rangtotaal.setLatijnsenaam(LATIJNSENAAM);
    rangtotaal.setTaxonId(TAXONID);
    rangtotaal.setTotaal(TOTAAL);
    rangtotaal.setOpFoto(OPFOTO);
  }

  @Test
  public void testCompareTo() {
    Rangtotaal  gelijk  = new Rangtotaal(leeg);
    gelijk.setTaxonId(TAXONID);
    Rangtotaal  groter  = new Rangtotaal(leeg);
    groter.setTaxonId(rangtotaal.getTaxonId() + 1);
    Rangtotaal  kleiner = new Rangtotaal(leeg);
    kleiner.setTaxonId(rangtotaal.getTaxonId() - 1);

    assertTrue(rangtotaal.compareTo(groter) < 0);
    assertEquals(0, rangtotaal.compareTo(gelijk));
    assertTrue(rangtotaal.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Rangtotaal  object    = null;
    Rangtotaal  instance  = new Rangtotaal(leeg);

    assertNotEquals(rangtotaal, object);
    assertNotEquals(rangtotaal, instance);

    instance.setTaxonId(rangtotaal.getTaxonId());
    assertEquals(rangtotaal, instance);
  }

  @Test
  public void testGetLatijnsenaam() {
    assertEquals(LATIJNSENAAM, rangtotaal.getLatijnsenaam());
  }

  @Test
  public void testGetNaam() {
    assertNull(rangtotaal.getNaam());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(TAXONID, rangtotaal.getTaxonId());
  }

  @Test
  public void testGetOpFoto() {
    assertEquals(OPFOTO, rangtotaal.getOpFoto());
  }

  @Test
  public void testGetTotaal() {
    assertEquals(TOTAAL, rangtotaal.getTotaal());
  }

  @Test
  public void testHashCode() {
    assertEquals(TAXONID_HASH, rangtotaal.hashCode());
  }

  @Test
  public void testSetLatijnsenaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(LATIJNSENAAM, instance.getLatijnsenaam());
    instance.setLatijnsenaam(LATIJNSENAAM);

    assertEquals(LATIJNSENAAM, instance.getLatijnsenaam());
  }

  @Test
  public void testSetNaam() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(NAAM, instance.getNaam());
    instance.setNaam(NAAM);

    assertEquals(NAAM, instance.getNaam());
  }

  @Test
  public void testSetOpFoto() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(OPFOTO, instance.getOpFoto());
    instance.setOpFoto(OPFOTO);

    assertEquals(OPFOTO, instance.getOpFoto());
  }

  @Test
  public void testSetTaxonId() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TAXONID, instance.getTaxonId());
    instance.setTaxonId(TAXONID);

    assertEquals(TAXONID, instance.getTaxonId());
  }

  @Test
  public void testSetTotaal() {
    Rangtotaal  instance  = new Rangtotaal(leeg);
    Assert.assertNotEquals(TOTAAL, instance.getTotaal());
    instance.setTotaal(TOTAAL);

    assertEquals(TOTAAL, instance.getTotaal());
  }
}
