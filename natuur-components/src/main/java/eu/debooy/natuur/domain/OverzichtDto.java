/*
 * Copyright (c) 2021 Marco de Booij
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name="OVERZICHT", schema="NATUUR")
@IdClass(OverzichtPK.class)
@NamedQuery(name="overzichtRang", query="select o from OverzichtDto o where o.parentRang=:parentrang")
public class OverzichtDto extends Dto implements Comparable<OverzichtDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_OPFOTO              = "opFoto";
  public static final String  COL_PARENTID            = "parentId";
  public static final String  COL_PARENTLATIJNSENAAM  = "parentLatijnsenaam";
  public static final String  COL_PARENTRANG          = "parentRang";
  public static final String  COL_PARENTVOLGNUMMER    = "parentVolgnummer";
  public static final String  COL_RANG                = "rang";
  public static final String  COL_TOTAAL              = "totaal";
  public static final String  COL_WAARGENOMEN         = "waargenomen";

  public static final String  PAR_PARENTRANG  = "parentrang";

  public static final String  QRY_OVERZICHTRANG = "overzichtRang";

  @ReadOnly
  @Column(name="OP_FOTO", insertable= false, updatable=false)
  private Integer   opFoto;
  @Id
  @ReadOnly
  @Column(name="PARENT_ID", insertable= false, updatable=false)
  private Long      parentId;
  @ReadOnly
  @Column(name="PARENT_LATIJNSENAAM", length=255, insertable= false, updatable=false)
  private String    parentLatijnsenaam;
  @Id
  @ReadOnly
  @Column(name="PARENT_RANG", length=3, insertable= false, updatable=false)
  private String    parentRang;
  @ReadOnly
  @Column(name="PARENT_VOLGNUMMER", insertable= false, updatable=false)
  private Integer   parentVolgnummer;
  @Id
  @ReadOnly
  @Column(name="RANG", length=3, insertable= false, updatable=false)
  private String    rang;
  @ReadOnly
  @Column(name="TOTAAL", insertable= false, updatable=false)
  private Integer   totaal;
  @ReadOnly
  @Column(name="WAARGENOMEN", insertable= false, updatable=false)
  private Integer   waargenomen;

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=false)
  @JoinColumn(name="TAXON_ID", referencedColumnName="PARENT_ID", nullable=false, updatable=false, insertable=false)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> parentnamen = new HashMap<>();

  @Override
  public int compareTo(OverzichtDto overzichtDto) {
    return new CompareToBuilder().append(parentVolgnummer,
                                         overzichtDto.parentVolgnummer)
                                 .append(parentLatijnsenaam,
                                         overzichtDto.parentLatijnsenaam)
                                 .append(rang, overzichtDto.rang)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof OverzichtDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    OverzichtDto  overzichtDto  = (OverzichtDto) object;
    return new EqualsBuilder().append(parentLatijnsenaam,
                                      overzichtDto.parentLatijnsenaam)
                              .append(parentRang, overzichtDto.parentRang)
                              .append(rang, overzichtDto.rang)
                              .isEquals();
  }

  @Transient
  public String getNaam(String taal) {
    if (parentnamen.containsKey(taal)) {
      return parentnamen.get(taal).getNaam();
    } else {
      return parentLatijnsenaam;
    }
  }

  public Integer getOpFoto() {
    return opFoto;
  }

  public Long getParentId() {
    return parentId;
  }

  public String getParentLatijnsenaam() {
    return parentLatijnsenaam;
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

  public Integer getTotaal() {
    return totaal;
  }

  public Integer getWaargenomen() {
    return waargenomen;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(parentLatijnsenaam)
                                .append(parentRang)
                                .append(rang).toHashCode();
  }
}
