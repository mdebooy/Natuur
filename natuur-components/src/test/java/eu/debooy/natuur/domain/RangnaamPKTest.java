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

import static eu.debooy.natuur.TestConstants.RANG;
import static eu.debooy.natuur.TestConstants.RANG_KL;
import static eu.debooy.natuur.TestConstants.TAAL;
import static eu.debooy.natuur.TestConstants.TAAL_KL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class RangnaamPKTest {
  private static final int    HASHCODE  = 161138;
  private static final String TOSTRING  =
      "RangnaamPK (rang=ra, taal=nl)";

  @Test
  public void testRangnaamPk() {
    var rangnaamPk  = new RangnaamPK();

    rangnaamPk.setRang(RANG);
    rangnaamPk.setTaal(TAAL);

    assertEquals("rang", RANG, rangnaamPk.getRang());
    assertEquals("taal", TAAL, rangnaamPk.getTaal());
    assertEquals("toString()", TOSTRING, rangnaamPk.toString());
  }

  @Test
  public void testEquals() {
    var rangnaamPk  = new RangnaamPK();
    rangnaamPk.setRang(RANG);
    rangnaamPk.setTaal(TAAL);
    assertEquals("== this", rangnaamPk, rangnaamPk);

    var gelijk      = new RangnaamPK();
    gelijk.setRang(RANG);
    gelijk.setTaal(TAAL);
    assertEquals("equals1", gelijk, rangnaamPk);

    gelijk.setRang(RANG_KL);
    assertNotEquals("!equals1", gelijk, rangnaamPk);

    gelijk.setTaal(TAAL_KL);
    assertNotEquals("!equals2", gelijk, rangnaamPk);

    gelijk.setRang(RANG);
    assertNotEquals("!equals3", gelijk, rangnaamPk);

    gelijk.setTaal(TAAL);
    assertEquals("equals2", gelijk, rangnaamPk);
  }

  @Test
  public void testHashCode() {
    var rangnaamPk  = new RangnaamPK();
    rangnaamPk.setRang(RANG);
    rangnaamPk.setTaal(TAAL);
    assertEquals("HashCode", HASHCODE, rangnaamPk.hashCode());
  }
}
