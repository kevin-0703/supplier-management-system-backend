package com.kigali.supplier_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.Branch;
import com.kigali.supplier_management_system.domain.ProcurementOfficer;

@Repository
public interface ProcurementOfficerRepository extends JpaRepository<ProcurementOfficer, UUID> {

   
    @Query("""
            SELECT p
            FROM ProcurementOfficer p
            JOIN p.supplyCategories c
            WHERE LOWER(c.name) = LOWER(:name)
            """)
    List<ProcurementOfficer> findBySupplyCategoryName(@Param("name") String name);

   
    boolean existsByBranch(Branch branch);

}
