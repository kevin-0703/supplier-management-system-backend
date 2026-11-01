package com.kigali.supplier_management_system.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.domain.OrderStatus;
import com.kigali.supplier_management_system.domain.PurchaseOrder;
import com.kigali.supplier_management_system.service.PurchaseOrderService;

@RestController
@RequestMapping(value = "/api/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @PostMapping(value = "/save")
    public ResponseEntity<?> savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        String result = purchaseOrderService.savePurchaseOrder(purchaseOrder);

        if (result.equals("Purchase order date cannot be in the past")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        if (result.equals("Procurement officer already has an order on that date")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        if (result.contains("does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllPurchaseOrders() {
        List<PurchaseOrder> orders = purchaseOrderService.getAllPurchaseOrders();

        if (orders.isEmpty()) {
            return new ResponseEntity<>("Purchase orders not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getPurchaseOrderById(@PathVariable UUID id) {
        Optional<PurchaseOrder> order = purchaseOrderService.getPurchaseOrderById(id);

        if (order == null || order.isEmpty()) {
            return new ResponseEntity<>("The purchase order with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updatePurchaseOrder(@PathVariable UUID id, @RequestBody PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> updatedOrder = purchaseOrderService.updatePurchaseOrder(id, purchaseOrder);

        if (updatedOrder == null || updatedOrder.isEmpty()) {
            return new ResponseEntity<>("The purchase order with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Purchase order updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable UUID id) {
        String result = purchaseOrderService.deletePurchaseOrder(id);

        if (result.equals("The purchase order with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.equals("That purchase order has delivery notes, delete them first")) {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

   
    @GetMapping(value = "/by-status")
    public ResponseEntity<?> getPurchaseOrdersByStatus(@RequestParam OrderStatus status) {
        List<PurchaseOrder> orders = purchaseOrderService.getPurchaseOrdersByStatus(status);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value = "/between")
    public ResponseEntity<?> getPurchaseOrdersBetweenDates(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        List<PurchaseOrder> orders = purchaseOrderService.getPurchaseOrdersBetweenDates(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

 
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> updatePurchaseOrderStatus(@PathVariable UUID id, @RequestParam OrderStatus status) {
        String result = purchaseOrderService.updatePurchaseOrderStatus(id, status);

        if (result.equals("The purchase order with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if (result.startsWith("Cannot change status")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<List<Object[]>> getStatsByStatus() {
        return new ResponseEntity<>(purchaseOrderService.getPurchaseOrderStatsByStatus(), HttpStatus.OK);
    }

    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<String> cancelOrdersForOfficerOnDate(@RequestParam UUID procurementOfficerId,
            @RequestParam String date) {
        LocalDate orderDate = LocalDate.parse(date);
        String result = purchaseOrderService.cancelPurchaseOrdersForOfficerOnDate(procurementOfficerId, orderDate);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}

