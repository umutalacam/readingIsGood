package org.umutalacam.readingapp.system.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedResponse<T> {
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private long totalRecords;
    private List<T> records;
}
