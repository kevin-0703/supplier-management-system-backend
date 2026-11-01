package com.kigali.supplier_management_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.OrderStatus;
import com.kigali.supplier_management_system.domain.ProcurementOfficer;
import com.kigali.supplier_management_system.domain.PurchaseOrder;
import com.kigali.supplier_management_system.domain.Supplier;
import com.kigali.supplier_management_system.repository.ProcurementOfficerRepository;
import com.kigali.supplier_management_system.repository.PurchaseOrderRepository;
import com.kigali.supplier_management_system.repository.SupplierRepository;
import com.kigali.supplier_management_system.repository.DeliveryNoteRepository;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepo;

    @Autowired
    private SupplierRepository supplierRepo;

    @Autowired
    private ProcurementOfficerRepository procurementOfficerRepo;

    @Autowired
    private DeliveryNoteRepository deliveryNoteRepo;

    public String savePurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getOrderDate().isBefore(LocalDate.now())) {
            return "Purchase order date cannot be in the past";
        }

        Optional<ProcurementOfficer> officer = procurementOfficerRepo.findById(purchaseOrder.getProcurementOfficer().getId());
        if (officer.isEmpty()) {
            return "The procurement officer with that id does not exist";
        }

        boolean duplicate = purchaseOrderRepo.existsByProcurementOfficerAndOrderDateAndStatusNot(
                officer.get(), purchaseOrder.getOrderDate(), OrderStatus.CANCELLED);
        if (duplicate) {
            return "Procurement officer already has an order on that date";
        }

        purchaseOrder.setProcurementOfficer(officer.get());

        if (purchaseOrder.getSupplier() != null && purchaseOrder.getSupplier().getId() != null) {
            Optional<Supplier> supplier = supplierRepo.findById(purchaseOrder.getSupplier().getId());
            if (supplier.isEmpty()) {
                return "The supplier with that id does not exist";
            }
            purchaseOrder.setSupplier(supplier.get());
        }

        purchaseOrder.setStatus(OrderStatus.ISSUED);

        purchaseOrderRepo.save(purchaseOrder);
        return "Purchase order saved successfully";
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        List<PurchaseOrder> orders = purchaseOrderRepo.findAll();
        return orders;
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(UUID id) {
        Optional<PurchaseOrder> order = purchaseOrderRepo.findById(id);

        if (order.isEmpty()) {
            return Optional.empty();
        }

        return order;
    }

   
    public Optional<PurchaseOrder> updatePurchaseOrder(UUID id, PurchaseOrder purchaseOrder) {
        Optional<PurchaseOrder> savedOrder = purchaseOrderRepo.findById(id);

        if (savedOrder.isEmpty()) {
            return Optional.empty();
        }

        PurchaseOrder updatedOrder = savedOrder.get();
        updatedOrder.setOrderDate(purchaseOrder.getOrderDate());
        updatedOrder.setDescription(purchaseOrder.getDescription());
        updatedOrder.setTotalAmount(purchaseOrder.getTotalAmount());
       

        if (purchaseOrder.getSupplier() != null && purchaseOrder.getSupplier().getId() != null) {
            Optional<Supplier> supplier = supplierRepo.findById(purchaseOrder.getSupplier().getId());
            if (supplier.isEmpty()) {
                return Optional.empty();
            }
            updatedOrder.setSupplier(supplier.get());
        }

        if (purchaseOrder.getProcurementOfficer() != null && purchaseOrder.getProcurementOfficer().getId() != null) {
            Optional<ProcurementOfficer> officer = procurementOfficerRepo.findById(purchaseOrder.getProcurementOfficer().getId());
            if (officer.isEmpty()) {
                return Optional.empty();
            }
            updatedOrder.setProcurementOfficer(officer.get());
        }

        purchaseOrderRepo.save(updatedOrder);
        return Optional.of(updatedOrder);
    }

    public String deletePurchaseOrder(UUID id) {
        Optional<PurchaseOrder> order = purchaseOrderRepo.findById(id);

        if (order.isEmpty()) {
            return "The purchase order with that id does not exist";
        }

        if (deliveryNoteRepo.existsByPurchaseOrder(order.get())) {
            return "That purchase order has delivery notes, delete them first";
        }

        purchaseOrderRepo.deleteById(id);
        return "Purchase order deleted successfully";
    }

    public List<Object[]> getPurchaseOrderStatsByStatus() {
        return purchaseOrderRepo.countOrdersByStatus();
    }

    public String cancelPurchaseOrdersForOfficerOnDate(UUID officerId, LocalDate date) {
        int cancelled = purchaseOrderRepo.cancelOrdersForOfficerOnDate(
                officerId, date, OrderStatus.CANCELLED, OrderStatus.FULFILLED);
        return cancelled + " purchase orders cancelled";
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(OrderStatus status) {
        List<PurchaseOrder> orders = purchaseOrderRepo.findByStatusOrderByOrderDateAsc(status);
        return orders;
    }

    
    public List<PurchaseOrder> getPurchaseOrdersBetweenDates(LocalDate start, LocalDate end) {
        List<PurchaseOrder> orders = purchaseOrderRepo.findByOrderDateBetweenOrderByOrderDateAsc(start, end);
        return orders;
    }

    
    public String updatePurchaseOrderStatus(UUID id, OrderStatus newStatus) {
        Optional<PurchaseOrder> savedOrder = purchaseOrderRepo.findById(id);

        if (savedOrder.isEmpty()) {
            return "The purchase order with that id does not exist";
        }

        PurchaseOrder order = savedOrder.get();
        OrderStatus currentStatus = order.getStatus();

        if (currentStatus == OrderStatus.ISSUED) {
            if (newStatus == OrderStatus.ACKNOWLEDGED || newStatus == OrderStatus.CANCELLED) {
                order.setStatus(newStatus);
                purchaseOrderRepo.save(order);
                return "Purchase order status updated successfully";
            } else {
                return "Cannot change status from " + currentStatus + " to " + newStatus;
            }
        } else if (currentStatus == OrderStatus.ACKNOWLEDGED) {
            if (newStatus == OrderStatus.FULFILLED || newStatus == OrderStatus.CANCELLED) {
                order.setStatus(newStatus);
                purchaseOrderRepo.save(order);
                return "Purchase order status updated successfully";
            } else {
                return "Cannot change status from " + currentStatus + " to " + newStatus;
            }
        } else {
            
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
    }

}

