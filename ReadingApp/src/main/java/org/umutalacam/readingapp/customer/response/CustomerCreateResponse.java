package org.umutalacam.readingapp.customer.response;

import lombok.Data;
import org.umutalacam.readingapp.system.response.RestResponse;
@Data
public class CustomerCreateResponse extends RestResponse {
    private String customerId;
}
