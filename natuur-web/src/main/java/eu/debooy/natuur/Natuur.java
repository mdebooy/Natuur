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
package eu.debooy.natuur;

import eu.debooy.doos.component.bean.DoosBean;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.service.DetailService;
import eu.debooy.natuur.service.FotoService;
import eu.debooy.natuur.service.GebiedService;
import eu.debooy.natuur.service.GeenFotoService;
import eu.debooy.natuur.service.OverzichtService;
import eu.debooy.natuur.service.RangService;
import eu.debooy.natuur.service.RegiolijstService;
import eu.debooy.natuur.service.RegiolijstTaxonService;
import eu.debooy.natuur.service.TaxonService;
import eu.debooy.natuur.service.TaxonnaamService;
import eu.debooy.natuur.service.WaarnemingService;
import eu.debooy.sedes.component.business.ISedesRemote;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
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

  private transient DetailService           detailService;
  private transient FotoService             fotoService;
  private transient GebiedService           gebiedService;
  private transient GeenFotoService         geenFotoService;
  private transient OverzichtService        overzichtService;
  private transient RangService             rangService;
  private transient RegiolijstService       regiolijstService;
  private transient RegiolijstTaxonService  regiolijstTaxonService;
  private transient TaxonnaamService        taxonnaamService;
  private transient TaxonService            taxonService;
  private transient WaarnemingService       waarnemingService;

  @EJB
  private transient ISedesRemote  sedesRemote;

  protected static final  String  AANTALPERREGIO_REDIRECT   =
      "/statistieken/aantalperregiolijst.xhtml";
  protected static final  String  ADMIN_ROLE                = "natuur-admin";
  protected static final  String  APPLICATIE_NAAM           = "Natuur";
  protected static final  String  DD_STATS                  = "stats";
  public    static final  String  DEF_GEBIEDID              =
      "natuur.default.gebiedid";
  public    static final  String  DEF_LANDID                =
      "natuur.default.landid";
  public    static final  String  DEF_RANG                  =
      "natuur.default.rang";
  protected static final  String  FOTO_REDIRECT             =
      "/waarnemingen/foto.xhtml";
  protected static final  String  FOTOS_REDIRECT            =
      "/fotos/fotos.xhtml";
  protected static final  String  GEBIED_REDIRECT           =
      "/gebieden/gebied.xhtml";
  protected static final  String  GEBIEDEN_REDIRECT         =
      "/gebieden/gebieden.xhtml";
  protected static final  String  GEENFOTOS_REDIRECT        =
      "/rangen/geenfotos.xhtml";
  protected static final  String  LBL_FOTO                  = "label.foto";
  protected static final  String  LBL_GEBIED                = "label.gebied";
  protected static final  String  LBL_PARAMETERS            =
      "label.parameters";
  protected static final  String  LBL_RANG                  = "label.rang";
  protected static final  String  LBL_REGIOLIJST            =
      "label.regiolijst";
  protected static final  String  LBL_TAAL                  = "label.taal";
  protected static final  String  LBL_TAXON                 = "label.taxon";
  protected static final  String  LBL_TAXONNAAM             = "label.taxonnaam";
  protected static final  String  LBL_WAARNEMING            =
      "label.waarneming";
  protected static final  String  NAMENPERTAAL_REDIRECT     =
      "/statistieken/namenpertaal.xhtml";
  protected static final  String  RANG_REDIRECT             =
      "/rangen/rang.xhtml";
  protected static final  String  RANGEN_REDIRECT           =
      "/rangen/rangen.xhtml";
  protected static final  String  RANGNAAM_REDIRECT         =
      "/rangen/rangnaam.xhtml";
  protected static final  String  RANG_TOTALEN_REDIRECT     =
      "/rangen/totalen.xhtml";
  protected static final  String  REGIOLIJST_REDIRECT       =
      "/regiolijsten/regiolijst.xhtml";
  protected static final  String  REGIOLIJSTEN_REDIRECT     =
      "/regiolijsten/regiolijsten.xhtml";
  protected static final  String  REGIOLIJSTPARAMS_REDIRECT =
      "/regiolijsten/regiolijstparameters.xhtml";
  protected static final  String  REGIOLIJSTTAXON_REDIRECT  =
      "/regiolijsten/regiolijsttaxon.xhtml";
  protected static final  String  REGIOLIJSTUPLOAD_REDIRECT =
      "/regiolijsten/regiolijstupload.xhtml";
  protected static final  String  STATUSSEN                 =
      "natuur.taxon.status";
  protected static final  String  TAXA_REDIRECT             =
      "/taxa/taxa.xhtml";
  protected static final  String  TAXALIJSTPARAMS_REDIRECT  =
      "/taxa/taxalijstparameters.xhtml";
  protected static final  String  TAXON_REDIRECT            =
      "/taxa/taxon.xhtml";
  protected static final  String  TAXONNAAM_REDIRECT        =
      "/taxa/taxonnaam.xhtml";
  protected static final  String  TAXONNAMENUPLOAD_REDIRECT =
      "/taxa/taxonnaamupload.xhtml";
  protected static final  String  USER_ROLE                 = "natuur-user";
  protected static final  String  UITGESTORVEN_REDIRECT     =
      "/statistieken/uitgestorven.xhtml";
  protected static final  String  VIEW_ROLE                 = "natuur-view";
  protected static final  String  WAARNEMING_REDIRECT       =
      "/waarnemingen/waarneming.xhtml";
  protected static final  String  WAARNEMINGEN_REDIRECT     =
      "/waarnemingen/waarnemingen.xhtml";
  protected static final  String  WNMFOTO_REDIRECT          =
      "/waarnemingen/foto.xhtml";
  protected static final  String  WNMNPERLAND_REDIRECT      =
      "/statistieken/waarnemingenperland.xhtml";
  protected static final  String  ZOEKEN_REDIRECT           =
      "/zoeken/opnaam.xhtml";

  public Natuur() {
    LOGGER.debug("Nieuwe Natuur Sessie geopend.");
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    setViewRole(getExternalContext().isUserInRole(VIEW_ROLE));
    setPath(getExternalContext().getRequestContextPath());
    if (isAdministrator()) {
      addMenuitem("Dropdown." + DD_ADMIN, "menu.administratie");
      addDropdownmenuitem(DD_ADMIN, APP_LOGS_REDIRECT,
                                          "menu.applicatielogs");
      addDropdownmenuitem(DD_ADMIN, APP_PARAMS_REDIRECT,
                                          "menu.applicatieparameters");
    }
    if (isGerechtigd()) {
      addMenuitem(RANGEN_REDIRECT,        "menu.rangen");
      addMenuitem(GEBIEDEN_REDIRECT,      "menu.gebieden");
      addMenuitem(REGIOLIJSTEN_REDIRECT,  "menu.regiolijsten");
      addMenuitem(TAXA_REDIRECT,          "menu.taxa");
      addMenuitem(WAARNEMINGEN_REDIRECT,  "menu.waarnemingen");
      addMenuitem(FOTOS_REDIRECT,         "menu.fotos");
      addMenuitem(ZOEKEN_REDIRECT,        "menu.zoeken");
      addMenuitem("Dropdown." + DD_STATS, "menu.statistieken");
      addDropdownmenuitem(DD_STATS, NAMENPERTAAL_REDIRECT,
                                          "menu.namen.per.taal");
      addDropdownmenuitem(DD_STATS, WNMNPERLAND_REDIRECT,
                                          "menu.waarnemingen.per.land");
      addDropdownmenuitem(DD_STATS, AANTALPERREGIO_REDIRECT,
                                          "menu.aantal.per.regiolijst");
      addDropdownmenuitem(DD_STATS, UITGESTORVEN_REDIRECT,
                                          "menu.uitgestorven");
    }
  }

  protected DetailService getDetailService() {
    if (null == detailService) {
      detailService = (DetailService)
          new JNDI.JNDINaam().metBean(DetailService.class).locate();
    }

    return detailService;
  }

  protected FotoService getFotoService() {
    if (null == fotoService) {
      fotoService = (FotoService)
          new JNDI.JNDINaam().metBean(FotoService.class).locate();
    }

    return fotoService;
  }

  protected GebiedService getGebiedService() {
    if (null == gebiedService) {
      gebiedService = (GebiedService)
          new JNDI.JNDINaam().metBean(GebiedService.class).locate();
    }

    return gebiedService;
  }

  protected GeenFotoService getGeenFotoService() {
    if (null == geenFotoService) {
      geenFotoService = (GeenFotoService)
          new JNDI.JNDINaam().metBean(GeenFotoService.class).locate();
    }

    return geenFotoService;
  }

  protected ISedesRemote getSedesRemote() {
    return sedesRemote;
  }

  protected OverzichtService getOverzichtService() {
    if (null == overzichtService) {
      overzichtService  = (OverzichtService)
          new JNDI.JNDINaam().metBean(OverzichtService.class).locate();
    }

    return overzichtService;
  }

  protected RangService getRangService() {
    if (null == rangService) {
      rangService = (RangService)
          new JNDI.JNDINaam().metBean(RangService.class).locate();
    }

    return rangService;
  }

  protected RegiolijstService getRegiolijstService() {
    if (null == regiolijstService) {
      regiolijstService = (RegiolijstService)
          new JNDI.JNDINaam().metBean(RegiolijstService.class).locate();
    }

    return regiolijstService;
  }

  protected RegiolijstTaxonService getRegiolijstTaxonService() {
    if (null == regiolijstTaxonService) {
      regiolijstTaxonService  = (RegiolijstTaxonService)
          new JNDI.JNDINaam().metBean(RegiolijstTaxonService.class).locate();
    }

    return regiolijstTaxonService;
  }

  protected TaxonnaamService getTaxonnaamService() {
    if (null == taxonnaamService) {
      taxonnaamService  = (TaxonnaamService)
          new JNDI.JNDINaam().metBean(TaxonnaamService.class).locate();
    }

    return taxonnaamService;
  }

  protected TaxonService getTaxonService() {
    if (null == taxonService) {
      taxonService  = (TaxonService)
          new JNDI.JNDINaam().metBean(TaxonService.class).locate();
    }

    return taxonService;
  }

  protected WaarnemingService getWaarnemingService() {
    if (null == waarnemingService) {
      waarnemingService = (WaarnemingService)
          new JNDI.JNDINaam().metBean(WaarnemingService.class).locate();
    }

    return waarnemingService;
  }
}
