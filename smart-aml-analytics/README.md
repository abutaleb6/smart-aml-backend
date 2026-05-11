# Smart AML - Analytics Module

This module collects aggregated metrics for tenants and screening operations.

Behavior summary
- Listens to ScreeningCompletedEvent and aggregates daily metrics into analytics_metrics table.
- Exposes simple REST endpoints to fetch tenant summaries and daily screening metrics.

Notes
- This is intentionally lightweight; later iterations can add caching, Prometheus exporters, or pre-aggregated OLAP tables.
