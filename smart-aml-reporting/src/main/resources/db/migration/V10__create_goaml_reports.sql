-- V10__create_goaml_reports.sql

CREATE TABLE IF NOT EXISTS goaml_reports (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID NOT NULL,
  customer_id UUID,
  customer_type VARCHAR(50),
  org_id_fiu_id VARCHAR(255),
  transaction_type VARCHAR(255),
  comments TEXT,
  item_make VARCHAR(255),
  item_type VARCHAR(255),
  description TEXT,
  disposed_value NUMERIC(18,2),
  estimated_value NUMERIC(18,2),
  status_comments TEXT,
  currency_code VARCHAR(10),
  created_by UUID,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE goaml_reports ADD CONSTRAINT fk_goaml_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_goaml_tenant ON goaml_reports(tenant_id);
CREATE INDEX IF NOT EXISTS idx_goaml_created ON goaml_reports(created_at DESC);
