/*
 * Copyright (c) 2022 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only.
 *
 * @author Marco de Booij
 */
@Entity
@Table(name="GEEN_FOTO", schema="NATUUR")
@IdClass(GeenFotoPK.class)
@NamedQuery(name="geenfotoPerRang", query="select g from GeenFotoDto g where g.parentRang=:rang")
@NamedQuery(name="geenfotoPerTaxon", query="select g from GeenFotoDto g where g.parentId=:taxonId")
public class GeenFotoDto extends Dto implements Comparable<GeenFotoDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_PARENTID    = "parentId";
  public static final String  COL_PARENTRANG  = "parentRang";
  public static final String  COL_TAXONID     = "taxonId";

  public static final String  PAR_PARENTRANG  = "rang";
  public static final String  PAR_TAXONID     = "taxonId";

  public static final String  QRY_PERRANG     = "geenfotoPerRang";
  public static final String  QRY_PERTAXON    = "geenfotoPerTaxon";

  @Id
  @ReadOnly
  @Column(name="PARENT_ID", insertable= false, updatable=false)
  private Long      parentId;
  @OneToOne
  @ReadOnly
  @JoinColumn(name="PARENT_ID", referencedColumnName="TAXON_ID", nullable=false)
  private TaxonDto  parent;
  @Id
  @ReadOnly
  @Column(name="PARENT_RANG", length=3, nullable=false)
  private String    parentRang;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable= false, updatable=false)
  private Long      taxonId;
  @OneToOne
  @ReadOnly
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;

  @Override
  public int compareTo(GeenFotoDto geenFotoDto) {
    return
        new CompareToBuilder().append(parent.getVolgnummer(),
                                      geenFotoDto.getParent().getVolgnummer())
                              .append(taxon.getVolgnummer(),
                                      geenFotoDto.getTaxon().getVolgnummer())
                              .append(parent.getLatijnsenaam(),
                                      geenFotoDto.getParent().getLatijnsenaam())
                              .append(taxon.getLatijnsenaam(),
                                      geenFotoDto.getTaxon().getLatijnsenaam())
                              .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof GeenFotoDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var geenFotoDto  = (GeenFotoDto) object;
    return new EqualsBuilder().append(parent.getParentId(),
                                      geenFotoDto.getParent().getParentId())
                              .append(parentRang, geenFotoDto.getParentRang())
                              .append(taxon.getTaxonId(),
                                      geenFotoDto.getTaxon().getTaxonId())
                              .isEquals();
  }

  public TaxonDto getParent() {
    return parent;
  }

  public String getParentRang() {
    return parentRang;
  }

  public Long getParentId() {
    return parentId;
  }

  public TaxonDto getTaxon() {
    return taxon;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentId)
                                .append(parentRang)
                                .append(taxonId).toHashCode();
  }
}
