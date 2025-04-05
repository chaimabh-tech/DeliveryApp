package com.company.deliveries.controller;

import com.company.deliveries.kafka.DeliveryEventProducer;
import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;
import com.company.deliveries.repository.TimeSlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for managing delivery modes and time slot reservations.
 */
@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    private DeliveryEventProducer deliveryEventProducer;

    public DeliveryController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    /**
     * Returns the list of available delivery modes (e.g., STANDARD, EXPRESS).
     *
     * @return array of delivery modes
     */
    @GetMapping("/modes")
    public DeliveryMode[] getAvailableDeliveryModes() {
        return DeliveryMode.values();
    }

    /**
     * Returns the list of available time slots for a given delivery mode and date.
     * Results are cached by delivery mode and date to reduce database load.
     *
     * Example: GET /api/delivery/slots?mode=STANDARD&date=2025-04-06
     *
     * @param mode the delivery mode (STANDARD or EXPRESS)
     * @param date the date for which to retrieve available time slots
     * @return list of matching time slots
     */
    @Cacheable(value = "timeSlots", key = "#mode.toString() + '-' + #date.toString()")
    @GetMapping("/slots")
    public List<TimeSlot> getTimeSlots(
            @RequestParam DeliveryMode mode,
            @RequestParam LocalDate date) {

        return timeSlotRepository.findByDeliveryModeAndDate(mode, date);
    }

    /**
     * Reserves a specific time slot by ID.
     * If the slot is already reserved, returns a message indicating it.
     * Otherwise, marks it as reserved and sends an event via Kafka.
     *
     * Example: POST /api/delivery/slots/5/reserve
     *
     * @param id the ID of the time slot to reserve
     * @return reservation confirmation message
     */
    @PostMapping("/slots/{id}/reserve")
    public String reserveSlot(@PathVariable Long id) {
        var slot = timeSlotRepository.findById(id).orElseThrow();

        if (slot.isReserved()) {
            return "Already reserved!";
        }

        slot.setReserved(true);
        timeSlotRepository.save(slot);

        deliveryEventProducer.sendDeliveryEvent("Time slot reserved: " + slot.getId());

        return "Slot reserved!";
    }
}
