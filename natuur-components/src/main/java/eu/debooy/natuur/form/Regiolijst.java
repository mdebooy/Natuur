/*
 * Copyright (c) 2023 Marco de Booij
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

package eu.debooy.natuur.form;

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.RegiolijstDto;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Regiolijst extends Formulier
    implements Comparable<Regiolijst>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Date    einddatum;
  private String  omschrijving;
  private Long    regioId;
  private Long    regiolijstId;
  private Date    startdatum;

  public Regiolijst() {}

  public Regiolijst(RegiolijstDto regiolijst) {
    einddatum     = regiolijst.getEinddatum();
    omschrijving  = regiolijst.getOmschrijving();
    regioId       = regiolijst.getRegioId();
    regiolijstId  = regiolijst.getRegiolijstId();
    startdatum    = regiolijst.getStartdatum();
  }

  public static class SelecttekstComparator
      implements Comparator<Regiolijst>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Regiolijst regiolijst1, Regiolijst regiolijst2) {
      return new CompareToBuilder().append(regiolijst1.getOmschrijving(),
                                           regiolijst2.getOmschrijving())
                                   .append(regiolijst1.getStartdatum(),
                                           regiolijst2.getStartdatum())
                                   .append(regiolijst1.getEinddatum(),
                                           regiolijst2.getEinddatum())
                                   .append(regiolijst1.getRegioId(),
                                           regiolijst2.getRegioId())
                                   .toComparison();
    }
  }

  @Override
  public int compareTo(Regiolijst regiolijst) {
    return new CompareToBuilder().append(regioId, regiolijst.regioId)
                                 .append(startdatum, regiolijst.startdatum)
                                 .append(einddatum, regiolijst.einddatum)
                                 .append(regiolijstId, regiolijst.regiolijstId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Regiolijst)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var regiolijst  = (Regiolijst) object;
    return new EqualsBuilder().append(regioId, regiolijst.regioId)
                              .append(startdatum, regiolijst.startdatum)
                              .append(einddatum, regiolijst.einddatum)
                              .append(regiolijstId, regiolijst.regiolijstId)
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

  public String getPeriode() {
    if (null == einddatum) {
      return Datum.fromDate(startdatum);
    }

    return String.format("%s - %s", Datum.fromDate(startdatum),
                                    Datum.fromDate(einddatum));
  }

  public Long getRegioId() {
    return regioId;
  }

  public Long getRegiolijstId() {
    return regiolijstId;
  }

  public String getSelecttekst() {
    return String.format("%s (%s)", omschrijving, getPeriode());
  }

  public Date getStartdatum() {
    if (null == startdatum) {
      return null;
    }

    return new Date(startdatum.getTime());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(regioId).append(startdatum)
                                .append(einddatum)
                                .append(regiolijstId).toHashCode();
  }

  public void persist(RegiolijstDto regiolijstDto) {
    regiolijstDto.setEinddatum(einddatum);
    regiolijstDto.setOmschrijving(omschrijving);
    regiolijstDto.setRegioId(regioId);
    regiolijstDto.setRegiolijstId(regiolijstId);
    regiolijstDto.setStartdatum(startdatum);
  }

  public void setEinddatum(Date einddatum) {
    this.einddatum        = Datum.stripTime(einddatum);
  }

  public void setOmschrijving(String omschrijving) {
    this.omschrijving = DoosUtils.strip(omschrijving);
  }

  public void setRegioId(Long regioId) {
    this.regioId      = regioId;
  }

  public void setRegiolijstId(Long regiolijstId) {
    this.regiolijstId = regiolijstId;
  }

  public void setStartdatum(Date startdatum) {
    this.startdatum       = Datum.stripTime(startdatum);
  }
}
