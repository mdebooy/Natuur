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

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Foto implements Comparable<Foto>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private boolean gewijzigd = false;

  private Long    taxonId;
  private Long    taxonSeq;

  public Foto() {}

  public Foto(FotoDto fotoDto) {
    taxonSeq  = fotoDto.getTaxonSeq();
  }

  @Override
  public int compareTo(Foto andere) {
    return new CompareToBuilder().append(taxonId, andere.getTaxonId())
                                 .append(taxonSeq, andere.getTaxonSeq())
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Foto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    Foto  andere  = (Foto) object;
    return new EqualsBuilder().append(taxonId, andere.taxonId)
                              .append(taxonSeq, andere.taxonSeq)
                              .isEquals();
  }

  /**
   * @return de taxonId
   */
  public Long getTaxonId() {
    return taxonId;
  }

  /**
   * @return de taxonSeq
   */
  public Long getTaxonSeq() {
    return taxonSeq;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).append(taxonSeq)
                                .toHashCode();
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
    if (!new EqualsBuilder().append(this.taxonSeq,
                                    parameter.getTaxonSeq()).isEquals()) {
      parameter.setTaxonSeq(this.taxonSeq);
    }
  }

  /**
   * @param Long de waarde van taxonId
   */
  public void setTaxonId(Long taxonId) {
    if (!new EqualsBuilder().append(this.taxonId, taxonId).isEquals()) {
      gewijzigd = true;
      if (null == taxonId) {
        this.taxonId  = null;
      } else {
        this.taxonId  = taxonId;
      }
    }
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
}
