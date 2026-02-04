/**
 * Copyright (c) 2015 Marco de Booij
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
import static eu.debooy.natuur.NatuurConstants.NATUUR_EM;
import eu.debooy.natuur.domain.TaxonDto;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class TaxonDao extends Dao<TaxonDto> {
  @PersistenceContext(unitName=NATUUR_EM,
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public TaxonDao() {
    super(TaxonDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public TaxonDto getTaxon(Long taxonId) {
    return getByPrimaryKey(taxonId);
  }

  public List<TaxonDto> getKinderen(Long parentId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonDto.PAR_OUDER, parentId);

    return namedQuery(TaxonDto.QRY_KINDEREN, params);
  }

  public TaxonDto getTaxon(String latijnsenaam) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonDto.PAR_LATIJNSENAAM, latijnsenaam);
    List<TaxonDto> taxon  = namedQuery(TaxonDto.QRY_LATIJNSENAAM, params);
    if (taxon.isEmpty()) {
      return new TaxonDto();
    }

    return taxon.get(0);
  }

  public List<TaxonDto> getOuders(Long kind) {
    Map<String, Object> params  = new HashMap<>();
    params.put(TaxonDto.PAR_KIND, kind);

    return namedQuery(TaxonDto.QRY_OUDERS, params);
  }

  public List<TaxonDto> getSoorten() {
    return namedQuery(TaxonDto.QRY_SOORT);
  }
}
