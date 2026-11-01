package com.kigali.supplier_management_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.domain.DeliveryNote;
import com.kigali.supplier_management_system.service.DeliveryNoteService;

@RestController
@RequestMapping("/api/delivery-notes")
public class DeliveryNoteController {

    @Autowired
    private DeliveryNoteService deliveryNoteService;

    @PostMapping("/save")
    public ResponseEntity<String> saveDeliveryNote(@RequestParam UUID purchaseOrderId,
            @RequestBody DeliveryNote deliveryNote) {
        String result = deliveryNoteService.saveDeliveryNote(purchaseOrderId, deliveryNote);
        if (result.equals("The purchase order with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        if (result.equals("Delivery notes are only for FULFILLED purchase orders")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/supplier/{tin}")
    public ResponseEntity<List<DeliveryNote>> getSupplierDeliveryNotes(@PathVariable String tin) {
        return new ResponseEntity<>(deliveryNoteService.getSupplierDeliveryNotes(tin), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDeliveryNote(@PathVariable UUID id) {
        String result = deliveryNoteService.deleteDeliveryNote(id);
        if (result.equals("The delivery note with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
