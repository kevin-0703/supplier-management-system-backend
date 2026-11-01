package com.kigali.supplier_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.Supplier;
import com.kigali.supplier_management_system.repository.ProcurementOfficerRepository;
import com.kigali.supplier_management_system.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepo;

    @Autowired
    private ProcurementOfficerRepository procurementOfficerRepo;

    public Supplier saveSupplier(Supplier supplier) {
        Supplier savedSupplier = supplierRepo.save(supplier);
        return savedSupplier;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepo.findAll();
        return suppliers;
    }

    public Optional<Supplier> getSupplierById(UUID id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);

        if (supplier.isEmpty()) {
            return Optional.empty();
        }

        return supplier;
    }

    public Optional<Supplier> updateSupplier(UUID id, Supplier supplier) {
        Optional<Supplier> savedSupplier = supplierRepo.findById(id);

        if (savedSupplier.isEmpty()) {
            return Optional.empty();
        }

        Supplier updatedSupplier = savedSupplier.get();
        updatedSupplier.setCompanyName(supplier.getCompanyName());
        updatedSupplier.setContactName(supplier.getContactName());
        updatedSupplier.setTin(supplier.getTin());
        updatedSupplier.setEmail(supplier.getEmail());
        updatedSupplier.setPhone(supplier.getPhone());
        supplierRepo.save(updatedSupplier);

        return Optional.of(updatedSupplier);
    }

    public String deleteSupplier(UUID id) {
        Optional<Supplier> supplier = supplierRepo.findById(id);

        if (supplier.isEmpty()) {
            return "The supplier with that id does not exist";
        }

        supplierRepo.deleteById(id);
        return "Supplier deleted successfully";
    }


    public List<Supplier> getSuppliersByContactName(String contactName) {
        List<Supplier> suppliers = supplierRepo.findByContactNameIgnoreCaseOrderByCompanyNameAsc(contactName);
        return suppliers;
    }


    public List<Supplier> getSuppliersOfProcurementOfficer(UUID procurementOfficerId) {
        List<Supplier> suppliers = supplierRepo.findSuppliersByProcurementOfficerId(procurementOfficerId);
        return suppliers;
    }

    public boolean procurementOfficerExists(UUID procurementOfficerId) {
        return procurementOfficerRepo.existsById(procurementOfficerId);
    }

}

