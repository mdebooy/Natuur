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
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only. Het voorziet in de mogelijkheid om enkel
 * bepaalde rangen te laten zien. Bijvoorbeeld een soort met zijn klasse.
 *
 * @author Marco de Booij
 */
@Entity
@Table(name="DETAILS", schema="NATUUR")
@IdClass(DetailPK.class)
@NamedQuery(name="detailSoortMetKlasse", query="select d from DetailDto d where d.parentRang='kl' and d.rang in ('so', 'oso')")
@NamedQuery(name="detailWaargenomen", query="select d from DetailDto d where d.taxonId in (select distinct w.taxon.taxonId from WaarnemingDto w) and d.parentRang='kl'")
public class DetailDto extends Dto implements Comparable<DetailDto> {
  private static final  long  serialVersionUID  = 1L;


  public static final String  QRY_SOORTMETKLASSE  = "detailSoortMetKlasse";
  public static final String  QRY_WAARGENOMEN     = "detailWaargenomen";

  @ReadOnly
  @Column(name="LATIJNSENAAM", insertable= false, updatable=false)
  private String    latijnsenaam;
  @ReadOnly
  @Column(name="NIVEAU", insertable= false, updatable=false)
  private Long      niveau;
  @ReadOnly
  @Column(name="OP_FOTO", insertable= false, updatable=false)
  private Integer   opFoto;
  @ReadOnly
  @Column(name="OPMERKING", insertable= false, updatable=false)
  private String    opmerking;
  @Id
  @ReadOnly
  @Column(name="PARENT_ID", insertable= false, updatable=false)
  private Long      parentId;
  @ReadOnly
  @Column(name="PARENT_LATIJNSENAAM", insertable= false, updatable=false)
  private String    parentLatijnsenaam;
  @ReadOnly
  @Column(name="PARENT_RANG", insertable= false, updatable=false)
  private String    parentRang;
  @ReadOnly
  @Column(name="PARENT_VOLGNUMMER", insertable= false, updatable=false)
  private Integer   parentVolgnummer;
  @ReadOnly
  @Column(name="RANG", insertable= false, updatable=false)
  private String    rang;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable= false, updatable=false)
  private Long      taxonId;
  @Column(name="UITGESTORVEN", length=1, nullable=false)
  private String    uitgestorven;
  @Column(name="VOLGNUMMER")
  private Integer   volgnummer;

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", referencedColumnName="PARENT_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> parentnamen = new HashMap<>();

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> taxonnamen  = new HashMap<>();

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
                                   .append(detailDto1.volgnummer,
                                           detailDto2.volgnummer)
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

    private String  taal  = "nl";

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(DetailDto detailDto1, DetailDto detailDto2) {
      return new CompareToBuilder().append(detailDto1.parentVolgnummer,
                                           detailDto2.parentVolgnummer)
                                   .append(detailDto1.getParentNaam(taal),
                                           detailDto2.getParentNaam(taal))
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

    DetailDto detailDto = (DetailDto) object;
    return new EqualsBuilder().append(parentId, detailDto.parentId)
                              .append(taxonId, detailDto.taxonId)
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
  public String getParentNaam(String taal) {
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

  public Integer getParentVolgnummer() {
    return parentVolgnummer;
  }

  public String getRang() {
    return rang;
  }

  public Long getTaxonId() {
    return taxonId;
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
    return new HashCodeBuilder().append(parentId).append(taxonId).toHashCode();
  }

  public boolean isUitgestorven() {
    return getUitgestorven();
  }
}
