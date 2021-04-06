/*
 * Copyright (c) 2021 Marco de Booij
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

/**
 * Author:  Marco de Booij
 */

update natuur.fotos f
set    waarneming_id = w.waarneming_id,
       datum = w.datum
from   natuur.waarnemingen w join (
  select   w1.taxon_id, w1.gebied_id , count(*)
  from     natuur.waarnemingen w1
  group by w1.taxon_id, w1.gebied_id
  having   count(*) = 1) w2 on w.gebied_id = w2.gebied_id and w.taxon_id = w2.taxon_id
where  w.taxon_id  = f.taxon_id
and    w.gebied_id = f.gebied_id
and    f.waarneming_id is null;

update natuur.fotos f
set    waarneming_id = w.waarneming_id
from   natuur.waarnemingen w
where  f.waarneming_id is null
and    w.datum     = f.datum
and    w.gebied_id = f.gebied_id
and    w.taxon_id  = f.taxon_id;

select   *
from     natuur.fotos f
where    f.waarneming_id is null;

select   f.*
from     natuur.waarnemingen w join natuur.fotos f on w.waarneming_id = f.waarneming_id
where    w.datum != f.datum ;