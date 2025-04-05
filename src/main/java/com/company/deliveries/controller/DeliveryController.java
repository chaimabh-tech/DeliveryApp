package com.company.deliveries.controller;

import com.company.deliveries.kafka.DeliveryEventProducer;
import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;
import com.company.deliveries.repository.TimeSlotRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    private final TimeSlotRepository timeSlotRepository;
    
    @Autowired
    private DeliveryEventProducer deliveryEventProducer;
    
    public DeliveryController(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    @GetMapping("/modes")
    public DeliveryMode[] getAvailableDeliveryModes() {
        return DeliveryMode.values();
    }

    @GetMapping("/slots")
    public List<TimeSlot> getTimeSlots(
            @RequestParam DeliveryMode mode,
            @RequestParam LocalDate date) {

        List<TimeSlot> timeSlots = timeSlotRepository.findByDeliveryModeAndDate(mode, date);
     
        return timeSlots;
    }

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
