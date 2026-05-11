package com.smartaml.customer.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "customer_documents")
@Getter
@Setter
@NoArgsConstructor
public class CustomerDocument {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "customer_id", columnDefinition = "uuid", nullable = false)
    private UUID customerId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "stored_on_s3")
    private boolean storedOnS3;
}
