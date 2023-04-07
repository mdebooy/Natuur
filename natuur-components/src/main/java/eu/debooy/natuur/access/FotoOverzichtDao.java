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
import eu.debooy.natuur.domain.FotoOverzichtDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;


/**
 * @author Marco de Booij
 */
@Interceptors({PersistenceExceptionHandlerInterceptor.class})
public class FotoOverzichtDao extends Dao<FotoOverzichtDto> {
  @PersistenceContext(unitName="natuur",
                      type=PersistenceContextType.TRANSACTION)
  private EntityManager em;

  public FotoOverzichtDao() {
    super(FotoOverzichtDto.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return em;
  }

  public List<FotoOverzichtDto> getPerGebied(Long gebiedId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(FotoOverzichtDto.PAR_GEBIEDID, gebiedId);

    return namedQuery(FotoOverzichtDto.QRY_PERGEBIED, params);
  }

  public List<FotoOverzichtDto> getPerTaxon(Long taxonId) {
    Map<String, Object> params  = new HashMap<>();
    params.put(FotoOverzichtDto.PAR_TAXONID, taxonId);

    return namedQuery(FotoOverzichtDto.QRY_PERTAXON, params);
  }

  public List<FotoOverzichtDto> getPerRang(String rang) {
    Map<String, Object> params  = new HashMap<>();
    params.put(FotoOverzichtDto.PAR_RANG, rang);

    return namedQuery(FotoOverzichtDto.QRY_PERRANG, params);
  }

  public FotoOverzichtDto getTaxonSeq(Long taxonId, Long taxonSeq) {
    Map<String, Object> params  = new HashMap<>();
    params.put(FotoOverzichtDto.PAR_TAXONID, taxonId);
    params.put(FotoOverzichtDto.PAR_TAXONSEQ, taxonSeq);

    List<FotoOverzichtDto>  fotos = namedQuery(FotoOverzichtDto.QRY_TAXONSEQ,
                                               params);

    if (fotos.isEmpty()) {
      return new FotoOverzichtDto();
    }

    return fotos.get(0);
  }
}
