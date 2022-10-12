package com.example.may.multitenancy.controller;

import com.example.may.multitenancy.service.TenantService;
import liquibase.exception.LiquibaseException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Evelina Tubalets
 */
@RestController
@AllArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/tenants")
    public void createTenant(@RequestParam String schema) throws LiquibaseException {
        this.tenantService.createTenant(schema);
    }
}
