package org.umutalacam.readingapp.stats;

import org.springframework.stereotype.Service;
import org.umutalacam.readingapp.order.BookOrder;
import org.umutalacam.readingapp.order.Order;
import org.umutalacam.readingapp.order.OrderRepository;
import org.umutalacam.readingapp.stats.response.Month;
import org.umutalacam.readingapp.stats.response.MonthlyStats;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
public class StatsService {

    private final OrderRepository orderRepository;
    private static final Logger logger = Logger.getLogger(StatsService.class.getName());

    public StatsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Calculates the order statistics within the given period.
     * @param months Defines how old the records will be processed.
     * @return stats
     */
    public HashMap<Integer, HashMap<Month, MonthlyStats>> getMonthlyStats(int months) {
        // Set date intervals
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -months);
        Date monthsAgo = calendar.getTime();
        List<Order> ordersWithinMonths = this.orderRepository.findAllByOrderTimeGreaterThan(monthsAgo);

        // Analyze historical data in parallel
        HashMap<Integer, HashMap<Month, MonthlyStats>> yearlyStats = new HashMap<>();

        Month[] monthValues = Month.values();
        ordersWithinMonths.stream().parallel().forEach(order -> {
            Date orderTime = order.getOrderTime();

            Calendar orderCalendar = Calendar.getInstance();
            orderCalendar.setTime(orderTime);

            int yearIndex = orderCalendar.get(Calendar.YEAR);
            int monthIndex = orderCalendar.get(Calendar.MONTH);
            Month month = monthValues[monthIndex];

            if (!yearlyStats.containsKey(yearIndex)) {
                yearlyStats.put(yearIndex, new HashMap<>());
            }
            HashMap<Month, MonthlyStats> monthlyStats = yearlyStats.get(yearIndex);

            if (!monthlyStats.containsKey(monthValues[monthIndex])) {
                monthlyStats.put(month, new MonthlyStats());
            }
            // Get this month's stats object
            MonthlyStats stats = monthlyStats.get(month);

            // Sum books in the order
            long bookCount = 0;
            for (BookOrder bo: order.getItems()) {
                bookCount += bo.getAmount();
            }
            // Update stats
            stats.setBookCount(stats.getBookCount() + bookCount);
            stats.setTotalAmount(stats.getTotalAmount() + order.getTotalPrice());
            stats.setOrderCount(stats.getOrderCount() + 1);
        });

        return yearlyStats;
    }

}
