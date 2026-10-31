package com.kigali.supplier_management_system.domain;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "supply_category")
public class SupplyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "name")
    String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "supplyCategories")
    List<ProcurementOfficer> procurementOfficers;

    public SupplyCategory() {
    }

    public SupplyCategory(UUID id, String name, List<ProcurementOfficer> procurementOfficers) {
        this.id = id;
        this.name = name;
        this.procurementOfficers = procurementOfficers;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProcurementOfficer> getProcurementOfficers() {
        return procurementOfficers;
    }

    public void setProcurementOfficers(List<ProcurementOfficer> procurementOfficers) {
        this.procurementOfficers = procurementOfficers;
    }

}
