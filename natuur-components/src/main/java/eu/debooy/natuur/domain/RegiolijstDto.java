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

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.domain.Dto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="REGIOLIJSTEN", schema="NATUUR")
@NamedQuery(name="regiolijstenPerTaxon", query="select r from RegiolijstDto r, RegiolijstTaxonDto rt where r.regiolijstId=rt.regiolijstId and rt.taxonId=:taxonId")
public class RegiolijstDto
    extends Dto implements Comparable<RegiolijstDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_DATUM         = "datum";
  public static final String  COL_OMSCHRIJVING  = "omschrijving";
  public static final String  COL_REGIOID       = "regioId";
  public static final String  COL_REGIOLIJSTID  = "regiolijstId";

  public static final String  PAR_TAXONID = "taxonId";

  public static final String  QRY_PERTAXON  = "regiolijstenPerTaxon";

  @Column(name="DATUM", nullable=false)
  private Date    datum;
  @Column(name="OMSCHRIJVING", length=2000)
  private String  omschrijving;
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;
  @Id
  @Column(name="REGIOLIJST_ID", nullable=false)
  private Long    regiolijstId;

  @Override
  public int compareTo(RegiolijstDto regiolijstDto) {
    return new CompareToBuilder().append(regioId, regiolijstDto.regioId)
                                 .append(datum, regiolijstDto.datum)
                                 .append(regiolijstId,
                                         regiolijstDto.regiolijstId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof RegiolijstDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijstDto = (RegiolijstDto) object;

    return new EqualsBuilder().append(regioId, regiolijstDto.regioId)
                              .append(datum, regiolijstDto.datum)
                              .append(regiolijstId, regiolijstDto.regiolijstId)
                              .isEquals();
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return new Date(datum.getTime());
  }

  public String getOmschrijving() {
    return omschrijving;
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getRegiolijstId() {
    return regiolijstId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(datum)
                                .append(regiolijstId).toHashCode();
  }

  public void setDatum(Date datum) {
    this.datum        = Datum.stripTime(datum);
  }

  public void setOmschrijving(String omschrijving) {
    this.omschrijving   = DoosUtils.strip(omschrijving);
  }

  public void setRegioId(Long regioId) {
    this.regioId        = regioId;
  }

  public void setRegiolijstId(Long regiolijstId) {
    this.regiolijstId   = regiolijstId;
  }
}
