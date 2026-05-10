-- V4__create_user_sessions.sql

CREATE TABLE IF NOT EXISTS user_sessions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL,
  refresh_token_hash VARCHAR(255) NOT NULL,
  ip_address VARCHAR(100),
  device_type VARCHAR(100),
  os VARCHAR(200),
  browser_name VARCHAR(200),
  browser_version VARCHAR(100),
  user_agent TEXT,
  is_mobile_app BOOLEAN NOT NULL DEFAULT false,
  app_source VARCHAR(50),
  device_id VARCHAR(255),
  is_active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  expires_at TIMESTAMP WITH TIME ZONE
);

ALTER TABLE user_sessions ADD CONSTRAINT fk_sessions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
CREATE INDEX IF NOT EXISTS idx_user_sessions_user ON user_sessions(user_id);
CREATE INDEX IF NOT EXISTS idx_user_sessions_created ON user_sessions(created_at DESC);
