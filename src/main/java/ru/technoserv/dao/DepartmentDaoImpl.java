package ru.technoserv.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {
    private static final String CREATE_DEPARTMENT =
            "INSERT INTO DEPARTMENT (DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID) VALUES (?, ?, ?, ?)";
    private static final String SELECT_DEPARTMENT_BY_ID =
            "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT WHERE DEPT_ID = ?";
    private static final String DELETE_DEPARTMENT = "DELETE FROM DEPARTMENT WHERE DEPT_ID = ?";
    private static final String SELECT_DEPARTMENTS_LIST =
            "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT";
    private static final String SELECT_ALL_SUB_DEPTS =
            "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT START WITH PARENT_DEPT_ID = ? " +
                    "CONNECT BY  PRIOR  DEPT_ID = PARENT_DEPT_ID";
    private static final String SELECT_LEVEL_BELOW_SUB_DEPTS =
            "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT WHERE PARENT_DEPT_ID = ?";
    private static final String UPDATE_PARENT_DEPT_ID =
            "UPDATE DEPARTMENT SET PARENT_DEPT_ID = ? WHERE DEPT_ID = ?";
    private static final String UPDATE_DEPT_HEAD =
            "UPDATE DEPARTMENT SET DEPT_HEAD_ID = ? WHERE DEPT_ID = ?";
    private static final String SELECT_MAX_ID = "SELECT MAX(DEPT_ID) AS DEPT_ID FROM DEPARTMENT";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int getID() {
        SqlRowSet set = jdbcTemplate.queryForRowSet(SELECT_MAX_ID);
        set.first();
        return set.getInt("DEPT_ID");
    }

    @Override
    public void create(Department department) {
        jdbcTemplate.update(
                CREATE_DEPARTMENT, department.getId(), department.getParentDeptId(),
                department.getDeptName(), department.getDeptHeadId());
    }

    @Override
    public Department readById(Integer depId) {
        return jdbcTemplate.queryForObject(SELECT_DEPARTMENT_BY_ID, new DepartmentRowMapper(), depId);
    }

    @Override
    public void updateDeptHead(Integer newDeptHeadId, Integer depId) {
        jdbcTemplate.update(UPDATE_DEPT_HEAD, newDeptHeadId, depId);
    }

    @Override
    public void updateParentDeptId(Integer newParentDeptId, Integer depId) {
        jdbcTemplate.update(UPDATE_PARENT_DEPT_ID, newParentDeptId, depId);
    }

    @Override
    public void delete(Integer depId) {
        jdbcTemplate.update(DELETE_DEPARTMENT, depId);
    }

    @Override
    public List<Department> getDepartmentsList() {
        return jdbcTemplate.query(SELECT_DEPARTMENTS_LIST, new DepartmentRowMapper());
    }

    @Override
    public List<Department> getAllSubDepts(Integer depId) {
        return jdbcTemplate.query(SELECT_ALL_SUB_DEPTS, new DepartmentRowMapper(), depId);
    }

    @Override
    public List<Department> getLevelBelowSubDepts(Integer depId) {
        return jdbcTemplate.query(SELECT_LEVEL_BELOW_SUB_DEPTS, new DepartmentRowMapper(), depId);
    }

    private static class DepartmentRowMapper implements RowMapper<Department> {

        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Department(
                    resultSet.getInt("DEPT_ID"),
                    resultSet.getInt("PARENT_DEPT_ID"),
                    resultSet.getString("DEPT_NAME"),
                    resultSet.getInt("DEPT_HEAD_ID")
            );
        }

    }
}