alter table natuur.taxa rename to taxa_oud;
alter table natuur.taxa_oud rename constraint pk_taxa to pk_taxa_oud;
CREATE TABLE NATUUR.TAXA (
  LATIJNSENAAM                    VARCHAR(255)    NOT NULL,
  NAAM                            VARCHAR(255)    NOT NULL,
  OPMERKING                       VARCHAR(2000),
  PARENT_ID                       INTEGER,
  RANG                            VARCHAR(3)      NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL  DEFAULT NEXTVAL('NATUUR.SEQ_TAXA'::REGCLASS),
  CONSTRAINT PK_TAXA PRIMARY KEY (TAXON_ID)
);
alter table natuur.taxa owner to natuur;
insert into natuur.taxa
select LATIJNSENAAM, NAAM, NULL, PARENT_ID, RANG, TAXON_ID from natuur.taxa_oud;
CREATE OR REPLACE VIEW natuur.foto_overzicht AS 
 SELECT   tax.naam, tax.latijnsenaam, fot.taxon_seq, geb.naam AS gebied
 FROM     natuur.fotos fot
            JOIN natuur.taxa tax ON fot.taxon_id = tax.taxon_id
            JOIN natuur.gebieden geb ON fot.gebied_id = geb.gebied_id
 ORDER BY tax.naam, fot.taxon_seq;
drop view natuur.details;
drop view natuur.taxonomie;
CREATE OR REPLACE VIEW natuur.taxonomie AS
WITH RECURSIVE q AS (
  SELECT h.*::natuur.taxa AS h, 1 AS level, ARRAY[h.taxon_id] AS breadcrumb
  FROM   natuur.taxa h
  WHERE  h.parent_id IS NULL
  UNION ALL
  SELECT hi.*::natuur.taxa AS hi, q_1.level + 1 AS level, q_1.breadcrumb || hi.taxon_id
  FROM   q q_1
           JOIN natuur.taxa hi ON hi.parent_id = (q_1.h).taxon_id)
SELECT   (q.h).taxon_id AS taxon_id, (q.h).parent_id AS parent_id, (q.h).rang AS rang,
         (q.h).naam AS naam, (q.h).latijnsenaam AS latijnsenaam,
         (q.h).opmerking AS opmerking, q.level,
         q.breadcrumb AS path
FROM     q
ORDER BY q.breadcrumb;
CREATE OR REPLACE VIEW natuur.details AS
SELECT   t.parent_id, p.rang AS parent_rang, p.naam AS parent_naam,
         p.latijnsenaam AS parent_latijnsenaam,
         r.niveau, t.taxon_id, t.rang, t.naam, t.latijnsenaam, t.opmerking
FROM     natuur.taxonomie t
           JOIN natuur.taxa p
             ON  p.taxon_id<>t.taxon_id
             AND (p.taxon_id =ANY(t.path))
           JOIN natuur.rangen r
             ON  t.rang=r.rang;
alter table natuur.details   owner to natuur;
alter table natuur.taxonomie owner to natuur;
ALTER TABLE NATUUR.FOTOS drop CONSTRAINT FK_FOT_TAXON_ID;
ALTER TABLE NATUUR.FOTOS
  ADD CONSTRAINT FK_FOT_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
ALTER TABLE NATUUR.TAXA
  ADD CONSTRAINT FK_TAX_RANG FOREIGN KEY (RANG)
  REFERENCES NATUUR.RANGEN (RANG)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.TAXA           TO NATUUR_SEL;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE NATUUR.TAXA           TO NATUUR_UPD;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_UPD;
COMMENT ON TABLE  NATUUR.TAXA                     IS 'Deze tabel bevat alle nodige TAXA (ev. TAXON).';
COMMENT ON COLUMN NATUUR.TAXA.LATIJNSENAAM        IS 'De latijnse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.NAAM                IS 'De nederlandse naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.OPMERKING           IS 'Een opmerking voor deze taxon.';
COMMENT ON COLUMN NATUUR.TAXA.PARENT_ID           IS 'De parent van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.RANG                IS 'De rang van de taxon.';
COMMENT ON COLUMN NATUUR.TAXA.TAXON_ID            IS 'De sleutel van de taxon.';

update natuur.rangen set niveau= nieuw
from (select   niveau, niveau+1 as nieuw
      from     natuur.rangen
      where    niveau>=4
      order by niveau desc) as t1
where  natuur.rangen.niveau=t1.niveau;

insert into natuur.rangen (niveau, rang) values(4, 'ori');
