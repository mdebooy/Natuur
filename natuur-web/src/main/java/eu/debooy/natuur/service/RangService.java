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
import eu.debooy.natuur.access.RangDao;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.form.Rang;

import java.util.Collection;
import java.util.HashSet;
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
import javax.interceptor.Interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurRangService")
@Lock(LockType.WRITE)
public class RangService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(TaxonService.class);

  @Inject
  private RangDao     rangDao;

  private Set<Rang>   rangen;

  /**
   * Initialisatie.
   */
  public RangService() {
    LOGGER.debug("init RangService");
  }

  /**
   * Verwijder de Rang
   * 
   * @param String sleutel
   */
  @Interceptors({PersistenceExceptionHandlerInterceptor.class})
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void delete(String sleutel) {
    RangDto rang  = rangDao.getByPrimaryKey(sleutel);
    rangDao.delete(rang);
    rangen.remove(new Rang(rang));
  }

  /**
   * Geef alle Rangen.
   * 
   * @return Collection<Rang>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Rang> lijst() {
    if (null == rangen) {
      rangen  = new HashSet<Rang>();
      Collection<RangDto> rijen = rangDao.getAll();
      for (RangDto rij : rijen) {
        rangen.add(new Rang(rij));
      }
    }

    return rangen;
  }

  /**
   * Geef alle Rangen.
   * 
   * @return Collection<Rang>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public Collection<Rang> lijst(Long niveau) {
    if (null == rangen) {
      lijst();
    }

    Set<Rang> groter  = new HashSet<Rang>();
    for (Rang rang : rangen) {
      if (rang.getNiveau().compareTo(niveau) > 0) {
        groter.add(rang);
      }
    }

    return groter;
  }

  /**
   * Geef een Rang.
   * 
   * @param String rang
   * @return RangDto
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public RangDto rang(String rang) {
    RangDto rangDto = rangDao.getByPrimaryKey(rang);

    return rangDto;
  }

  /**
   * Maak de Rang in de database.
   * 
   * @param Rang
   */
  @Interceptors({PersistenceExceptionHandlerInterceptor.class})
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public void save(Rang rang) {
    RangDto dto = new RangDto();
    rang.persist(dto);

    rangDao.create(dto);

    if (null != rangen) {
      rangen.add(rang);
    }
  }

  /**
   * Geef alle rangen als SelectItem.
   *  
   * @return List<SelectItem>
   */
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<SelectItem> selectRangen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<RangDto>      rijen =
        new TreeSet<RangDto>(new RangDto.NiveauComparator());
    rijen.addAll(rangDao.getAll());
    for (RangDto rij : rijen) {
      items.add(new SelectItem(rij.getRang(), rij.getRang()));
    }

    return items;
  }
}