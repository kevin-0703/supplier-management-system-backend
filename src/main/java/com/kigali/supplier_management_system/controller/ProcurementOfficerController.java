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

import com.kigali.supplier_management_system.domain.ProcurementOfficer;
import com.kigali.supplier_management_system.service.ProcurementOfficeService;

@RestController
@RequestMapping(value = "/api/procurement-officers")
public class ProcurementOfficerController {

    @Autowired
    private ProcurementOfficeService procurementOfficeService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveProcurementOffice(@RequestBody ProcurementOfficer procurementOfficer) {
        ProcurementOfficer officer = procurementOfficeService.saveProcurementOfficer(procurementOfficer);
        return new ResponseEntity<>("Procurement officer saved successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllProcurementOfficers() {
        List<ProcurementOfficer> officer = procurementOfficeService.getAllProcurementOfficers();

        if (officer.isEmpty()) {
            return new ResponseEntity<>("Procurement Officers not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(officer, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProcurementOfficerById(@PathVariable UUID id) {
        Optional<ProcurementOfficer> officer = procurementOfficeService.getProcurementOfficerById(id);
        if (officer.isEmpty()) {
            return new ResponseEntity<>("The procurement office with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(officer, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateProcurementOfficer(@PathVariable UUID id,
            @RequestBody ProcurementOfficer procurementOfficer) {
        Optional<ProcurementOfficer> officer = procurementOfficeService.updateProcurementOfficer(id,
                procurementOfficer);

        if (officer.isEmpty()) {
            return new ResponseEntity<>("The Procurement Officer or Branch does not exist", HttpStatus.NOT_FOUND);
        }

        if (officer == null) {
            return new ResponseEntity<>("The Categories with those names do not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Procurement officer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteProcurementOfficer(@PathVariable UUID id) {
        Optional<ProcurementOfficer> officer = procurementOfficeService.deleteProcurementOfficer(id);

        if (officer.isEmpty()) {
            return new ResponseEntity<>("The procurement officer with that id does not exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Procurement officer deleted successfully", HttpStatus.OK);
    }

}
