/**
 * Copyright (c) 2015 Marco de Booij
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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.TaxonDto;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Taxon
    extends Formulier implements Comparable<Taxon>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String              latijnsenaam;
  private String              naam;
  private String              opmerking;
  private Long                parentId;
  private String              parentLatijnsenaam;
  private String              parentNaam;
  private Long                parentVolgnummer;
  private String              rang;
  private String              rangnaam;
  private Long                sorteervolgnummer   = 0L;
  private Long                taxonId;
  private Boolean             uitgestorven        = Boolean.FALSE;
  private Long                volgnummer          = 0L;

  public Taxon() {}

  public Taxon(Taxon taxon) {
    latijnsenaam        = taxon.getLatijnsenaam();
    naam                = taxon.getNaam();
    opmerking           = taxon.getOpmerking();
    parentId            = taxon.getParentId();
    parentLatijnsenaam  = taxon.getParentLatijnsenaam();
    parentNaam          = taxon.getParentNaam();
    parentVolgnummer    = taxon.getParentVolgnummer();
    rang                = taxon.getRang();
    sorteervolgnummer   = taxon.getSorteervolgnummer();
    taxonId             = taxon.getTaxonId();
    uitgestorven        = taxon.getUitgestorven();
    volgnummer          = taxon.getVolgnummer();
  }

  public Taxon(TaxonDto taxonDto) {
    this(taxonDto, null);
  }

  public Taxon(TaxonDto taxonDto, String taal) {
    latijnsenaam          = taxonDto.getLatijnsenaam();
    if (DoosUtils.isNotBlankOrNull(taal)) {
      naam                = taxonDto.getNaam(taal);
    }
    opmerking             = taxonDto.getOpmerking();
    parentId              = taxonDto.getParentId();
    if (null != taxonDto.getParent()) {
      parentLatijnsenaam  = taxonDto.getParent().getLatijnsenaam();
      parentVolgnummer    = taxonDto.getParent().getVolgnummer();
    }
    rang                  = taxonDto.getRang();
    if (null != rang) {
      sorteervolgnummer   = taxonDto.getSorteervolgnummer();
    }
    taxonId               = taxonDto.getTaxonId();
    uitgestorven          = taxonDto.getUitgestorven();
    volgnummer            = taxonDto.getVolgnummer();
  }

  public Taxon(DetailDto detailDto) {
    this(detailDto, null);
  }

  public Taxon(DetailDto detailDto, String taal) {
    latijnsenaam        = detailDto.getLatijnsenaam();
    if (DoosUtils.isNotBlankOrNull(taal)) {
      naam              = detailDto.getNaam(taal);
      parentNaam        = detailDto.getParentnaam(taal);
    }
    opmerking           = detailDto.getOpmerking();
    parentId            = detailDto.getParentId();
    parentLatijnsenaam  = detailDto.getParentLatijnsenaam();
    parentVolgnummer    = detailDto.getParentVolgnummer();
    rang                = detailDto.getRang();
    sorteervolgnummer   = detailDto.getSorteervolgnummer();
    taxonId             = detailDto.getTaxonId();
    uitgestorven        = detailDto.getUitgestorven();
    volgnummer          = detailDto.getVolgnummer();
 }

  public static class AlfabetischeComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxonDto1, Taxon taxonDto2) {
      return new CompareToBuilder().append(taxonDto1.getParentNaam(),
                                           taxonDto2.getParentNaam())
                                   .append(taxonDto1.getNaam(),
                                           taxonDto2.getNaam())
                                   .toComparison();
    }
  }

  public static class LatijnsenaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return taxon1.latijnsenaam.compareTo(taxon2.latijnsenaam);
    }
  }

  public static class LijstComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return new CompareToBuilder().append(taxon1.getParentVolgnummer(),
                                           taxon2.getParentVolgnummer())
                                   .append(taxon1.getParentNaam(),
                                           taxon2.getParentNaam())
                                   .append(taxon1.getSorteervolgnummer(),
                                           taxon2.getSorteervolgnummer())
                                   .append(taxon1.getNaam(),
                                           taxon2.getNaam())
                                   .toComparison();
    }
  }

  public static class NaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return new CompareToBuilder().append(taxon1.getNaam(),
                                           taxon2.getNaam())
                                   .append(taxon1.getLatijnsenaam(),
                                           taxon2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  /**
   * Sorteren op het volgnummer en de Latijnsenaam van het taxon.
   */
  public static class VolgnummerLatijnsenaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return new CompareToBuilder().append(taxon1.getSorteervolgnummer(),
                                           taxon2.getSorteervolgnummer())
                                   .append(taxon1.getLatijnsenaam(),
                                           taxon2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  /**
   * Sorteren op het volgnummer en de naam van het taxon. De Latijnsenaam is
   * toegevoegd om dubbele namen niet te laten verdwijnen in een Map.
   */
  public static class VolgnummerNaamComparator
      implements Comparator<Taxon>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Taxon taxon1, Taxon taxon2) {
      return new CompareToBuilder().append(taxon1.getSorteervolgnummer(),
                                           taxon2.getSorteervolgnummer())
                                   .append(taxon1.getNaam(),
                                           taxon2.getNaam())
                                   .append(taxon1.getLatijnsenaam(),
                                           taxon2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(Taxon taxon) {
    return new CompareToBuilder().append(taxonId, taxon.taxonId)
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

    var taxon = (Taxon) object;
    return new EqualsBuilder().append(taxonId, taxon.taxonId).isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return (DoosUtils.isBlankOrNull(naam) ? latijnsenaam : naam);
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getParentId() {
    return parentId;
  }

  public String getParentLatijnsenaam() {
    return parentLatijnsenaam;
  }

  public String getParentNaam() {
    return (DoosUtils.isBlankOrNull(parentNaam) ? parentLatijnsenaam
                                                : parentNaam);
  }

  public Long getParentVolgnummer() {
    return parentVolgnummer;
  }

  public String getRang() {
    return rang;
  }

  public String getRangnaam() {
    return rangnaam;
  }

  public Long getSorteervolgnummer() {
    return sorteervolgnummer;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public Boolean getUitgestorven() {
    return uitgestorven;
  }

  public Long getVolgnummer() {
    return volgnummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  public boolean isUitgestorven() {
    return getUitgestorven();
  }

  public void persist(TaxonDto parameter) {
    parameter.setLatijnsenaam(latijnsenaam);
    parameter.setOpmerking(opmerking);
    parameter.setParentId(parentId);
    parameter.setRang(rang);
    parameter.setTaxonId(taxonId);
    parameter.setUitgestorven(uitgestorven);
    parameter.setVolgnummer(volgnummer);
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam       = latijnsenaam;
  }

  public void setNaam(String naam) {
    this.naam               = naam;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking          = opmerking;
  }

  public void setParentId(Long parentId) {
    this.parentId           = parentId;
  }

  public void setParentLatijnsenaam(String parentLatijnsenaam) {
    this.parentLatijnsenaam = parentLatijnsenaam;
  }

  public void setParentNaam(String parentNaam) {
    this.parentNaam         = parentNaam;
  }

  public void setParentVolgnummer(Long parentVolgnummer) {
    this.parentVolgnummer   = parentVolgnummer;
  }

  public void setRang(String rang) {
    this.rang               = rang;
  }

  public void setRangnaam(String rangnaam) {
    this.rangnaam           = rangnaam;
  }

  public void setSorteervolgnummer(Long sorteervolgnummer) {
    this.sorteervolgnummer  = sorteervolgnummer;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId            = taxonId;
  }

  public void setUitgestorven(String uitgestorven) {
    if (uitgestorven.equals(DoosConstants.WAAR)
            || uitgestorven.equals(DoosConstants.ONWAAR)) {
      this.uitgestorven = uitgestorven.equals(DoosConstants.WAAR);
    } else {
      this.uitgestorven = null;
    }
  }

  public void setUitgestorven(boolean uitgestorven) {
    this.uitgestorven       = uitgestorven;
  }

  public void setVolgnummer(Long volgnummer) {
    this.volgnummer       = volgnummer;
  }
}
