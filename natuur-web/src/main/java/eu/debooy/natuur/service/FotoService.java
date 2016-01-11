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
import eu.debooy.natuur.access.FotoDao;
import eu.debooy.natuur.domain.FotoDto;
import eu.debooy.natuur.domain.FotoPK;
import eu.debooy.natuur.form.Foto;

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
@Named("natuurFotoService")
@Lock(LockType.WRITE)
public class FotoService {
  private static final  Logger  logger  =
      LoggerFactory.getLogger(FotoService.class);

  @Inject
  private FotoDao     fotoDao;

  private Set<Foto>   fotos;

  /**
   * Initialisatie.
   */
  public FotoService() {
    logger.debug("init FotoService");
  }

  public void delete(Long fotoId) {
    FotoDto foto  = fotoDao.getByPrimaryKey(fotoId);
    fotoDao.delete(foto);
  }

  /**
   * Geef alle Fotos.
   * 
   * @return Een List met Fotos.
   */
  public Collection<Foto> lijst() {
    if (null == fotos) {
      fotos = new HashSet<Foto>();
      Collection<FotoDto> rijen = fotoDao.getAll();
      for (FotoDto rij : rijen) {
        fotos.add(new Foto(rij));
      }
    }

    return fotos;
  }

  /**
   * Maak of wijzig de Foto in de database.
   * 
   * @param foto
   */
  public void save(Foto foto) {
    FotoDto  dto = new FotoDto();
    foto.persist(dto);

    fotoDao.update(dto);

    if (null != fotos) {
      fotos.remove(foto);
      fotos.add(foto);
    }
  }

  /**
   * Geef een Foto.
   * 
   * @return Een Foto.
   */
  public FotoDto foto(Long taxonId, Long taxonSeq) {
    FotoPK  sleutel = new FotoPK(taxonId, taxonSeq);
    FotoDto foto    = fotoDao.getByPrimaryKey(sleutel);
    // TODO Waarom dit statement er moet staan om de Taxon gegevens te zien?
    @SuppressWarnings("unused")
    String  dummy   = foto.getTaxon().toString();

    return foto;
  }

  /**
   * Valideer de Foto.
   */
  public List<Message> valideer(Foto foto) {
    List<Message> fouten  = new ArrayList<Message>();

    return fouten;
  }
}
