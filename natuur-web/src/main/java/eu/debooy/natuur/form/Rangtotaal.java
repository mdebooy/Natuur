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
package eu.debooy.natuur.form;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 */
public class Rangtotaal
    implements Cloneable, Comparable<Rangtotaal>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  latijnsenaam;
  private String  naam;
  private Long    totaal;

  public Rangtotaal(Object[] rij) {
    naam          = (String) rij[0];
    latijnsenaam  = (String) rij[1];
    totaal        = (Long)   rij[2];
  }

  @Override
  public Rang clone() throws CloneNotSupportedException {
    Rang  clone = (Rang) super.clone();

    return clone;
  }

  @Override
  public int compareTo(Rangtotaal andere) {
    return new CompareToBuilder().append(naam, andere.naam)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rangtotaal)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Rangtotaal  andere  = (Rangtotaal) object;
    return new EqualsBuilder().append(naam, andere.naam).isEquals();
  }

  /**
   * @return de latijnsenaam
   */
  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  /**
   * @return de naam
   */
  public String getNaam() {
    return naam;
  }

  /**
   * @return de totaal
   */
  public Long getTotaal() {
    return totaal;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(naam).toHashCode();
  }

  /**
   * @param latijnsenaam de waarde van latijnsenaam
   */
  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  /**
   * @param naam de waarde van naam
   */
  public void setNaam(String naam) {
    this.naam = naam;
  }

  /**
   * @param totaal de waarde van totaal
   */
  public void setTotaal(Long totaal) {
    this.totaal = totaal;
  }
}
