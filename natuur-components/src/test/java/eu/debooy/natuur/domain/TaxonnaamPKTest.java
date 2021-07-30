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
package eu.debooy.natuur.domain;

import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class TaxonnaamPKTest {
  private static final int    HASHCODE  = -2147456487;
  private static final String TOSTRING  =
      "TaxonnaamPK (taxonId=9223372036854775797, taal=nl)";

  @Test
  public void testTaxonnaamPk() {
    var taxonnaamPk = new TaxonnaamPK();

    taxonnaamPk.setTaxonId(TAXONID);
    taxonnaamPk.setTaal(TAAL);

    assertEquals("taxon", TAXONID, taxonnaamPk.getTaxonId());
    assertEquals("taal", TAAL, taxonnaamPk.getTaal());
    assertEquals("toString()", TOSTRING, taxonnaamPk.toString());
  }

  @Test
  public void testEquals() {
    var taxonnaamPk = new TaxonnaamPK();
    taxonnaamPk.setTaxonId(TAXONID);
    taxonnaamPk.setTaal(TAAL);
    assertEquals("== this", taxonnaamPk, taxonnaamPk);

    var gelijk      = new TaxonnaamPK();
    gelijk.setTaxonId(TAXONID);
    gelijk.setTaal(TAAL);
    assertEquals("equals1", gelijk, taxonnaamPk);

    gelijk.setTaxonId(TAXONID+1);
    assertNotEquals("!equals1", gelijk, taxonnaamPk);

    gelijk.setTaal(TAAL_KL);
    assertNotEquals("!equals2", gelijk, taxonnaamPk);

    gelijk.setTaxonId(TAXONID);
    assertNotEquals("!equals3", gelijk, taxonnaamPk);

    gelijk.setTaal(TAAL);
    assertEquals("equals2", gelijk, taxonnaamPk);
  }

  @Test
  public void testHashCode() {
    var taxonnaamPk  = new TaxonnaamPK();
    taxonnaamPk.setTaxonId(TAXONID);
    taxonnaamPk.setTaal(TAAL);
    assertEquals("HashCode", HASHCODE, taxonnaamPk.hashCode());
  }
}
