-- V11__create_audit_trail.sql

CREATE TABLE IF NOT EXISTS audit_trail (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID,
  tenant_id UUID,
  action VARCHAR(150),
  module VARCHAR(150),
  entity_type VARCHAR(150),
  entity_id VARCHAR(255),
  old_value JSONB,
  new_value JSONB,
  ip_address VARCHAR(100),
  device_type VARCHAR(100),
  browser_name VARCHAR(200),
  user_agent TEXT,
  app_source VARCHAR(50),
  status VARCHAR(50) DEFAULT 'SUCCESS',
  error_message TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_audit_created ON audit_trail(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_audit_action ON audit_trail(action);
CREATE INDEX IF NOT EXISTS idx_audit_user ON audit_trail(user_id);
