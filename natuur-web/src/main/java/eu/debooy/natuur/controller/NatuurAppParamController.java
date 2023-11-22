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
package eu.debooy.natuur.controller;

import eu.debooy.doos.controller.AppParamController;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * @author Marco de Booij
 */
@Named("natuurAppParam")
@SessionScoped
public class NatuurAppParamController extends AppParamController {
  private static final  long  serialVersionUID  = 1L;

  public NatuurAppParamController() {
    initSpeciaal();
    addSpeciaal("natuur.default.gebiedid");
    addSpeciaal("natuur.default.landid");
    addSpeciaal("natuur.regiolijst.taal.1");
    addSpeciaal("natuur.regiolijst.taal.2");
    addSpeciaal("natuur.regiolijst.taal.3");
  }

  public Long getGebiedId() {
    return Long.valueOf(getWaarde());
  }

  public Long getLandId() {
    return Long.valueOf(getWaarde());
  }

  public String getTaal1() {
    return getWaarde();
  }

  public String getTaal2() {
    return getWaarde();
  }

  public String getTaal3() {
    return getWaarde();
  }

  public void setGebiedId(Long gebiedId) {
    setWaarde(String.valueOf(gebiedId));
  }

  public void setLandId(Long landId) {
    setWaarde(String.valueOf(landId));
  }

  public void setTaal1(String taal) {
    setWaarde(taal);
  }

  public void setTaal2(String taal) {
    setWaarde(taal);
  }

  public void setTaal3(String taal) {
    setWaarde(taal);
  }
}
