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

import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.TAXONID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class DetailPKTest {
  private static final int    HASHCODE  = -2147460291;
  private static final String TOSTRING  =
      "DetailPK (parentId=2, taxonId=9223372036854775797)";

  @Test
  public void testDetailPk() {
    var detailPk  = new DetailPK();

    detailPk.setParentId(PARENTID);
    detailPk.setTaxonId(TAXONID);

    assertEquals("parentId", PARENTID, detailPk.getParentId());
    assertEquals("taxonId", TAXONID, detailPk.getTaxonId());
    assertEquals("toString()", TOSTRING, detailPk.toString());
  }

  @Test
  public void testEquals() {
    var detailPk  = new DetailPK();
    detailPk.setParentId(PARENTID);
    detailPk.setTaxonId(TAXONID);
    assertEquals("== this", detailPk, detailPk);

    var gelijk    = new DetailPK();
    gelijk.setParentId(PARENTID);
    gelijk.setTaxonId(TAXONID);
    assertEquals("equals1", gelijk, detailPk);

    gelijk.setParentId(PARENTID+1);
    assertNotEquals("!equals1", gelijk, detailPk);

    gelijk.setTaxonId(TAXONID+1);
    assertNotEquals("!equals2", gelijk, detailPk);

    gelijk.setParentId(PARENTID);
    assertNotEquals("!equals3", gelijk, detailPk);

    gelijk.setTaxonId(TAXONID);
    assertEquals("equals2", gelijk, detailPk);
  }

  @Test
  public void testHashCode() {
    var detailPk  = new DetailPK();
    detailPk.setParentId(PARENTID);
    detailPk.setTaxonId(TAXONID);
    assertEquals("HashCode", HASHCODE, detailPk.hashCode());
  }
}
