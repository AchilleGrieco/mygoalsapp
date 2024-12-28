package com.example.MyGoalsApp.authentication.security.DoSPrevention;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
public class RateLimitingService {

    private Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private final int MAX_REQUESTS_PER_MINUTE = 100;

    public RateLimitingService() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            requestCounts.clear();
        }, 1, 1, TimeUnit.MINUTES);
    }

    public boolean isRequestAllowed(String username) {
        requestCounts.merge(username, 1, Integer::sum);

        return requestCounts.get(username) <= MAX_REQUESTS_PER_MINUTE;
    }
}
