-- V8__create_sanctions_entries.sql

CREATE TABLE IF NOT EXISTS sanctions_entries (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  reference_no VARCHAR(200) UNIQUE NOT NULL,
  entry_type VARCHAR(50) NOT NULL,
  primary_name VARCHAR(500),
  aliases TEXT[],
  nationality VARCHAR(255),
  dob DATE,
  dob_raw VARCHAR(255),
  gender VARCHAR(50),
  passport_no VARCHAR(255),
  national_id VARCHAR(255),
  address TEXT,
  country VARCHAR(255),
  list_type VARCHAR(100) DEFAULT 'UN_CONSOLIDATED',
  un_committee VARCHAR(255),
  listed_on DATE,
  last_updated DATE,
  remarks TEXT,
  raw_xml TEXT,
  synced_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Full-text index on primary_name
CREATE INDEX IF NOT EXISTS idx_sanctions_primary_name_fts ON sanctions_entries USING GIN (to_tsvector('english', primary_name));
-- GIN index on aliases
CREATE INDEX IF NOT EXISTS idx_sanctions_aliases ON sanctions_entries USING GIN (aliases);

