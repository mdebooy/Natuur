/**
 * Copyright (c) 2016 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
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
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.form.Gebied;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class GebiedValidator extends NatuurValidator {
  private GebiedValidator() {}

  public static List<Message> valideer(GebiedDto gebied) {
    return valideer(new Gebied(gebied));
  }

  public static List<Message> valideer(Gebied gebied) {
    List<Message> fouten  = new ArrayList<>();

    valideerLandId(gebied.getLandId(), fouten);
    valideerNaam(DoosUtils.nullToEmpty(gebied.getNaam()), fouten);
    int leeg  = valideerLatitude(gebied.getLatitude(), fouten);
    leeg  += valideerLatitudeGraden(gebied.getLatitudeGraden(), fouten);
    leeg  += valideerLatitudeMinuten(gebied.getLatitudeMinuten(), fouten);
    leeg  += valideerLatitudeSeconden(gebied.getLatitudeSeconden(), fouten);
    leeg  += valideerLongitude(gebied.getLongitude(), fouten);
    leeg  += valideerLongitudeGraden(gebied.getLongitudeGraden(), fouten);
    leeg  += valideerLongitudeMinuten(gebied.getLongitudeMinuten(), fouten);
    leeg  += valideerLongitudeSeconden(gebied.getLongitudeSeconden(), fouten);

    // Latitude en Longitude kunnen default waardes bevatten.
    if (DoosUtils.isNotBlankOrNull(gebied.getLatitude())
        && DoosUtils.isNotBlankOrNull(gebied.getLongitude())
        && leeg == 6) {
      leeg = 8;
    }

    if (leeg != 0 && leeg != 8) {
      fouten.add(new Message.Builder()
                            .setSeverity(Message.ERROR)
                            .setMessage("error.coordinaten.onvolledig")
                            .build());
    }

    return fouten;
  }

  private static void valideerLandId(Long landId, List<Message> fouten) {
    if (null == landId) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LANDID)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.land"})
                            .build());
    }
  }

  private static int valideerLatitude(String latitude, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(latitude)) {
      return 1;
    }

    if (!("N".equals(latitude) || "S".equals(latitude))) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LATITUDE)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.latitude")
                            .build());
    }

    return 0;
  }

  private static int valideerLatitudeGraden(Integer latitudeGraden,
                                            List<Message> fouten) {
    if (null == latitudeGraden) {
      return 1;
    }

    if (latitudeGraden < 0 || latitudeGraden > 90) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LATITUDEGRADEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.latitude.graden")
                            .build());
    }

    return 0;
  }

  private static int valideerLatitudeMinuten(Integer latitudeMinuten,
                                             List<Message> fouten) {
    if (null == latitudeMinuten) {
      return 1;
    }

    if (latitudeMinuten < 0 || latitudeMinuten > 59) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LATITUDEMINUTEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.latitude.minuten")
                            .build());
    }

    return 0;
  }

  private static int valideerLatitudeSeconden(Double latitudeSeconden,
                                              List<Message> fouten) {
    if (null == latitudeSeconden) {
      return 1;
    }

    if (!(latitudeSeconden >= 0 && latitudeSeconden < 60)) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LATITUDESECONDEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.latitude.seconden")
                            .build());
    }

    return 0;
  }

  private static int valideerLongitude(String longitude,
                                       List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(longitude)) {
      return 1;
    }

    if (!("E".equals(longitude) || "W".equals(longitude))) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LONGITUDE)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.longitude")
                            .build());
    }

    return 0;
  }

  private static int valideerLongitudeGraden(Integer longitudeGraden,
                                             List<Message> fouten) {
    if (null == longitudeGraden) {
      return 1;
    }

    if (longitudeGraden < 0 || longitudeGraden > 180) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LONGITUDEGRADEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.longitude.graden")
                            .build());
    }

    return 0;
  }

  private static int valideerLongitudeMinuten(Integer longitudeMinuten,
                                             List<Message> fouten) {
    if (null == longitudeMinuten) {
      return 1;
    }

    if (longitudeMinuten < 0 || longitudeMinuten > 59) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LONGITUDEMINUTEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.longitude.minuten")
                            .build());
    }

    return 0;
  }

  private static int valideerLongitudeSeconden(Double longitudeSeconden,
                                              List<Message> fouten) {
    if (null == longitudeSeconden) {
      return 1;
    }

    if (!(longitudeSeconden >= 0 && longitudeSeconden < 60)) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_LONGITUDESECONDEN)
                            .setSeverity(Message.ERROR)
                            .setMessage("error.longitude.seconden")
                            .build());
    }

    return 0;
  }

  private static void valideerNaam(String naam, List<Message> fouten) {
    if (DoosUtils.isBlankOrNull(naam)) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.REQUIRED)
                            .setParams(new Object[]{"_I18N.label.gebied"})
                            .build());
      return;
    }

    if (naam.length() > 255) {
      fouten.add(new Message.Builder()
                            .setAttribute(GebiedDto.COL_NAAM)
                            .setSeverity(Message.ERROR)
                            .setMessage(PersistenceConstants.MAXLENGTH)
                            .setParams(new Object[]{"_I18N.label.gebied", 255})
                            .build());
    }
  }
}
