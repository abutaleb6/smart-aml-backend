package com.smartaml.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import com.smartaml.shared.context.TenantContext;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

@Component
public class TenantFilterInterceptor implements HandlerInterceptor {

    @Autowired
    private EntityManagerFactory emf;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID tenantId = null;
        Object attr = request.getAttribute("tenantId");
        if (attr instanceof UUID) {
            tenantId = (UUID) attr;
        } else {
            String header = request.getHeader("X-Tenant-Id");
            if (header != null && !header.isBlank()) {
                try {
                    tenantId = UUID.fromString(header);
                } catch (Exception ignored) {
                }
            }
        }

        if (tenantId != null) {
            TenantContext.set(tenantId);
            // Enable Hibernate filter
            EntityManager em = emf.createEntityManager();
            Session session = em.unwrap(Session.class);
            if (!session.getEnabledFilters().contains("tenantFilter")) {
                Filter filter = session.enableFilter("tenantFilter");
                filter.setParameter("tenantId", tenantId);
            }
            em.close();
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}
