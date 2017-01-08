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

import eu.debooy.doos.component.bean.DoosBean;
import eu.debooy.doosutils.service.JNDI;
import eu.debooy.natuur.service.DetailService;
import eu.debooy.natuur.service.FotoService;
import eu.debooy.natuur.service.GebiedService;
import eu.debooy.natuur.service.RangService;
import eu.debooy.natuur.service.TaxonService;
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
    LOGGER.debug("Nieuwe Natuur Sessie geopend.");
    setAdminRole(getExternalContext().isUserInRole(ADMIN_ROLE));
    setApplicatieNaam(APPLICATIE_NAAM);
    setUserRole(getExternalContext().isUserInRole(USER_ROLE));
    setPath(getExternalContext().getRequestContextPath());
    if (isAdministrator()) {
      addMenuitem("/admin/parameters.xhtml",  "menu.parameters");
    }
    addMenuitem(RANGEN_REDIRECT,                    "menu.rangen");
    addMenuitem(GEBIEDEN_REDIRECT,                  "menu.gebieden");
    addMenuitem(TAXA_REDIRECT,                      "menu.taxa");
    addMenuitem("/waarnemingen/waarnemingen.xhtml", "menu.waarnemingen");
    addMenuitem(FOTOS_REDIRECT,                     "menu.fotos");
  }

  /**
   * Geef de DetailService. Als die nog niet gekend is haal het dan op.
   * 
   * @return DetailService
   */
  protected DetailService getDetailService() {
    if (null == detailService) {
      detailService = (DetailService)
          new JNDI.JNDINaam().metBean(DetailService.class).locate();
    }

    return detailService;
  }

  /**
   * Geef de FotoService. Als die nog niet gekend is haal het dan op.
   * 
   * @return FotoService
   */
  protected FotoService getFotoService() {
    if (null == fotoService) {
      fotoService = (FotoService)
          new JNDI.JNDINaam().metBean(FotoService.class).locate();
    }

    return fotoService;
  }

  /**
   * Geef de GebiedService. Als die nog niet gekend is haal het dan op.
   * 
   * @return GebiedService
   */
  protected GebiedService getGebiedService() {
    if (null == gebiedService) {
      gebiedService = (GebiedService)
          new JNDI.JNDINaam().metBean(GebiedService.class).locate();
    }

    return gebiedService;
  }

  /**
   * Geef de II18nLandnaam.
   * 
   * @return II18nLandnaam
   */
  protected II18nLandnaam getI18nLandnaam() {
    return i18nLandnaam;
  }

  /**
   * Geef de RangService. Als die nog niet gekend is haal het dan op.
   * 
   * @return RangService
   */
  protected RangService getRangService() {
    if (null == rangService) {
      rangService = (RangService)
          new JNDI.JNDINaam().metBean(RangService.class).locate();
    }

    return rangService;
  }

  /**
   * Geef de TaxonService. Als die nog niet gekend is haal het dan op.
   * 
   * @return TaxonService
   */
  protected TaxonService getTaxonService() {
    if (null == taxonService) {
      taxonService  = (TaxonService)
          new JNDI.JNDINaam().metBean(TaxonService.class).locate();
    }

    return taxonService;
  }
}
