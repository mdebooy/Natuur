/**
 * Copyright (c) 2015 Marco de Booij
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

import eu.debooy.doosutils.domain.Dto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="FOTOS", schema="NATUUR")
@NamedQuery(name="fotosPerWaarneming", query="select f from FotoDto f where f.waarnemingId=:waarnemingId")
public class FotoDto extends Dto implements Comparable<FotoDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_FOTOBESTAND   = "fotoBestand";
  public static final String  COL_FOTODETAIL    = "fotoDetail";
  public static final String  COL_FOTOID        = "fotoId";
  public static final String  COL_OPMERKING     = "opmerking";
  public static final String  COL_TAXONSEQ      = "taxonSeq";
  public static final String  COL_WAARNEMINGID  = "waarnemingId";

  public static final String  PAR_WAARNEMINGID  = "waarnemingId";

  public static final String  QRY_PERWAARNEMING = "fotosPerWaarneming";

  @Column(name="FOTO_BESTAND", length=255)
  private String  fotoBestand;
  @Column(name="FOTO_DETAIL", length=20)
  private String  fotoDetail;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="FOTO_ID", nullable=false)
  private Long    fotoId;
  @Column(name="OPMERKING", length=2000)
  private String  opmerking;
  @Column(name="TAXON_SEQ", nullable=false)
  private Long    taxonSeq    = Long.valueOf("0");
  @Column(name="WAARNEMING_ID", nullable=false)
  private Long    waarnemingId;

  @Override
  public int compareTo(FotoDto fotoDto) {
    return new CompareToBuilder().append(fotoId, fotoDto.fotoId).toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof FotoDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var fotoDto = (FotoDto) object;

    return new EqualsBuilder().append(fotoId, fotoDto.fotoId).isEquals();
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

  public void setFotoBestand(String fotoBestand) {
    this.fotoBestand  = fotoBestand;
  }

  public void setFotoDetail(String fotoDetail) {
    this.fotoDetail   = fotoDetail;
  }

  public void setFotoId(Long fotoId) {
    this.fotoId       = fotoId;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking    = opmerking;
  }

  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq     = taxonSeq;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
