package com.smartaml.user.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import jakarta.persistence.Id;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
public class Permission {
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "module_key", nullable = false)
    private String moduleKey;

    @Column(name = "menu_label", nullable = false)
    private String menuLabel;

    @Column(name = "action", nullable = false)
    private String action;

    @Column(name = "description")
    private String description;
}
