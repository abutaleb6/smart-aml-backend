# Smart AML - Auth Module

This module provides authentication: login, refresh tokens, sessions, and password reset flows.

Configuration
- jwt.secret must be set (HS512 key)
- Redis recommended for session and token blacklist
- SMTP configured for password reset

