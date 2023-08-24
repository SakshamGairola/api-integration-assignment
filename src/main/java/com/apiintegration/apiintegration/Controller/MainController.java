package com.apiintegration.apiintegration.Controller;

import com.apiintegration.apiintegration.Entities.Customer;
import com.apiintegration.apiintegration.Entities.User;
import com.apiintegration.apiintegration.Service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller("/")
public class MainController {

    @Autowired
    private WebClientService webClientService;

    @RequestMapping("/")
    public String red() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        //send a GET request with credentials and get the authentication token
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/login-verify")
    public String verifyLogin(@ModelAttribute("user") User user) {
        try {
            webClientService.fetchAccessToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        return "redirect:/view-all";
    }

    @GetMapping("view-all")
    public ModelAndView getAllCustomer() {
        ModelAndView mav = new ModelAndView("viewAll");
        List<Customer> allCustomers = webClientService.getAllCustomer();
        mav.addObject("allCustomers", allCustomers);
        return mav;
    }

    @GetMapping("delete-customer")
    public String deleteCustomer(@RequestParam(name = "uuid") String uuid) {
        try {
            webClientService.deleteCustomer(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/view-all";
    }

    @GetMapping("get-customer-details")
    public ModelAndView addCustomerForm() {
        ModelAndView mav = new ModelAndView("addCustomer");
        mav.addObject("customer", new Customer());
        return mav;
    }

    @PostMapping("add-customer")
    public String addCustomer(@ModelAttribute("customer") Customer customer) {
        try {
            webClientService.createCustomer(customer);
        } catch (Exception e) {
            e.getMessage();
        }
        return "redirect:/view-all";
    }

    @GetMapping("update-customer-details")
    public ModelAndView updateCustomerForm(@RequestParam(name = "uuid") String uuid) {
        ModelAndView mav = new ModelAndView("updateCustomer");
        Customer customer = new Customer();
        customer.setUuid(uuid);
        mav.addObject("customer", customer);
        return mav;
    }

    @PostMapping("update-customer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) {
        try {
            webClientService.updateCustomer(customer.getUuid(), customer);
        } catch (Exception e) {
            e.getMessage();
        }
        return "redirect:/view-all";
    }

}
