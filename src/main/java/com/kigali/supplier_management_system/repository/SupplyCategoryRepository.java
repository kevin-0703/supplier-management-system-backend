package com.kigali.supplier_management_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kigali.supplier_management_system.domain.SupplyCategory;

@Repository
public interface SupplyCategoryRepository extends JpaRepository<SupplyCategory, UUID> {
    List<SupplyCategory> findByName(String name);

}
