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

import eu.debooy.doosutils.domain.Dto;
import java.io.Serializable;
import java.util.Comparator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOLIJST_TAXA", schema="NATUUR")
@IdClass(RegiolijstTaxonPK.class)
@NamedQuery(name="regiolijsttaxonPerRegio", query="select r from RegiolijstTaxonDto r where r.regioId=:regioId")
@NamedQuery(name="regiolijsttaxonPerTaxon", query="select r from RegiolijstTaxonDto r where r.taxonId=:taxonId")
@NamedQuery(name="regiolijsttaxonTotalenPerRegio", query="select r.regioId, count(r.taxonId), sum(o.waargenomen) from RegiolijstTaxonDto r, OverzichtDto o where r.taxonId=o.parentId and o.parentRang=o.rang group by r.regioId")
public class RegiolijstTaxonDto extends Dto implements Comparable<RegiolijstTaxonDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_REGIOID = "regioId";
  public static final String  COL_STATUS  = "status";
  public static final String  COL_TAXONID = "taxonId";

  public static final String  PAR_REGIOID = "regioId";
  public static final String  PAR_TAXONID = "taxonId";

  public static final String  QRY_TOTPERREGIO =
      "regiolijsttaxonTotalenPerRegio";
  public static final String  QRY_REGIO       = "regiolijsttaxonPerRegio";
  public static final String  QRY_TAXON       = "regiolijsttaxonPerTaxon";

  @Transient
  private boolean   gezien  = false;
  @Id
  @Column(name="REGIO_ID", nullable=false)
  private Long      regioId;
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
    return new CompareToBuilder().append(regioId, naamDto.regioId)
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
    return new EqualsBuilder().append(regioId, naamDto.regioId)
                              .append(taxonId, naamDto.taxonId)
                              .isEquals();
  }

  public Long getRegioId() {
    return regioId;
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
    return new HashCodeBuilder().append(regioId).append(taxonId).toHashCode();
  }

  public boolean isGezien() {
    return gezien;
  }

  public void setGezien(boolean gezien) {
    this.gezien   = gezien;
  }

  public void setRegioId(Long regioId) {
    this.regioId  = regioId;
  }

  public void setStatus(String status) {
    if (null == status) {
      this.status = null;
    } else {
      this.status = status;
    }
  }

  public void setTaxon(TaxonDto taxon) {
    if (null == taxon) {
      this.taxon= null;
    } else {
      this.taxon = taxon;
    }
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId  = taxonId;
  }
}
