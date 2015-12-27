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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.domain.Dto;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author Marco de Booij
 * 
 * Een taxon (meervoud: taxa) is een taxonomische eenheid of taxonomische groep.
 * Het is een groep organismen, die door een taxonoom geacht worden een te
 * onderscheiden eenheid te vormen. Een taxon kan ook zijn: een groep van taxa
 * van een onderliggend niveau, of een deel van een taxon van een hoger niveau.
 * Er zijn dus niveaus te onderscheiden.
 */
@Entity
@Table(name="TAXA", schema="NATUUR")
@NamedQuery(name="soort", query="select t from TaxonDto t where t.rang='so'")
public class TaxonDto extends Dto implements Comparable<TaxonDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Column(name="NAAM", length=225, nullable=false)
  private String    naam;
  @Column(name="LATIJNSENAAM", length=225, nullable=false)
  private String    latijnsenaam;
  @Column(name="PARENT_ID", nullable=false, updatable=false)
  private Long      parentId;
  @Column(name="RANG", length=3, nullable=false)
  private String    rang;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="TAXON_ID", nullable=false, updatable=false)
  private Long      taxonId;

  /**
   * Sorteren op de naam van het taxon.
   */
  public static class LatijnsenaamComparator implements Comparator<TaxonDto> {
    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return taxonDto1.latijnsenaam.compareTo(taxonDto2.latijnsenaam);
    }
  };

  /**
   * Sorteren op de naam van het taxon.
   */
  public static class NaamComparator implements Comparator<TaxonDto> {
    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return taxonDto1.naam.compareTo(taxonDto2.naam);
    }
  };
  
  @Override
  public TaxonDto clone() throws CloneNotSupportedException {
    TaxonDto  clone = (TaxonDto) super.clone();

    return clone;
  }

  public int compareTo(TaxonDto taxonDto) {
    return new CompareToBuilder().append(naam, taxonDto.naam)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof TaxonDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    TaxonDto  taxonDto  = (TaxonDto) object;
    return new EqualsBuilder().append(taxonId, taxonDto.taxonId)
                              .isEquals();
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
}
