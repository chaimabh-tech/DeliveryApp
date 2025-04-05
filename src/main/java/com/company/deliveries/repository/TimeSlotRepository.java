package com.company.deliveries.repository;

import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDeliveryModeAndDate(DeliveryMode deliveryMode, LocalDate date);
}
