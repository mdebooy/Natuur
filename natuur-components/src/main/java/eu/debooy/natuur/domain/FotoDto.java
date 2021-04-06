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
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
@Entity
@Table(name="FOTOS", schema="NATUUR")
@NamedQuery(name="fotosPerGebied", query="select f from FotoDto f where f.gebied.gebiedId=:gebiedId")
@NamedQuery(name="fotosPerTaxon", query="select f from FotoDto f where f.taxon.taxonId=:taxonId")
@NamedQuery(name="fotosPerWaarneming", query="select f from FotoDto f where f.waarnemingId=:waarnemingId")
public class FotoDto extends Dto implements Comparable<FotoDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  PAR_GEBIEDID      = "gebiedId";
  public static final String  PAR_TAXONID       = "taxonId";
  public static final String  PAR_WAARNEMINGID  = "waarnemingId";

  public static final String  QRY_PERGEBIED     = "fotosPerGebied";
  public static final String  QRY_PERTAXON      = "fotosPerTaxon";
  public static final String  QRY_WAARNEMINGID  = "fotosPerWaarneming";

  // Tijdelijk attribute om de link naar de waarneming te maken.
  @Column(name="DATUM")
  private Date      datum;
  @Column(name="FOTO_BESTAND", length=255)
  private String    fotoBestand;
  @Column(name="FOTO_DETAIL", length=20)
  private String    fotoDetail;
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="FOTO_ID", nullable=false)
  private Long      fotoId;
  // Deprecated attribute tot de link naar de waarneming gemaakt is.
  @OneToOne
  @JoinColumn(name="GEBIED_ID")
  private GebiedDto gebied;
  @Column(name="OPMERKING", length=2000)
  private String    opmerking;
  // Deprecated attribute tot de link naar de waarneming gemaakt is.
  @OneToOne
  @JoinColumn(name="TAXON_ID")
  private TaxonDto  taxon;
  @Column(name="TAXON_SEQ", nullable=false)
  private Long      taxonSeq    = Long.valueOf("0");
  // Zet nullable=false nadat de link naar de waarneming gemaakt is.
  @Column(name="WAARNEMING_ID")
  private Long      waarnemingId;

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

    FotoDto fotoDto = (FotoDto) object;

    return new EqualsBuilder().append(fotoId, fotoDto.fotoId).isEquals();
  }

  public Date getDatum() {
    if (null == datum) {
      return null;
    }

    return (Date) datum.clone();
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

  public GebiedDto getGebied() {
    return gebied;
  }

  public String getOpmerking() {
    return opmerking;
  }

  public TaxonDto getTaxon() {
    return taxon;
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

  public void setDatum(Date datum) {
    if (null == datum) {
      this.datum  = null;
    } else {
      this.datum  = (Date) datum.clone();
    }
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

  public void setGebied(GebiedDto gebied) {
    this.gebied       = gebied;
  }

  public void setOpmerking(String opmerking) {
    this.opmerking    = opmerking;
  }

  public void setTaxon(TaxonDto taxon) {
    this.taxon        = taxon;
  }

  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq     = taxonSeq;
  }

  public void setWaarnemingId(Long waarnemingId) {
    this.waarnemingId = waarnemingId;
  }
}
