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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class LijstparameterTest {
  @Test
  public void testCompleet() {
    var instance  = new Lijstparameter();

    assertNull(instance.getCompleet());

    instance.setCompleet(TestConstants.COMPLEET);

    assertEquals(TestConstants.COMPLEET, instance.getCompleet());
  }

  @Test
  public void testGezien() {
    var instance  = new Lijstparameter();

    assertNull(instance.getGezien());

    instance.setGezien(true);

    assertTrue(instance.getGezien());

    instance.setGezien(false);

    assertFalse(instance.getGezien());
  }

  @Test
  public void testSortering() {
    var instance  = new Lijstparameter();

    assertNull(instance.getSortering());

    instance.setSortering(TestConstants.SORTERING);

    assertEquals(TestConstants.SORTERING, instance.getSortering());
  }

  @Test
  public void testTaal1() {
    var instance  = new Lijstparameter();

    assertNull(instance.getTaal1());

    instance.setTaal1(TestConstants.TAAL);

    assertEquals(TestConstants.TAAL, instance.getTaal1());
  }

  @Test
  public void testTaal2() {
    var instance  = new Lijstparameter();

    assertNull(instance.getTaal2());

    instance.setTaal2(TestConstants.TAAL);

    assertEquals(TestConstants.TAAL, instance.getTaal2());
  }

  @Test
  public void testTaal3() {
    var instance  = new Lijstparameter();

    assertNull(instance.getTaal3());

    instance.setTaal3(TestConstants.TAAL);

    assertEquals(TestConstants.TAAL, instance.getTaal3());
  }
}
