package com.kigali.supplier_management_system.domain;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "procurement_officer")
public class ProcurementOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email")
    String email;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = true)
    private Branch branch;

    @ManyToMany
    @JoinTable(name = "procurement_officer_supply_category", joinColumns = @JoinColumn(name = "procurement_officer_id"), inverseJoinColumns = @JoinColumn(name = "supply_category_id"))
    List<SupplyCategory> supplyCategories;

    public ProcurementOfficer() {
    }

    public ProcurementOfficer(UUID id, String firstName, String lastName, String email, Branch branch,
            List<SupplyCategory> supplyCategories) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.branch = branch;
        this.supplyCategories = supplyCategories;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<SupplyCategory> getSupplyCategories() {
        return supplyCategories;
    }

    public void setSupplyCategories(List<SupplyCategory> supplyCategories) {
        this.supplyCategories = supplyCategories;
    }

}
