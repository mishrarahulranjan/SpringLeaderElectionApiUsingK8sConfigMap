package com.spring.leaderelection.springleaderelectionapiusingk8sconfigmap.actuator.health;

import com.spring.leaderelection.springleaderelectionapiusingk8sconfigmap.service.K8sFabric8LeaderService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("customHealthCheck")
public class CustomHealthCheck implements HealthIndicator {

    private K8sFabric8LeaderService leaderService;

    public CustomHealthCheck(K8sFabric8LeaderService leaderService) {
        this.leaderService = leaderService;
    }

    @Override
    public Health health() {

        if(leaderService.isLeader()){
            return Health.up().build();
        }else{
            return Health.down().build();
        }
    }
}