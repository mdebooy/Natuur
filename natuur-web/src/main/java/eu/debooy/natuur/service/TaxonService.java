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

import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.natuur.access.TaxonDao;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;

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

  private Set<Taxon>  taxa;

  /**
   * Initialisatie.
   */
  public TaxonService() {
    LOGGER.debug("init TaxonService");
  }

  @Interceptors({PersistenceExceptionHandlerInterceptor.class})
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long taxonId) {
    TaxonDto  taxon = taxonDao.getByPrimaryKey(taxonId);
    taxonDao.delete(taxon);
    taxa.remove(new Taxon(taxon));
  }

  /**
   * Geef alle kinderen.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getKinderen(Long parentId) {
    List<Taxon>     kinderen  = new ArrayList<Taxon>();
    List<TaxonDto>  rows      = taxonDao.getKinderen(parentId);
    for (TaxonDto taxonDto : rows) {
      kinderen.add(new Taxon(taxonDto));
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
    return taxonDao.getOuders(kind);
  }

  /**
   * Geef alle soorten/waarnemingen.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> getSoorten() {
    List<Taxon>     soorten = new ArrayList<Taxon>();
    List<TaxonDto>  rows    = taxonDao.getSoorten();
    for (TaxonDto taxonDto : rows) {
      soorten.add(new Taxon(taxonDto));
    }

    return soorten;
  }

  /**
   * Geef alle Taxons.
   * 
   * @return Collection<Taxon>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Taxon> lijst() {
    if (null == taxa) {
      taxa  = new HashSet<Taxon>();
      Collection<TaxonDto>  rijen = taxonDao.getAll();
      for (TaxonDto rij : rijen) {
        taxa.add(new Taxon(rij));
      }
    }

    return taxa;
  }

  /**
   * Maak of wijzig de Taxon in de database.
   * 
   * @param taxon
   */
  @Interceptors({PersistenceExceptionHandlerInterceptor.class})
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Taxon taxon) {
    TaxonDto  dto = new TaxonDto();
    taxon.persist(dto);

    if (null == taxon.getTaxonId()) {
      taxonDao.create(dto);
      taxon.setTaxonId(dto.getTaxonId());
    } else {
      taxonDao.update(dto);
    }

    if (null != taxa) {
      taxa.remove(taxon);
      taxa.add(taxon);
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
