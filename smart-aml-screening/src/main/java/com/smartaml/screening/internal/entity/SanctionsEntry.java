package com.smartaml.screening.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sanctions_entries")
@Getter
@Setter
@NoArgsConstructor
public class SanctionsEntry {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "reference_no", nullable = false)
    private String referenceNo;

    @Column(name = "entry_type")
    private String entryType;

    @Column(name = "primary_name")
    private String primaryName;

    @ElementCollection
    @CollectionTable(name = "sanctions_aliases", joinColumns = @JoinColumn(name = "entry_id"))
    private List<String> aliases;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "listed_on")
    private java.time.LocalDate listedOn;

    @Column(name = "raw_xml", columnDefinition = "text")
    private String rawXml;
}
