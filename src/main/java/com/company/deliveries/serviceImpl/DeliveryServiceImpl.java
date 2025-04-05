package com.company.deliveries.serviceImpl;

import com.company.deliveries.kafka.DeliveryEventProducer;
import com.company.deliveries.model.DeliveryMode;
import com.company.deliveries.model.TimeSlot;
import com.company.deliveries.repository.TimeSlotRepository;
import com.company.deliveries.service.DeliveryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final TimeSlotRepository timeSlotRepository;
    private final DeliveryEventProducer deliveryEventProducer;

    @Autowired
    public DeliveryServiceImpl(TimeSlotRepository timeSlotRepository, DeliveryEventProducer deliveryEventProducer) {
        this.timeSlotRepository = timeSlotRepository;
        this.deliveryEventProducer = deliveryEventProducer;
    }

    @Override
    public DeliveryMode[] getAvailableDeliveryModes() {
        return DeliveryMode.values();
    }

    @Override
    public List<TimeSlot> getTimeSlots(DeliveryMode mode, LocalDate date) {
        return timeSlotRepository.findByDeliveryModeAndDate(mode, date);
    }

    @Override
    public String reserveSlot(Long id) {
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
