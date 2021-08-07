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

import static eu.debooy.natuur.TestConstants.NAAM;
import static eu.debooy.natuur.TestConstants.OVERZICHTPK_HASH;
import static eu.debooy.natuur.TestConstants.PARENTID;
import static eu.debooy.natuur.TestConstants.PARENTRANG;
import static eu.debooy.natuur.TestConstants.RANG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class OverzichtPKTest {
  private static final String TOSTRING  =
      "OverzichtPK (parentId=2, parentRang=pr, rang=ra)";

  private static  OverzichtPK overzichtPk;

  @BeforeClass
  public static void setUpClass() {
    overzichtPk = new OverzichtPK();
    overzichtPk.setParentId(PARENTID);
    overzichtPk.setParentRang(PARENTRANG);
    overzichtPk.setRang(RANG);
  }

  @Test
  public void testEquals() {
    var instance  = new OverzichtPK();

    assertEquals(overzichtPk, overzichtPk);
    assertNotEquals(overzichtPk, null);
    assertNotEquals(overzichtPk, NAAM);
    assertNotEquals(overzichtPk, instance);
  }

  @Test
  public void testGetParentId() {
    assertEquals(PARENTID, overzichtPk.getParentId());
  }

  @Test
  public void testGetParentRang() {
    assertEquals(PARENTRANG, overzichtPk.getParentRang());
  }

  @Test
  public void testGetRang() {
    assertEquals(RANG, overzichtPk.getRang());
  }

  @Test
  public void testHashCode() {
    assertEquals(OVERZICHTPK_HASH, overzichtPk.hashCode());
  }

  @Test
  public void testInit1() {
    var instance  = new OverzichtPK();

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testInit2() {
    var instance  = new OverzichtPK(PARENTID, PARENTRANG, RANG);

    assertEquals(PARENTID, instance.getParentId());
    assertEquals(PARENTRANG, instance.getParentRang());
    assertEquals(RANG, instance.getRang());
  }

  @Test
  public void testSetParentId() {
    var instance  = new OverzichtPK();
    assertNotEquals(PARENTID, instance.getParentId());
    instance.setParentId(PARENTID);

    assertEquals(PARENTID, instance.getParentId());
    assertNull(instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetParentRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(PARENTRANG, instance.getParentRang());
    instance.setParentRang(PARENTRANG);

    assertNull(instance.getParentId());
    assertEquals(PARENTRANG, instance.getParentRang());
    assertNull(instance.getRang());
  }

  @Test
  public void testSetRang() {
    var instance  = new OverzichtPK();
    assertNotEquals(RANG, instance.getRang());
    instance.setRang(RANG);

    assertNull(instance.getParentId());
    assertNull(instance.getParentRang());
    assertEquals(RANG, instance.getRang());
  }
}
