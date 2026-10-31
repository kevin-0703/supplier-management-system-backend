package com.kigali.supplier_management_system.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

}
