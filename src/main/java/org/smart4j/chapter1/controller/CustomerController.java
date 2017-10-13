package org.smart4j.chapter1.controller;

import org.smart4j.chapter1.Bean.Param;
import org.smart4j.chapter1.Bean.View;
import org.smart4j.chapter1.annotation.Action;
import org.smart4j.chapter1.annotation.Controller;
import org.smart4j.chapter1.annotation.Inject;
import org.smart4j.chapter1.entity.Customer;
import org.smart4j.chapter1.service.CustomerService;

import java.util.List;

/**
 * Created by qingbowu on 2017/10/10.
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param){
        List<Customer> customerList = customerService.getCustomerList2();

        return null;
    }
}
