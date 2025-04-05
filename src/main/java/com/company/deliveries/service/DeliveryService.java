package com.company.deliveries.service;

import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface DeliveryService {

    DeliveryMode[] getAvailableDeliveryModes();

    List<TimeSlot> getTimeSlots(DeliveryMode mode, LocalDate date);

    String reserveSlot(Long id);
}
