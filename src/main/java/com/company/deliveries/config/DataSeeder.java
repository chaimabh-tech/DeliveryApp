package com.company.deliveries.config;

import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;
import com.company.deliveries.repository.TimeSlotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(TimeSlotRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                LocalDate date = LocalDate.now().plusDays(1);
                List<TimeSlot> slots = List.of(
                        new TimeSlot(LocalTime.of(9, 0), LocalTime.of(10, 0), false, DeliveryMode.DRIVE, date),
                        new TimeSlot(LocalTime.of(10, 0), LocalTime.of(11, 0), false, DeliveryMode.DELIVERY, date),
                        new TimeSlot(LocalTime.of(11, 0), LocalTime.of(12, 0), false, DeliveryMode.DELIVERY_TODAY, date),
                        new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 0), false, DeliveryMode.DELIVERY_ASAP, date)
                );
                repository.saveAll(slots);
                System.out.println("✅ Seeded sample time slots.");
            }
        };
    }
}
