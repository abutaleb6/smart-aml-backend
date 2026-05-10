package com.smartaml.shared.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * Pagination metadata for paginated responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class PageMeta {

    private int page;           // Current page number (0-indexed)
    private int size;           // Page size
    private long totalElements; // Total number of elements
    private int totalPages;     // Total number of pages
    private boolean first;      // Is this the first page
    private boolean last;       // Is this the last page

    /**
     * Create PageMeta from Spring Data Page.
     */
    public static PageMeta of(Page<?> page) {
        return PageMeta.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
