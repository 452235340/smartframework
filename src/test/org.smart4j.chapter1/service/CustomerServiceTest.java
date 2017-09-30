package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter1.entity.Customer;
import org.smart4j.chapter1.service.CustomerService;

import java.util.List;

/**
 * Created by qingbowu on 2017/9/30.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest(){
        customerService = new CustomerService();
    }


    @Before
    public void init(){
        //TODO 初始化数据库

    }

    @Test
    public void getCustomerList() throws Exception{
//        List<Customer> customerList = customerService.getCustomerList();
        List<Customer> customerList = customerService.getCustomerList2();

        Assert.assertEquals(2,customerList.size());
    }











}
