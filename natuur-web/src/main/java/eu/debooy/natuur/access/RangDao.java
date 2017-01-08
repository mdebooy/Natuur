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
import eu.debooy.doosutils.errorhandling.handler.interceptor.PersistenceExceptionHandlerInterceptor;
import eu.debooy.natuur.domain.RangDto;

import java.util.List;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class RangDao extends Dao<RangDto> {
  @PersistenceContext(unitName="natuur", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public RangDao() {
    super(RangDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  /**
   * Haal de soorten op.
   * 
   * @return Collection<TaxonDto>
   */
  @SuppressWarnings("unchecked")
  public List<RangDto>  getVanaf(Long niveau) {
    Query   query         =
        getEntityManager().createNamedQuery("vanaf")
                          .setParameter("niveau", niveau);

    return query.getResultList();
  }
}
