package com.kigali.supplier_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kigali.supplier_management_system.service.BranchService;

@RestController
@RequestMapping(value = "/api/branchs")
public class BranchController {

    @Autowired
    private BranchService branchService;

}
