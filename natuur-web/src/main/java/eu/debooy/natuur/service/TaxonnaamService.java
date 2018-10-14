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

import eu.debooy.natuur.access.TaxonnaamDao;
import eu.debooy.natuur.domain.TaxonnaamDto;
import eu.debooy.natuur.domain.TaxonnaamPK;
import eu.debooy.natuur.form.Taxonnaam;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

  public TaxonnaamService() {
    LOGGER.debug("init TaxonnaamService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public TaxonnaamDto taxonnaam(Long taxonId, String taal) {
    TaxonnaamDto  resultaat =
        taxonnaamDao.getByPrimaryKey(new TaxonnaamPK(taxonId, taal));

    if (null == resultaat) {
      return new TaxonnaamDto();
    }

    return resultaat;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxonnaam> taxonnamen(String taal) {
    List<Taxonnaam>     taxonnamen  = new ArrayList<Taxonnaam>();
    List<TaxonnaamDto>  rijen       = taxonnaamDao.getPerTaal(taal);
    for (TaxonnaamDto rij : rijen) {
      taxonnamen.add(new Taxonnaam(rij));
    }

    return taxonnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<Taxonnaam> query(Long taxonId) {
    List<Taxonnaam>     taxonnamen  = new ArrayList<Taxonnaam>();
    List<TaxonnaamDto>  rijen       = taxonnaamDao.getPerTaxon(taxonId);
    for (TaxonnaamDto rij : rijen) {
      taxonnamen.add(new Taxonnaam(rij));
    }

    return taxonnamen;
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectTaxonnamen(String taal) {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<TaxonnaamDto> rijen =
        new TreeSet<TaxonnaamDto>(new TaxonnaamDto.NaamComparator());
    rijen.addAll(taxonnaamDao.getPerTaal(taal));
    for (TaxonnaamDto rij : rijen) {
      items.add(new SelectItem(rij.getTaxonId(), rij.getNaam()));
    }

    return items;
  }
}
