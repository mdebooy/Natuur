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

import java.util.Collection;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
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
@NamedQueries({
  @NamedQuery(name="detailSoortMetKlasse", query="select d from DetailDto d where d.parentRang='kl' and d.rang in ('so', 'oso')"),
  @NamedQuery(name="detailTotalen", query="select d.parentId as naam, d.parentLatijnsenaam as latijnsenaam, count(d.parentId) as totaal, sum(d.opFoto) from DetailDto d where d.parentRang=:groep and d.rang in ('so', 'oso') group by d.parentId, d.parentLatijnsenaam"),
  @NamedQuery(name="detailWaargenomen", query="select d from DetailDto d where d.taxonId in (select distinct w.taxon.taxonId from WaarnemingDto w) and d.parentRang='kl'")
})
public class DetailDto extends Dto implements Comparable<DetailDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  QRY_SOORTMETKLASSE  = "detailSoortMetKlasse";
  public static final String  QRY_TOTALEN         = "detailTotalen";
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
  @Column(name="RANG", insertable= false, updatable=false)
  private String    rang;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable= false, updatable=false)
  private Long      taxonId;

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", referencedColumnName="PARENT_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> parentnamen =
      new HashMap<String, TaxonnaamDto>();

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> taxonnamen  =
      new HashMap<String, TaxonnaamDto>();

  public int compareTo(DetailDto detailDto) {
    return new CompareToBuilder().append(parentId, detailDto.parentId)
                                 .append(taxonId, detailDto.taxonId)
                                 .toComparison();
  }

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

  public String getParentRang() {
    return parentRang;
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

  public int hashCode() {
    return new HashCodeBuilder().append(parentId).append(taxonId).toHashCode();
  }
}
