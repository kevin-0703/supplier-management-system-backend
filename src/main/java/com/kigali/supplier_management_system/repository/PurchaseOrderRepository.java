package com.kigali.supplier_management_system.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kigali.supplier_management_system.domain.OrderStatus;
import com.kigali.supplier_management_system.domain.ProcurementOfficer;
import com.kigali.supplier_management_system.domain.PurchaseOrder;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {

    List<PurchaseOrder> findByStatusOrderByOrderDateAsc(OrderStatus status);

    List<PurchaseOrder> findByOrderDateBetweenOrderByOrderDateAsc(LocalDate start, LocalDate end);

    boolean existsByProcurementOfficerAndOrderDateAndStatusNot(ProcurementOfficer procurementOfficer,
            LocalDate orderDate, OrderStatus status);

    @Query("""
            SELECT o.status, COUNT(o)
            FROM PurchaseOrder o
            GROUP BY o.status
            """)
    List<Object[]> countOrdersByStatus();

    @Modifying
    @Transactional
    @Query("""
            UPDATE PurchaseOrder o
            SET o.status = :newStatus
            WHERE o.procurementOfficer.id = :officerId
              AND o.orderDate = :orderDate
              AND o.status <> :fulfilledStatus
            """)
    int cancelOrdersForOfficerOnDate(@Param("officerId") UUID officerId,
            @Param("orderDate") LocalDate orderDate,
            @Param("newStatus") OrderStatus newStatus,
            @Param("fulfilledStatus") OrderStatus fulfilledStatus);

}
