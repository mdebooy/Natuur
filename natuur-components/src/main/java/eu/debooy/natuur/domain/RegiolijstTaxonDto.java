/**
 * Copyright (c) 2023 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.domain.Dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOLIJST_TAXA", schema="NATUUR")
@IdClass(RegiolijstTaxonPK.class)
@NamedQuery(name="regiolijsttaxonPerRegiolijst", query="select r from RegiolijstTaxonDto r where r.regiolijstId=:regiolijstId")
@NamedQuery(name="regiolijsttaxonPerTaxon", query="select r from RegiolijstTaxonDto r where r.taxonId=:taxonId")
// Zonder is de query veel te traag: and o.parentRang in ('so', 'oso') and o.rang in ('so', 'oso')
@NamedQuery(name="regiolijsttaxonTotalenPerRegiolijst", query="select r.regiolijstId, rl.regioId, rl.datum, count(r.taxonId), sum(o.waargenomen) from RegiolijstTaxonDto r, OverzichtDto o, RegiolijstDto rl where r.regiolijstId=rl.regiolijstId and r.taxonId=o.parentId and o.parentRang in ('so', 'oso') and o.rang in ('so', 'oso') and o.parentRang=o.rang group by r.regiolijstId, rl.regioId, rl.datum")
public class RegiolijstTaxonDto extends Dto implements Comparable<RegiolijstTaxonDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_REGIOLIJSTID  = "regiolijstId";
  public static final String  COL_STATUS        = "status";
  public static final String  COL_TAXONID       = "taxonId";

  public static final String  PAR_REGIOLIJSTID  = "regiolijstId";
  public static final String  PAR_TAXONID       = "taxonId";

  public static final String  QRY_TOTPERREGIOLIJST  =
      "regiolijsttaxonTotalenPerRegiolijst";
  public static final String  QRY_REGIOLIJST        =
      "regiolijsttaxonPerRegiolijst";
  public static final String  QRY_TAXON             = "regiolijsttaxonPerTaxon";

  @Transient
  private boolean   gezien  = false;
  @Id
  @Column(name="REGIOLIJST_ID", nullable=false)
  private Long      regiolijstId;
  @Column(name="STATUS", length = 2)
  private String    status;
  @Id
  @Column(name="TAXON_ID", nullable=false)
  private Long      taxonId;

  @OneToOne
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;

  public static class VolgnummerLatijnsenaamComparator
      implements Comparator<RegiolijstTaxonDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(RegiolijstTaxonDto taxonDto1,
                       RegiolijstTaxonDto taxonDto2) {
      return new CompareToBuilder()
                    .append(taxonDto1.getTaxon().getVolgnummer(),
                            taxonDto2.getTaxon().getVolgnummer())
                    .append(taxonDto1.getTaxon().getLatijnsenaam(),
                            taxonDto2.getTaxon().getLatijnsenaam())
                    .toComparison();
    }
  }

  @Override
  public int compareTo(RegiolijstTaxonDto naamDto) {
    return new CompareToBuilder().append(regiolijstId, naamDto.regiolijstId)
                                 .append(taxonId, naamDto.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegiolijstTaxonDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var naamDto = (RegiolijstTaxonDto) object;
    return new EqualsBuilder().append(regiolijstId, naamDto.regiolijstId)
                              .append(taxonId, naamDto.taxonId)
                              .isEquals();
  }

  public Long getRegiolijstId() {
    return regiolijstId;
  }

  public String getStatus() {
    return status;
  }

  public TaxonDto getTaxon() {
    return taxon;
  }

  public Long getTaxonId() {
    return taxonId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regiolijstId)
                                .append(taxonId).toHashCode();
  }

  public boolean isGezien() {
    return gezien;
  }

  public void setGezien(boolean gezien) {
    this.gezien       = gezien;
  }

  public void setRegiolijstId(Long regiolijstId) {
    this.regiolijstId = regiolijstId;
  }

  public void setStatus(String status) {
    this.status       = DoosUtils.stripToLowerCase(status);
  }

  public void setTaxon(TaxonDto taxon) {
    if (null == taxon) {
      this.taxon      = null;
    } else {
      this.taxon      = taxon;
    }
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId      = taxonId;
  }
}
