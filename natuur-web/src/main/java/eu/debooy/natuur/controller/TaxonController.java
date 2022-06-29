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
package eu.debooy.natuur.controller;

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

  private static final  String  KINDEREN_TAB      = "kinderenTab";
  private static final  String  NAMEN_TAB         = "namenTab";

  private String        aktieveTab;
  private Taxon         ouder;
  private Long          ouderNiveau;
  private Taxon         taxon;
  private TaxonDto      taxonDto;
  private Taxonnaam     taxonnaam;
  private TaxonnaamDto  taxonnaamDto;

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

  public void create() {
    aktieveTab  = KINDEREN_TAB;
    taxon       = new Taxon();
    taxonDto    = new TaxonDto();
    ouderNiveau = Long.valueOf(0);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

  public void create(Long parentId) {
    aktieveTab    = KINDEREN_TAB;
    taxon         = new Taxon();
    taxonDto      = new TaxonDto();
    var ouderRang = getTaxonService().taxon(parentId).getRang();
    ouderNiveau   = getRangService().rang(ouderRang).getNiveau();
    taxon.setParentId(parentId);
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.taxon.create");
    redirect(TAXON_REDIRECT);
  }

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

  public void delete(Long taxonId) {
    String  naam;
    try {
      taxonDto  = getTaxonService().taxon(taxonId);
      naam      = taxonDto.getNaam(getGebruikersTaal());
      getTaxonService().delete(taxonId);
      addInfo(PersistenceConstants.DELETED, naam);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonId);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void deleteTaxonnaam(String taal) {
    try {
      taxonDto.removeTaxonnaam(taal);
      getTaxonService().save(taxonDto);
      addInfo(PersistenceConstants.DELETED, "'" + taal + "'");
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taal);
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public String getAktieveTab() {
    return aktieveTab;
  }

  public Collection<Taxon> getKinderen(Long parentId) {
    var         rangnamen = getRangnamen();
    List<Taxon> taxa      = new ArrayList<>();

    getTaxonService().getKinderen(parentId, getGebruikersTaal())
                     .forEach(rij -> {
      rij.setRangnaam(rangnamen.get(rij.getRang()));
      taxa.add(rij);
    });

    return taxa;
  }

  public Taxon getOuder() {
    return ouder;
  }

  public Long getOuderNiveau() {
    return ouderNiveau;
  }

  private Map<String, String> getRangnamen() {
    Map<String, String> rangnamen = new HashMap<>();

    getRangService().query(getGebruikersTaal())
                    .forEach(rij -> rangnamen.put(rij.getRang(),
                                                  rij.getNaam()));

    return rangnamen;
  }

  public Collection<Taxon> getTaxa() {
    var         rangnamen = getRangnamen();
    List<Taxon> taxa      = new ArrayList<>();

    getTaxonService().query(getGebruikersTaal())
                     .forEach(rij -> {
      rij.setRangnaam(rangnamen.get(rij.getRang()));
      taxa.add(rij);
    });

    return taxa;
  }

  public Taxon getTaxon() {
    return taxon;
  }

  public Taxonnaam getTaxonnaam() {
    return taxonnaam;
  }

  public Collection<Taxonnaam> getTaxonnamen() {
    Collection<Taxonnaam> taxonnamen  = new HashSet<>();

    taxonDto.getTaxonnamen().forEach(rij -> taxonnamen.add(new Taxonnaam(rij)));

    return taxonnamen;
  }

  public void retrieve(Long taxonId) {
    aktieveTab  = KINDEREN_TAB;
    taxonDto    = getTaxonService().taxon(taxonId);
    taxon       = new Taxon(taxonDto, getGebruikersTaal());
    bepaalOuder(taxon.getParentId());
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(taxon.getNaam());
    redirect(TAXON_REDIRECT);
  }

  public void save() {
    var messages  = TaxonValidator.valideer(taxon);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      taxon.persist(taxonDto);
      getTaxonService().save(taxonDto);
      aktieveTab  = KINDEREN_TAB;
      bepaalOuder(taxon.getParentId());
      var naam    = taxon.getNaam();
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          taxon.setTaxonId(taxonDto.getTaxonId());
          addInfo(PersistenceConstants.CREATED, naam);
          setAktie(PersistenceConstants.UPDATE);
          setSubTitel("natuur.titel.taxon.update");
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, naam);
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxon.getLatijnsenaam());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxon.getNaam());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void saveTaxonnaam() {
    var messages  = TaxonnaamValidator.valideer(taxonnaam);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    if (getDetailAktie().getAktie() == PersistenceConstants.CREATE
        && taxonDto.hasTaxonnaam(taxonnaam.getTaal())) {
      addError(PersistenceConstants.DUPLICATE, taxonnaam.getTaal());
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
          addError(ComponentsConstants.WRONGREDIRECT,
                   getDetailAktie().getAktie()) ;
          break;
      }
      redirect(TAXON_REDIRECT);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, taxonnaam.getTaal());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, taxonnaam.getTaal());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public List<SelectItem> selectOuders(String rang) {
    Long              niveau;
    if (DoosUtils.isBlankOrNull(rang)) {
      niveau  = Long.valueOf("1000");
    } else {
      niveau  = getRangService().rang(rang).getNiveau();
    }
    List<SelectItem>  items = new LinkedList<>();
    Set<TaxonDto>     rijen = new TreeSet<>(new TaxonDto.NaamComparator());
    rijen.addAll(getTaxonService().getOuders(niveau));
    rijen.forEach(rij ->
      items.add(new SelectItem(rij.getTaxonId(),
                               rij.getNaam(getGebruikersTaal()) + " ("
                                + rij.getLatijnsenaam() + ")")));

    return items;
  }

  public void update(Long taxonId) {
    aktieveTab  = KINDEREN_TAB;
    taxonDto    = getTaxonService().taxon(taxonId);
    taxon       = new Taxon(taxonDto, getGebruikersTaal());
    bepaalOuder(taxon.getParentId());
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.taxon.update");
    redirect(TAXON_REDIRECT);
  }

  public void updateTaxonnaam(String taal) {
    aktieveTab    = NAMEN_TAB;
    taxonnaamDto  = taxonDto.getTaxonnaam(taal);
    taxonnaam     = new Taxonnaam(taxonnaamDto);
    setDetailAktie(PersistenceConstants.UPDATE);
    setDetailSubTitel("natuur.titel.taxonnaam.update");
    redirect(TAXONNAAM_REDIRECT);
  }
}
