/**
 * Copyright (c) 2015 Marco de Booij
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
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Foto
    extends Formulier implements Comparable<Foto>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  fotoBestand;
  private String  fotoDetail;
  private Long    fotoId;
  private String  opmerking;
  private Long    taxonSeq    = 0L;
  private Long    waarnemingId;

  public Foto() {}

  public Foto(Foto foto) {
    fotoBestand   = foto.getFotoBestand();
    fotoDetail    = foto.getFotoDetail();
    fotoId        = foto.getFotoId();
    opmerking     = foto.getOpmerking();
    taxonSeq      = foto.getTaxonSeq();
    waarnemingId  = foto.getWaarnemingId();
  }

  public Foto(FotoDto foto) {
    fotoBestand   = foto.getFotoBestand();
    fotoDetail    = foto.getFotoDetail();
    fotoId        = foto.getFotoId();
    opmerking     = foto.getOpmerking();
    taxonSeq      = foto.getTaxonSeq();
    waarnemingId  = foto.getWaarnemingId();
  }

  @Override
  public int compareTo(Foto andere) {
    return new CompareToBuilder().append(fotoId, andere.fotoId).toComparison();
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
    return new EqualsBuilder().append(fotoId, andere.fotoId).isEquals();
  }

  public String getFotoBestand() {
    return fotoBestand;
  }

  public String getFotoDetail() {
    return fotoDetail;
  }

  public Long getFotoId() {
    return fotoId;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public Long getWaarnemingId() {
    return waarnemingId;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }

  public void persist(FotoDto fotoDto) {
    if (!new EqualsBuilder().append(fotoBestand,
                                    fotoDto.getFotoBestand()).isEquals()) {
      fotoDto.setFotoBestand(fotoBestand);
    }
    if (!new EqualsBuilder().append(fotoDetail,
                                    fotoDto.getFotoDetail()).isEquals()) {
      fotoDto.setFotoDetail(fotoDetail);
    }
    if (!new EqualsBuilder().append(opmerking,
                                    fotoDto.getOpmerking()).isEquals()) {
      fotoDto.setOpmerking(opmerking);
    }
    if (!new EqualsBuilder().append(fotoId,
                                    fotoDto.getFotoId()).isEquals()) {
      fotoDto.setFotoId(fotoId);
    }
    if (!new EqualsBuilder().append(taxonSeq,
                                    fotoDto.getTaxonSeq()).isEquals()) {
      fotoDto.setTaxonSeq(taxonSeq);
    }
    if (!new EqualsBuilder().append(waarnemingId,
                                    fotoDto.getWaarnemingId()).isEquals()) {
      fotoDto.setWaarnemingId(waarnemingId);
    }
  }

  public void setFotoBestand(String fotoBestand) {
    this.fotoBestand  = fotoBestand;
  }

  public void setFotoDetail(String fotoDetail) {
    this.fotoDetail = fotoDetail;
  }

  public void setFotoId(Long fotoId) {
    this.fotoId = fotoId;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking  = opmerking;
  }

  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq = taxonSeq;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
