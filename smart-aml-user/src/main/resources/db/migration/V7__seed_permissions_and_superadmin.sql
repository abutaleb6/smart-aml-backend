-- V7__seed_permissions_and_superadmin.sql

-- Insert 27 permissions
INSERT INTO permissions (id, module_key, menu_label, action, description, created_at) VALUES
  (gen_random_uuid(), 'user_management', 'User Management', 'VIEW', 'View users', NOW()),
  (gen_random_uuid(), 'user_management', 'User Management', 'CREATE', 'Create users', NOW()),
  (gen_random_uuid(), 'user_management', 'User Management', 'EDIT', 'Edit users', NOW()),
  (gen_random_uuid(), 'user_management', 'User Management', 'DELETE', 'Delete users', NOW()),
  (gen_random_uuid(), 'role_management', 'Role Management', 'CREATE', 'Create roles', NOW()),
  (gen_random_uuid(), 'role_management', 'Role Management', 'EDIT', 'Edit roles', NOW()),
  (gen_random_uuid(), 'role_management', 'Role Management', 'DELETE', 'Delete roles', NOW()),
  (gen_random_uuid(), 'permission_management', 'Permission Management', 'VIEW', 'View permissions', NOW()),
  (gen_random_uuid(), 'permission_management', 'Permission Management', 'EDIT', 'Edit permissions', NOW()),
  (gen_random_uuid(), 'customer_management', 'Customer Management', 'VIEW', 'View customers', NOW()),
  (gen_random_uuid(), 'customer_management', 'Customer Management', 'CREATE', 'Create customers', NOW()),
  (gen_random_uuid(), 'customer_management', 'Customer Management', 'EDIT', 'Edit customers', NOW()),
  (gen_random_uuid(), 'customer_management', 'Customer Management', 'DELETE', 'Delete customers', NOW()),
  (gen_random_uuid(), 'screening', 'Screening', 'RUN', 'Run individual screening', NOW()),
  (gen_random_uuid(), 'screening', 'Screening', 'BATCH', 'Run batch screening', NOW()),
  (gen_random_uuid(), 'screening', 'Screening', 'ANNOTATE', 'Annotate candidates', NOW()),
  (gen_random_uuid(), 'reporting', 'Reporting', 'VIEW', 'View reports', NOW()),
  (gen_random_uuid(), 'reporting', 'Reporting', 'EXPORT', 'Export reports', NOW()),
  (gen_random_uuid(), 'audit_trail', 'Audit Trail', 'VIEW', 'View audit trail', NOW()),
  (gen_random_uuid(), 'support', 'Support', 'CREATE', 'Create tickets', NOW()),
  (gen_random_uuid(), 'support', 'Support', 'VIEW', 'View tickets', NOW()),
  (gen_random_uuid(), 'support', 'Support', 'MANAGE', 'Manage tickets', NOW()),
  (gen_random_uuid(), 'analytics', 'Analytics', 'VIEW', 'View analytics dashboard', NOW()),
  (gen_random_uuid(), 'tenant_management', 'Tenant Management', 'APPROVE', 'Approve tenant', NOW()),
  (gen_random_uuid(), 'tenant_management', 'Tenant Management', 'SUSPEND', 'Suspend tenant', NOW()),
  (gen_random_uuid(), 'system', 'System', 'SUPER_ADMIN', 'Full access', NOW());

-- Create Super Admin tenant and user
INSERT INTO tenants (id, company_name, company_email, used_screenings, total_screenings_quota, status, created_at)
VALUES (
  '00000000-0000-0000-0000-000000000001',
  'Smart AML - Platform',
  'superadmin@smartaml.com',
  0,
  0,
  'ACTIVE',
  NOW()
);

-- Create Super Admin role in the super admin tenant
INSERT INTO roles (id, tenant_id, name, description, is_system_role, created_at)
VALUES (
  gen_random_uuid(),
  '00000000-0000-0000-0000-000000000001',
  'SUPER_ADMIN',
  'Platform super administrator',
  true,
  NOW()
);

-- Insert Super Admin user
INSERT INTO users (id, tenant_id, full_name, email, password_hash, status, is_super_admin, email_verified, created_at)
VALUES (
  gen_random_uuid(),
  '00000000-0000-0000-0000-000000000001',
  'Super Admin',
  'superadmin@smartaml.com',
  '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQyCgFPH3n7N5qxkH5YD7gJbK',
  'ACTIVE',
  true,
  true,
  NOW()
);

-- Assign all permissions to SUPER_ADMIN role
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r CROSS JOIN permissions p WHERE r.name = 'SUPER_ADMIN';

-- Link user to SUPER_ADMIN role
INSERT INTO user_roles (user_id, role_id, assigned_at)
SELECT u.id, r.id, NOW() FROM users u, roles r WHERE u.email = 'superadmin@smartaml.com' AND r.name = 'SUPER_ADMIN';
