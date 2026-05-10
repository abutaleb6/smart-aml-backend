-- V6__create_customer_details.sql

-- Individual details (one-to-one with customers)
CREATE TABLE IF NOT EXISTS individual_details (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  dob DATE,
  residential_status VARCHAR(100),
  address TEXT,
  city VARCHAR(100),
  state VARCHAR(100),
  country VARCHAR(100),
  nationality VARCHAR(100),
  country_code VARCHAR(10),
  contact_no VARCHAR(50),
  email VARCHAR(255),
  gender VARCHAR(50),
  is_pep BOOLEAN NOT NULL DEFAULT false,
  occupation VARCHAR(255),
  source_of_income VARCHAR(255),
  transaction_purpose VARCHAR(255),
  payment_mode VARCHAR(100),
  product_type VARCHAR(100),
  mode_of_approach VARCHAR(100),
  expected_transactions BIGINT,
  expected_volume NUMERIC(18,2),
  id_type VARCHAR(100),
  id_no VARCHAR(200),
  id_issued_by VARCHAR(255),
  id_issued_at DATE,
  id_expiry_date DATE,
  place_of_birth VARCHAR(255),
  country_of_residence VARCHAR(100),
  has_dual_nationality BOOLEAN NOT NULL DEFAULT false,
  has_adverse_event BOOLEAN NOT NULL DEFAULT false,
  remarks TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE individual_details ADD CONSTRAINT fk_individual_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_individual_details_customer ON individual_details(customer_id);
CREATE INDEX IF NOT EXISTS idx_individual_details_email ON individual_details(email) WHERE email IS NOT NULL;

-- Corporate details
CREATE TABLE IF NOT EXISTS corporate_details (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  company_name VARCHAR(255),
  company_address TEXT,
  state VARCHAR(100),
  city VARCHAR(100),
  country_of_incorporation VARCHAR(100),
  po_box_no VARCHAR(50),
  customer_type_detail VARCHAR(255),
  office_country_code VARCHAR(10),
  office_contact_no VARCHAR(50),
  mobile_country_code VARCHAR(10),
  mobile_contact_no VARCHAR(50),
  email VARCHAR(255),
  trade_license_cr_no VARCHAR(255),
  trade_license_issued_at DATE,
  issued_by VARCHAR(255),
  issued_date DATE,
  expiry_date DATE,
  vat_registration_no VARCHAR(255),
  tenancy_contract_expiry DATE,
  entity_legal_status VARCHAR(255),
  countries_of_operation TEXT[],
  business_activity VARCHAR(500),
  deals_import_export BOOLEAN NOT NULL DEFAULT false,
  has_sister_concern BOOLEAN NOT NULL DEFAULT false,
  account_holding_bank VARCHAR(255),
  purpose_of_relationship VARCHAR(500),
  product_type VARCHAR(100),
  product_source VARCHAR(100),
  payment_mode VARCHAR(100),
  delivery_channel VARCHAR(100),
  expected_transactions BIGINT,
  expected_volume NUMERIC(18,2),
  deals_dual_use_goods BOOLEAN NOT NULL DEFAULT false,
  remarks TEXT,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE corporate_details ADD CONSTRAINT fk_corporate_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_corporate_customer ON corporate_details(customer_id);
CREATE INDEX IF NOT EXISTS idx_corporate_email ON corporate_details(email) WHERE email IS NOT NULL;

-- AML Questionnaire
CREATE TABLE IF NOT EXISTS aml_questionnaire (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  goaml_registered BOOLEAN NOT NULL DEFAULT false,
  kyc_docs_collected BOOLEAN NOT NULL DEFAULT false,
  other_answers JSONB,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE aml_questionnaire ADD CONSTRAINT fk_aml_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_aml_customer ON aml_questionnaire(customer_id);

-- UBO Representatives
CREATE TABLE IF NOT EXISTS ubo_representative (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  rep_type VARCHAR(100),
  name VARCHAR(255),
  is_pep BOOLEAN NOT NULL DEFAULT false,
  nationality VARCHAR(100),
  id_type VARCHAR(100),
  id_no VARCHAR(200),
  id_issue_date DATE,
  id_expiry_date DATE,
  date_of_birth DATE,
  role VARCHAR(255),
  percentage_of_share NUMERIC(5,2),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE ubo_representative ADD CONSTRAINT fk_ubo_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_ubo_customer ON ubo_representative(customer_id);

-- Customer Documents
CREATE TABLE IF NOT EXISTS customer_documents (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  customer_id UUID NOT NULL,
  file_name VARCHAR(255),
  file_path VARCHAR(1000),
  file_size BIGINT,
  file_type VARCHAR(100),
  document_type VARCHAR(50),
  uploaded_by UUID,
  stored_on_s3 BOOLEAN NOT NULL DEFAULT false,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
ALTER TABLE customer_documents ADD CONSTRAINT fk_docs_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_customer_documents_customer ON customer_documents(customer_id);
