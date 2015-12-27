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
package eu.debooy.natuur.access;

import eu.debooy.doosutils.access.Dao;
import eu.debooy.natuur.domain.GebiedDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * @author Marco de Booij
 */
public class GebiedDao extends Dao<GebiedDto> {
  @PersistenceContext(unitName="natuur", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public GebiedDao() {
    super(GebiedDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }
}
