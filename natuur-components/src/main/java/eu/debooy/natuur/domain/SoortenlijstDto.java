/*
 * Copyright (c) 2026 Marco de Booij
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
import eu.debooy.natuur.NatuurConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only. Het geeft alle 'soorten' die gezien zijn.
 *
 * @author Marco de Booij
 */
@Entity
@Table(name="SOORTENLIJST", schema="NATUUR")
public class SoortenlijstDto
    extends Dto implements Comparable<SoortenlijstDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_LATIJNSENAAM        = "latijnsenaam";
  public static final String  COL_OPFOTO              = "opFoto";
  public static final String  COL_RANG                = "rang";
  public static final String  COL_TAXONID             = "taxonId";
  public static final String  COL_VOLGNUMMER          = "volgnummer";

  @ReadOnly
  @Column(name="LATIJNSENAAM", insertable=false, updatable=false)
  private String  latijnsenaam;
  @ReadOnly
  @Column(name="OP_FOTO", insertable=false, updatable=false)
  private Integer opFoto;
  @ReadOnly
  @Column(name="RANG", insertable=false, updatable=false)
  private String  rang;
  @Id
  @ReadOnly
  @Column(name="TAXON_ID", insertable=false, updatable=false)
  private Long    taxonId;
  @ReadOnly
  @Column(name="VOLGNUMMER", insertable=false, updatable=false)
  private Long    volgnummer;

  @ReadOnly
  @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonDto.class, orphanRemoval=false)
  @JoinColumn(name="TAXON_ID", updatable=false, insertable=false)
  private TaxonDto  taxon;

  public static class LatijnsenaamComparator
      implements Comparator<SoortenlijstDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(SoortenlijstDto soortenlijstDto1,
                       SoortenlijstDto soortenlijstDto2) {
      return soortenlijstDto1.latijnsenaam
                             .compareTo(soortenlijstDto2.latijnsenaam);
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele namen niet te laten verdwijnen in
   * een Map.
   */
  public static class NaamComparator
      implements Comparator<SoortenlijstDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = NatuurConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(SoortenlijstDto soortenlijstDto1,
                       SoortenlijstDto soortenlijstDto2) {
      return new CompareToBuilder().append(soortenlijstDto1.getNaam(taal),
                                           soortenlijstDto2.getNaam(taal))
                                   .append(soortenlijstDto1.getLatijnsenaam(),
                                           soortenlijstDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  public static class VolgnummerLatijnsenaamComparator
      implements Comparator<SoortenlijstDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(SoortenlijstDto soortenlijstDto1,
                       SoortenlijstDto soortenlijstDto2) {
      return new CompareToBuilder().append(soortenlijstDto1.getVolgnummer(),
                                           soortenlijstDto2.getVolgnummer())
                                   .append(soortenlijstDto1.getLatijnsenaam(),
                                           soortenlijstDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  /**
   * De Latijnsenaam is toegevoegd om dubbele volgnummer+namen niet te laten
   * verdwijnen in een Map.
   */
  public static class VolgnummerNaamComparator
      implements Comparator<SoortenlijstDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = NatuurConstants.DEF_TAAL;

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(SoortenlijstDto soortenlijstDto1,
                       SoortenlijstDto soortenlijstDto2) {
      return new CompareToBuilder().append(soortenlijstDto1.getVolgnummer(),
                                           soortenlijstDto2.getVolgnummer())
                                   .append(soortenlijstDto1.getNaam(taal),
                                           soortenlijstDto2.getNaam(taal))
                                   .append(soortenlijstDto1.getLatijnsenaam(),
                                           soortenlijstDto2.getLatijnsenaam())
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(SoortenlijstDto soortenlijstDto) {
    return new CompareToBuilder().append(taxonId, soortenlijstDto.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof SoortenlijstDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var soortenlijstDto = (SoortenlijstDto) object;
    return new EqualsBuilder().append(taxonId, soortenlijstDto.taxonId)
                              .isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  @Transient
  public String getNaam(String taal) {
    return taxon.getNaam(taal);
  }

  public Integer getOpFoto() {
    return opFoto;
  }

  public String getRang() {
    return rang;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public Long getVolgnummer() {
    return volgnummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  @Transient
  public boolean hasTaxonnaam(String taal) {
    return taxon.hasTaxonnaam(taal);
  }

  @Transient
  public boolean isOpFoto(){
    return opFoto.equals(1);
  }
}
