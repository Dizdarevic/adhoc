// METRICS MANAGER

package com.slalom.devsecops.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.stereotype.Component;




@Component
public class MetricsManager {

    private Counter exampleCounter;
    
    public MetricsManager(MeterRegistry meterRegistry) {
        exampleCounter = meterRegistry.counter("my.example.test.counter");
    }

    public void increaseAccumulatorServiceSaveToP2pAccumsCounter() {
        exampleCounter.increment();
    }
}




// DEPENDENCIES
// <dependency>
//     <groupId>io.micrometer</groupId>
//     <artifactId>micrometer-registry-prometheus</artifactId>
//     <version>1.0.5</version>
//     <scope>compile</scope>
// </dependency>

// <dependency>
//     <groupId>io.micrometer</groupId>
//     <artifactId>micrometer-core</artifactId>
//     <version>1.1.2</version>
// </dependency>