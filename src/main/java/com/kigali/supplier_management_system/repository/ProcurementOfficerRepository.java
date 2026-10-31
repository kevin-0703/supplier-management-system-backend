package com.kigali.supplier_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.ProcurementOfficer;

@Repository
public interface ProcurementOfficerRepository extends JpaRepository<ProcurementOfficer, UUID> {

}
