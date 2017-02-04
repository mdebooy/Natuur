\prompt 'Database  : ' db_naam

-- Connect als NATUUR om de objecten te maken
\c :db_naam natuur

CREATE TABLE NATUUR.TAXONNAMEN (
  TAAL                            CHARACTER(2)    NOT NULL,
  NAAM                            VARCHAR(255)    NOT NULL,
  TAXON_ID                        INTEGER         NOT NULL,
  CONSTRAINT PK_TAXONNAMEN PRIMARY KEY (TAXON_ID, TAAL)
);

ALTER TABLE NATUUR.TAXONNAMEN
  ADD CONSTRAINT FK_TNM_TAXON_ID FOREIGN KEY (TAXON_ID)
  REFERENCES NATUUR.TAXA (TAXON_ID)
  ON DELETE CASCADE
  ON UPDATE RESTRICT;

INSERT INTO NATUUR.TAXONNAMEN (NAAM, TAAL, TAXON_ID)
SELECT NAAM, 'nl', TAXON_ID
FROM   NATUUR.TAXA
WHERE  NAAM != LATIJNSENAAM;

ALTER TABLE NATUUR.TAXA
DROP COLUMN NAAM;

DROP VIEW natuur.foto_overzicht;
DROP VIEW natuur.details;
DROP VIEW natuur.taxonomie;

CREATE OR REPLACE VIEW natuur.taxonomie AS
WITH RECURSIVE q AS (
  SELECT h.*::natuur.taxa AS h, 1 AS level, ARRAY[h.taxon_id] AS breadcrumb
  FROM   natuur.taxa h
  WHERE  h.parent_id IS NULL
  UNION ALL
  SELECT hi.*::natuur.taxa AS hi, q_1.level + 1 AS level, q_1.breadcrumb || hi.taxon_id
  FROM   q q_1
           JOIN natuur.taxa hi ON hi.parent_id = (q_1.h).taxon_id)
SELECT   (q.h).taxon_id AS taxon_id, (q.h).parent_id AS parent_id,
         (q.h).rang AS rang, (q.h).latijnsenaam AS latijnsenaam,
         (q.h).opmerking AS opmerking, q.level,
         q.breadcrumb AS path
FROM     q
ORDER BY q.breadcrumb;

CREATE OR REPLACE VIEW natuur.details AS
SELECT   p.taxon_id AS parent_id, p.rang AS parent_rang,
         p.latijnsenaam AS parent_latijnsenaam,
         r.niveau, t.taxon_id, t.rang, t.latijnsenaam, t.opmerking,
         CASE WHEN f.aantal IS NULL THEN 0 ELSE 1 END op_foto
FROM     natuur.taxonomie t
           JOIN natuur.taxa p
             ON  p.taxon_id<>t.taxon_id
             AND (p.taxon_id =ANY(t.path))
           JOIN natuur.rangen r
             ON  t.rang=r.rang
           LEFT JOIN (SELECT   taxon_id, COUNT(*) aantal
                      FROM     natuur.fotos
                      GROUP BY taxon_id) f
             ON t.taxon_id=f.taxon_id;

CREATE OR REPLACE VIEW natuur.foto_overzicht AS 
 SELECT   fot.foto_id, det.parent_id AS klasse_id,
          det.parent_latijnsenaam AS klasse_latijnsenaam,
          det.taxon_id, det.latijnsenaam,
          fot.taxon_seq, geb.land_id, geb.naam AS gebied
 FROM     natuur.fotos fot
            JOIN natuur.details det ON fot.taxon_id = det.taxon_id
            JOIN natuur.gebieden geb ON fot.gebied_id = geb.gebied_id
 WHERE    det.parent_rang='kl'
 AND      det.rang in ('so', 'oso');

GRANT SELECT                         ON TABLE NATUUR.DETAILS        TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.FOTO_OVERZICHT TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONNAMEN     TO NATUUR_SEL;
GRANT SELECT                         ON TABLE NATUUR.TAXONOMIE      TO NATUUR_SEL;

GRANT SELECT                         ON TABLE    NATUUR.DETAILS         TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.FOTO_OVERZICHT  TO NATUUR_UPD;
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLE    NATUUR.TAXONNAMEN      TO NATUUR_UPD;
GRANT SELECT                         ON TABLE    NATUUR.TAXONOMIE       TO NATUUR_UPD;

COMMENT ON TABLE  NATUUR.TAXONNAMEN               IS 'Deze tabel bevat de namen van de TAXA in verschillende talen.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.NAAM          IS 'De naam van de taxon.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAAL          IS 'De taal.';
COMMENT ON COLUMN NATUUR.TAXONNAMEN.TAXON_ID      IS 'De sleutel van de taxon.';
