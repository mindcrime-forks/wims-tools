-- Table: ontology_preferred

-- DROP TABLE ontology_preferred;

CREATE TABLE ontology_preferred
(
  concept text NOT NULL,
  parent text,
  definition text,
  gloss text,
  CONSTRAINT ontology_preferred_pk_concept PRIMARY KEY (concept)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ontology_preferred
  OWNER TO jesse;

-- Index: ontology_preferred_index_concept

-- DROP INDEX ontology_preferred_index_concept;

CREATE INDEX ontology_preferred_index_concept
  ON ontology_preferred
  USING btree
  (concept COLLATE pg_catalog."default");



-- Table: ontology_preferred_mapping

-- DROP TABLE ontology_preferred_mapping;

CREATE TABLE ontology_preferred_mapping
(
  sense text NOT NULL,
  concept text,
  score double precision,
  CONSTRAINT ontology_preferred_mapping_pk_sense PRIMARY KEY (sense)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ontology_preferred_mapping
  OWNER TO jesse;

