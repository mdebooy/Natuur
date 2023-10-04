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
package eu.debooy.natuur.domain;

import eu.debooy.doosutils.domain.Dto;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.openjpa.persistence.ReadOnly;


/**
 * Deze Entity is enkel read-only.
 *
 * @author Marco de Booij
 */
@Entity
@Table(name="FOTO_OVERZICHT", schema="NATUUR")
@NamedQuery(name="fotooverzichtPerGebied", query="select f from FotoOverzichtDto f where f.parentId=f.taxon.taxonId and f.gebiedId=:gebiedId")
@NamedQuery(name="fotooverzichtPerRang", query="select f from FotoOverzichtDto f where f.parentRang=:rang")
@NamedQuery(name="fotooverzichtPerTaxon", query="select f from FotoOverzichtDto f where f.parentId=:taxonId")
@NamedQuery(name="fotooverzichtTaxonSeq", query="select f from FotoOverzichtDto f where f.taxon.taxonId=:taxonId and f.taxonSeq=:taxonSeq")
public class FotoOverzichtDto
    extends Dto implements Comparable<FotoOverzichtDto> {
  private static final  long  serialVersionUID  = 1L;

  public static final String  COL_DATUM               = "datum";
  public static final String  COL_FOTOBESTAND         = "fotoBestand";
  public static final String  COL_FOTODETAIL          = "fotoDetail";
  public static final String  COL_FOTOID              = "fotoId";
  public static final String  COL_GEBIED              = "gebied";
  public static final String  COL_GEBIEDID            = "gebiedId";
  public static final String  COL_LANDID              = "landId";
  public static final String  COL_LATIJNSENAAM        = "latijnsenaam";
  public static final String  COL_OPMERKING           = "opmerking";
  public static final String  COL_PARENTID            = "parentId";
  public static final String  COL_PARENTLATIJNSENAAM  = "parentLatijnsenaam";
  public static final String  COL_PARENTRANG          = "parentRang";
  public static final String  COL_PARENTVOLGNUMMER    = "parentVolgnummer";
  public static final String  COL_RANG                = "rang";
  public static final String  COL_TAXONID             = "taxonId";
  public static final String  COL_TAXONSEQ            = "taxonSeq";
  public static final String  COL_VOLGNUMMER          = "volgnummer";

  public static final String  PAR_GEBIEDID  = "gebiedId";
  public static final String  PAR_RANG      = "rang";
  public static final String  PAR_TAXONID   = "taxonId";
  public static final String  PAR_TAXONSEQ  = "taxonSeq";

  public static final String  QRY_PERGEBIED = "fotooverzichtPerGebied";
  public static final String  QRY_PERRANG   = "fotooverzichtPerRang";
  public static final String  QRY_PERTAXON  = "fotooverzichtPerTaxon";
  public static final String  QRY_TAXONSEQ  = "fotooverzichtTaxonSeq";

  @ReadOnly
  @OrderColumn
  @Column(name="DATUM", nullable=false)
  private Date      datum;
  @ReadOnly
  @Column(name="FOTO_BESTAND")
  private String    fotoBestand;
  @ReadOnly
  @Column(name="FOTO_DETAIL")
  private String    fotoDetail;
  @Id
  @ReadOnly
  @Column(name="FOTO_ID")
  private Long      fotoId;
  @ReadOnly
  @Column(name="GEBIED")
  private String    gebied;
  @ReadOnly
  @Column(name="GEBIED_ID")
  private Long      gebiedId;
  @ReadOnly
  @Column(name="LAND_ID")
  private Long      landId;
  @ReadOnly
  @Column(name="LATIJNSENAAM")
  private String    latijnsenaam;
  @ReadOnly
  @Column(name="OPMERKING")
  private String    opmerking;
  @ReadOnly
  @Column(name="PARENT_ID")
  private Long      parentId;
  @ReadOnly
  @Column(name="PARENT_RANG")
  private String    parentRang;
  @ReadOnly
  @Column(name="PARENT_LATIJNSENAAM")
  private String    parentLatijnsenaam;
  @ReadOnly
  @Column(name="PARENT_VOLGNUMMER")
  private Long      parentVolgnummer;
  @ReadOnly
  @Column(name="RANG")
  private String    rang;
  @ReadOnly
  @OneToOne
  @JoinColumn(name="TAXON_ID", nullable=false)
  private TaxonDto  taxon;
  @ReadOnly
  @Column(name="TAXON_SEQ")
  private Long      taxonSeq;
  @ReadOnly
  @Column(name="VOLGNUMMER")
  private Long      volgnummer;

  @ReadOnly
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=TaxonnaamDto.class, orphanRemoval=true)
  @JoinColumn(name="TAXON_ID", referencedColumnName="PARENT_ID", nullable=false, updatable=false, insertable=true)
  @MapKey(name="taal")
  private Map<String, TaxonnaamDto> parentnamen = new HashMap<>();

  public static class LijstComparator
      implements Comparator<FotoOverzichtDto>, Serializable {
    private static final  long  serialVersionUID  = 1L;

    private String  taal  = "";

    public void setTaal(String taal) {
      this.taal = taal;
    }

    @Override
    public int compare(FotoOverzichtDto fotoOverzichtDto1,
                       FotoOverzichtDto fotoOverzichtDto2) {
      return
          new CompareToBuilder()
                  .append(fotoOverzichtDto1.getParentVolgnummer(),
                          fotoOverzichtDto2.getParentVolgnummer())
                  .append(fotoOverzichtDto1.getParentNaam(taal),
                          fotoOverzichtDto2.getParentNaam(taal))
                  .append(fotoOverzichtDto1.getTaxon().getVolgnummer(),
                          fotoOverzichtDto2.getTaxon().getVolgnummer())
                  .append(fotoOverzichtDto1.getNaam(taal),
                          fotoOverzichtDto2.getNaam(taal))
                  .append(fotoOverzichtDto1.taxonSeq,
                          fotoOverzichtDto2.taxonSeq)
                  .toComparison();
    }
  }

  @Override
  public int compareTo(FotoOverzichtDto fotoOverzichtDto) {
    return new CompareToBuilder().append(datum, fotoOverzichtDto.datum)
                                 .append(fotoId, fotoOverzichtDto.fotoId)
                                 .toComparison();
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof FotoOverzichtDto)) {
      return false;
    }
    if (object == this) {
      return true;
    }

    var fotoOverzichtDto  = (FotoOverzichtDto) object;

    return new EqualsBuilder().append(fotoId, fotoOverzichtDto.fotoId)
                              .isEquals();
  }

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

  public Long getGebiedId() {
    return gebiedId;
  }

  public Long getLandId() {
    return landId;
  }

  public String getLatijnsenaam() {
    return latijnsenaam;
  }

  @Transient
  public String getNaam(String taal) {
    return taxon.getNaam(taal);
  }

  public String getOpmerking() {
    return opmerking;
  }

  public Long getParentId() {
    return parentId;
  }

  public String getParentLatijnsenaam() {
    return parentLatijnsenaam;
  }

  @Transient
  public String getParentNaam(String taal) {
    if (parentnamen.containsKey(taal)) {
      return parentnamen.get(taal).getNaam();
    } else {
      return parentLatijnsenaam;
    }
  }

  public String getParentRang() {
    return parentRang;
  }

  public Long getParentVolgnummer() {
    return parentVolgnummer;
  }

  public Map<String, TaxonnaamDto> getParentnamen() {
    return parentnamen;
  }

  public String getRang() {
    return rang;
  }

  public TaxonDto getTaxon() {
    return taxon;
  }

  public Long getTaxonId() {
    return taxon.getTaxonId();
  }

  public Collection<TaxonnaamDto> getTaxonnamen() {
    return taxon.getTaxonnamen();
  }

  public Long getTaxonSeq() {
    return taxonSeq;
  }

  public Long getVolgnummer() {
    return taxon.getVolgnummer();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(fotoId).toHashCode();
  }
}
