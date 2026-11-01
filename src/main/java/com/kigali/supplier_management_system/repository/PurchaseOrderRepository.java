package com.kigali.supplier_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.OrderStatus;
import com.kigali.supplier_management_system.domain.ProcurementOfficer;
import com.kigali.supplier_management_system.domain.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {

    List<PurchaseOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<PurchaseOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    boolean existsByProcurementOfficerAndOrderDateAndStatusNot(ProcurementOfficer procurementOfficer,
            LocalDate orderDate, OrderStatus status);

}
