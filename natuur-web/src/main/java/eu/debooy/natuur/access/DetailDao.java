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
import eu.debooy.natuur.domain.DetailDto;
import eu.debooy.natuur.form.Rangtotaal;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class DetailDao extends Dao<DetailDto> {
  @PersistenceContext(unitName="natuur", type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public DetailDao() {
    super(DetailDto.class);
  }

  protected EntityManager getEntityManager() {
    return em;
  }

  public DetailDto create(DetailDto dto) {
    return null;
  }

  /**
   * Haal de soorten op.
   * 
   * @return Collection<DetailDto>
   */
  @SuppressWarnings("unchecked")
  public Collection<DetailDto> getSoortenMetKlasse() {
    Query   query         =
        getEntityManager().createNamedQuery("soortMetKlasse");

    return query.getResultList();
  }


  /**
   * Haal het aantal soorten per groep op.
   * 
   * @param String
   * @return Collection<Rangtotaal>
   */
  @SuppressWarnings("unchecked")
  public Collection<Rangtotaal> getSoortenMetRang(String rang) {
    Query   query         =
        getEntityManager().createNamedQuery("totalen")
                          .setParameter("groep", rang);
    List<Object[]>  rijen = query.getResultList();
    Set<Rangtotaal> totalen = new HashSet<Rangtotaal>();
    for (Object[] rij : rijen) {
      totalen.add(new Rangtotaal(rij));
    }

    return totalen;
  }

  /**
   * Haal de waargenomen (onder)soorten op.
   * 
   * @return Collection<DetailDto>
   */
  @SuppressWarnings("unchecked")
  public List<DetailDto> getWaargenomen() {
    Query   query         =
        getEntityManager().createNamedQuery("waargenomen");

    return query.getResultList();
  }
}
