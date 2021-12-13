package org.umutalacam.readingapp.order.response;

import lombok.Data;
import org.umutalacam.readingapp.system.response.RestResponse;
@Data
public class OrderCreatedResponse extends RestResponse {
    private String orderId;
    public OrderCreatedResponse() {
        super("Order created successfully.");
    }
}
