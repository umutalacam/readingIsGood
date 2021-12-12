package org.umutalacam.readingapp.stats;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.umutalacam.readingapp.stats.response.Month;
import org.umutalacam.readingapp.stats.response.MonthlyStats;

import java.util.HashMap;

@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/stats")
    public HashMap<Integer, HashMap<Month, MonthlyStats>> getStats(@RequestParam(defaultValue = "3") int months) {
        return this.statsService.getMonthlyStats(months);
    }
}
