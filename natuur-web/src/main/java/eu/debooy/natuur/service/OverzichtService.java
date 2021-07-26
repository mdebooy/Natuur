/*
 * Copyright (c) 2021 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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
import eu.debooy.natuur.access.OverzichtDao;
import eu.debooy.natuur.domain.OverzichtDto;
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
@Named("natuurOverzichtService")
@Lock(LockType.WRITE)
public class OverzichtService {
  private static final  Logger  LOGGER  =
      LoggerFactory.getLogger(OverzichtService.class);

  @Inject
  private OverzichtDao  overzichtDao;

  public OverzichtService() {
    LOGGER.debug("init OverzichtService");
  }

  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<OverzichtDto> getTotalenVoorRang(String rang) {
    List<OverzichtDto> overzicht = new ArrayList<>();

    try {
      overzicht.addAll(overzichtDao.getOverzichtRang(rang));
    } catch (ObjectNotFoundException e) {
      // Er wordt nu gewoon een lege ArrayList gegeven.
    }

    return overzicht;
  }
}
