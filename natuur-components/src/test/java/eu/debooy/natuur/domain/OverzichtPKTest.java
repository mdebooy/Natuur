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
import static eu.debooy.natuur.TestConstants.PARENTRANG;
import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANG_GR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class OverzichtPKTest {
  private static final int    HASHCODE  = 1000152;
  private static final String TOSTRING  =
      "OverzichtPK (parentId=2, parentRang=pr, rang=ra)";

  @Test
  public void testOverzichtPK() {
    var overzichtPk = new OverzichtPK();

    overzichtPk.setParentId(PARENTID);
    overzichtPk.setParentRang(PARENTRANG);
    overzichtPk.setRang(RANG);

    assertEquals("parentId", PARENTID, overzichtPk.getParentId());
    assertEquals("parentRang", PARENTRANG, overzichtPk.getParentRang());
    assertEquals("Rang", RANG, overzichtPk.getRang());
    assertEquals("toString()", TOSTRING, overzichtPk.toString());
  }

  @Test
  public void testEquals() {
    var overzichtPk = new OverzichtPK();
    overzichtPk.setParentId(PARENTID);
    overzichtPk.setParentRang(PARENTRANG);
    overzichtPk.setRang(RANG);
    assertEquals("== this", overzichtPk, overzichtPk);

    var gelijk      = new OverzichtPK();
    gelijk.setParentId(PARENTID);
    gelijk.setParentRang(PARENTRANG);
    gelijk.setRang(RANG);
    assertEquals("equals1", gelijk, overzichtPk);

    gelijk.setParentId(PARENTID+1);
    assertNotEquals("!equals1", gelijk, overzichtPk);

    gelijk.setParentRang(RANG_GR);
    assertNotEquals("!equals2", gelijk, overzichtPk);

    gelijk.setParentId(PARENTID);
    assertNotEquals("!equals3", gelijk, overzichtPk);

    gelijk.setParentRang(PARENTRANG);
    assertEquals("equals2", gelijk, overzichtPk);
  }

  @Test
  public void testHashCode() {
    var overzichtPk = new OverzichtPK();
    overzichtPk.setParentId(PARENTID);
    overzichtPk.setParentRang(PARENTRANG);
    overzichtPk.setRang(RANG);
    assertEquals("HashCode", HASHCODE, overzichtPk.hashCode());
  }
}
