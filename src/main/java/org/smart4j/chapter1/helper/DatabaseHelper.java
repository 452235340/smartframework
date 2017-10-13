package org.smart4j.chapter1.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter1.util.CollectionUtil;
import org.smart4j.chapter1.util.PropsUtil;
import org.smart4j.chapter1.util.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作帮助类
 * Created by qingbowu on 2017/9/30.
 */
public final class DatabaseHelper {

    private static final ThreadLocal<Connection> THREADLOCAL_HOLDER;

    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    static {
        THREADLOCAL_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties properties = PropsUtil.loadProps("config.properties");
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = THREADLOCAL_HOLDER.get();
        if(connection == null){
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure",e);
            }
        }
        return connection;
    }





    /**
     * 获取实体列表
     * @param sql
     * @param entityClass
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(String sql,Class<T> entityClass,Object... params){
        List<T> entityList = null;
        try {
            Connection connection = getConnection();
            entityList = QUERY_RUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity List failure",e);
            throw new RuntimeException(e);
        }
        return entityList;
    }


    /**
     * 获取实体
     * @param sql
     * @param entityClass
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(String sql,Class<T> entityClass,Object... params){
        T entity = null;
        try {
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            LOGGER.error("query entity failure",e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 执行查询语句(支持连表查询)
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String,Object>> execueteQuery(String sql,Object... params){
        List<Map<String,Object>> resultList = null;
        try {
            Connection connection = getConnection();
            resultList = QUERY_RUNNER.query(connection,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("execuete query failure",e);
            throw new RuntimeException(e);
        }
        return resultList;
    }

    /**
     * 执行更新语句
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql,Object... params){
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection,sql,params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure",e);
            throw  new RuntimeException(e);
        }

        return rows;
    }



    /**
     * 插入实体
     * @param entityClass
     * @param paramsMap
     * @param <T>
     * @return
     */
    public static <T> boolean  insertEntity(Class<T> entityClass,Map<String,Object> paramsMap){
        if(CollectionUtil.isEmpty(paramsMap)){
            LOGGER.error("can not insert entity : paramsMap is empty");
            return false;
        }
        //动态拼装sql语句
        String sql = " INSERT INTO "+getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        for (String fieldName:paramsMap.keySet()){
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "),columns.length(),")");
        values.replace(values.lastIndexOf(", "),values.length(),")");

        sql = sql + columns + "VALUES" + values;

        Object[] params = paramsMap.values().toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     *更新实体
     */
    public static <T> boolean  updateEntity(Class<T> entityClass,Long id,Map<String,Object> paramsMap){
        if(CollectionUtil.isEmpty(paramsMap)){
            LOGGER.error("can not insert entity : paramsMap is empty");
            return false;
        }
        //动态拼装sql语句
        String sql = " UPDATE "+getTableName(entityClass) +" SET ";
        StringBuilder columns = new StringBuilder("(");

        for (String fieldName:paramsMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }
        columns.replace(columns.lastIndexOf(", "),columns.length(),")");

        sql = sql + columns + "WHERE id =?";

        List<Object> paramsList = new ArrayList();
        paramsList.addAll(paramsMap.values());
        paramsList.add(id);
        Object[] paramArry = paramsList.toArray();

        return executeUpdate(sql,paramArry) == 1;
    }

    /**
     * 删除实体
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass,Long id){
        String sql = " DELETE "+getTableName(entityClass) + "WHERE id=?";
        return executeUpdate(sql,id) == 1;
    }



    /**
     * 根据class对象获取对应的表名
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> entityClass){
        String name = entityClass.getName();
        if(StringUtil.isEmpty(name)){
            LOGGER.error("entity class name is empty,can not find table name");
            return "";
        }
        return name;
    }




}
