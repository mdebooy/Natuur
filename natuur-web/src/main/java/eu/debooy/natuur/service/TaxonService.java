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

import eu.debooy.natuur.access.TaxonDao;
import eu.debooy.natuur.domain.TaxonDto;
import eu.debooy.natuur.form.Taxon;

import java.util.ArrayList;
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
  private TaxonDao  taxonDao;

  public TaxonService() {
    LOGGER.debug("init TaxonService");
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(Long taxonId) {
    TaxonDto  taxon = taxonDao.getByPrimaryKey(taxonId);
    taxonDao.delete(taxon);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getKinderen(Long parentId) {
    List<Taxon>     kinderen  = new ArrayList<Taxon>();
    List<TaxonDto>  rows      = taxonDao.getKinderen(parentId);
    for (TaxonDto taxonDto : rows) {
      kinderen.add(new Taxon(taxonDto));
    }

    return kinderen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getKinderen(Long parentId, String taal) {
    List<Taxon>     kinderen  = new ArrayList<Taxon>();
    List<TaxonDto>  rijen     = taxonDao.getKinderen(parentId);
    for (TaxonDto rij : rijen) {
      kinderen.add(new Taxon(rij, taal));
    }

    return kinderen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<TaxonDto> getOuders(Long kind) {
    return taxonDao.getOuders(kind);
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoorten() {
    List<Taxon>     soorten = new ArrayList<Taxon>();
    List<TaxonDto>  rijen   = taxonDao.getSoorten();
    for (TaxonDto rij : rijen) {
      soorten.add(new Taxon(rij));
    }

    return soorten;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> getSoorten(String taal) {
    List<Taxon>     soorten = new ArrayList<Taxon>();
    List<TaxonDto>  rijen   = taxonDao.getSoorten();
    for (TaxonDto rij : rijen) {
      soorten.add(new Taxon(rij, taal));
    }

    return soorten;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> query() {
    List<Taxon>     taxa  = new ArrayList<Taxon>();
    List<TaxonDto>  rijen = taxonDao.getAll();
    for (TaxonDto rij : rijen) {
      taxa.add(new Taxon(rij));
    }

    return taxa;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxon> query(String taal) {
    List<Taxon>     taxa  = new ArrayList<Taxon>();
    List<TaxonDto>  rijen = taxonDao.getAll();
    for (TaxonDto rij : rijen) {
      taxa.add(new Taxon(rij, taal));
    }

    return taxa;
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(TaxonDto taxon) {
    if (null == taxon.getTaxonId()) {
      taxonDao.create(taxon);
      taxon.setTaxonId(taxon.getTaxonId());
    } else {
      taxonDao.update(taxon);
    }
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonDto taxon(Long taxonId) {
    TaxonDto  resultaat = taxonDao.getByPrimaryKey(taxonId);

    if (null == resultaat) {
      return new TaxonDto();
    }

    return resultaat;
  }
}
