package org.umutalacam.readingapp.stats.response;

import lombok.Data;

@Data
public class MonthlyStats {
    private long orderCount;
    private long bookCount;
    private double totalAmount;
}
