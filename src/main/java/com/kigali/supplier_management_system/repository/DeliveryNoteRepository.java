package com.kigali.supplier_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.DeliveryNote;
import com.kigali.supplier_management_system.domain.PurchaseOrder;

@Repository
public interface DeliveryNoteRepository extends JpaRepository<DeliveryNote, UUID> {

    @Query("""
            SELECT d
            FROM DeliveryNote d
            WHERE d.purchaseOrder.supplier.tin = :tin
            ORDER BY d.receivedDate DESC
            """)
    List<DeliveryNote> findBySupplierTin(@Param("tin") String tin);

    boolean existsByPurchaseOrder(PurchaseOrder purchaseOrder);
}
