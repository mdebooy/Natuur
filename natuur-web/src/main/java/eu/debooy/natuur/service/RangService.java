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

import eu.debooy.doosutils.components.Message;
import eu.debooy.natuur.access.RangDao;
import eu.debooy.natuur.domain.RangDto;
import eu.debooy.natuur.form.Rang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurRangService")
@Lock(LockType.WRITE)
public class RangService {
  private static  Logger  logger  =
      LoggerFactory.getLogger(TaxonService.class);

  @Inject
  private RangDao     rangDao;

  private List<Rang>  rangen;

  /**
   * Initialisatie.
   */
  public RangService() {
    logger.debug("init RangService");
  }

  public void delete(Long rangId) {
    RangDto rang  = rangDao.getByPrimaryKey(rangId);
    rangDao.delete(rang);
  }

  /**
   * Geef alle Rangen.
   * 
   * @return List<Rang>
   */
  public List<Rang> lijst() {
    if (null == rangen) {
      rangen  = new ArrayList<Rang>();
      Collection<RangDto>  rows  = rangDao.getAll();
      for (RangDto rangDto : rows) {
        rangen.add(new Rang(rangDto));
      }
    }

    return rangen;
  }

  /**
   * Geef een Rang.
   * 
   * @return Een RangDto.
   */
  public RangDto rang(String rang) {
    RangDto rangDto = rangDao.getByPrimaryKey(rang);

    return rangDto;
  }

  /**
   * Maak of wijzig de Rang in de database.
   * 
   * @param Rang
   */
  public void save(Rang rang) {
    RangDto dto = new RangDto();
    rang.persist(dto);
    logger.debug(dto.toString());

    if (null == rang.getRang()) {
      rangDao.create(dto);
      rang.setRang(dto.getRang());
    } else {
      rangDao.update(dto);
    }
    if (null != rangen) {
      rangen.remove(rang);
      rangen.add(rang);
    }
  }

  /**
   * Geef alle rangen als SelectItem.
   *  
   * @return List<SelectItem>
   */
  public List<SelectItem> selectRangen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    List<RangDto>     rijen = rangDao.getAll();
    Collections.sort(rijen, new RangDto.NiveauComparator());
    for (RangDto rangDto : rijen) {
      items.add(new SelectItem(rangDto.getRang(), rangDto.getRang()));
    }

    return items;
  }

  /**
   * Valideer de Rang.
   */
  public List<Message> valideer(Rang rang) {
    List<Message> fouten  = new ArrayList<Message>();

    return fouten;
  }
}