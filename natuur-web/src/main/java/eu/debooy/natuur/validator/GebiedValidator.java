/**
 * Copyright 2016 Marco de Booij
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
import eu.debooy.natuur.form.Gebied;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class GebiedValidator {
  private GebiedValidator() {
  }

  /**
   * Valideer de Foto.
   */
  public static List<Message> valideer(Gebied gebied) {
    List<Message> fouten  = new ArrayList<Message>();

    String  waarde  = DoosUtils.nullToEmpty(gebied.getNaam());
    if (waarde.length() < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.gebied"));
    } else {
      if (waarde.length() > 255) {
        fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                               "_I18N.label.gebied", 255));
      }
    }

    int leeg  = 0;
    String karakter = gebied.getLatitude();
    if (DoosUtils.isBlankOrNull(karakter)) {
      leeg++;
    } else {
      if (!("N".equals(karakter) || "S".equals(karakter))) {
        fouten.add(new Message(Message.ERROR, "error.latitude"));
      }
    }

    Integer iwaarde = gebied.getLatitudeGraden();
    if (null == iwaarde) {
      leeg++;
    } else {
      if (iwaarde < 0 || iwaarde > 90) {
        fouten.add(new Message(Message.ERROR, "error.latitude.graden"));
      }
    }

    iwaarde = gebied.getLatitudeMinuten();
    if (null == iwaarde) {
      leeg++;
    } else {
      if (iwaarde < 0 || iwaarde > 59) {
        fouten.add(new Message(Message.ERROR, "error.latitude.minuten"));
      }
    }

    Double  dwaarde = gebied.getLatitudeSeconden();
    if (null == dwaarde) {
      leeg++;
    } else {
      if (!(dwaarde >= 0 && dwaarde < 60)) {
        fouten.add(new Message(Message.ERROR, "error.latitude.seconden"));
      }
    }

    karakter = gebied.getLongitude();
    if (DoosUtils.isBlankOrNull(karakter)) {
      leeg++;
    } else {
      if (!("E".equals(karakter) || "W".equals(karakter))) {
        fouten.add(new Message(Message.ERROR, "error.longitude"));
      }
    }

    iwaarde = gebied.getLongitudeGraden();
    if (null == iwaarde) {
      leeg++;
    } else {
      if (iwaarde < 0 || iwaarde > 180) {
        fouten.add(new Message(Message.ERROR, "error.longitude.graden"));
      }
    }

    iwaarde = gebied.getLongitudeMinuten();
    if (null == iwaarde) {
      leeg++;
    } else {
      if (iwaarde < 0 || iwaarde > 59) {
        fouten.add(new Message(Message.ERROR, "error.longitude.minuten"));
      }
    }

    dwaarde = gebied.getLongitudeSeconden();
    if (null == dwaarde) {
      leeg++;
    } else {
      if (!(dwaarde >= 0 && dwaarde < 60)) {
        fouten.add(new Message(Message.ERROR, "error.longitude.seconden"));
      }
    }

    if (leeg != 0 && leeg != 6) {
      fouten.add(new Message(Message.ERROR, "error.coordinaten.onvolledig"));
    }

    return fouten;
  }
}
