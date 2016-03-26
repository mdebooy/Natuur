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

import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Foto implements Cloneable, Comparable<Foto>, Serializable {
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

  /**
   * Sorteren op de naam van de Taxon en de taxonSeq.
   */
  public static class LijstComparator
      implements Comparator<Foto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    public int compare(Foto foto1, Foto foto2) {
      return new CompareToBuilder().append(foto1.taxon.getNaam(),
                                           foto2.taxon.getNaam())
                                   .append(foto1.taxonSeq, foto2.taxonSeq)
                                   .toComparison();
    }
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

  /**
   * @return de fotoId
   */
  public Long getFotoId() {
    return fotoId;
  }

  /**
   * @return het gebied
   */
  public Gebied getGebied() {
    return gebied;
  }

  /**
   * @return de taxon
   */
  public Taxon getTaxon() {
    return taxon;
  }

  /**
   * @return de taxonSeq
   */
  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }

  /**
   * @return de gewijzigd
   */
  public boolean isGewijzigd() {
    return gewijzigd;
  }

  /**
   * Zet de gegevens in een FotoDto
   *
   * @param FotoDto
   */
  public void persist(FotoDto parameter) {
    if (!new EqualsBuilder().append(this.gebied, parameter.getGebied())
                            .isEquals()) {
      GebiedDto gebiedDto = new GebiedDto();
      gebied.persist(gebiedDto);
      parameter.setGebied(gebiedDto);
    }
    if (!new EqualsBuilder().append(this.taxon, parameter.getTaxon())
                            .isEquals()) {
      TaxonDto  taxonDto  = new TaxonDto();
      taxon.persist(taxonDto);
      parameter.setTaxon(taxonDto);
    }
    if (!new EqualsBuilder().append(this.taxonSeq,
                                    parameter.getTaxonSeq()).isEquals()) {
      parameter.setTaxonSeq(this.taxonSeq);
    }
  }

  /**
   * @param fotoId de waarde van fotoId
   */
  public void setFotoId(Long fotoId) {
    this.fotoId = fotoId;
  }

  /**
   * @param gebied de waarde van gebied
   */
  public void setGebied(Gebied gebied) throws CloneNotSupportedException {
    this.gebied = gebied.clone();
  }

  /**
   * @param taxon de waarde van taxon
   * @throws CloneNotSupportedException 
   */
  public void setTaxon(Taxon taxon) throws CloneNotSupportedException {
    this.taxon = taxon.clone();
  }

  /**
   * @param Long de waarde van taxonSeq
   */
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

  public String toString() {
    StringBuilder resultaat = new StringBuilder();
    resultaat.append("Foto (").append("[");
    if (null == gebied) {
      resultaat.append("Gebied <null>");
    } else {
      resultaat.append(gebied.toString());
    }
    resultaat.append("], ").append("[");
    if (null == taxon) {
      resultaat.append("Taxon <null>");
    } else {
      resultaat.append(taxon.toString());
    }
    resultaat.append("], ")
             .append("taxonSeq=[").append(taxonSeq).append("], ")
             .append("class=[").append(this.getClass().getSimpleName())
             .append("])");

    return resultaat.toString();
  }
}
