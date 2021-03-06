package ru.technoserv.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.technoserv.dao.DepartmentDao;
import ru.technoserv.dao.EmployeeDao;
import ru.technoserv.domain.DepWithChildren;
import ru.technoserv.domain.Department;
import ru.technoserv.domain.EmployeeHistory;
import ru.technoserv.exceptions.StuffExceptions;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class HibernateDepartmentDao implements DepartmentDao {

    private static final Logger logger = Logger.getLogger(HibernateEmployeeDao.class);

    @Autowired
    CacheManager cacheManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EmployeeDao employeeDao;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    private static String sqlQueryForSubDepts1 = "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT START WITH PARENT_DEPT_ID = ";

    private static String sqlQueryForSubDepts2 =
            "CONNECT BY  PRIOR  DEPT_ID = PARENT_DEPT_ID";

    @Override
    @CacheEvict(cacheNames = "subdepts", allEntries = true, beforeInvocation = true)
    public Integer create(Department department) {
        logger.info("Запрос к базе на создание отдела");
        Serializable id;
        Session session = getSession();
        try{
            id = session.save(department);
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }

        return (Integer) id;
    }

    @Override
    @Cacheable(cacheNames = "department", key = "#depId")
    public Department readById(Integer depId) {
        logger.info("Запрос к базе на получение отдела c ID: " + depId);
        Department department;
        Session session = getSession();
        try{
            department = (Department) session.get(Department.class, depId);
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }
        if(department==null) throw new RuntimeException(StuffExceptions.NOT_FOUND.toString());

        return department;
    }

    @Override
    @Caching(evict ={@CacheEvict(cacheNames = "subdepts", allEntries = true, beforeInvocation = true)},
            put = {@CachePut(cacheNames = "department", key = "#department.id")})
    public Department updateDept(Department department) {
        logger.info("Запрос к базе на изменение отдела c ID: " + department.getId());
        Session session = getSession();
        try{
            session.update(department);
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }
        return readById(department.getId());
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "subdepts", allEntries = true, beforeInvocation = true),
            @CacheEvict(cacheNames = "department", key = "#depId", beforeInvocation = true)})
    public void delete(Integer depId) {
        logger.info("Запрос к базе на удаление отдела c ID: " + depId);
        Department department = readById(depId);
        Session session = getSession();
        try{
            session.delete(department);
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }
    }

    //TODO Cвязать с кэшем "Employee"?
    @Override
    public EmployeeHistory getDeptHead(Integer depId) {
        logger.info("Запрос к базе на чтение начальника отдела c ID: " + depId);
        Department department = readById(depId);
        return new EmployeeHistory();//employeeDao.read(department.getDeptHeadId());
    }

    @Override
    public List<Department> getDepartmentsList() {
        logger.info("Запрос к базе на чтение списка всех отделов");
        List<Department> dhList;

        Session session = getSession();
        try{
            Criteria crit = session.
                    createCriteria(Department.class);
            dhList = crit.list();
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }

        return dhList;
    }

    @Override
    @Cacheable(cacheNames = "subdepts", key = "#depId")
    public List<Department> getAllSubDepts(Integer depId) {
        logger.info("Запрос к базе на чтение подразделений отдела с ID: " + depId);
        List<Department> departments;
        Session session = getSession();
        try{
            departments = session.createSQLQuery(sqlQueryForSubDepts1+depId+sqlQueryForSubDepts2).addEntity(Department.class).list();
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }

        return departments;
    }

    @Override
    public List<Department> getLevelBelowSubDepts(Integer depId) {
        throw new NotImplementedException();
    }

    @Override
    public List<DepWithChildren> getHierarchy() {
        List<DepWithChildren> departments;
        String sql = "SELECT DEPT_ID, PARENT_DEPT_ID, DEPT_NAME, DEPT_HEAD_ID FROM DEPARTMENT WHERE PARENT_DEPT_ID IS NULL";
        Session session = getSession();
        try{
            departments = session.createSQLQuery(sql).addEntity(DepWithChildren.class).list();
        }catch (HibernateException e){
            logger.error(e.getMessage());
            throw new RuntimeException(StuffExceptions.DATABASE_ERROR.toString(),e);
        }

        return departments;
    }

}
