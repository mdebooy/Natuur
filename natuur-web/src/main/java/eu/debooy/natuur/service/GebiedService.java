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
import eu.debooy.natuur.access.GebiedDao;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.form.Gebied;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Singleton
@Named("natuurGebiedService")
@Lock(LockType.WRITE)
public class GebiedService {
  private static  Logger  logger  =
      LoggerFactory.getLogger(GebiedService.class);

  @Inject
  private GebiedDao   gebiedDao;

  private Set<Gebied> gebieden;

  /**
   * Initialisatie.
   */
  public GebiedService() {
    logger.debug("init GebiedService");
  }

  public void delete(Long gebiedId) {
    GebiedDto gebied  = gebiedDao.getByPrimaryKey(gebiedId);
    gebiedDao.delete(gebied);
  }

  /**
   * Geef alle Gebieden.
   * 
   * @return Een Set met Gebieden.
   */
  public Set<Gebied> lijst() {
    if (null == gebieden) {
      gebieden  = new HashSet<Gebied>();
      Collection<GebiedDto>  rows    = gebiedDao.getAll();
      for (GebiedDto gebiedDto : rows) {
        gebieden.add(new Gebied(gebiedDto));
      }
    }

    return gebieden;
  }

  /**
   * Maak of wijzig de Gebied in de database.
   * 
   * @param gebied
   */
  public void save(Gebied gebied) {
    logger.debug(gebied.toString());
    GebiedDto  dto = new GebiedDto();
    gebied.persist(dto);
    logger.debug(dto.toString());

    if (null == gebied.getGebiedId()) {
      gebiedDao.create(dto);
      gebied.setGebiedId(dto.getGebiedId());
    } else {
      gebiedDao.update(dto);
    }
    if (null != gebieden) {
      gebieden.remove(gebied);
      gebieden.add(gebied);
    }
  }

  /**
   * Geef een Gebied.
   * 
   * @return Een Gebied.
   */
  public GebiedDto gebied(Long gebiedId) {
    GebiedDto gebied  = gebiedDao.getByPrimaryKey(gebiedId);

    return gebied;
  }

  /**
   * Valideer de Gebied.
   */
  public List<Message> valideer(Gebied gebied) {
    List<Message> fouten  = new ArrayList<Message>();

    return fouten;
  }
}
