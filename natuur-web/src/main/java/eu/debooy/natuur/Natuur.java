/**
 * Copyright 2015 Marco de Booij
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
package eu.debooy.natuur;

import eu.debooy.doos.component.Export;
import eu.debooy.doos.model.ExportData;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.components.bean.DoosBean;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.TechnicalException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Foto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Rangtotaal;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.service.DetailService;
import eu.debooy.natuur.service.FotoService;
import eu.debooy.natuur.service.GebiedService;
import eu.debooy.natuur.service.RangService;
import eu.debooy.natuur.service.TaxonService;
import eu.debooy.natuur.validator.FotoValidator;
import eu.debooy.natuur.validator.GebiedValidator;
import eu.debooy.natuur.validator.RangValidator;
import eu.debooy.natuur.validator.TaxonValidator;
import eu.debooy.sedes.component.business.II18nLandnaam;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuur")
@SessionScoped
public class Natuur extends DoosBean {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(Natuur.class);

  private Foto      foto;
  private Gebied    gebied;
  private Rang      rang;
  private Taxon     taxon;

  private Map<String, String> menu;
  private Long                ouderNiveau;
  private String              path;

  private transient DetailService detailService;
  private transient FotoService   fotoService;
  private transient GebiedService gebiedService;
  private transient RangService   rangService;
  private transient TaxonService  taxonService;

  @EJB
  private transient II18nLandnaam i18nLandnaam;

  public static final String  ADMIN_ROLE            = "natuur-admin";
  public static final String  APPLICATIE_NAAM       = "Natuur";
  public static final String  FOTO_REDIRECT         = "/fotos/foto.xhtml";
  public static final String  FOTOS_REDIRECT        = "/fotos/fotos.xhtml";
  public static final String  GEBIED_REDIRECT       = "/gebieden/gebied.xhtml";
  public static final String  GEBIEDEN_REDIRECT     =
      "/gebieden/gebieden.xhtml";
  public static final String  RANG_REDIRECT         = "/rangen/rang.xhtml";
  public static final String  RANGEN_REDIRECT       = "/rangen/rangen.xhtml";
  public static final String  RANG_TOTALEN_REDIRECT = "/rangen/totalen.xhtml";
  public static final String  TAXON_REDIRECT        = "/taxa/taxon.xhtml";
  public static final String  TAXA_REDIRECT         = "/taxa/taxa.xhtml";
  public static final String  USER_ROLE             = "natuur-user";

  public Natuur() {
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    LOGGER.debug("Nieuwe Natuur Sessie geopend.");
    menu  = new LinkedHashMap<String, String>();
    path  = getExternalContext().getRequestContextPath();
    if (isAdministrator()) {
      menu.put(path + "/admin/parameters.xhtml",  "menu.parameters");
    }
    menu.put(path + RANGEN_REDIRECT,                    "menu.rangen");
    menu.put(path + GEBIEDEN_REDIRECT,                  "menu.gebieden");
    menu.put(path + TAXA_REDIRECT,                      "menu.taxa");
    menu.put(path + "/waarnemingen/waarnemingen.xhtml", "menu.waarnemingen");
    menu.put(path + FOTOS_REDIRECT,                     "menu.fotos");
  }

  // Algemeen
  public Set<Entry<String, String>> getMenu() {
    return menu.entrySet();
  }

  public Long getOuderNiveau() {
    return ouderNiveau;
  }

  // TODO Naar DoosBean
  public String getPath() {
    return path;
  }

  // Details
  /**
   * Geef de DetailService. Als die nog niet gekend is haal het dan op.
   * 
   * @return DetailService
   */
  private DetailService getDetailService() {
    if (null == detailService) {
      detailService = (DetailService)
          new JNDI.JNDINaam().metBean(DetailService.class).locate();
    }

    return detailService;
  }

  /**
   * Geef alle gebieden als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectWaarnemingen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<DetailDto>    rijen =
        new TreeSet<DetailDto>(new DetailDto.NaamComparator());
    rijen.addAll(getDetailService().getSoortenMetKlasse());
    for (DetailDto rij : rijen) {
      items.add(new SelectItem(new Taxon(rij),
                               rij.getNaam() + " (" + rij.getLatijnsenaam()
                                   + ")"));
    }

    return items;
  }

  /**
   * Geef de lijst met waarnemingen.
   * 
   * @return Collection<DetailDto> met DetailDto objecten.
   */
  public Collection<DetailDto> getSoorten() {
    return getDetailService().getSoortenMetKlasse();
  }

  /**
   * Rapport met Waarnemingen.
   */
  public void waarnemingen() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "waarnemingen");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "parentNaam", "parentLatijnsenaam",
                                          "naam", "latijnsenaam" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.waarnemingen"));

    List<DetailDto> waarnemingen  =
        new LinkedList<DetailDto>(getDetailService().getSoortenMetKlasse());
    Collections.sort(waarnemingen, new DetailDto.LijstComparator());
    for (DetailDto waarneming : waarnemingen) {
      exportData.addData(new String[] {waarneming.getParentNaam(),
                                       waarneming.getParentLatijnsenaam(),
                                       waarneming.getNaam(),
                                       waarneming.getLatijnsenaam()});

    }

    HttpServletResponse response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
    } catch (IllegalArgumentException e) {
      generateExceptionMessage(e);
      return;
    } catch (TechnicalException e) {
      generateExceptionMessage(e);
      return;
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  // Fotos
  /**
   * Rapport met Waarnemingen.
   */
  public void fotolijst() {
    ExportData  exportData  = new ExportData();

    exportData.addMetadata("application", getApplicatieNaam());
    exportData.addMetadata("auteur",      getGebruikerNaam());
    exportData.addMetadata("lijstnaam",   "fotolijst");
    exportData.setKleuren(getLijstKleuren());

    exportData.setKolommen(new String[] { "sequence", "land",
                                          "naam", "gebied" });
    exportData.setType(getType());
    exportData.addVeld("ReportTitel",
                       getTekst("natuur.titel.fotolijst"));

    Set<Foto> rijen = new TreeSet<Foto>(new Foto.LijstComparator());
    rijen.addAll(getFotoService().lijst());
    String      taal  = getGebruikersTaal();
    for (Foto rij : rijen) {
      exportData.addData(new String[] {rij.getTaxonSeq().toString(),
                                       i18nLandnaam
                                           .getI18nLandnaam(rij.getGebied()
                                                               .getLandId(),
                                                            taal),
                                       rij.getTaxon().getNaam(),
                                       rij.getGebied().getNaam()});

    }

    HttpServletResponse response  =
        (HttpServletResponse) FacesContext.getCurrentInstance()
                                          .getExternalContext().getResponse();
    try {
      Export.export(response, exportData);
    } catch (IllegalArgumentException e) {
      generateExceptionMessage(e);
      return;
    } catch (TechnicalException e) {
      generateExceptionMessage(e);
      return;
    }

    FacesContext.getCurrentInstance().responseComplete();
  }

  /**
   * Geef de geselecteerde foto.
   * 
   * @return Foto
   */
  public Foto getFoto() {
    return foto;
  }

  /**
   * Geef de lijst met fotos.
   * 
   * @return Collection<Foto> met Gebied objecten.
   */
  public Collection<Foto> getFotos() {
    return getFotoService().lijst();
  }

  /**
   * Geef de FotoService. Als die nog niet gekend is haal het dan op.
   * 
   * @return FotoService
   */
  private FotoService getFotoService() {
    if (null == fotoService) {
      fotoService = (FotoService)
          new JNDI.JNDINaam().metBean(FotoService.class).locate();
    }

    return fotoService;
  }

  /**
   * Prepareer een nieuwe Foto.
   */
  public void nieuweFoto() {
    foto  = new Foto();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.foto.create");
    redirect(FOTO_REDIRECT);
  }

  /**
   * Persist de Foto
   * 
   * @param Foto
   */
  public void saveFoto() {
    List<Message> messages  = FotoValidator.valideer(foto);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getFotoService().save(foto);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, foto.getTaxon().getNaam() + " "
                                               + foto.getTaxonSeq());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, foto.getTaxon().getNaam() + " "
                                              + foto.getTaxonSeq());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(FOTOS_REDIRECT);
  }

  /**
   * Verwijder de Foto
   * 
   * @param Foto
   */
  public void verwijderFoto(Long fotoId, String naam) {
    try {
      getFotoService().delete(fotoId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo("info.delete", "'" + naam + "'");
  }

  /**
   * Zet de Foto die gewijzigd gaat worden klaar.
   * 
   * @param Long gebiedId
   */
  public void wijzigFoto(Long fotoId) {
    foto  = new Foto(getFotoService().foto(fotoId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.foto.update");
    redirect(FOTO_REDIRECT);
  }

  // Gebieden
  /**
   * Geef het geselecteerde gebied.
   * 
   * @return Gebied
   */
  public Gebied getGebied() {
    return gebied;
  }

  /**
   * Geef de lijst met gebieden.
   * 
   * @return Collection<Gebied> met Gebied objecten.
   */
  public Collection<Gebied> getGebieden() {
    return getGebiedService().lijst();
  }

  /**
   * Geef de GebiedService. Als die nog niet gekend is haal het dan op.
   * 
   * @return GebiedService
   */
  private GebiedService getGebiedService() {
    if (null == gebiedService) {
      gebiedService = (GebiedService)
          new JNDI.JNDINaam().metBean(GebiedService.class).locate();
    }

    return gebiedService;
  }

  /**
   * Geef alle gebieden als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectGebieden() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<Gebied>       rijen = new TreeSet<Gebied>(new Gebied.NaamComparator());
    rijen.addAll(getGebiedService().lijst());
    for (Gebied rij : rijen) {
      items.add(new SelectItem(rij, rij.getNaam()));
    }

    return items;
  }

  /**
   * Prepareer een nieuw Gebied.
   */
  public void nieuwGebied() {
    gebied  = new Gebied();
    gebied.setLandId(Long.parseLong(getParameter("natuur.default.landid")));
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.gebied.create");
    redirect(GEBIED_REDIRECT);
  }

  /**
   * Persist het Gebied
   * 
   * @param Gebied
   */
  public void saveGebied() {
    List<Message> messages  = GebiedValidator.valideer(gebied);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getGebiedService().save(gebied);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, gebied.getNaam());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, gebied.getNaam());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(GEBIEDEN_REDIRECT);
  }

  /**
   * Verwijder het Gebied
   * 
   * @param Long gebiedId
   * @param String naam
   */
  public void verwijderGebied(Long gebiedId, String naam) {
    try {
      getGebiedService().delete(gebiedId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo("info.delete", naam);
  }

  /**
   * Zet de Gebied die gewijzigd gaat worden klaar.
   * 
   * @param Long gebiedId
   */
  public void wijzigGebied(Long gebiedId) {
    gebied  = new Gebied(getGebiedService().gebied(gebiedId));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.gebied.update");
    redirect(GEBIED_REDIRECT);
  }

  // Rangen
  /**
   * Zet de Rang die gevraagd is klaar.
   * 
   * @param String rang
   */
  public void bekijkRang(String rang) {
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(MessageFormat.format(getTekst("natuur.titel.rang.totalen"),
                                     getTekst("biologie.rang."
                                         + this.rang.getRang())));
    redirect(RANG_TOTALEN_REDIRECT);
  }

  /**
   * Geef de geselecteerde rang.
   * 
   * @return Rang
   */
  public Rang getRang() {
    return rang;
  }

  /**
   * Geef de lijst met rangen.
   * 
   * @return Collection<Rang> met Gebied objecten.
   */
  public Collection<Rang> getRangen() {
    return getRangService().lijst();
  }

  /**
   * Geef de RangService. Als die nog niet gekend is haal het dan op.
   * 
   * @return RangService
   */
  private RangService getRangService() {
    if (null == rangService) {
      rangService = (RangService)
          new JNDI.JNDINaam().metBean(RangService.class).locate();
    }

    return rangService;
  }

  /**
   * Geef de lijst met totalen voor de rang.
   * 
   * @return Collection<Rangtotaal>
   */
  public Collection<Rangtotaal> getRangtotalen() {
    return getDetailService().getTotalenVoorRang(rang.getRang());
  }

  /**
   * Geef alle rangen als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectRangen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<Rang>         rijen = new TreeSet<Rang>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().lijst());
    for (Rang rij : rijen) {
      items.add(new SelectItem(rij.getRang(),
                               getTekst("biologie.rang." + rij.getRang())));
    }

    return items;
  }

  /**
   * Prepareer een nieuw gebied.
   */
  public void nieuweRang() {
    rang  = new Rang();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.rang.create");
    redirect(RANG_REDIRECT);
  }

  /**
   * Persist de Rang
   * 
   * @param Rang
   */
  public void saveRang() {
    List<Message> messages  = RangValidator.valideer(rang);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getRangService().save(rang);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rang.getRang());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang.getRang());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(RANGEN_REDIRECT);
  }

  /**
   * Geef rangen 'groter' dan de rang van de ouder als SelectItems.
   * 
   * @param Long niveau
   * @return List<SelectItem>
   */
  public List<SelectItem> selectRangen(Long niveau) {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<Rang>         rijen = new TreeSet<Rang>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().lijst(niveau));
    LOGGER.debug("#rangen > niveau " + niveau + ": " + rijen.size());
    for (Rang rij : rijen) {
      items.add(new SelectItem(rij.getRang(),
                               getTekst("biologie.rang." + rij.getRang())));
    }

    return items;
  }

  /**
   * Verwijder de rang
   * 
   * @param String
   */
  public void verwijderRang(String rang) {
    try {
      getRangService().delete(rang);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo("info.delete", getTekst("biologie.rang." + rang));
  }

  /**
   * Zet de Rang die gewijzigd gaat worden klaar.
   * 
   * @param String rang
   */
  public void wijzigRang(String rang) {
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.rang.update");
    redirect(RANG_REDIRECT);
  }

  // Taxa
  /**
   * Zet het Taxon dat gevraagd is klaar.
   * 
   * @param Long taxonId
   */
  public void bekijkTaxon(Long taxonId) {
    taxon       = new Taxon(getTaxonService().taxon(taxonId));
    String  ouderRang = getTaxonService().taxon(taxon.getParentId()).getRang();
    ouderNiveau = getRangService().rang(ouderRang).getNiveau();
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  /**
   * Geef de lijst met kinderen van de taxon.
   * 
   * @return Collection<Taxon> met Taxon objecten.
   */
  public Collection<Taxon> getKinderen(Long parentId) {
    return getTaxonService().getKinderen(parentId);
  }

  /**
   * Geef de lijst met taxa.
   * 
   * @return Collection<Taxon> met Taxon objecten.
   */
  public Collection<Taxon> getTaxa() {
    return getTaxonService().lijst();
  }

  /**
   * Geef het geselecteerde taxon.
   * 
   * @return Taxon
   */
  public Taxon getTaxon() {
    return taxon;
  }

  /**
   * Geef de gevraagde Taxon.
   * 
   * @param Long taxonId
   * @return Taxon
   */
  public Taxon getTaxon(Long taxonId) {
    return new Taxon(getTaxonService().taxon(taxonId));
  }

  /**
   * Geef de TaxonService. Als die nog niet gekend is haal het dan op.
   * 
   * @return TaxonService
   */
  private TaxonService getTaxonService() {
    if (null == taxonService) {
      taxonService  = (TaxonService)
          new JNDI.JNDINaam().metBean(TaxonService.class).locate();
    }

    return taxonService;
  }

  /**
   * Prepareer een nieuw taxon.
   */
  public void nieuweTaxon() {
    taxon       = new Taxon();
    ouderNiveau = new Long(0);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Prepareer een nieuw taxon met parentId.
   */
  public void nieuweTaxon(Long parentId) {
    taxon       = new Taxon();
    ouderNiveau = new Long(0);
    taxon.setParentId(parentId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Persist het Taxon
   * 
   * @param Taxon
   */
  public void saveTaxon() {
    List<Message> messages  = TaxonValidator.valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getTaxonService().save(taxon);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxon.getLatijnsenaam());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxon.getNaam());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    if (getAktie().isNieuw()) {
      addInfo("info.create", taxon.getNaam());
      setAktie(PersistenceConstants.RETRIEVE);
    } else {
      addInfo("info.update", taxon.getNaam());
    }
  }

  /**
   * Geef alle taxa als SelectItems.
   * 
   * @return List<SelectItem>
   */
  public List<SelectItem> selectOuders(String rang) {
    Long              niveau;
    if (DoosUtils.isBlankOrNull(rang)) {
      niveau  = Long.valueOf("1000");
    } else {
      niveau  = getRangService().rang(rang).getNiveau();
    }
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<TaxonDto>     rijen =
        new TreeSet<TaxonDto>(new TaxonDto.NaamComparator());
    rijen.addAll(getTaxonService().getOuders(niveau));
    for (TaxonDto rij : rijen) {
      items.add(new SelectItem(rij.getTaxonId(),
                               rij.getNaam() + " (" + rij.getLatijnsenaam()
                                   + ")"));
    }

    return items;
  }

  /**
   * Verwijder het Taxon
   * 
   * @param Taxon
   */
  public void verwijderTaxon(Long taxonId, String naam) {
    try {
      getTaxonService().delete(taxonId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, naam);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo("info.delete", naam);
  }

  /**
   * Zet de Taxon die gewijzigd gaat worden klaar.
   * 
   * @param Long
   */
  public void wijzigTaxon(Long taxonId) {
    taxon       = new Taxon(getTaxonService().taxon(taxonId));
    String  ouderRang = getTaxonService().taxon(taxon.getParentId()).getRang();
    ouderNiveau = getRangService().rang(ouderRang).getNiveau();
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.taxon.update");
    redirect(TAXON_REDIRECT);
  }
}
