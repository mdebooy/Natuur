/**
 * Copyright 2016 Marco de Booij
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
package eu.debooy.natuur.controller;

import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.components.Message;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.form.Rang;
import eu.debooy.natuur.form.Rangtotaal;
import eu.debooy.natuur.validator.RangValidator;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurRang")
@SessionScoped
public class RangController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(RangController.class);

  private Rang  rang;

  /**
   * Prepareer een nieuw gebied.
   */
  public void create() {
    rang  = new Rang();
    setAktie(PersistenceConstants.CREATE);
    setSubTitel("natuur.titel.rang.create");
    redirect(RANG_REDIRECT);
  }

  /**
   * Verwijder de rang
   * 
   * @param String
   */
  public void delete(String rang) {
    try {
      getRangService().delete(rang);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang);
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }
    addInfo(PersistenceConstants.DELETED, getTekst("biologie.rang." + rang));
  }

  /**
   * Geef de geselecteerde rang.
   * 
   * @return Rang
   */
  public Rang getRang() {
    return rang;
  }

  /**
   * Geef de lijst met rangen.
   * 
   * @return Collection<Rang> met Gebied objecten.
   */
  public Collection<Rang> getRangen() {
    return getRangService().query();
  }

  /**
   * Geef de lijst met totalen voor de rang.
   * 
   * @return Collection<Rangtotaal>
   */
  public Collection<Rangtotaal> getRangtotalen() {
    return getDetailService().getTotalenVoorRang(rang.getRang());
  }

  /**
   * Geef alle rangen als SelectItems.
   * 
   * @return
   */
  public List<SelectItem> getSelectRangen() {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<Rang>         rijen = new TreeSet<Rang>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().query());
    for (Rang rij : rijen) {
      items.add(new SelectItem(rij.getRang(),
                               getTekst("biologie.rang." + rij.getRang())));
    }

    return items;
  }

  /**
   * Zet de Rang die gevraagd is klaar.
   * 
   * @param String rang
   */
  public void retrieve(String rang) {
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(MessageFormat.format(getTekst("natuur.titel.rang.totalen"),
                                     getTekst("biologie.rang."
                                         + this.rang.getRang())));
    redirect(RANG_TOTALEN_REDIRECT);
  }

  /**
   * Persist de Rang
   * 
   * @param Rang
   */
  public void save() {
    List<Message> messages  = RangValidator.valideer(rang);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getRangService().save(rang);
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, rang.getRang());
      return;
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, rang.getRang());
      return;
    } catch (DoosRuntimeException e) {
      LOGGER.error("RT: " + e.getLocalizedMessage(), e);
      generateExceptionMessage(e);
      return;
    }

    redirect(RANGEN_REDIRECT);
  }

  /**
   * Geef rangen 'groter' dan de rang van de ouder als SelectItems.
   * 
   * @param Long niveau
   * @return List<SelectItem>
   */
  public List<SelectItem> selectRangen(Long niveau) {
    List<SelectItem>  items = new LinkedList<SelectItem>();
    Set<Rang>         rijen = new TreeSet<Rang>(new Rang.NiveauComparator());
    rijen.addAll(getRangService().query(niveau));
    LOGGER.debug("#rangen > niveau " + niveau + ": " + rijen.size());
    for (Rang rij : rijen) {
      items.add(new SelectItem(rij.getRang(),
                               getTekst("biologie.rang." + rij.getRang())));
    }

    return items;
  }

  /**
   * Zet de Rang die gewijzigd gaat worden klaar.
   * 
   * @param String rang
   */
  public void update(String rang) {
    this.rang = new Rang(getRangService().rang(rang));
    setAktie(PersistenceConstants.UPDATE);
    setSubTitel("natuur.titel.rang.update");
    redirect(RANG_REDIRECT);
  }
}