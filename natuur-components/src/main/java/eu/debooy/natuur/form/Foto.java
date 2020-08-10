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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.domain.TaxonDto;
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
  private Gebied  gebied;
  private String  opmerking;
  private Taxon   taxon;
  private Long    taxonSeq    = 0L;

  public Foto() {}

  public Foto(Foto foto) {
    fotoBestand = foto.getFotoBestand();
    fotoDetail  = foto.getFotoDetail();
    fotoId      = foto.getFotoId();
    if (null == foto.getGebied()) {
      gebied    = null;
    } else {
      gebied    = new Gebied(foto.getGebied());
    }
    opmerking   = foto.getOpmerking();
    if (null == foto.getTaxon()) {
      taxon     = null;
    } else {
      taxon     = new Taxon(foto.getTaxon());
    }
    taxonSeq    = foto.getTaxonSeq();
  }

  public Foto(FotoDto fotoDto) {
    this(fotoDto, null);
  }

  public Foto(FotoDto fotoDto, String taal) {
    fotoBestand = fotoDto.getFotoBestand();
    fotoDetail  = fotoDto.getFotoDetail();
    fotoId      = fotoDto.getFotoId();
    if (null == fotoDto.getGebied()) {
      gebied    = null;
    } else {
      gebied    = new Gebied(fotoDto.getGebied());
    }
    opmerking   = fotoDto.getOpmerking();
    if (null == fotoDto.getTaxon()) {
      taxon     = null;
    } else {
      if (DoosUtils.isBlankOrNull(taal)) {
        taxon   = new Taxon(fotoDto.getTaxon());
      } else {
        taxon   = new Taxon(fotoDto.getTaxon(), taal);
      }
    }
    taxonSeq    = fotoDto.getTaxonSeq();
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

  public Gebied getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Long getTaxonSeq() {
    return taxonSeq;
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
    if (!new EqualsBuilder().append(gebied, fotoDto.getGebied())
                            .isEquals()) {
      GebiedDto gebiedDto = new GebiedDto();
      gebied.persist(gebiedDto);
      fotoDto.setGebied(gebiedDto);
    }
    if (!new EqualsBuilder().append(opmerking,
                                    fotoDto.getOpmerking()).isEquals()) {
      fotoDto.setOpmerking(opmerking);
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

  public void setFotoBestand(String fotoBestand) {
    this.fotoBestand  = fotoBestand;
  }

  public void setFotoDetail(String fotoDetail) {
    this.fotoDetail = fotoDetail;
  }

  public void setFotoId(Long fotoId) {
    this.fotoId = fotoId;
  }

  public void setGebied(Gebied gebied) {
    this.gebied = new Gebied(gebied);
  }

  public void setOpmerking(String opmerking) {
    this.opmerking  = opmerking;
  }

  public void setTaxon(Taxon taxon) {
    this.taxon = new Taxon(taxon);
  }

  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq = taxonSeq;
  }
}
