/*
 * Copyright (c) 2021 Marco de Booij
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
import eu.debooy.doosutils.DoosConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.natuur.domain.FotoOverzichtDto;
import java.util.Date;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class FotoOverzicht {
  private Date    datum;
  private String  fotoBestand;
  private String  fotoDetail;
  private Long    fotoId;
  private String  gebied;
  private Long    klasseId;
  private String  klasseLatijnsenaam;
  private String  klasseNaam;
  private Long    klasseVolgnummer;
  private Long    landId;
  private String  landnaam;
  private String  latijnsenaam;
  private String  naam;
  private Long    taxonId;
  private Long    taxonSeq;
  private Long    volgnummer;

  public FotoOverzicht(FotoOverzichtDto fotoOverzichtDto) {
    this(fotoOverzichtDto, "", "");
  }

  public FotoOverzicht(FotoOverzichtDto fotoOverzichtDto, String taal) {
    this(fotoOverzichtDto, taal, "");
  }

  public FotoOverzicht(FotoOverzichtDto fotoOverzichtDto, String taal,
                       String landnaam) {
    datum               = new Date(fotoOverzichtDto.getDatum().getTime());
    fotoBestand         = fotoOverzichtDto.getFotoBestand();
    fotoDetail          = fotoOverzichtDto.getFotoDetail();
    fotoId              = fotoOverzichtDto.getFotoId();
    gebied              = fotoOverzichtDto.getGebied();
    klasseId            = fotoOverzichtDto.getKlasseId();
    klasseLatijnsenaam  = fotoOverzichtDto.getKlasseLatijnsenaam();
    klasseNaam          = fotoOverzichtDto.getKlasseNaam(taal);
    klasseVolgnummer    = fotoOverzichtDto.getKlasseVolgnummer();
    landId              = fotoOverzichtDto.getLandId();
    this.landnaam       = landnaam;
    latijnsenaam        = fotoOverzichtDto.getLatijnsenaam();
    if (DoosUtils.isNotBlankOrNull(taal)) {
      naam              = fotoOverzichtDto.getNaam(taal);
    }
    taxonId             = fotoOverzichtDto.getTaxonId();
    taxonSeq            = fotoOverzichtDto.getTaxonSeq();
    volgnummer          = fotoOverzichtDto.getVolgnummer();
  }

  public int compareTo(FotoOverzicht fotoOverzicht) {
    return new CompareToBuilder().append(datum, fotoOverzicht.datum)
                                 .append(fotoId, fotoOverzicht.fotoId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof FotoOverzicht)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var fotoOverzicht = (FotoOverzicht) object;

    return new EqualsBuilder().append(fotoId, fotoOverzicht.fotoId)
                              .isEquals();
  }

  @SuppressWarnings("common-java:DuplicatedBlocks")
  public Date getDatum() {
    return new Date(datum.getTime());
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

  public String getGebied() {
    return gebied;
  }

  public Long getKlasseId() {
    return klasseId;
  }

  public String getKlasseLatijnsenaam() {
    return klasseLatijnsenaam;
  }

  public String getKlasseNaam() {
    return klasseNaam;
  }

  public Long getKlasseVolgnummer() {
    return klasseVolgnummer;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLandnaam() {
    return landnaam;
  }

  @SuppressWarnings("common-java:DuplicatedBlocks")
  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return naam;
  }

  public String getSorteerdatum() {
    if (null == datum) {
      return "";
    }

    return Datum.fromDate(datum, DoosConstants.SORTEERDATUM);
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public Long getVolgnummer() {
    return volgnummer;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }

  public void setDatum(Date datum) {
    this.datum = new Date(datum.getTime());
  }

  public void setFotoBestand(String fotoBestand) {
    this.fotoBestand = fotoBestand;
  }

  public void setFotoDetail(String fotoDetail) {
    this.fotoDetail = fotoDetail;
  }

  public void setFotoId(Long fotoId) {
    this.fotoId             = fotoId;
  }

  public void setGebied(String gebied) {
    this.gebied             = gebied;
  }

  public void setKlasseId(Long klasseId) {
    this.klasseId           = klasseId;
  }

  public void setKlasseLatijnsenaam(String klasseLatijnsenaam) {
    this.klasseLatijnsenaam = klasseLatijnsenaam;
  }

  public void setKlasseNaam(String klasseNaam) {
    this.klasseNaam         = klasseNaam;
  }

  public void setKlasseVolgnummer(Long klasseVolgnummer) {
    this.klasseVolgnummer = klasseVolgnummer;
  }

  public void setLandId(Long landId) {
    this.landId             = landId;
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam       = latijnsenaam;
  }

  public void setNaam(String naam) {
    this.naam               = naam;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId            = taxonId;
  }

  public void setTaxonSeq(Long taxonSeq) {
    this.taxonSeq           = taxonSeq;
  }

  public void setVolgnummer(Long volgnummer) {
    this.volgnummer = volgnummer;
  }
}
