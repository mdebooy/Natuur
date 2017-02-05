/**
 * Copyright 2017 Marco de Booij
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

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.WaarnemingDto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Waarneming extends Formulier
    implements Cloneable, Comparable<Waarneming>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Integer aantal;
  private Date    datum;
  private Gebied  gebied;
  private String  opmerking;
  private Taxon   taxon;
  private Long    waarnemingId;

  public Waarneming() {}

  public Waarneming(WaarnemingDto waarnemingDto) {
    aantal        = waarnemingDto.getAantal();
    datum         = waarnemingDto.getDatum();
    gebied        = new Gebied(waarnemingDto.getGebied());
    opmerking     = waarnemingDto.getOpmerking();
    taxon         = new Taxon(waarnemingDto.getTaxon());
    waarnemingId  = waarnemingDto.getWaarnemingId();
  }
  
  public Waarneming(WaarnemingDto waarnemingDto, String taal) {
    aantal        = waarnemingDto.getAantal();
    datum         = waarnemingDto.getDatum();
    gebied        = new Gebied(waarnemingDto.getGebied());
    opmerking     = waarnemingDto.getOpmerking();
    taxon         = new Taxon(waarnemingDto.getTaxon(), taal);
    waarnemingId  = waarnemingDto.getWaarnemingId();
  }

  public Waarneming clone() throws CloneNotSupportedException {
    Waarneming  clone = (Waarneming) super.clone();

    return clone;
  }

  public int compareTo(Waarneming andere) {
    return new CompareToBuilder().append(waarnemingId,
                                         andere.waarnemingId).toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Foto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Waarneming  andere  = (Waarneming) object;
    return new EqualsBuilder().append(waarnemingId,
                                      andere.waarnemingId).isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder().append(waarnemingId).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(WaarnemingDto waarnemingDto) {
    if (!new EqualsBuilder().append(aantal, waarnemingDto.getAantal())
                            .isEquals()) {
      waarnemingDto.setAantal(aantal);
    }
    if (!new EqualsBuilder().append(datum, waarnemingDto.getDatum())
                            .isEquals()) {
      waarnemingDto.setDatum(datum);
    }
    if (!new EqualsBuilder().append(gebied, waarnemingDto.getGebied())
                            .isEquals()) {
      GebiedDto gebiedDto = new GebiedDto();
      gebied.persist(gebiedDto);
      waarnemingDto.setGebied(gebiedDto);
    }
    if (!new EqualsBuilder().append(opmerking, waarnemingDto.getOpmerking())
                            .isEquals()) {
      waarnemingDto.setOpmerking(opmerking);
    }
    if (!new EqualsBuilder().append(taxon, waarnemingDto.getTaxon())
                            .isEquals()) {
      TaxonDto  taxonDto  = new TaxonDto();
      taxon.persist(taxonDto);
      waarnemingDto.setTaxon(taxonDto);
    }
  }

  public Integer getAantal() {
    return aantal;
  }

  public Date getDatum() {
    return datum;
  }

  public Gebied getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Long getWaarnemingId() {
    return waarnemingId;
  }

  public void setAantal(Integer aantal) {
    this.aantal = aantal;
  }

  public void setDatum(Date datum) {
    this.datum = datum;
  }

  public void setGebied(Gebied gebied) {
    this.gebied = gebied;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking = opmerking;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon = taxon;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}