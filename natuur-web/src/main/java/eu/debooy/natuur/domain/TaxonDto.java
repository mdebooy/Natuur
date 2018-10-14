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
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@NamedQueries({
  @NamedQuery(name="kinderen", query="select t from TaxonDto t where t.parentId=:ouder"),
  @NamedQuery(name="ouders", query="select t from TaxonDto t, RangDto r where t.rang=r.rang and r.niveau<:kind"),
  @NamedQuery(name="soort", query="select t from TaxonDto t where t.rang in ('so', 'oso')")})
public class TaxonDto extends Dto implements Comparable<TaxonDto>, Cloneable {
  private static final  long  serialVersionUID  = 1L;

  @Column(name="LATIJNSENAAM", length=255, nullable=false)
  private String    latijnsenaam;
  @Column(name="OPMERKING", length=2000)
  private String    opmerking;
  @Column(name="PARENT_ID")
  private Long      parentId;
  @Column(name="RANG", length=3, nullable=false)
  private String    rang;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="TAXON_ID", nullable=false, unique=true, updatable=false)
  private Long      taxonId;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> taxonnamen  =
      new HashMap<String, TaxonnaamDto>();

  /**
   * Sorteren op de latijnsenaam van het taxon.
   */
  public static class LatijnsenaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return taxonDto1.latijnsenaam.compareTo(taxonDto2.latijnsenaam);
    }
  }

  /**
   * Sorteren op de naam van het taxon. De Latijnsenaam is toegevoegd om
   * dubbele namen niet te laten verdwijnen in een Map.
   */
  public static class NaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = "nl";

    public void setTaal(String taal) {
      this.taal = taal;
    }

    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return new CompareToBuilder().append(taxonDto1.getNaam(taal),
                                           taxonDto2.getNaam(taal))
                                   .append(taxonDto1.getLatijnsenaam(),
                                           taxonDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  public void addNaam(TaxonnaamDto taxonnaamDto) {
    //TODO Kijken voor 'de' JPA manier.
    if (null == taxonnaamDto.getTaxonId()) {
      taxonnaamDto.setTaxonId(taxonId);
    }
    taxonnamen.put(taxonnaamDto.getTaal(), taxonnaamDto);
  }

  public TaxonDto clone() throws CloneNotSupportedException {
    TaxonDto  clone = (TaxonDto) super.clone();

    return clone;
  }

  public int compareTo(TaxonDto taxonDto) {
    return new CompareToBuilder().append(taxonId, taxonDto.taxonId)
                                 .toComparison();
  }

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

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public TaxonnaamDto getTaxonnaam(String taal) {
    if (taxonnamen.containsKey(taal)) {
      return taxonnamen.get(taal);
    } else {
      return new TaxonnaamDto();
    }
  }

  public Collection<TaxonnaamDto> getTaxonnamen() {
    return taxonnamen.values();
  }

  @Transient
  public String getNaam(String taal) {
    if (taxonnamen.containsKey(taal)) {
      return taxonnamen.get(taal).getNaam();
    } else {
      return latijnsenaam;
    }
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getParentId() {
    return parentId;
  }

  public String getRang() {
    return rang;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  public void removeNaam(String taal) {
    if (taxonnamen.containsKey(taal)) {
      taxonnamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  public void setNamen(Collection<TaxonnaamDto> taxonnamen) {
    this.taxonnamen.clear();
    for (TaxonnaamDto taxonnaam : taxonnamen) {
      this.taxonnamen.put(taxonnaam.getTaal(), taxonnaam);
    }
  }

  public void setNamen(Map<String, TaxonnaamDto> taxonnamen) {
    this.taxonnamen.clear();
    this.taxonnamen.putAll(taxonnamen);
  }

  public void setOpmerking(String opmerking) {
    this.opmerking  = opmerking;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public void setRang(String rang) {
    this.rang = rang;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId = taxonId;
  }
}
