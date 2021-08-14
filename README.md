Natuur
======

Dit project is bedoeld voor het bijhouden van waarnemingen in de natuur. Via de taxonomie kan iedere soort worden opgenomen in een waarnemingslijst. Verder kan er worden bijgehouden waar de foto's van ieder soort zijn genomen. Het programma maakt gebruik van de projecten `DOOS` en `Sedes`.

Het project bestaat uit 2 modules.

natuur-config
-------------

Deze produceert een jar met hierin alle bestanden die nodig zijn om met de applicatie te kunnen werken:
* `resources_XX.properties` bevat de teksten in taal `XX`.
* `parameters.properties` bevat de parameters voor de applicatie.
* `reports` directory bevat de rapporten voor de applicatie.
* `db` directory bevat de database scripts. Er is een sub-directory per database type. Scripts met de naam `XXXX-patch.sql` moeten enkel worden uitgevoerd als er wordt overgegaan naar een nieuwere versie. Sla geen versie over.

natuur-web
----------

Dit is de eigenlijke applicatie. Zet het war-bestand in de `webapps` directory van Tomee. De eerste maal dat de applicatie gebruikt wordt zijn er geen teksten of parameters aanwezig. Laad ze in via de juiste menu opties. Van zodra ze geladen zijn worden ze in de applicatie gebruikt.

Versies
-------

2.1.x
_____

Deze versie is een tussenversie die nodig is om naar de nieuwe databasestructuur over te gaan. Het geeft de mogelijkheid om elke `foto` aan de juiste `waarneming` te koppelen. Sla deze versie dus niet over om dataverlies te voorkomen.

<hr />

This project is meant to keep track of all species that were spotted. Through taxonomy every specie can be put in an observation list. The application uses the projects `DOOS` and `Sedes`.

The project consists of 2 modules.

natuur-config
-------------

This produces a jar that includes all files that are needed to work with the application:
* `resources_XX.properties` contains the texts in language `XX`;
* `parameters.properties` contains the parameters for the application;
* `reports` directory contains the reports for the application;
* `db` directory contains the database scripts. There is a sub-directory per database type. Scripts the name `XXXX-patch.sql` should only be executed when it is for an upgrade from one version to another. Do not skip a version.

natuur-web
----------

This is the application. Put the war-file in the `webapps` directory of Tomee. The first time that you use the application there will be no texts or parameters available. Load them through the right menu options. As soon as they are loaded they will be used by the application.

Versions
--------

2.1.x
_____

This version is an intermediate version that is necessary to move to the new database structure. It gives you the possibility to connect every `foto` (picture) to the right `waarneming` (observation). Do not skip this version to prevent data loss.

