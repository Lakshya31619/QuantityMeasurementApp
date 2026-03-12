package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository
        implements IQuantityMeasurementRepository {

    private final ConnectionPool pool = new ConnectionPool();

    @Override
    public void save(QuantityMeasurementEntity entity) {

        String sql = "INSERT INTO quantity_measurement_entity " +
                "(operation_type, measurement_type, value1, unit1, value2, unit2, result, timestamp) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        Connection conn = null;

        try {

            conn = pool.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, entity.getOperationType());
                ps.setString(2, entity.getMeasurementType());
                ps.setDouble(3, entity.getValue1());
                ps.setString(4, entity.getUnit1());
                ps.setDouble(5, entity.getValue2());
                ps.setString(6, entity.getUnit2());
                ps.setDouble(7, entity.getResult());
                ps.setTimestamp(8, entity.getTimestamp());

                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new DatabaseException("Error saving measurement", e);
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM quantity_measurement_entity";

        Connection conn = null;

        try {

            conn = pool.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    QuantityMeasurementEntity entity =
                            new QuantityMeasurementEntity();

                    entity.setOperationType(rs.getString("operation_type"));
                    entity.setMeasurementType(rs.getString("measurement_type"));
                    entity.setValue1(rs.getDouble("value1"));
                    entity.setUnit1(rs.getString("unit1"));
                    entity.setValue2(rs.getDouble("value2"));
                    entity.setUnit2(rs.getString("unit2"));
                    entity.setResult(rs.getDouble("result"));
                    entity.setTimestamp(rs.getTimestamp("timestamp"));

                    list.add(entity);
                }
            }

        } catch (Exception e) {
            throw new DatabaseException("Error retrieving measurements", e);
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }

        return list;
    }

    @Override
    public int getTotalCount() {

        String sql = "SELECT COUNT(*) FROM quantity_measurement_entity";

        Connection conn = null;

        try {

            conn = pool.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new DatabaseException("Error counting measurements", e);
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }

        return 0;
    }

    @Override
    public void deleteAll() {

        String sql = "DELETE FROM quantity_measurement_entity";

        Connection conn = null;

        try {

            conn = pool.getConnection();

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.executeUpdate();
            }

        } catch (Exception e) {
            throw new DatabaseException("Error deleting measurements", e);
        } finally {
            if (conn != null) {
                pool.releaseConnection(conn);
            }
        }
    }

    @Override
    public void releaseResources() {
        // If connection pool later supports closing connections
        // cleanup logic will go here
    }
}