/**
 * Copyright (c) 2016 Marco de Booij
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

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.form.Formulier;
import eu.debooy.natuur.domain.OverzichtDto;
import java.io.Serializable;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


/**
 * @author Marco de Booij
 */
public class Rangtotaal
    extends Formulier implements Comparable<Rangtotaal>, Serializable {
  private static final  long  serialVersionUID  = 1L;

  private String  latijnsenaam;
  private String  naam;
  private Integer opFoto;
  private String  rang;
  private Long    taxonId;
  private Integer totaal;
  private Long    volgnummer;
  private Integer waargenomen;

  public Rangtotaal() {}

  public Rangtotaal(OverzichtDto overzicht) {
    this(overzicht, null);
  }

  public Rangtotaal(OverzichtDto overzicht, String taal) {
    latijnsenaam  = overzicht.getParentLatijnsenaam();
    if (DoosUtils.isNotBlankOrNull(taal)) {
      naam        = overzicht.getNaam(taal);
    } else {
      naam        = overzicht.getParentLatijnsenaam();
    }
    opFoto        = overzicht.getOpFoto();
    rang          = overzicht.getParentRang();
    taxonId       = overzicht.getParentId();
    totaal        = overzicht.getTotaal();
    volgnummer    = overzicht.getParentVolgnummer();
    waargenomen   = overzicht.getWaargenomen();
  }

  public Rangtotaal(Rangtotaal rangtotaal) {
    latijnsenaam  = rangtotaal.getLatijnsenaam();
    naam          = rangtotaal.getNaam();
    opFoto        = rangtotaal.getOpFoto();
    rang          = rangtotaal.getRang();
    taxonId       = rangtotaal.getTaxonId();
    totaal        = rangtotaal.getTotaal();
    volgnummer    = rangtotaal.getVolgnummer();
    waargenomen   = rangtotaal.getWaargenomen();
  }

  public void addOpFoto(Integer aantal) {
    opFoto      += aantal;
  }

  public void addTotaal(Integer aantal) {
    totaal      += aantal;
  }

  public void addWaargenomen(Integer aantal) {
    waargenomen += aantal;
  }

  @Override
  public int compareTo(Rangtotaal rangtotaal) {
    return new CompareToBuilder().append(taxonId, rangtotaal.taxonId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Rangtotaal)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var rangtotaal  = (Rangtotaal) object;
    return new EqualsBuilder().append(taxonId, rangtotaal.taxonId).isEquals();
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  public String getNaam() {
    return naam;
  }

  public Integer getOpFoto() {
    return opFoto;
  }

  public String getRang() {
    return rang;
  }

  public int getPctOpFoto() {
    if (null == opFoto
        || null == waargenomen
        || waargenomen == 0) {
      return 0;
    }

    return Math.round(((float)opFoto/waargenomen)*100);
  }

  public Long getTaxonId() {
    return taxonId;
  }

  public Integer getTotaal() {
    return totaal;
  }

  public Long getVolgnummer() {
    return volgnummer;
  }

  public Integer getWaargenomen() {
    return waargenomen;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(taxonId).toHashCode();
  }

  public void setLatijnsenaam(String latijnsenaam) {
    this.latijnsenaam = latijnsenaam;
  }

  public void setNaam(String naam) {
    this.naam         = naam;
  }

  public void setOpFoto(Integer opFoto) {
    this.opFoto       = opFoto;
  }

  public void setRang(String rang) {
    this.rang         = rang;
  }

  public void setTaxonId(Long taxonId) {
    this.taxonId      = taxonId;
  }

  public void setTotaal(Integer totaal) {
    this.totaal       = totaal;
  }

  public void setVolgnummer(Long volgnummer) {
    this.volgnummer   = volgnummer;
  }

  public void setWaargenomen(Integer waargenomen) {
    this.waargenomen  = waargenomen;
  }
}
