package com.smartaml.infrastructure.audit;

import com.smartaml.shared.enums.AuditAction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import java.util.UUID;
import com.smartaml.shared.event.AuditLogEvent;

@Component
public class AuditEventPublisher {

    private final ApplicationEventPublisher publisher;

    public AuditEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishAudit(AuditAction action, String module, UUID entityId, Object oldValue, Object newValue, String status, String error) {
        AuditLogEvent event = AuditLogEvent.builder()
                .action(action)
                .module(module)
                .entityId(entityId)
                .oldValue(oldValue)
                .newValue(newValue)
                .status(status)
                .errorMessage(error)
                .build();
        publisher.publishEvent(event);
    }
}
