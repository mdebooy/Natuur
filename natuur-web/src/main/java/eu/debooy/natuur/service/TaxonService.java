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
package eu.debooy.natuur.service;

import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.natuur.access.TaxonDao;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurTaxonService")
@Lock(LockType.WRITE)
public class TaxonService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonService.class);

  @Inject
  private TaxonDao    taxonDao;

  /**
   * Initialisatie.
   */
  public TaxonService() {
    LOGGER.debug("init TaxonService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long taxonId) {
    TaxonDto  taxon = taxonDao.getByPrimaryKey(taxonId);
    taxonDao.delete(taxon);
  }

  /**
   * Geef alle kinderen.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getKinderen(Long parentId) {
    Collection<Taxon> kinderen  = new ArrayList<Taxon>();
    try {
      List<TaxonDto>  rows      = taxonDao.getKinderen(parentId);
      for (TaxonDto taxonDto : rows) {
        kinderen.add(new Taxon(taxonDto));
      }
    } catch (ObjectNotFoundException e) {
      // No problem.
    }

    return kinderen;
  }

  /**
   * Geef alle kinderen.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getKinderen(Long parentId, String taal) {
    Collection<Taxon> kinderen  = new ArrayList<Taxon>();
    try {
      List<TaxonDto>  rijen     = taxonDao.getKinderen(parentId);
      for (TaxonDto rij : rijen) {
        kinderen.add(new Taxon(rij, taal));
      }
    } catch (ObjectNotFoundException e) {
      // No problem.
    }

    return kinderen;
  }

  /**
   * Geef de mogelijke 'ouders' van de gevraagde rang.
   * 
   * @param kind
   * @return Collection<TaxonDto>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<TaxonDto> getOuders(Long kind) {
    Collection<TaxonDto>  ouders  = new ArrayList<TaxonDto>();
    try {
      ouders  = taxonDao.getOuders(kind);
    } catch (ObjectNotFoundException e) {
      // No problem.
    }

    return ouders;
  }

  /**
   * Geef alle soorten/waarnemingen.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getSoorten() {
    List<Taxon>       soorten = new ArrayList<Taxon>();
    try {
      List<TaxonDto>  rijen   = taxonDao.getSoorten();
      for (TaxonDto rij : rijen) {
        soorten.add(new Taxon(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  /**
   * Geef alle soorten.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getSoorten(String taal) {
    List<Taxon>       soorten = new ArrayList<Taxon>();
    try {
      List<TaxonDto>  rijen   = taxonDao.getSoorten();
      for (TaxonDto rij : rijen) {
        soorten.add(new Taxon(rij, taal));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return soorten;
  }

  /**
   * Geef alle Taxons.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> query() {
    Collection<Taxon>       taxa  = new HashSet<Taxon>();
    try {
      Collection<TaxonDto>  rijen = taxonDao.getAll();
      for (TaxonDto rij : rijen) {
        taxa.add(new Taxon(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  /**
   * Geef alle Taxons.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> query(String taal) {
    Collection<Taxon>       taxa  = new HashSet<Taxon>();
    try {
      Collection<TaxonDto>  rijen = taxonDao.getAll();
      for (TaxonDto rij : rijen) {
        taxa.add(new Taxon(rij, taal));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxa;
  }

  /**
   * Maak of wijzig de Taxon in de database.
   * 
   * @param taxon
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(TaxonDto taxon) {
    if (null == taxon.getTaxonId()) {
      taxonDao.create(taxon);
      taxon.setTaxonId(taxon.getTaxonId());
    } else {
      taxonDao.update(taxon);
    }
  }

  /**
   * Geef een Taxon.
   * 
   * @return Een Taxon.
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonDto taxon(Long taxonId) {
    TaxonDto  taxon = taxonDao.getByPrimaryKey(taxonId);

    return taxon;
  }
}
