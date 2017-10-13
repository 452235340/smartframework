package org.smart4j.chapter1.service;

import org.apache.log4j.Logger;
import org.smart4j.chapter1.annotation.Service;
import org.smart4j.chapter1.entity.Customer;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.util.PropsUtil;

import java.sql.*;
import java.util.*;

/**
 * Created by qingbowu on 2017/9/30.
 */
@Service
public class CustomerService {

    private static final Logger LOGGER = Logger.getLogger(CustomerService.class);







    /**
     * 获取客户列表
     * @return
     */
    public List<Customer> getCustomerList(){
        Connection connection = null;
        List<Customer> list = new ArrayList<Customer>();
        String sql = "select * from customer";
        try {
            connection = DatabaseHelper.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));

                list.add(customer);
            }
        } catch (SQLException e) {
            LOGGER.error("execute sql failure",e);
        }
        return list;
    }

    /**
     * 获取客户列表(使用DBUtil)
     * @return
     */
    public List<Customer> getCustomerList2(){
        Connection connection = null;
        List<Customer> list = new ArrayList<Customer>();
        String sql = "select * from customer";
        List<Customer>customerList = DatabaseHelper.queryEntityList(sql,Customer.class);
        return customerList;

    }

    /**
     * 获取客户
     * @param
     * @return
     */
    public Customer getCustomer(Long id){
        String sql = "select * from customer where id = ?";
        return DatabaseHelper.queryEntity(sql, Customer.class, id);
    }

    /**
     * 新增客户
     * @param customer
     * @return
     */
    public boolean addCustmer(Customer customer){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        return DatabaseHelper.insertEntity(Customer.class,paramsMap);
    }

    /**
     * 修改客户
     * @param customer
     * @return
     */
    public boolean updateCustmer(Customer customer){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        return DatabaseHelper.updateEntity(Customer.class,customer.getId(),paramsMap);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustmer(Long id){
        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
