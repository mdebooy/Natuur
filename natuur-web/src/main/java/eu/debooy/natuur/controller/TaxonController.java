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
import eu.debooy.natuur.form.Taxon;
import eu.debooy.natuur.validator.TaxonValidator;

import java.util.Collection;
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

  private Taxon ouder;
  private Long  ouderNiveau;
  private Taxon taxon;

  /**
   * Prepareer een nieuw taxon.
   */
  public void create() {
    taxon       = new Taxon();
    ouderNiveau = Long.valueOf(0);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Prepareer een nieuw taxon met parentId.
   */
  public void create(Long parentId) {
    taxon       = new Taxon();
    String  ouderRang = getTaxonService().taxon(parentId).getRang();
    ouderNiveau = getRangService().rang(ouderRang).getNiveau();
    taxon.setParentId(parentId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  /**
   * Verwijder het Taxon
   * 
   * @param Taxon
   */
  public void delete(Long taxonId, String naam) {
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
    addInfo(PersistenceConstants.DELETED, naam);
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
    return getTaxonService().query();
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
   * Zet het Taxon dat gevraagd is klaar.
   * 
   * @param Long taxonId
   */
  public void retrieve(Long taxonId) {
    taxon = new Taxon(getTaxonService().taxon(taxonId));
    Long  parentId  = taxon.getParentId();
    if (null == parentId) {
      ouder       = new Taxon();
      ouderNiveau = Long.valueOf(0);
    } else {
      ouder       = new Taxon(getTaxonService().taxon(parentId));
      ouderNiveau = getRangService().rang(ouder.getRang()).getNiveau();
    }
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
      getTaxonService().save(taxon);
      Long  parentId  = taxon.getParentId();
      if (null == parentId) {
        ouder       = new Taxon();
        ouderNiveau = Long.valueOf(0);
      } else {
        if (null != ouder.getTaxonId()
            && parentId != ouder.getTaxonId()) {
          ouder       = new Taxon(getTaxonService().taxon(parentId));
          ouderNiveau = getRangService().rang(ouder.getRang()).getNiveau();
        }
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

    if (getAktie().isNieuw()) {
      addInfo("info.create", taxon.getNaam());
      setAktie(PersistenceConstants.RETRIEVE);
      setSubTitel(taxon.getNaam());
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
   * Zet de Taxon die gewijzigd gaat worden klaar.
   * 
   * @param Long taxonId
   */
  public void update(Long taxonId) {
    taxon       = new Taxon(getTaxonService().taxon(taxonId));
    String  ouderRang = getTaxonService().taxon(taxon.getParentId()).getRang();
    ouderNiveau = getRangService().rang(ouderRang).getNiveau();
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.taxon.update");
    redirect(TAXON_REDIRECT);
  }
}
