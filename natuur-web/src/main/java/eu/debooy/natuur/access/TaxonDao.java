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
import eu.debooy.natuur.domain.TaxonDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
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
  public List<TaxonDto> getKinderen(Long parentId) {
    Map<String, Object> params  = new HashMap<String, Object>();
    params.put("ouder", parentId);

    return namedQuery("kinderen", params);
  }

  /**
   * Geef de mogelijke 'ouders' van de gevraagde rang.
   * 
   * @param kind
   * @return List<DetailDto>
   */
  public List<TaxonDto> getOuders(Long kind) {
    Map<String, Object> params  = new HashMap<String, Object>();
    params.put("kind", kind);

    return namedQuery("ouders", params);
  }

  /**
   * Haal de soorten op.
   * 
   * @return Collection<TaxonDto>
   */
  public List<TaxonDto> getSoorten() {
    return namedQuery("soort");
  }
}
