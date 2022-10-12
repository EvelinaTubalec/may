package com.example.may.multitenancy.provider;

import lombok.AllArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.example.may.multitenancy.resolver.TenantSchemaResolver.DEFAULT_TENANT;

/**
 * @author Evelina Tubalets
 */
@Component
@AllArgsConstructor
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(final Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(final String tenantName) throws SQLException {
        final Connection connection = getAnyConnection();
        connection.setSchema(tenantName);
        return connection;
    }

    @Override
    public void releaseConnection(final String tenantName, final Connection connection) throws SQLException {
        connection.setSchema(DEFAULT_TENANT);
        releaseAnyConnection(connection);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
