/**
 * Copyright 2016 Marco de Booij
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
package eu.debooy.natuur.controller;

import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.form.Taxonnaam;
import eu.debooy.natuur.validator.TaxonValidator;
import eu.debooy.natuur.validator.TaxonnaamValidator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurTaxon")
@SessionScoped
public class TaxonController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(TaxonController.class);

  public static final String  KINDEREN_TAB  = "kinderenTab";
  public static final String  NAMEN_TAB     = "namenTab";

  private String        aktieveTab;
  private Taxon         ouder;
  private Long          ouderNiveau;
  private Taxon         taxon;
  private TaxonDto      taxonDto;
  private Taxonnaam     taxonnaam;
  private TaxonnaamDto  taxonnaamDto;

  /**
   * Vul 'ouder' en 'ouderNiveau' voor de gevraagde parentId.
   * 
   * @param parentId
   */
  public void bepaalOuder(Long parentId) {
    if (null == parentId) {
      ouder       = new Taxon();
      ouderNiveau = Long.valueOf(0);
    } else {
      ouder       = new Taxon(getTaxonService().taxon(parentId),
                              getGebruikersTaal());
      ouderNiveau = getRangService().rang(ouder.getRang()).getNiveau();
    }
  }

  /**
   * Prepareer een nieuw taxon.
   */
  public void create() {
    aktieveTab  = KINDEREN_TAB;
    taxon       = new Taxon();
    taxonDto    = new TaxonDto();
    ouderNiveau = Long.valueOf(0);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Prepareer een nieuw taxon met parentId.
   */
  public void create(Long parentId) {
    aktieveTab  = KINDEREN_TAB;
    taxon       = new Taxon();
    taxonDto    = new TaxonDto();
    String  ouderRang = getTaxonService().taxon(parentId).getRang();
    ouderNiveau = getRangService().rang(ouderRang).getNiveau();
    taxon.setParentId(parentId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Prepareer een nieuw Taxonnaam.
   */
  public void createTaxonnaam() {
    aktieveTab    = NAMEN_TAB;
    taxonnaam     = new Taxonnaam();
    taxonnaam.setTaal(getGebruikersTaal());
    taxonnaamDto  = new TaxonnaamDto();
    taxonnaamDto.setTaal(getGebruikersTaal());
    setDetailAktie(PersistenceConstants.CREATE);
    setDetailSubTitel("natuur.titel.taxonnaam.create");
    redirect(TAXONNAAM_REDIRECT);
  }

  /**
   * Verwijder het Taxon
   * 
   * @param Taxon
   */
  public void delete(Long taxonId) {
    String  naam  = "";
    try {
      taxonDto	= getTaxonService().taxon(taxonId);
      naam      = taxonDto.getNaam(getGebruikersTaal());
      getTaxonService().delete(taxonId);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonId);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, naam);
  }

  /**
   * Verwijder de Taxonnaam.
   * 
   * @param String taal
   */
  public void deleteTaxonnaam(String taal) {
    try {
      taxonDto.removeNaam(taal);
      getTaxonService().save(taxonDto);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taal);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, "'" + taal + "'");
  }

  /**
   * Geef de aktieve tab.
   * 
   * @return
   */
  public String getAktieveTab() {
    return aktieveTab;
  }

  /**
   * Geef de lijst met kinderen van de taxon.
   * 
   * @return Collection<Taxon> met Taxon objecten.
   */
  public Collection<Taxon> getKinderen(Long parentId) {
    return getTaxonService().getKinderen(parentId, getGebruikersTaal());
  }

  /**
   * Geeft de ouder van de taxon.
   * 
   * @return Taxon
   */
  public Taxon getOuder() {
    return ouder;
  }

  /**
   * Geeft het rang niveau van de ouder.
   * 
   * @return Long
   */
  public Long getOuderNiveau() {
    return ouderNiveau;
  }

  /**
   * Geef de naam van de gevraagde rang.
   * 
   * @return String
   */
  public String getRangnaam() {
    return getTekst("biologie.rang." + taxon.getRang());
  }

  /**
   * Geef de lijst met taxa.
   * 
   * @return Collection<Taxon> met Taxon objecten.
   */
  public Collection<Taxon> getTaxa() {
    return getTaxonService().query(getGebruikersTaal());
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
   * Geef de geselecteerde werelddeelnaam.
   * 
   * @return Taxonnaam
   */
  public Taxonnaam getTaxonnaam() {
    return taxonnaam;
  }

  /**
   * Geef de lijst met werelddeelnamen.
   * 
   * @return Collection<Taxonnaam>
   */
  public Collection<Taxonnaam> getTaxonnamen() {
    Collection<Taxonnaam> taxonnamen  =
        new HashSet<Taxonnaam>();
    for (TaxonnaamDto rij : taxonDto.getTaxonnamen()) {
      taxonnamen.add(new Taxonnaam(rij));
    }

    return taxonnamen;
  }

  /**
   * Zet het Taxon dat gevraagd is klaar.
   * 
   * @param Long taxonId
   */
  public void retrieve(Long taxonId) {
    aktieveTab  = KINDEREN_TAB;
    taxonDto    = getTaxonService().taxon(taxonId);
    taxon       = new Taxon(taxonDto, getGebruikersTaal());
    bepaalOuder(taxon.getParentId());
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  /**
   * Persist het Taxon
   */
  public void save() {
    List<Message> messages  = TaxonValidator.valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      taxon.persist(taxonDto);
      getTaxonService().save(taxonDto);
      aktieveTab    = KINDEREN_TAB;
      bepaalOuder(taxon.getParentId());
      String  naam  = taxon.getNaam();
      switch (getAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        taxon.setTaxonId(taxonDto.getTaxonId());
        addInfo(PersistenceConstants.CREATED, naam);
        setAktie(PersistenceConstants.UPDATE);
        setSubTitel(naam);
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, naam);
        break;
      default:
        addError("error.aktie.wrong", getAktie().getAktie());
        break;
      }
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
  }

  /**
   * Persist de Taxonnaam
   */
  public void saveTaxonnaam() {
    List<Message> messages  = TaxonnaamValidator.valideer(taxonnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      taxonnaamDto  = new TaxonnaamDto();
      taxonnaam.persist(taxonnaamDto);
      taxonDto.addNaam(taxonnaamDto);
      if (getGebruikersTaal().equals(taxonnaam.getTaal())) {
        taxon.setNaam(taxonnaam.getNaam());
      }
      getTaxonService().save(taxonDto);
      switch (getDetailAktie().getAktie()) {
      case PersistenceConstants.CREATE:
        addInfo(PersistenceConstants.CREATED, "'" + taxonnaam.getTaal() + "'");
        break;
      case PersistenceConstants.UPDATE:
        addInfo(PersistenceConstants.UPDATED, "'" + taxonnaam.getTaal() + "'");
        break;
      default:
        addError("error.aktie.wrong", getDetailAktie().getAktie()) ;
        break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxonnaam.getTaal());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonnaam.getTaal());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(TAXON_REDIRECT);
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
                               rij.getNaam(getGebruikersTaal()) + " ("
                                   + rij.getLatijnsenaam() + ")"));
    }

    return items;
  }

  /**
   * Zet de Taxon die gewijzigd gaat worden klaar.
   * 
   * @param Long taxonId
   */
  public void update(Long taxonId) {
    aktieveTab  = KINDEREN_TAB;
    taxonDto    = getTaxonService().taxon(taxonId);
    taxon       = new Taxon(taxonDto, getGebruikersTaal());
    bepaalOuder(taxon.getParentId());
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.taxon.update");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Zet de Taxonnaam die gewijzigd gaat worden klaar.
   * 
   * @param Long taxonId
   */
  public void updateTaxonnaam(String taal) {
    aktieveTab    = NAMEN_TAB;
    taxonnaamDto  = taxonDto.getTaxonnaam(taal);
    taxonnaam     = new Taxonnaam(taxonnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("natuur.titel.taxonnaam.update");
    redirect(TAXONNAAM_REDIRECT);
  }
}
