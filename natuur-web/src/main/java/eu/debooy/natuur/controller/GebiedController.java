/**
 * Copyright (c) 2016 Marco de Booij
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

import eu.debooy.doosutils.ComponentsConstants;
import eu.debooy.doosutils.DoosUtils;
import eu.debooy.doosutils.PersistenceConstants;
import eu.debooy.doosutils.errorhandling.exception.DuplicateObjectException;
import eu.debooy.doosutils.errorhandling.exception.ObjectNotFoundException;
import eu.debooy.doosutils.errorhandling.exception.base.DoosRuntimeException;
import eu.debooy.natuur.Natuur;
import eu.debooy.natuur.domain.GebiedDto;
import eu.debooy.natuur.form.Gebied;
import eu.debooy.natuur.validator.GebiedValidator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Marco de Booij
 */
@Named("natuurGebied")
@SessionScoped
public class GebiedController extends Natuur {
  private static final  long    serialVersionUID  = 1L;
  private static final  Logger  LOGGER            =
      LoggerFactory.getLogger(GebiedController.class);

  private static final  String  TIT_CREATE    = "natuur.titel.gebied.create";
  private static final  String  TIT_RETRIEVE  = "natuur.titel.gebied.retrieve";
  private static final  String  TIT_UPDATE    = "natuur.titel.gebied.update";

  private Gebied  gebied;

  public void create() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    gebied  = new Gebied();
    gebied.setLandId(Long.valueOf(getParameter("natuur.default.landid")));
    setAktie(PersistenceConstants.CREATE);
    setSubTitel(getTekst(TIT_CREATE));
    redirect(GEBIED_REDIRECT);
  }

  public void delete() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    try {
      getGebiedService().delete(gebied.getGebiedId());
      addInfo(PersistenceConstants.DELETED, gebied.getNaam());
      gebied  = new Gebied();
      redirect(GEBIEDEN_REDIRECT);
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, gebied.getNaam());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public Gebied getGebied() {
    return gebied;
  }

  public Collection<SelectItem> getSelectGebieden() {
    List<SelectItem>  items = new LinkedList<>();

    getGebiedService().query()
                      .forEach(rij ->
      items.add(new SelectItem(rij, rij.getNaam())));

    return items;
  }

  public Collection<SelectItem> getSelectGebiedenId() {
    List<SelectItem>  items = new LinkedList<>();

    getGebiedService().query()
                      .forEach(rij ->
      items.add(new SelectItem(rij.getGebiedId(), rij.getNaam())));

    return items;
  }

  public Collection<SelectItem> getLatitudes() {
    List<SelectItem>  items = new LinkedList<>();

    items.add(new SelectItem("", ""));
    items.add(new SelectItem("N", getTekst("windstreek.N")));
    items.add(new SelectItem("S", getTekst("windstreek.S")));

    return items;
  }

  public Collection<SelectItem> getLongitudes() {
    List<SelectItem>  items = new LinkedList<>();

    items.add(new SelectItem("", ""));
    items.add(new SelectItem("E", getTekst("windstreek.E")));
    items.add(new SelectItem("W", getTekst("windstreek.W")));

    return items;
  }

  public String getWindstreek(String windstreek) {
    if (DoosUtils.isBlankOrNull(windstreek)) {
      return "";
    }

    return DoosUtils.nullToValue(getTekst("windstreek."+windstreek), "");
  }

  public void retrieve() {
    if (!isUser() && !isView()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var ec        = FacesContext.getCurrentInstance().getExternalContext();

    if (!ec.getRequestParameterMap().containsKey(GebiedDto.COL_GEBIEDID)) {
      addError(ComponentsConstants.GEENPARAMETER, GebiedDto.COL_GEBIEDID);
      return;
    }

    var gebiedId  = Long.valueOf(ec.getRequestParameterMap()
                                   .get(GebiedDto.COL_GEBIEDID));

    gebied  = new Gebied(getGebiedService().gebied(gebiedId));
    setAktie(PersistenceConstants.RETRIEVE);
    setSubTitel(getTekst(TIT_RETRIEVE));
    redirect(GEBIED_REDIRECT);
  }

  public void save() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    var messages  = GebiedValidator.valideer(gebied);
    if (!messages.isEmpty()) {
      addMessage(messages);
      return;
    }

    try {
      getGebiedService().save(gebied);
      switch (getAktie().getAktie()) {
        case PersistenceConstants.CREATE:
          addInfo(PersistenceConstants.CREATED, gebied.getNaam());
          update();
          break;
        case PersistenceConstants.UPDATE:
          addInfo(PersistenceConstants.UPDATED, gebied.getNaam());
          break;
        default:
          addError(ComponentsConstants.WRONGREDIRECT, getAktie().getAktie());
          break;
      }
    } catch (DuplicateObjectException e) {
      addError(PersistenceConstants.DUPLICATE, gebied.getNaam());
    } catch (ObjectNotFoundException e) {
      addError(PersistenceConstants.NOTFOUND, gebied.getNaam());
    } catch (DoosRuntimeException e) {
      LOGGER.error(String.format(ComponentsConstants.ERR_RUNTIME,
                                 e.getLocalizedMessage()), e);
      generateExceptionMessage(e);
    }
  }

  public void update() {
    if (!isUser()) {
      addError(ComponentsConstants.GEENRECHTEN);
      return;
    }

    setAktie(PersistenceConstants.UPDATE);
    setSubTitel(getTekst(TIT_UPDATE));
  }
}
