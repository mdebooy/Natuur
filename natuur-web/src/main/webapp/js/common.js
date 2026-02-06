/*
 * Copyright (c) 2022 Marco de Booij
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by
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

const beschrijvingtypes = {};
const landen = {};
const rangen = {};
const regios = {};
const statussen = {};
const windstreken = {};

function getBeschrijvingtype(beschrijvingtype, taal) {
  const beschrijving = getBeschrijving(beschrijvingtype);

  const naam = beschrijving.teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return beschrijvingtype;
  }

  return beschrijving.teksten[naam].tekst;
}

function getBeschrijvingseq(beschrijvingtype) {
  return getBeschrijving(beschrijvingtype).volgorde;
}

function getBeschrijving(beschrijvingtype) {
  const beschrijving = {};
  if (beschrijvingtypes.hasOwnProperty(beschrijvingtype)) {
    beschrijving = beschrijvingtypes[beschrijvingtype];
  } else {
    $.ajax({ url: '/doos/i18nLijsten/natuur.taxon.beschrijving.type/'+beschrijvingtype,
             dataType: 'json',
             async: false,
             success:  function(data) {
               beschrijvingtypes[beschrijvingtype] = data;
               beschrijving = data;
             }
    });
  }

  return beschrijving;
}

function getCoordinaten(gebied) {
  var coordinaten = '';
  if (gebied.hasOwnProperty('latitude')) {
    coordinaten += gebied.latitude;
  }
  if (gebied.hasOwnProperty('latitudeGraden')) {
    coordinaten += ' '
            + String(gebied.latitudeGraden).padStart(2, '0') + ' '
            + String(gebied.latitudeMinuten).padStart(2, '0') + ' '
            + String(gebied.latitudeSeconden.toFixed(3)).padStart(7, '0');
  }
  if (gebied.hasOwnProperty('longitude')) {
    coordinaten += ' - ' + gebied.longitude;
  }
  if (gebied.hasOwnProperty('longitudeGraden')) {
    coordinaten += ' '
            + String(gebied.longitudeGraden).padStart(3, '0') + ' '
            + String(gebied.longitudeMinuten).padStart(2, '0') + ' '
            + String(gebied.longitudeSeconden.toFixed(3)).padStart(7, '0');
  }

  return coordinaten;
}

function getGebied(gebiedId) {
  let gebied = {};
  $.ajax({ url: '/natuur/gebieden/'+gebiedId,
           dataType: 'json',
           async: false,
           success:  function(data) {
             gebied = data;
           }
  });

  return gebied;
}

function getLandnaam(landId, taal) {
  var landnamen = [];
  if (landen.hasOwnProperty(landId)) {
    landnamen = landen[landId].landnamen;
  } else {
    $.ajax({ url: '/sedes/landen/'+landId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               landen[landId] = data;
               landnamen = data.landnamen;
             }
    });
  }

  var naam = landnamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return landId+taal;
  }

  return landnamen[naam].naam;
}

function getLatijnsenaam(latijnsenaam, uitgestorven=false) {
  if (uitgestorven) {
    return latijnsenaam + ' †';
  }
  return latijnsenaam;
}

function getRangnaam(rang, taal) {
  var rangnamen = [];
  if (rangen.hasOwnProperty(rang)) {
    rangnamen = rangen[rang].rangnamen;
  } else {
    $.ajax({ url: '/natuur/rangen/'+rang,
             dataType: 'json',
             async: false,
             success:  function(data) {
               rangen[rang] = data;
               rangnamen = data.rangnamen;
             }
    });
  }

  return getRangNaam(rangnamen, rang, taal);
}

function getRangNaam(rangnamen, rang, taal) {
  var naam = rangnamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return rang;
  }

  return rangnamen[naam].naam;
}

function getRegionaam(regioId, taal) {
  var regionamen = [];
  if (regios.hasOwnProperty(regioId)) {
    regionamen = regios[regioId].regionamen;
  } else {
    $.ajax({ url: '/sedes/regios/'+regioId,
             dataType: 'json',
             async: false,
             success:  function(data) {
               regios[regioId] = data;
               regionamen = data.regionamen;
             }
    });
  }

  var naam = regionamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return regioId;
  }

  return regionamen[naam].naam;
}

function getStatus(status, taal) {
  var teksten = [];
  if (statussen.hasOwnProperty(status)) {
    teksten = statussen[status].teksten;
  } else {
    $.ajax({ url: '/doos/i18nCodes/natuur.taxon.status.'+status,
             dataType: 'json',
             async: false,
             success:  function(data) {
               statussen[status] = data;
               teksten = data.teksten;
             }
    });
  }

  var naam = teksten.findIndex(i => i.taalKode === taal);
  if (naam < 0) {
    return status;
  }

  return teksten[naam].tekst;
}

function getTaxonnaam(taxon, taal) {
  var naam = taxon.taxonnamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return taxon.latijnsenaam;
  }

  return(taxon.taxonnamen[naam].naam);
}

function taxonnaam(taxonnamen, taal, defaultnaam='') {
  var naam = taxonnamen.findIndex(i => i.taal === taal);
  if (naam < 0) {
    return defaultnaam;
  }

  return(taxonnamen[naam].naam);
}
