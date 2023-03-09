/**
 * Copyright (c) 2015 Marco de Booij
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

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.domain.Dto;
import eu.debooy.doosutils.errorhandling.exception.IllegalArgumentException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosLayer;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


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
@NamedQuery(name="taxonKinderen", query="select t from TaxonDto t where t.parentId=:ouder order by t.rang, t.volgnummer")
@NamedQuery(name="taxonLatijnsenaam", query="select t from TaxonDto t where t.latijnsenaam=:latijnsenaam")
@NamedQuery(name="taxonOuders", query="select t from TaxonDto t, RangDto r where t.rang=r.rang and r.niveau<:kind order by t.rang, t.volgnummer")
@NamedQuery(name="taxonSoort", query="select t from TaxonDto t where t.rang in ('so', 'oso')")
@NamedQuery(name="taxonTalen", query="select distinct t.taal from natuur.taxonnamen t")
public class TaxonDto extends Dto implements Comparable<TaxonDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_LATIJNSENAAM  = "latijnsenaam";
  public static final String  COL_OPMERKING     = "opmerking";
  public static final String  COL_PARENTID      = "parentId";
  public static final String  COL_RANG          = "rang";
  public static final String  COL_TAXONID       = "taxonId";
  public static final String  COL_UITGESTORVEN  = "uitgestorven";
  public static final String  COL_VOLGNUMMER    = "volgnummer";

  public static final String  PAR_KIND          = "kind";
  public static final String  PAR_LATIJNSENAAM  = "latijnsenaam";
  public static final String  PAR_OUDER         = "ouder";

  public static final String  QRY_KINDEREN      = "taxonKinderen";
  public static final String  QRY_LATIJNSENAAM  = "taxonLatijnsenaam";
  public static final String  QRY_OUDERS        = "taxonOuders";
  public static final String  QRY_SOORT         = "taxonSoort";
  public static final String  QRY_TALEN         = "taxonTalen";

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
  @Column(name="UITGESTORVEN", length=1, nullable=false)
  private String    uitgestorven  = DoosConstants.ONWAAR;
  @Column(name="VOLGNUMMER", nullable=false)
  private Integer   volgnummer    = 0;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

  public static class LatijnsenaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return taxonDto1.latijnsenaam.compareTo(taxonDto2.latijnsenaam);
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele namen niet te laten verdwijnen in
   * een Map.
   */
  public static class NaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = ComponentsConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return new CompareToBuilder().append(taxonDto1.getNaam(taal),
                                           taxonDto2.getNaam(taal))
                                   .append(taxonDto1.getLatijnsenaam(),
                                           taxonDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  public static class VolgnummerLatijnsenaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return new CompareToBuilder().append(taxonDto1.volgnummer,
                                           taxonDto2.volgnummer)
                                   .append(taxonDto1.getLatijnsenaam(),
                                           taxonDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele volgnummer+namen niet te laten
   * verdwijnen in een Map.
   */
  public static class VolgnummerNaamComparator
      implements Comparator<TaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = ComponentsConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(TaxonDto taxonDto1, TaxonDto taxonDto2) {
      return new CompareToBuilder().append(taxonDto1.volgnummer,
                                           taxonDto2.volgnummer)
                                   .append(taxonDto1.getNaam(taal),
                                           taxonDto2.getNaam(taal))
                                   .append(taxonDto1.getLatijnsenaam(),
                                           taxonDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  public TaxonDto() {}

  public TaxonDto(JsonObject json) {
    latijnsenaam  = json.getString(COL_LATIJNSENAAM);
    opmerking     = json.getString(COL_OPMERKING);
    parentId      = json.getJsonNumber(COL_PARENTID).longValue();
    if (parentId.equals(0L)) {
      parentId    = null;
    }
    rang          = json.getString(COL_RANG);
    if (json.containsKey(COL_TAXONID)) {
      taxonId     = json.getJsonNumber(COL_TAXONID).longValue();
    }
    uitgestorven  = json.getString(COL_UITGESTORVEN);
    volgnummer    = json.getJsonNumber(COL_VOLGNUMMER).intValue();
  }

  public void addNaam(TaxonnaamDto taxonnaamDto) {
    if (null == taxonnaamDto.getTaxonId()
        && null != taxonId) {
      taxonnaamDto.setTaxonId(taxonId);
    }
    if (!new EqualsBuilder().append(taxonId, taxonnaamDto.getTaxonId())
                            .isEquals()) {
      var message = new StringBuilder().append("TaxonId taxonnaam (")
                                       .append(taxonnaamDto.getTaxonId())
                                       .append(") komt niet overeen met ")
                                       .append(taxonId).toString();
      throw new IllegalArgumentException(DoosLayer.PERSISTENCE, message);
    }

    taxonnamen.put(taxonnaamDto.getTaal(), taxonnaamDto);
  }

  @Override
  public int compareTo(TaxonDto taxonDto) {
    return new CompareToBuilder().append(taxonId, taxonDto.taxonId)
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

    var taxonDto  = (TaxonDto) object;
    return new EqualsBuilder().append(taxonId, taxonDto.taxonId)
                              .isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
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

  public boolean getUitgestorven() {
    return uitgestorven.equals(DoosConstants.WAAR);
  }

  public Integer getVolgnummer() {
    return volgnummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  @Transient
  public boolean hasTaxonnaam(String taal) {
    return taxonnamen.containsKey(taal);
  }

  public boolean isUitgestorven() {
    return getUitgestorven();
  }

  public void removeTaxonnaam(String taal) {
    if (taxonnamen.containsKey(taal)) {
      taxonnamen.remove(taal);
    } else {
      throw new ObjectNotFoundException(DoosLayer.PERSISTENCE, taal);
    }
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking    = opmerking;
  }

  public void setParentId(Long parentId) {
    this.parentId     = parentId;
  }

  public void setRang(String rang) {
    this.rang         = rang;
  }

  @SuppressWarnings("java:S1612")
  public void setTaxonId(Long taxonId) {
    if (!new EqualsBuilder().append(this.taxonId, taxonId).isEquals()) {
      this.taxonId = taxonId;
      taxonnamen.values().forEach(taxonnaam -> taxonnaam.setTaxonId(taxonId));
    }
  }

  @SuppressWarnings("java:S1612")
  public void setTaxonnamen(Collection<TaxonnaamDto> taxonnamen) {
    this.taxonnamen.clear();
    taxonnamen.forEach(taxonnaam -> addNaam(taxonnaam));
  }

  public void setTaxonnamen(Map<String, TaxonnaamDto> taxonnamen) {
    setTaxonnamen(taxonnamen.values());
  }

  public void setUitgestorven(boolean uitgestorven) {
    this.uitgestorven =
        uitgestorven ? DoosConstants.WAAR : DoosConstants.ONWAAR;
  }

  public void setVolgnummer(Integer volgnummer) {
    this.volgnummer   = volgnummer;
  }
}
