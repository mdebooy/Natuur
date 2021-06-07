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
package eu.debooy.natuur.validator;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import static eu.debooy.natuur.TestConstants.LANDID;
import static eu.debooy.natuur.TestConstants.LATITUDE;
import static eu.debooy.natuur.TestConstants.LATITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.LATITUDE_SECONDEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE;
import static eu.debooy.natuur.TestConstants.LONGITUDE_GRADEN;
import static eu.debooy.natuur.TestConstants.LONGITUDE_MINUTEN;
import static eu.debooy.natuur.TestConstants.NAAM;
import eu.debooy.natuur.TestUtils;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.form.Gebied;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;


/**
 * @author Marco de Booij
 */
public class GebiedValidatorTest {
  private static final  Message ERR_LATITUDE      =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LATITUDE)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.latitude")
                 .build();
  private static final  Message ERR_LATITUDE_GR   =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LATITUDEGRADEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.latitude.graden")
                 .build();
  private static final  Message ERR_LATITUDE_MIN  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LATITUDEMINUTEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.latitude.minuten")
                 .build();
  private static final  Message ERR_LATITUDE_SEC  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LATITUDESECONDEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.latitude.seconden")
                 .build();
  private static final  Message ERR_LONGITUDE     =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LONGITUDE)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.longitude")
                 .build();
  private static final  Message ERR_LONGITUDE_GR  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LONGITUDEGRADEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.longitude.graden")
                 .build();
  private static final  Message ERR_LONGITUDE_MIN =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LONGITUDEMINUTEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.longitude.minuten")
                 .build();
  private static final  Message ERR_LONGITUDE_SEC =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LONGITUDESECONDEN)
                 .setSeverity(Message.ERROR)
                 .setMessage("error.longitude.seconden")
                 .build();
  private static final  Message ERR_NAAM          =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.MAXLENGTH)
                 .setParams(new Object[]{"_I18N.label.gebied", 255})
                 .build();
  private static final  Message ERR_ONVOLLEDIG    =
      new Message.Builder()
                 .setSeverity(Message.ERROR)
                 .setMessage("error.coordinaten.onvolledig")
                 .build();

  private static final  Message REQ_GEBIED  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_NAAM)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{"_I18N.label.gebied"})
                 .build();
  private static final  Message REQ_LANDID  =
      new Message.Builder()
                 .setAttribute(GebiedDto.COL_LANDID)
                 .setSeverity(Message.ERROR)
                 .setMessage(PersistenceConstants.REQUIRED)
                 .setParams(new Object[]{"_I18N.label.land"})
                 .build();

  private Gebied getFoutGebied1() {
    Gebied  gebied  = new Gebied();

    gebied.setLandId(null);
    gebied.setLatitude("X");
    gebied.setLatitudeGraden(-1);
    gebied.setLatitudeMinuten(-1);
    gebied.setLatitudeSeconden(-0.1d);
    gebied.setLongitude("X");
    gebied.setLongitudeGraden(-1);
    gebied.setLongitudeMinuten(-1);
    gebied.setLongitudeSeconden(-0.1d);
    gebied.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));

    return gebied;
  }

  private Gebied getFoutGebied2() {
    Gebied  gebied  = new Gebied();

    gebied.setLandId(null);
    gebied.setLatitude("X");
    gebied.setLatitudeGraden(91);
    gebied.setLatitudeMinuten(60);
    gebied.setLatitudeSeconden(60.0d);
    gebied.setLongitude("X");
    gebied.setLongitudeGraden(181);
    gebied.setLongitudeMinuten(60);
    gebied.setLongitudeSeconden(60.0d);
    gebied.setNaam(DoosUtils.stringMetLengte(NAAM, 256, "X"));

    return gebied;
  }

  private void setFouten(List<Message> expResult) {
    expResult.add(REQ_LANDID);
    expResult.add(ERR_NAAM);
    expResult.add(ERR_LATITUDE);
    expResult.add(ERR_LATITUDE_GR);
    expResult.add(ERR_LATITUDE_MIN);
    expResult.add(ERR_LATITUDE_SEC);
    expResult.add(ERR_LONGITUDE);
    expResult.add(ERR_LONGITUDE_GR);
    expResult.add(ERR_LONGITUDE_MIN);
    expResult.add(ERR_LONGITUDE_SEC);
  }

  @Test
  public void testValideerFouteGebied1() {
    Gebied        gebied    = getFoutGebied1();
    List<Message> expResult = new ArrayList<>();

    setFouten(expResult);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteGebied2() {
    Gebied        gebied    = getFoutGebied2();
    List<Message> expResult = new ArrayList<>();

    setFouten(expResult);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteGebied3() {
    Gebied        gebied    = TestUtils.getGebied();
    List<Message> expResult = new ArrayList<>();

    gebied.setLatitude(null);

    expResult.add(ERR_ONVOLLEDIG);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLatitude(LATITUDE);
    gebied.setLatitudeGraden(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLatitudeGraden(LATITUDE_GRADEN);
    gebied.setLatitudeMinuten(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLatitudeMinuten(LATITUDE_MINUTEN);
    gebied.setLatitudeSeconden(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLatitudeSeconden(LATITUDE_SECONDEN);
    gebied.setLongitude(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLongitude(LONGITUDE);
    gebied.setLongitudeGraden(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLongitudeGraden(LONGITUDE_GRADEN);
    gebied.setLongitudeMinuten(null);
    assertEquals(expResult.toString(), result.toString());

    gebied.setLongitudeMinuten(LONGITUDE_MINUTEN);
    gebied.setLongitudeSeconden(null);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedGebied1() {
    Gebied        gebied    = TestUtils.getGebied();
    List<Message> expResult = new ArrayList<>();

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeGebied2() {
    Gebied        gebied    = new Gebied();
    List<Message> expResult = new ArrayList<>();

    gebied.setLandId(LANDID);
    gebied.setLatitude(LATITUDE);
    gebied.setLongitude(LONGITUDE);
    gebied.setNaam(NAAM);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeGebied() {
    Gebied        gebied    = new Gebied();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIED);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteGebiedDto1() {
    GebiedDto     gebied    = new GebiedDto();
    List<Message> expResult = new ArrayList<>();

    getFoutGebied1().persist(gebied);

    setFouten(expResult);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerFouteGebiedDto2() {
    GebiedDto     gebied    = new GebiedDto();
    List<Message> expResult = new ArrayList<>();

    getFoutGebied2().persist(gebied);

    setFouten(expResult);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedGebiedDto1() {
    GebiedDto     gebied    = TestUtils.getGebiedDto();
    List<Message> expResult = new ArrayList<>();

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerGoedeGebied2Dto() {
    GebiedDto     gebied    = new GebiedDto();
    List<Message> expResult = new ArrayList<>();

    gebied.setLandId(LANDID);
    gebied.setLatitude(LATITUDE);
    gebied.setLongitude(LONGITUDE);
    gebied.setNaam(NAAM);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }

  @Test
  public void testValideerLegeGebiedDto() {
    GebiedDto     gebied    = new GebiedDto();
    List<Message> expResult = new ArrayList<>();

    expResult.add(REQ_GEBIED);

    List<Message> result    = GebiedValidator.valideer(gebied);
    assertEquals(expResult.toString(), result.toString());
  }
}
