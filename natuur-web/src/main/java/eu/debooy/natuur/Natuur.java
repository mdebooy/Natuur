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
import eu.debooy.natuur.service.OverzichtService;
import eu.debooy.natuur.service.RangService;
import eu.debooy.natuur.service.TaxonService;
import eu.debooy.natuur.service.TaxonnaamService;
import eu.debooy.natuur.service.WaarnemingService;
import eu.debooy.sedes.component.business.II18nLandnaam;
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

  private transient DetailService     detailService;
  private transient FotoService       fotoService;
  private transient GebiedService     gebiedService;
  private transient OverzichtService  overzichtService;
  private transient RangService       rangService;
  private transient TaxonnaamService  taxonnaamService;
  private transient TaxonService      taxonService;
  private transient WaarnemingService waarnemingService;

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
  public static final String  RANGNAAM_REDIRECT     = "/rangen/rangnaam.xhtml";
  public static final String  RANG_TOTALEN_REDIRECT = "/rangen/totalen.xhtml";
  public static final String  TAXON_REDIRECT        = "/taxa/taxon.xhtml";
  public static final String  TAXONNAAM_REDIRECT    = "/taxa/taxonnaam.xhtml";
  public static final String  TAXA_REDIRECT         = "/taxa/taxa.xhtml";
  public static final String  USER_ROLE             = "natuur-user";
  public static final String  WAARNEMING_REDIRECT   =
      "/waarnemingen/waarneming.xhtml";
  public static final String  WAARNEMINGEN_REDIRECT =
      "/waarnemingen/waarnemingen.xhtml";
  public static final String  WNMFOTO_REDIRECT      =
      "/waarnemingen/foto.xhtml";

  public Natuur() {
    LOGGER.debug("Nieuwe Natuur Sessie geopend.");
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    setPath(getExternalContext().getRequestContextPath());
    if (isAdministrator()) {
      addMenuitem("Dropdown.admin", "menu.administratie");
      addDropdownmenuitem(DD_ADMIN, APP_LOGS_REDIRECT,
          "menu.applicatielogs");
      addDropdownmenuitem(DD_ADMIN, APP_PARAMS_REDIRECT,
          "menu.applicatieparameters");
    }
    addMenuitem(RANGEN_REDIRECT,        "menu.rangen");
    addMenuitem(GEBIEDEN_REDIRECT,      "menu.gebieden");
    addMenuitem(TAXA_REDIRECT,          "menu.taxa");
    addMenuitem(WAARNEMINGEN_REDIRECT,  "menu.waarnemingen");
    addMenuitem(FOTOS_REDIRECT,         "menu.fotos");
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

  protected II18nLandnaam getI18nLandnaam() {
    return i18nLandnaam;
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
