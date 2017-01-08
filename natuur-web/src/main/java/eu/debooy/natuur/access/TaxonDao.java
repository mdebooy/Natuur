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
import eu.debooy.natuur.domain.TaxonDto;

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
public class TaxonDao extends Dao<TaxonDto> {
  @PersistenceContext(unitName="natuur", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public TaxonDao() {
    super(TaxonDto.class);
  }

  protected EntityManager getEntityManager() {
    return em;
  }

  /**
   * Haal de soorten op.
   * 
   * @return Collection<TaxonDto>
   */
  @SuppressWarnings("unchecked")
  public List<TaxonDto> getKinderen(Long parentId) {
    Query           query = getEntityManager().createNamedQuery("kinderen")
                                              .setParameter("ouder", parentId);

    return query.getResultList();
  }

  /**
   * Geef de mogelijke 'ouders' van de gevraagde rang.
   * 
   * @param kind
   * @return List<DetailDto>
   */
  @SuppressWarnings("unchecked")
  public List<TaxonDto> getOuders(Long kind) {
    Query   query         =
        getEntityManager().createNamedQuery("ouders")
                          .setParameter("kind", kind);

    return query.getResultList();
  }

  /**
   * Haal de soorten op.
   * 
   * @return Collection<TaxonDto>
   */
  @SuppressWarnings("unchecked")
  public List<TaxonDto> getSoorten() {
    Query   query         =
        getEntityManager().createNamedQuery("soort");

    return query.getResultList();
  }
}
