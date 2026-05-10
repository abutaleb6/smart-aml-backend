-- V1__create_tenants.sql
-- Enables gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Tenants table
CREATE TABLE IF NOT EXISTS tenants (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  company_name VARCHAR(255) NOT NULL,
  company_email VARCHAR(255) NOT NULL,
  trade_license_no VARCHAR(100),
  license_issue_date DATE,
  license_expiry_date DATE,
  used_screenings INT NOT NULL DEFAULT 0,
  total_screenings_quota INT NOT NULL DEFAULT 100,
  status VARCHAR(50) NOT NULL DEFAULT 'PENDING_APPROVAL',
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX IF NOT EXISTS idx_tenants_created ON tenants(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_tenants_status ON tenants(status);
CREATE INDEX IF NOT EXISTS idx_tenants_email ON tenants(company_email) WHERE company_email IS NOT NULL;
