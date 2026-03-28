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
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKey;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

  public static final String  COL_EINDDATUM     = "einddatum";
  public static final String  COL_OMSCHRIJVING  = "omschrijving";
  public static final String  COL_REGIOID       = "regioId";
  public static final String  COL_REGIOLIJSTID  = "regiolijstId";
  public static final String  COL_STARTDATUM    = "startdatum";

  public static final String  PAR_TAXONID = "taxonId";

  public static final String  QRY_PERTAXON  = "regiolijstenPerTaxon";

  @Column(name="EINDDATUM")
  private Date    einddatum;
  @Column(name="OMSCHRIJVING", length=2000)
  private String  omschrijving;
  @Column(name="REGIO_ID", nullable=false)
  private Long    regioId;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="REGIOLIJST_ID", nullable=false, unique=true, updatable=false)
  private Long    regiolijstId;
  @Column(name="STARTDATUM", nullable=false)
  private Date    startdatum;

  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity=RegiolijstTaxonDto.class, orphanRemoval=true)
  @JoinColumn(name="REGIOLIJST_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name=RegiolijstTaxonDto.COL_TAXONID)
  private Map<Long, RegiolijstTaxonDto>  taxa = new HashMap<>();

  @Override
  public int compareTo(RegiolijstDto regiolijstDto) {
    return new CompareToBuilder().append(regioId, regiolijstDto.regioId)
                                 .append(startdatum, regiolijstDto.startdatum)
                                 .append(einddatum, regiolijstDto.einddatum)
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
                              .append(startdatum, regiolijstDto.startdatum)
                              .append(einddatum, regiolijstDto.einddatum)
                              .append(regiolijstId, regiolijstDto.regiolijstId)
                              .isEquals();
  }

  public Date getEinddatum() {
    if (null == einddatum) {
      return null;
    }

    return new Date(einddatum.getTime());
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

  public Date getStartdatum() {
    if (null == startdatum) {
      return null;
    }

    return new Date(startdatum.getTime());
  }

  public Map<Long, RegiolijstTaxonDto> getTaxa() {
    return taxa;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(startdatum)
                                .append(einddatum)
                                .append(regiolijstId).toHashCode();
  }

  public void setEinddatum(Date einddatum) {
    this.einddatum      = Datum.stripTime(einddatum);
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

  public void setStartdatum(Date startdatum) {
    this.startdatum     = Datum.stripTime(startdatum);
  }
}
