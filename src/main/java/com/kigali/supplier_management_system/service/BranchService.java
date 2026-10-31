package com.kigali.supplier_management_system.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kigali.supplier_management_system.domain.Branch;
import com.kigali.supplier_management_system.repository.BranchRepository;

@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepo;

    public Branch saveBranch(Branch branch) {
        Branch saveBranch = branchRepo.save(branch);
        return saveBranch;
    }

    public List<Branch> getAllBranchs() {
        List<Branch> branchs = branchRepo.findAll();
        return branchs;
    }

    public Optional<Branch> getBranchById(UUID id) {
        Optional<Branch> branchs = branchRepo.findById(id);

        if (branchs.isEmpty()) {
            return null;
        }

        return branchs;
    }

    public Optional<Branch> updateBranch(Branch branch, UUID id) {
        Optional<Branch> savedBranch = branchRepo.findById(id);

        if (savedBranch.isEmpty()) {
            return savedBranch.empty();
        }

        Branch updateBranch = savedBranch.get();
        updateBranch.setBranchNumber(branch.getBranchNumber());
        updateBranch.setName(branch.getName());
        branchRepo.save(updateBranch);

        return Optional.of(updateBranch);
    }

    public String deleteBranch(UUID id) {
        Optional<Branch> branch = branchRepo.findById(id);

        if (branch.isEmpty()) {
            return "The branch with that id does not exist";
        }

        return "Branch deleted successfully";
    }

}
