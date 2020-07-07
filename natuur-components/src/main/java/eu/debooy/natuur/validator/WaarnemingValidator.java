/**
 * Copyright 2017 Marco de Booij
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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.domain.WaarnemingDto;
import eu.debooy.natuur.form.Waarneming;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Marco de Booij
 */
public final class WaarnemingValidator {
  private WaarnemingValidator() {}

  public static List<Message> valideer(WaarnemingDto waarneming) {
    return valideer(new Waarneming(waarneming));
  }

  public static List<Message> valideer(Waarneming waarneming) {
    List<Message> fouten  = new ArrayList<Message>();

    if (waarneming.getDatum().after(new Date())) {
      try {
        fouten.add(new Message(Message.ERROR, PersistenceConstants.FUTURE,
                               Datum.fromDate(waarneming.getDatum())));
      } catch (ParseException e) {
        fouten.add(new Message(Message.ERROR, PersistenceConstants.WRONGDATE,
                               waarneming.getDatum()));
      }
    }

    Integer aantal  = waarneming.getAantal();
    if (null != aantal
        && aantal.compareTo(0) < 1) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.ISKLEINER,
                             "_I18N.label.aantal", 1));
    }

    Long  seq = null;
    if (null != waarneming.getTaxon()) {
      seq = waarneming.getTaxon().getTaxonId();
    }
    if (DoosUtils.isBlankOrNull(seq)) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.soort"));
    }

    if (null == waarneming.getGebied()) {
      seq = null;
    } else {
      seq = waarneming.getGebied().getGebiedId();
    }
    if (DoosUtils.isBlankOrNull(seq)) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.REQUIRED,
                             "_I18N.label.gebied"));
    }

    String  waarde  = DoosUtils.nullToEmpty(waarneming.getOpmerking());
    if (waarde.length() > 2000) {
      fouten.add(new Message(Message.ERROR, PersistenceConstants.MAXLENGTH,
                             "_I18N.label.opmerking", 2000));
    }

    return fouten;
  }
}
