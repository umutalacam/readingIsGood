package org.umutalacam.readingapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.umutalacam.readingapp.stats.StatsService;

@SpringBootTest
public class StatsServiceTest {

    @Autowired
    StatsService statsService;

    @Test
    public void testStats() {
        statsService.getMonthlyStats(5);
    }
}
