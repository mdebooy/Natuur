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

import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.domain.Dto;
import eu.debooy.natuur.NatuurConstants;
import eu.debooy.natuur.NatuurUtils;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKey;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only. Het voorziet in de mogelijkheid om enkel
 * bepaalde rangen te laten zien. Bijvoorbeeld een soort met zijn klasse.
 *
 * Let Op!
 * De parent hoeft niet de directe parent te zijn.
 *
 * @author Marco de Booij
 */
@Entity
@Table(name="DETAILS", schema="NATUUR")
@IdClass(DetailPK.class)
@NamedQuery(name="detailPerGebied", query="select distinct d from DetailDto d, WaarnemingDto w where d.taxonId=w.taxon.taxonId and d.parentRang='kl' and w.gebied.gebiedId=:gebiedId")
@NamedQuery(name="detailSoortMetKlasse", query="select d from DetailDto d where d.parentRang='kl' and d.individu='J'")
@NamedQuery(name="detailSoortMetParent", query="select d from DetailDto d where d.parentId=:parentId and d.individu='J'")
@NamedQuery(name="detailUitgestorvenPerKlasse", query="select d from DetailDto d where d.parentRang = 'kl' and d.status = 'ex'")
@NamedQuery(name="detailVanRegiolijst", query="select d from DetailDto d, RegiolijstTaxonDto r where d.taxonId=r.taxonId and d.parentRang='kl' and r.regiolijstId=:regiolijstId")
@NamedQuery(name="detailWaargenomen", query="select d from DetailDto d, SoortenlijstDto s where d.taxonId = s.taxonId and d.parentRang='kl'")
public class DetailDto extends Dto implements Comparable<DetailDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_INDIVIDU            = "individu";
  public static final String  COL_LATIJNSENAAM        = "latijnsenaam";
  public static final String  COL_NIVEAU              = "niveau";
  public static final String  COL_OPFOTO              = "opFoto";
  public static final String  COL_OPMERKING           = "opmerking";
  public static final String  COL_PARENTID            = "parentId";
  public static final String  COL_PARENTLATIJNSENAAM  = "parentLatijnsenaam";
  public static final String  COL_PARENTRANG          = "parentRang";
  public static final String  COL_PARENTSTATUS        = "parentStatus";
  public static final String  COL_PARENTVOLGNUMMER    = "parentVolgnummer";
  public static final String  COL_RANG                = "rang";
  public static final String  COL_STATUS              = "status";
  public static final String  COL_TAXONID             = "taxonId";
  public static final String  COL_VOLGNUMMER          = "volgnummer";

  public static final String  PAR_GEBIEDID      = "gebiedId";
  public static final String  PAR_PARENTID      = "parentId";
  public static final String  PAR_REGIOID       = "regioId";
  public static final String  PAR_REGIOLIJSTID  = "regiolijstId";

  public static final String  QRY_PERGEBIED             =
      "detailPerGebied";
  public static final String  QRY_SOORTMETKLASSE        =
      "detailSoortMetKlasse";
  public static final String  QRY_SOORTMETPARENT        =
      "detailSoortMetParent";
  public static final String  QRY_UITGESTORVENPERKLASSE =
      "detailUitgestorvenPerKlasse";
  public static final String  QRY_VANREGIOLIJST         =
      "detailVanRegiolijst";
  public static final String  QRY_WAARGENOMEN           = "detailWaargenomen";

  @Transient
  private boolean gezien              = false;
  @ReadOnly
  @Column(name="INDIVIDU", length=1, nullable=false)
  private String  individu            = DoosConstants.ONWAAR;
  @ReadOnly
  @Column(name="LATIJNSENAAM", insertable=false, updatable=false)
  private String  latijnsenaam;
  @ReadOnly
  @Column(name="NIVEAU", insertable=false, updatable=false)
  private Long    niveau;
  @ReadOnly
  @Column(name="OP_FOTO", insertable=false, updatable=false)
  private Integer opFoto;
  @ReadOnly
  @Column(name="OPMERKING", insertable=false, updatable=false)
  private String  opmerking;
  @Id
  @ReadOnly
  @Column(name="PARENT_ID", insertable=false, updatable=false)
  private Long    parentId;
  @ReadOnly
  @Column(name="PARENT_LATIJNSENAAM", insertable=false, updatable=false)
  private String  parentLatijnsenaam;
  @ReadOnly
  @Column(name="PARENT_RANG", insertable=false, updatable=false)
  private String  parentRang;
  @ReadOnly
  @Column(name="PARENT_STATUS", insertable=false, updatable=false)
  private String  parentStatus;
  @ReadOnly
  @Column(name="PARENT_VOLGNUMMER", insertable=false, updatable=false)
  private Long    parentVolgnummer;
  @ReadOnly
  @Column(name="RANG", insertable=false, updatable=false)
  private String  rang;
  @ReadOnly
  @Column(name="STATUS", insertable=false, updatable=false)
  private String  status;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable=false, updatable=false)
  private Long    taxonId;
  @ReadOnly
  @Column(name="VOLGNUMMER", insertable=false, updatable=false)
  private Long    volgnummer;

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=false)
  @JoinColumn(name="TAXON_ID", referencedColumnName="PARENT_ID", nullable=false, updatable=false, insertable=false)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> parentnamen = new HashMap<>();

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=false)
  @JoinColumn(name="TAXON_ID", nullable=false, updatable=false, insertable=false)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

  @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=TaxonDto.class, orphanRemoval=false)
  @JoinColumn(name="TAXON_ID", updatable=false, insertable=false)
  private TaxonDto  taxon;

  public static class LijstComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = NatuurConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.getParentVolgnummer(),
                                           detailDto2.getParentVolgnummer())
                                   .append(detailDto1.getParentnaam(taal),
                                           detailDto2.getParentnaam(taal))
                                   .append(detailDto1.getVolgnummer(),
                                           detailDto2.getVolgnummer())
                                   .append(detailDto1.getNaam(taal),
                                           detailDto2.getNaam(taal))
                                   .toComparison();
    }
  }

  public static class NaamComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = NatuurConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.getParentnaam(taal),
                                           detailDto2.getParentnaam(taal))
                                   .append(detailDto1.getNaam(taal),
                                           detailDto2.getNaam(taal))
                                   .append(detailDto1.getParentVolgnummer(),
                                           detailDto2.getParentVolgnummer())
                                   .append(detailDto1.getVolgnummer(),
                                           detailDto2.getVolgnummer())
                                   .toComparison();
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele volgnummers niet te laten
   * verdwijnen in een Map.
   */
  public static class VolgnummerComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.parentVolgnummer,
                                           detailDto2.parentVolgnummer)
                                   .append(detailDto1.getParentLatijnsenaam(),
                                           detailDto2.getParentLatijnsenaam())
                                   .append(detailDto1.getVolgnummer(),
                                           detailDto2.getVolgnummer())
                                   .append(detailDto1.getLatijnsenaam(),
                                           detailDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele volgnummer+namen niet te laten
   * verdwijnen in een Map.
   */
  public static class VolgnummerNaamComparator
      implements Comparator<DetailDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = NatuurConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.parentVolgnummer,
                                           detailDto2.parentVolgnummer)
                                   .append(detailDto1.getParentnaam(taal),
                                           detailDto2.getParentnaam(taal))
                                   .append(detailDto1.getParentLatijnsenaam(),
                                           detailDto2.getParentLatijnsenaam())
                                   .append(detailDto1.volgnummer,
                                           detailDto2.volgnummer)
                                   .append(detailDto1.getNaam(taal),
                                           detailDto2.getNaam(taal))
                                   .append(detailDto1.getLatijnsenaam(),
                                           detailDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(DetailDto detailDto) {
    return new CompareToBuilder().append(parentId, detailDto.parentId)
                                 .append(taxonId, detailDto.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof DetailDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var detailDto = (DetailDto) object;
    return new EqualsBuilder().append(parentId, detailDto.parentId)
                              .append(taxonId, detailDto.taxonId)
                              .isEquals();
  }

  public boolean getIndividu() {
    return (DoosConstants.WAAR.equals(individu));
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  @Transient
  public String getNaam(String taal) {
    if (null != taxon) {
      return taxon.getNaam(taal);
    }

    return NatuurUtils.getNaam(taxonnamen, parentnamen, latijnsenaam, rang,
                               taal);
  }

  public Long getNiveau() {
    return niveau;
  }

  public Integer getOpFoto() {
    return opFoto;
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

  @Transient
  public String getParentnaam(String taal) {
    if (parentnamen.containsKey(taal)) {
      return parentnamen.get(taal).getNaam();
    } else {
      return parentLatijnsenaam;
    }
  }

  public Collection<TaxonnaamDto> getParentnamen() {
    return parentnamen.values();
  }

  public String getParentRang() {
    return parentRang;
  }

  public String getParentStatus() {
    return parentStatus;
  }

  public boolean getParentUitgestorven() {
    return NatuurUtils.isStatusUitgestorven(parentStatus);
  }

  public Long getParentVolgnummer() {
    return parentVolgnummer;
  }

  private TaxonDto getPostLoadTaxon() {
    return taxon;
  }

  public String getRang() {
    return rang;
  }

  public String getStatus() {
    return status;
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
    return NatuurUtils.isStatusUitgestorven(status);
  }

  public Long getVolgnummer() {
    return volgnummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId).append(taxonId).toHashCode();
  }

  @Transient
  public boolean hasParentnaam(String taal) {
    return parentnamen.containsKey(taal);
  }

  @Transient
  public boolean hasTaxonnaam(String taal) {
    return taxonnamen.containsKey(taal);
  }

  public boolean isGezien() {
    return gezien;
  }

  @Transient
  public boolean isOpFoto(){
    return opFoto.equals(1);
  }

  public boolean isParentUitgestorven() {
    return getParentUitgestorven();
  }

  public boolean isUitgestorven() {
    return getUitgestorven();
  }

  @PostLoad
  private void onPostLoad() {
    if (getRang().equals(NatuurConstants.RANG_ONDERSOORT)
        || getRang().equals(NatuurConstants.RANG_VARIETEIT)
        || getRang().equals(NatuurConstants.RANG_VORM)) {
      getPostLoadTaxon();
    }
  }

  public void setGezien(boolean gezien) {
    this.gezien   = gezien;
  }
}
