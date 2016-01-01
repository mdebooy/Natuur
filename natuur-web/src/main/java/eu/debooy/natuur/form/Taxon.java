/**
 * Copyright 2015 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.natuur.form;

import eu.debooy.natuur.domain.TaxonDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Taxon implements Cloneable, Comparable<Taxon>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private String  naam;
  private String  latijnsenaam;
  private Long    parentId;
  private String  rang;
  private Long    taxonId;

  public Taxon() {}

  public Taxon(TaxonDto taxonDto) {
    naam          = taxonDto.getNaam();
    latijnsenaam  = taxonDto.getLatijnsenaam();
    parentId      = taxonDto.getParentId();
    rang          = taxonDto.getRang();
    taxonId       = taxonDto.getTaxonId();
  }

  /**
   * Sorteren op de naam van het taxon.
   */
  public static class LatijnsenaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return taxon1.latijnsenaam.compareTo(taxon2.latijnsenaam);
    }
  }

  /**
   * Sorteren op de naam van het taxon.
   */
  public static class NaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return taxon1.naam.compareTo(taxon2.naam);
    }
  }
  
  @Override
  public Taxon clone() throws CloneNotSupportedException {
    Taxon clone = (Taxon) super.clone();

    return clone;
  }

  @Override
  public int compareTo(Taxon andere) {
    return new CompareToBuilder().append(taxonId, andere.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Taxon)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Taxon andere  = (Taxon) object;
    return new EqualsBuilder().append(taxonId, andere.taxonId).isEquals();
  }

  /**
   * @return de naam
   */
  public String getNaam() {
    return naam;
  }

  /**
   * @return de latijnsenaam
   */
  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  /**
   * @return de parentId
   */
  public Long getParentId() {
    return parentId;
  }

  /**
   * @return de rang
   */
  public String getRang() {
    return rang;
  }

  /**
   * @return de taxonId
   */
  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  /**
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een GebiedDto
   *
   * @param GebiedDto
   */
  public void persist(TaxonDto parameter) {
    if (!new EqualsBuilder().append(this.latijnsenaam,
                                    parameter.getLatijnsenaam()).isEquals()) {
      parameter.setLatijnsenaam(this.latijnsenaam);
    }
    if (!new EqualsBuilder().append(this.naam,
                                    parameter.getNaam()).isEquals()) {
      parameter.setNaam(this.naam);
    }
    if (!new EqualsBuilder().append(this.parentId,
                                    parameter.getParentId()).isEquals()) {
      parameter.setParentId(this.parentId);
    }
    if (!new EqualsBuilder().append(this.rang,
                                    parameter.getRang()).isEquals()) {
      parameter.setRang(this.rang);
    }
    if (!new EqualsBuilder().append(this.taxonId,
                                    parameter.getTaxonId()).isEquals()) {
      parameter.setTaxonId(this.taxonId);
    }
  }

  /**
   * @param naam de waarde van naam
   */
  public void setNaam(String naam) {
    this.naam = naam;
  }

  /**
   * @param latijnsenaam de waarde van latijnsenaam
   */
  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  /**
   * @param parentId de waarde van parentId
   */
  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  /**
   * @param rang de waarde van rang
   */
  public void setRang(String rang) {
    this.rang = rang;
  }

  /**
   * @param taxonId de waarde van taxonId
   */
  public void setTaxonId(Long taxonId) {
    this.taxonId = taxonId;
  }

  @Override
  public String toString() {
    StringBuilder resultaat = new StringBuilder();
    resultaat.append("Taxon (")
             .append("taxonId=[").append(taxonId).append("], ")
             .append("latijnsenaam=[").append(latijnsenaam).append("], ")
             .append("naam=[").append(naam).append("], ")
             .append("parentId=[").append(parentId).append("], ")
             .append("rang=[").append(rang).append("], ")
             .append("class=[").append(this.getClass().getSimpleName())
             .append("])");

    return resultaat.toString();
  }
}
