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
package eu.debooy.natuur.converter;

import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.service.GebiedService;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 * @author Marco de Booij
 */
public class GebiedConverter implements Converter {
  @Override
  public Object getAsObject(FacesContext facesContext,
                            UIComponent uiComponent, String sleutel) {
    GebiedService gebiedService = (GebiedService)
        new JNDI.JNDINaam().metBean(GebiedService.class).locate();

    if (null == sleutel) {
      return null;
    }

    return new Gebied(gebiedService.gebied(Long.parseLong(sleutel)));
  }

  @Override
  public String getAsString(FacesContext facesContext,
                            UIComponent uiComponent, Object object) {
    if (null == object) {
      return null;
    }

    return ((Gebied) object).getGebiedId().toString();
  }
}
