package org.smart4j.chapter1;

import org.smart4j.chapter1.entity.Customer;
import org.smart4j.chapter1.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by qingbowu on 2017/10/10.
 */
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customerList = customerService.getCustomerList2();
        req.setAttribute("customerList",customerList);
        req.getRequestDispatcher("/WEB-INF/jsp/customer.jsp").forward(req ,resp );
    }
}
