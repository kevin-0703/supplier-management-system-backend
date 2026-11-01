package com.kigali.supplier_management_system.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.Branch;

@Repository
public interface BranchRepository extends JpaRepository<Branch, UUID> {

    Optional<Branch> findById(UUID id);

   
    Optional<Branch> findByBranchNumber(int branchNumber);

    
    @Query("""
            SELECT b
            FROM Branch b
            WHERE NOT EXISTS (
                SELECT p
                FROM ProcurementOfficer p
                WHERE p.branch = b
            )
            ORDER BY b.branchNumber ASC
            """)
    List<Branch> findFreeBranches();

}
