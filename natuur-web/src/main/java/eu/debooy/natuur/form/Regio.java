/*
 * Copyright (c) 2023 Marco de Booij
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

import java.io.Serializable;
import org.json.simple.JSONObject;


/**
 * @author Marco de Booij
 */
public class Regio implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_LANDID    = "landId";
  public static final String  COL_NAAM      = "naam";
  public static final String  COL_REGIOKODE = "regiokode";
  public static final String  COL_REGIOID   = "regioId";

  private Long    landId;
  private String  naam;
  private String  regiokode;
  private Long    regioId;

  public Regio(JSONObject json) {
    setLandId(json);
    setNaam(json);
    setRegioId(json);
    setRegiokode(json);
  }

  public Long getLandId() {
    return landId;
  }

  public String getNaam() {
    return naam;
  }

  public String getRegiokode() {
    return regiokode;
  }

  public Long getRegioId() {
    return regioId;
  }

  private void setLandId(JSONObject json) {
    if (json.containsKey(COL_LANDID)) {
      landId  = (Long) json.get(COL_LANDID);
    }
  }

  private void setNaam(JSONObject json) {
    if (json.containsKey(COL_NAAM)) {
      naam    = (String) json.get(COL_NAAM);
    }
  }

  private void setRegiokode(JSONObject json) {
    if (json.containsKey(COL_REGIOID)) {
      regioId = (Long) json.get(COL_REGIOID);
    }
  }

  private void setRegioId(JSONObject json) {
    if (json.containsKey(COL_REGIOKODE)) {
      regiokode = (String) json.get(COL_REGIOKODE);
    }
  }
}
