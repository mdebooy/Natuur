/**
 * Copyright (c) 2017 Marco de Booij
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
package eu.debooy.natuur.form;

import eu.debooy.doosutils.Datum;
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Waarneming extends Formulier
    implements Comparable<Waarneming>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private Integer aantal;
  private Integer aantalFotos;
  private Date    datum;
  private Gebied  gebied;
  private String  opmerking;
  private Taxon   taxon;
  private Long    waarnemingId;

  public Waarneming() {
    aantalFotos = 0;
  }

  public Waarneming(WaarnemingDto waarnemingDto) {
    this(waarnemingDto, null);
  }

  public Waarneming(WaarnemingDto waarnemingDto, String taal) {
    aantal        = waarnemingDto.getAantal();
    aantalFotos   = waarnemingDto.getAantalFotos();
    datum         = waarnemingDto.getDatum();
    if (null == waarnemingDto.getGebied()) {
      gebied      = null;
    } else {
      gebied      = new Gebied(waarnemingDto.getGebied());
    }
    opmerking     = waarnemingDto.getOpmerking();
    if (null == waarnemingDto.getTaxon()) {
      taxon       = null;
    } else {
      if (DoosUtils.isBlankOrNull(taal)) {
        taxon   = new Taxon(waarnemingDto.getTaxon());
      } else {
        taxon   = new Taxon(waarnemingDto.getTaxon(), taal);
      }
    }
    waarnemingId  = waarnemingDto.getWaarnemingId();
  }

  public static class DatumComparator
      implements Comparator<Waarneming>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    @Override
    public int compare(Waarneming waarneming1,
                       Waarneming waarneming2) {
      return waarneming1.datum.compareTo(waarneming2.datum);
    }
  }

  @Override
  public int compareTo(Waarneming waarneming) {
    return new CompareToBuilder().append(waarnemingId, waarneming.waarnemingId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Waarneming)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var waarneming  = (Waarneming) object;
    return new EqualsBuilder().append(waarnemingId, waarneming.waarnemingId)
                              .isEquals();
  }

  public Integer getAantal() {
    return aantal;
  }

  public Integer getAantalFotos() {
    return aantalFotos;
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return new Date(datum.getTime());
  }

  public Gebied getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public String getSorteerdatum() {
    if (null == datum) {
      return "";
    }

    return Datum.fromDate(datum, DoosConstants.SORTEERDATUM);
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Long getWaarnemingId() {
    return waarnemingId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(waarnemingId).toHashCode();
  }

  public void persist(WaarnemingDto waarnemingDto) {
    waarnemingDto.setAantal(aantal);
    waarnemingDto.setDatum(datum);
    var gebiedDto = new GebiedDto();
    gebied.persist(gebiedDto);
    waarnemingDto.setGebied(gebiedDto);
    waarnemingDto.setOpmerking(opmerking);
    var taxonDto  = new TaxonDto();
    taxon.persist(taxonDto);
    waarnemingDto.setTaxon(taxonDto);
    waarnemingDto.setWaarnemingId(waarnemingId);
  }

  public void setAantal(Integer aantal) {
    this.aantal = aantal;
  }

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum  = null;
    } else {
      this.datum  = new Date(datum.getTime());
    }
  }

  public void setGebied(Gebied gebied) {
    this.gebied = gebied;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking    = opmerking;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon  = taxon;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
