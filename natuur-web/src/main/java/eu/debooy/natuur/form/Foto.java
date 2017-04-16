/**
 * Copyright 2015 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the Licence. You may
 * obtain a copy of the Licence at:
 *
 * http://www.osor.eu/eupl
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */
package eu.debooy.natuur.form;

import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Foto
    extends Formulier implements Cloneable, Comparable<Foto>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Long    fotoId;
  private Gebied  gebied;
  private Taxon   taxon;
  private Long    taxonSeq;

  public Foto() {}

  public Foto(FotoDto fotoDto) {
    fotoId    = fotoDto.getFotoId();
    gebied    = new Gebied(fotoDto.getGebied());
    taxon     = new Taxon(fotoDto.getTaxon());
    taxonSeq  = fotoDto.getTaxonSeq();
  }

  public Foto(FotoDto fotoDto, String taal) {
    fotoId    = fotoDto.getFotoId();
    gebied    = new Gebied(fotoDto.getGebied());
    taxon     = new Taxon(fotoDto.getTaxon(), taal);
    taxonSeq  = fotoDto.getTaxonSeq();
  }
  
  public Foto clone() throws CloneNotSupportedException {
    Foto  clone = (Foto) super.clone();

    return clone;
  }

  public int compareTo(Foto andere) {
    return new CompareToBuilder().append(fotoId, andere.fotoId).toComparison();
  }

  public boolean equals(Object object) {
    if (!(object instanceof Foto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Foto  andere  = (Foto) object;
    return new EqualsBuilder().append(fotoId, andere.fotoId).isEquals();
  }

  public Long getFotoId() {
    return fotoId;
  }

  public Gebied getGebied() {
    return gebied;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }

  public boolean isGewijzigd() {
    return gewijzigd;
  }

  public void persist(FotoDto fotoDto) {
    if (!new EqualsBuilder().append(gebied, fotoDto.getGebied())
                            .isEquals()) {
      GebiedDto gebiedDto = new GebiedDto();
      gebied.persist(gebiedDto);
      fotoDto.setGebied(gebiedDto);
    }
    if (!new EqualsBuilder().append(taxon, fotoDto.getTaxon())
                            .isEquals()) {
      TaxonDto  taxonDto  = new TaxonDto();
      taxon.persist(taxonDto);
      fotoDto.setTaxon(taxonDto);
    }
    if (!new EqualsBuilder().append(fotoId,
                                    fotoDto.getFotoId()).isEquals()) {
      fotoDto.setFotoId(fotoId);
    }
    if (!new EqualsBuilder().append(taxonSeq,
                                    fotoDto.getTaxonSeq()).isEquals()) {
      fotoDto.setTaxonSeq(taxonSeq);
    }
  }

  public void setFotoId(Long fotoId) {
    if (!new EqualsBuilder().append(this.fotoId, fotoId).isEquals()) {
      gewijzigd   = true;
      this.fotoId = fotoId;
    }
  }

  public void setGebied(Gebied gebied) {
    if (!new EqualsBuilder().append(this.gebied, gebied).isEquals()) {
      gewijzigd   = true;
      try {
        this.gebied = gebied.clone();
      } catch (CloneNotSupportedException e) {
        this.gebied = gebied;
      }
    }
  }

  public void setTaxon(Taxon taxon) {
    if (!new EqualsBuilder().append(this.taxon, taxon).isEquals()) {
      gewijzigd   = true;
      try {
        this.taxon = taxon.clone();
      } catch (CloneNotSupportedException e) {
        this.taxon = taxon;
      }
    }
  }

  public void setTaxonSeq(Long taxonSeq) {
    if (!new EqualsBuilder().append(this.taxonSeq, taxonSeq)
                            .isEquals()) {
      gewijzigd = true;
      if (null == taxonSeq) {
        this.taxonSeq = null;
      } else {
        this.taxonSeq = taxonSeq;
      }
    }
  }
}
