package com.kigali.supplier_management_system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.Branch;
import com.kigali.supplier_management_system.domain.ProcurementOfficer;
import com.kigali.supplier_management_system.domain.SupplyCategory;
import com.kigali.supplier_management_system.repository.BranchRepository;
import com.kigali.supplier_management_system.repository.ProcurementOfficerRepository;
import com.kigali.supplier_management_system.repository.SupplyCategoryRepository;

import jakarta.transaction.Transactional;

@Service
public class ProcurementOfficeService {

    @Autowired
    private ProcurementOfficerRepository procurementOfficerRepo;

    @Autowired
    private BranchRepository branchRepo;

    @Autowired
    private SupplyCategoryRepository supplyCategoryRepo;

    public ProcurementOfficer saveProcurementOfficer(ProcurementOfficer procurementOfficer) {
        procurementOfficerRepo.save(procurementOfficer);

        return procurementOfficer;
    }

    public List<ProcurementOfficer> getAllProcurementOfficers() {
        List<ProcurementOfficer> procurementOfficers = procurementOfficerRepo.findAll();
        return procurementOfficers;

    }

    public Optional<ProcurementOfficer> getProcurementOfficerById(UUID id) {
        Optional<ProcurementOfficer> procurementOfficer = procurementOfficerRepo.findById(id);

        if (procurementOfficer.isEmpty()) {
            return procurementOfficer.empty();
        }
        return procurementOfficer;
    }

    @Transactional
    public Optional<ProcurementOfficer> updateProcurementOfficer(UUID id, ProcurementOfficer procurementOfficer) {
        Optional<ProcurementOfficer> savedProcurementOfficer = procurementOfficerRepo.findById(id);
        if (savedProcurementOfficer.isEmpty()) {
            return savedProcurementOfficer.empty();
        }

        ProcurementOfficer updatedProcurementOfficer = savedProcurementOfficer.get();
        updatedProcurementOfficer.setEmail(procurementOfficer.getEmail());
        updatedProcurementOfficer.setFirstName(procurementOfficer.getFirstName());
        updatedProcurementOfficer.setLastName(procurementOfficer.getLastName());

        if (procurementOfficer.getBranch() == null || procurementOfficer.getBranch().getId() == null) {
            updatedProcurementOfficer.setBranch(null);
        } else {
            Optional<Branch> branch = branchRepo.findById(procurementOfficer.getBranch().getId());
            if (branch.isEmpty()) {
                return branch.empty();
            }
            updatedProcurementOfficer.setBranch(branch.get());
        }

        List<SupplyCategory> categories = new ArrayList<>();

        for (SupplyCategory incomingCategory : procurementOfficer.getSupplyCategories()) {
            List<SupplyCategory> savedCategories = supplyCategoryRepo.findByName(incomingCategory.getName());

            if (savedCategories.isEmpty()) {
                return null;
            }
            SupplyCategory existinCategory = savedCategories.get(0);
            categories.add(existinCategory);
        }
        updatedProcurementOfficer.setSupplyCategories(categories);
        procurementOfficerRepo.save(updatedProcurementOfficer);
        return Optional.of(procurementOfficer);

    }

    public Optional<ProcurementOfficer> deleteProcurementOfficer(UUID id) {
        Optional<ProcurementOfficer> officer = procurementOfficerRepo.findById(id);

        if (officer.isEmpty()) {
            return officer.empty();
        }
        procurementOfficerRepo.deleteById(id);
        return officer;
    }

    public List<ProcurementOfficer> getProcurementOfficersBySupplyCategory(String name) {
        List<ProcurementOfficer> officers = procurementOfficerRepo.findBySupplyCategoryName(name);
        return officers;
    }

   
    @Transactional
    public String addSupplyCategory(UUID procurementOfficerId, UUID supplyCategoryId) {
        Optional<ProcurementOfficer> savedOfficer = procurementOfficerRepo.findById(procurementOfficerId);

        if (savedOfficer.isEmpty()) {
            return "The procurement officer with that id does not exist";
        }

        Optional<SupplyCategory> savedCategory = supplyCategoryRepo.findById(supplyCategoryId);

        if (savedCategory.isEmpty()) {
            return "That supply category does not exist";
        }

        ProcurementOfficer officer = savedOfficer.get();
        SupplyCategory category = savedCategory.get();

        if (officer.getSupplyCategories().contains(category)) {
            return "The procurement officer already has that supply category";
        }

        officer.getSupplyCategories().add(category);
        procurementOfficerRepo.save(officer);
        return "Supply category added to the procurement officer";
    }

    
    @Transactional
    public String changeBranch(UUID procurementOfficerId, int branchNumber) {
        Optional<ProcurementOfficer> savedOfficer = procurementOfficerRepo.findById(procurementOfficerId);

        if (savedOfficer.isEmpty()) {
            return "The procurement officer with that id does not exist";
        }

        Optional<Branch> savedBranch = branchRepo.findByBranchNumber(branchNumber);

        if (savedBranch.isEmpty()) {
            return "The branch with that branchNumber does not exist";
        }

        Branch branch = savedBranch.get();
        ProcurementOfficer officer = savedOfficer.get();

      
        boolean branchTaken = procurementOfficerRepo.existsByBranch(branch);
        if (branchTaken && !branch.equals(officer.getBranch())) {
            return "That branch is taken by another procurement officer";
        }

        officer.setBranch(branch);
        procurementOfficerRepo.save(officer);
        return "Branch changed successfully";
    }

}

