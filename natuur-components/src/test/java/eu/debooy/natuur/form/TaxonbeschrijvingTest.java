/*
 * Copyright (c) 2026 Marco de Booij
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
import eu.debooy.natuur.domain.TaxonbeschrijvingDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonbeschrijvingTest {
  private static  Taxonbeschrijving     taxonbeschrijving;
  private static  TaxonbeschrijvingDto  taxonbeschrijvingDto;

  @BeforeClass
  public static void setUpClass() {
    taxonbeschrijving     = new Taxonbeschrijving();
    taxonbeschrijving.setBeschrijving(NatuurTestConstants.TAXONBESCHRIJVING);
    taxonbeschrijving
        .setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);
    taxonbeschrijving.setTaxonId(NatuurTestConstants.TAXONID);

    taxonbeschrijvingDto  = new TaxonbeschrijvingDto();
    taxonbeschrijvingDto.setBeschrijving(NatuurTestConstants.TAXONBESCHRIJVING);
    taxonbeschrijvingDto
        .setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);
    taxonbeschrijvingDto.setTaxonId(NatuurTestConstants.TAXONID);
  }

  @Test
  public void testCompareTo() {
    Taxonbeschrijving gelijk  = new Taxonbeschrijving();
    Taxonbeschrijving groter  = new Taxonbeschrijving();
    Taxonbeschrijving kleiner = new Taxonbeschrijving();

    gelijk.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);
    gelijk.setTaxonId(taxonbeschrijving.getTaxonId());
    groter.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);
    groter.setTaxonId(taxonbeschrijving.getTaxonId() + 1);
    kleiner.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);
    kleiner.setTaxonId(taxonbeschrijving.getTaxonId() - 1);

    assertTrue(taxonbeschrijving.compareTo(groter) < 0);
    assertEquals(0, taxonbeschrijving.compareTo(gelijk));
    assertTrue(taxonbeschrijving.compareTo(kleiner) > 0);

    groter.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE_GR);
    groter.setTaxonId(taxonbeschrijving.getTaxonId());
    kleiner.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE_KL);
    kleiner.setTaxonId(taxonbeschrijving.getTaxonId());

    assertTrue(taxonbeschrijving.compareTo(groter) < 0);
    assertEquals(0, taxonbeschrijving.compareTo(gelijk));
    assertTrue(taxonbeschrijving.compareTo(kleiner) > 0);
  }

  @Test
  public void testEquals() {
    Taxonbeschrijving instance  = new Taxonbeschrijving();

    assertEquals(taxonbeschrijving, taxonbeschrijving);
    assertNotEquals(taxonbeschrijving, null);
    assertNotEquals(taxonbeschrijving, NatuurTestConstants.TAXONBESCHRIJVING);
    assertNotEquals(taxonbeschrijving, instance);

    instance.setBeschrijvingtype(taxonbeschrijving.getBeschrijvingtype());
    instance.setTaxonId(taxonbeschrijving.getTaxonId());

    assertEquals(taxonbeschrijving, instance);

    instance  = new Taxonbeschrijving(taxonbeschrijvingDto);

    assertEquals(taxonbeschrijving, instance);
  }

  @Test
  public void testGetBeschrijving() {
    assertEquals(NatuurTestConstants.TAXONBESCHRIJVING,
                 taxonbeschrijving.getBeschrijving());
  }

  @Test
  public void testGetBeschrijvingtype() {
    assertEquals(NatuurTestConstants.TAXONBESCHRIJVINGTYPE,
                 taxonbeschrijving.getBeschrijvingtype());
  }

  @Test
  public void testGetTaxonId() {
    assertEquals(NatuurTestConstants.TAXONID, taxonbeschrijving.getTaxonId());
  }

  @Test
  public void testHashCode() {
    assertEquals(NatuurTestConstants.TAXONBESCHRIJVING_HASH,
                 taxonbeschrijving.hashCode());
  }

  @Test
  public void testPersist() {
    TaxonbeschrijvingDto  parameter = new TaxonbeschrijvingDto();

    taxonbeschrijving.persist(parameter);

    assertEquals(taxonbeschrijving.getBeschrijving(),
                 parameter.getBeschrijving());
    assertEquals(taxonbeschrijving.getBeschrijvingtype(),
                 parameter.getBeschrijvingtype());
    assertEquals(taxonbeschrijving.getTaxonId(), parameter.getTaxonId());
  }

  @Test
  public void testSetBeschrijving() {
    Taxonbeschrijving instance  = new Taxonbeschrijving();

    assertNotEquals(NatuurTestConstants.TAXONBESCHRIJVING,
                    instance.getBeschrijving());

    instance.setBeschrijving(NatuurTestConstants.TAXONBESCHRIJVING);

    assertEquals(NatuurTestConstants.TAXONBESCHRIJVING,
                 instance.getBeschrijving());
  }

  @Test
  public void testSetBeschrijvingtype() {
    Taxonbeschrijving instance  = new Taxonbeschrijving();

    assertNotEquals(NatuurTestConstants.TAXONBESCHRIJVINGTYPE,
                    instance.getBeschrijvingtype());

    instance.setBeschrijvingtype(NatuurTestConstants.TAXONBESCHRIJVINGTYPE);

    assertEquals(NatuurTestConstants.TAXONBESCHRIJVINGTYPE,
                 instance.getBeschrijvingtype());
  }

  @Test
  public void testSetTaxonId() {
    Taxonbeschrijving instance  = new Taxonbeschrijving();

    assertNotEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());

    instance.setTaxonId(NatuurTestConstants.TAXONID);

    assertEquals(NatuurTestConstants.TAXONID, instance.getTaxonId());
  }
}
