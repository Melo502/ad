package com.example.advserv.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericDao<T> {

    protected Connection connection;

    public GenericDao(Connection connection) {
        this.connection = connection;
    }

    // 插入数据
    public int insert(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(statement, params);
            int rowsAffected = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);  // 返回生成的主键ID
            }
            return 0;
        }
    }

    // 更新数据
    public int update(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            return statement.executeUpdate();
        }
    }

    // 删除数据
    public int delete(String sql, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            return statement.executeUpdate();
        }
    }

    // 查询单个实体
    public T findOne(String sql, RowMapper<T> rowMapper, Object... params) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet);
            }
            return null;
        }
    }

    // 查询多个实体
    public List<T> findAll(String sql, RowMapper<T> rowMapper, Object... params) throws SQLException {
        List<T> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            setParameters(statement, params);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                resultList.add(rowMapper.mapRow(resultSet));
            }
        }
        return resultList;
    }

    // 设置 PreparedStatement 的参数
    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    public AutoCloseable getConnection() {
        return connection;
    }

    // RowMapper 接口用于将结果集行映射为实体对象
    public interface RowMapper<T> {
        T mapRow(ResultSet resultSet) throws SQLException;
    }
}

