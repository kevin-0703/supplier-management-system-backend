package com.kigali.supplier_management_system.domain;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "order_date")
    LocalDate orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    String description;

    @Column(name = "total_amount")
    double totalAmount;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "procurement_officer_id")
    private ProcurementOfficer procurementOfficer;

    public PurchaseOrder() {
    }

    public PurchaseOrder(UUID id, LocalDate orderDate, OrderStatus status, String description, double totalAmount,
            Supplier supplier, ProcurementOfficer procurementOfficer) {
        this.id = id;
        this.orderDate = orderDate;
        this.status = status;
        this.description = description;
        this.totalAmount = totalAmount;
        this.supplier = supplier;
        this.procurementOfficer = procurementOfficer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ProcurementOfficer getProcurementOfficer() {
        return procurementOfficer;
    }

    public void setProcurementOfficer(ProcurementOfficer procurementOfficer) {
        this.procurementOfficer = procurementOfficer;
    }

}
