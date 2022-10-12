package com.example.may.multitenancy.service;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author Evelina Tubalets
 */
@Service
@AllArgsConstructor
public class TenantService {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;
    private final LiquibaseProperties liquibaseProperties;
    private final ResourceLoader resourceLoader;

    public void createTenant(final String schema) throws LiquibaseException {
        createSchema(schema);
        runLiquibase(dataSource, schema);
    }

    private void createSchema(final String schema) {
        jdbcTemplate.execute((StatementCallback<Boolean>) stmt -> stmt.execute("CREATE SCHEMA " + schema));
    }

    private void runLiquibase(final DataSource dataSource, final String schema) throws LiquibaseException {
        final SpringLiquibase liquibase = getSpringLiquibase(dataSource, schema);
        liquibase.afterPropertiesSet();
    }

    private SpringLiquibase getSpringLiquibase(final DataSource dataSource, final String schema) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setResourceLoader(resourceLoader);
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(schema);
        liquibase.setChangeLog(liquibaseProperties.getChangeLog());
        liquibase.setContexts(liquibaseProperties.getContexts());
        return liquibase;
    }
}
