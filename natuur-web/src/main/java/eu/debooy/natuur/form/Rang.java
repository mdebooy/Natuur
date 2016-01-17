/**
 * Copyright 2015 Marco de Booij
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

import eu.debooy.natuur.domain.RangDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Rang implements Cloneable, Comparable<Rang>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Long    niveau;
  private String  rang;

  public Rang() {}

  public Rang(RangDto rangDto) {
    niveau  = rangDto.getNiveau();
    rang    = rangDto.getRang();
  }

  /**
   * Sorteren op het niveau van de Rang.
   */
  public static class NiveauComparator
      implements Comparator<Rang>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Rang rang1, Rang rang2) {
      return rang1.niveau.compareTo(rang2.niveau);
    }
  }
  
  @Override
  public Rang clone() throws CloneNotSupportedException {
    Rang  clone = (Rang) super.clone();

    return clone;
  }

  @Override
  public int compareTo(Rang andere) {
    return new CompareToBuilder().append(rang, andere.rang)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rang)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Rang  andere  = (Rang) object;
    return new EqualsBuilder().append(rang, andere.rang).isEquals();
  }

  /**
   * @return de niveau
   */
  public Long getNiveau() {
    return niveau;
  }

  /**
   * @return de rang
   */
  public String getRang() {
    return rang;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(rang).toHashCode();
  }

  /**
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een RangDto
   *
   * @param RangDto
   */
  public void persist(RangDto parameter) {
    if (!new EqualsBuilder().append(niveau,
                                    parameter.getNiveau()).isEquals()) {
      parameter.setNiveau(niveau);
    }
    if (!new EqualsBuilder().append(rang,
                                    parameter.getRang()).isEquals()) {
      parameter.setRang(rang);
    }
  }

  /**
   * @param niveau de waarde van niveau
   */
  public void setNiveau(Long niveau) {
    this.niveau = niveau;
  }

  /**
   * @param rang de waarde van rang
   */
  public void setRang(String rang) {
    this.rang = rang;
  }

  @Override
  public String toString() {
    StringBuilder resultaat = new StringBuilder();
    resultaat.append("Rang (")
             .append("niveau=[").append(niveau).append("], ")
             .append("rang=[").append(rang).append("], ")
             .append("class=[").append(this.getClass().getSimpleName())
             .append("])");

    return resultaat.toString();
  }
}
