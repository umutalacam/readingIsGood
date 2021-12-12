package org.umutalacam.readingapp.system.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginatedCollectionResponse<T> {
    private int pageSize;
    private int currentPage;
    private int totalRecords;
    private List<T> records;
}
