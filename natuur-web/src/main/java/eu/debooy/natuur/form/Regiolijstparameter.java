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


/**
 * @author Marco de Booij
 */
public class Regiolijstparameter implements Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  taal1;
  private String  taal2;
  private String  taal3;

  public String getTaal1() {
    return taal1;
  }

  public String getTaal2() {
    return taal2;
  }

  public String getTaal3() {
    return taal3;
  }

  public void setTaal1(String taal1) {
    this.taal1 = taal1;
  }

  public void setTaal2(String taal2) {
    this.taal2 = taal2;
  }

  public void setTaal3(String taal3) {
    this.taal3 = taal3;
  }
}
