package com.kigali.supplier_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    List<Supplier> findByContactNameIgnoreCaseOrderByCompanyNameAsc(String contactName);


    @Query("""
            SELECT DISTINCT o.supplier
            FROM PurchaseOrder o
            WHERE o.procurementOfficer.id = :procurementOfficerId
            """)
    List<Supplier> findSuppliersByProcurementOfficerId(@Param("procurementOfficerId") UUID procurementOfficerId);

}
