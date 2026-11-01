package com.kigali.supplier_management_system.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.domain.Branch;
import com.kigali.supplier_management_system.service.BranchService;

@RestController
@RequestMapping(value = "/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveBranch(@RequestBody Branch branch) {
        branchService.saveBranch(branch);
        return new ResponseEntity<>("Branch saved successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllBranches() {
        List<Branch> branches = branchService.getAllBranchs();

        if (branches.isEmpty()) {
            return new ResponseEntity<>("Branches not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable UUID id) {
        Optional<Branch> branch = branchService.getBranchById(id);

        if (branch == null || branch.isEmpty()) {
            return new ResponseEntity<>("The branch with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(branch, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable UUID id, @RequestBody Branch branch) {
        Optional<Branch> updatedBranch = branchService.updateBranch(branch, id);

        if (updatedBranch == null || updatedBranch.isEmpty()) {
            return new ResponseEntity<>("The branch with that id does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Branch updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable UUID id) {
        String result = branchService.deleteBranch(id);

        if (result.equals("The branch with that id does not exist")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<?> getFreeBranches() {
        List<Branch> freeBranches = branchService.getFreeBranches();
        return new ResponseEntity<>(freeBranches, HttpStatus.OK);
    }

}

