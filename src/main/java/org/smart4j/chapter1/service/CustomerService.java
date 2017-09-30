package org.smart4j.chapter1.service;

import org.apache.log4j.Logger;
import org.smart4j.chapter1.entity.Customer;
import org.smart4j.chapter1.helper.DatabaseHelper;
import org.smart4j.chapter1.util.PropsUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by qingbowu on 2017/9/30.
 */
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
        }finally {
            DatabaseHelper.closeConnection();
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
        //TODO
        return null;
    }

    /**
     * 新增客户
     * @param customer
     * @return
     */
    public boolean addCustmer(Customer customer){
        //TODO
        return false;
    }

    /**
     * 修改客户
     * @param customer
     * @return
     */
    public boolean updateCustmer(Customer customer){
        //TODO
        return false;
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean deleteCustmer(Long id){
        //TODO
        return false;
    }
}
