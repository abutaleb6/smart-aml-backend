-- V9__create_screening_logs.sql

CREATE TABLE IF NOT EXISTS screening_logs (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID NOT NULL,
  user_id UUID,
  customer_id UUID,
  screening_type VARCHAR(50),
  search_name VARCHAR(500),
  dob DATE,
  gender VARCHAR(50),
  nationality VARCHAR(255),
  country VARCHAR(255),
  confidence_rating INT,
  sources_checked INT,
  candidates_reviewed INT,
  matches_found INT,
  hit_rate NUMERIC(5,4),
  result VARCHAR(50),
  raw_response JSONB,
  report_path VARCHAR(1000),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

ALTER TABLE screening_logs ADD CONSTRAINT fk_screening_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_screening_tenant ON screening_logs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_screening_result ON screening_logs(result);
CREATE INDEX IF NOT EXISTS idx_screening_created ON screening_logs(created_at DESC);

-- Screening candidates
CREATE TABLE IF NOT EXISTS screening_candidates (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  screening_log_id UUID NOT NULL,
  candidate_name VARCHAR(500),
  candidate_data JSONB,
  annotation VARCHAR(50),
  result_type VARCHAR(100),
  annotation_note TEXT,
  annotated_by UUID,
  annotated_at TIMESTAMP WITH TIME ZONE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE screening_candidates ADD CONSTRAINT fk_candidate_log FOREIGN KEY (screening_log_id) REFERENCES screening_logs(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_candidates_log ON screening_candidates(screening_log_id);
CREATE INDEX IF NOT EXISTS idx_candidates_created ON screening_candidates(created_at DESC);
