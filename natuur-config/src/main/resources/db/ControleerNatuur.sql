-- Controleer het Natuur schema.
-- 
-- Copyright 2020 Marco de Booij
--
-- Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
-- the European Commission - subsequent versions of the EUPL (the "Licence");
-- you may not use this work except in compliance with the Licence. You may
-- obtain a copy of the Licence at:
--
-- http://www.osor.eu/eupl
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
-- WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the Licence for the specific language governing permissions and
-- limitations under the Licence.
--
-- Project: Natuur
-- Author: Marco de Booij

-- Controleer de hiërarchische structuur van de taxa.
select   tax.latijnsenaam, tax.rang, par.latijnsenaam, par.rang
from     natuur.taxa tax join natuur.rangen rng on tax.rang     =rng.rang
                         join natuur.taxa par   on tax.parent_id=par.taxon_id,
         natuur.rangen rn1                                                  
where    par.rang   =rn1.rang
and      rng.niveau<=rn1.niveau;

select   t.latijnsenaam, n.naam, t.rang, count(n1.taal)
from     natuur.taxa t join natuur.taxonnamen n on t.taxon_id=n.taxon_id
                       join natuur.taxonnamen n1 on t.taxon_id=n1.taxon_id
where    n.taal='nl'
group by t.latijnsenaam, n.naam, t.rang
--having   count(n.taal)=30
order by t.latijnsenaam, n.naam, t.rang;

-- Dubbele Taxa?
with dubbel as (
  select   naam, taal, count(taxon_id)
  from     natuur.taxonnamen
  group by naam, taal
  having   count(taxon_id)>1)
select   tax.*, tnm.taal, tnm.naam
from     natuur.taxonnamen tnm join natuur.taxa tax on tnm.taxon_id=tax.taxon_id
                               join dubbel dub on tnm.naam=dub.naam and tnm.taal=dub.taal
order by tnm.naam, tax.latijnsenaam;
