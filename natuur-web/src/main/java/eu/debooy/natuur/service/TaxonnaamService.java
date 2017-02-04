/**
 * Copyright 2017 Marco de Booij
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
import eu.debooy.natuur.access.TaxonnaamDao;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.domain.TaxonnaamPK;
import eu.debooy.natuur.form.Taxonnaam;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurTaxonnaamService")
@Lock(LockType.WRITE)
public class TaxonnaamService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonnaamService.class);

  @Inject
  private TaxonnaamDao taxonnaamDao;

  /**
   * Initialisatie.
   */
  public TaxonnaamService() {
    LOGGER.debug("init TaxonnaamService");
  }

  /**
   * Geef een TaxonnaamDto.
   * 
   * @param taxonId
   * @param taal
   * @return TaxonnaamDto
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonnaamDto taxonnaam(Long taxonId, String taal) {
    TaxonnaamPK   sleutel   = new TaxonnaamPK(taxonId, taal);
    TaxonnaamDto  taxonnaam;
    try {
      taxonnaam = taxonnaamDao.getByPrimaryKey(sleutel);
    } catch (ObjectNotFoundException e) {
      return new TaxonnaamDto();
    }

    return taxonnaam;
  }

  /**
   * Geef de landnamen in een taal.
   * 
   * @param String taal
   * @return Collection<Taxonnaam>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxonnaam> taxonnamen(String taal) {
    Set<Taxonnaam>              taxonnamen  =
        new HashSet<Taxonnaam>();
    try {
      Collection<TaxonnaamDto>  rijen       =
          taxonnaamDao.getPerTaal(taal);
      for (TaxonnaamDto rij : rijen) {
        taxonnamen.add(new Taxonnaam(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxonnamen;
  }

  /**
   * Geef de taxonnamen van een taxon.
   * 
   * @param Long taxonId
   * @return Collection<Taxonnaam>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxonnaam> query(Long taxonId) {
    Set<Taxonnaam>              taxonnamen  =
        new HashSet<Taxonnaam>();
    try {
      Collection<TaxonnaamDto>  rijen       =
          taxonnaamDao.getPerTaxon(taxonId);
      for (TaxonnaamDto rij : rijen) {
        taxonnamen.add(new Taxonnaam(rij));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return taxonnamen;
  }

  /**
   * Geef alle taxonnamen (en taxonId) in de gevraagde taal als
   * SelectItems.
   *  
   * @param taal
   * @return Collection<SelectItem>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<SelectItem> selectTaxonnamen(String taal) {
    Collection<SelectItem> items = new LinkedList<SelectItem>();
    Set<TaxonnaamDto> rijen =
        new TreeSet<TaxonnaamDto>(new TaxonnaamDto.NaamComparator());
    try {
      rijen.addAll(taxonnaamDao.getPerTaal(taal));
      for (TaxonnaamDto rij : rijen) {
        items.add(new SelectItem(rij.getTaxonId(), rij.getNaam()));
      }
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return items;
  }
}
