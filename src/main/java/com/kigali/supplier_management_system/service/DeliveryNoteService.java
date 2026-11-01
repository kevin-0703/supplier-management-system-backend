package com.kigali.supplier_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.DeliveryNote;
import com.kigali.supplier_management_system.domain.OrderStatus;
import com.kigali.supplier_management_system.domain.PurchaseOrder;
import com.kigali.supplier_management_system.repository.DeliveryNoteRepository;
import com.kigali.supplier_management_system.repository.PurchaseOrderRepository;

@Service
public class DeliveryNoteService {

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepo;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    public String saveDeliveryNote(UUID purchaseOrderId, DeliveryNote deliveryNote) {
        Optional<PurchaseOrder> savedOrder = purchaseOrderRepo.findById(purchaseOrderId);
        if (savedOrder.isEmpty()) {
            return "The purchase order with that id does not exist";
        }
        if (savedOrder.get().getStatus() != OrderStatus.FULFILLED) {
            return "Delivery notes are only for FULFILLED purchase orders";
        }

        deliveryNote.setReceivedDate(LocalDate.now());
        deliveryNote.setPurchaseOrder(savedOrder.get());
        deliveryNoteRepo.save(deliveryNote);
        return "Delivery note saved successfully";
    }

    public List<DeliveryNote> getSupplierDeliveryNotes(String tin) {
        return deliveryNoteRepo.findBySupplierTin(tin);
    }

    public String deleteDeliveryNote(UUID id) {
        if (deliveryNoteRepo.findById(id).isEmpty()) {
            return "The delivery note with that id does not exist";
        }
        deliveryNoteRepo.deleteById(id);
        return "Delivery note deleted successfully";
    }
}
