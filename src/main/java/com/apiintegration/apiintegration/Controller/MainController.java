package com.apiintegration.apiintegration.Controller;

import com.apiintegration.apiintegration.Entities.Customer;
import com.apiintegration.apiintegration.Entities.User;
import com.apiintegration.apiintegration.Service.WebClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class MainController {

    @Autowired
    private WebClientService webClientService;

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        //send a GET request with credentials and get the authentication token
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping("/loginget")
    public ModelAndView loginHandler(@ModelAttribute("user") User user) throws JsonProcessingException {
        User user2 = new User("test@sunbasedata.com", "Test@123");
        webClientService.fetchAccessToken(user2);
                Customer customer = Customer.builder()
                .first_name("first_name").last_name("last_name")
                .address("address").state("state")
                .city("city").phone("4456").street("street").email("sda")
                .build();
        System.out.println(webClientService.updateCustomer("test0c42ceaad3fd4cf98881b6b00613cdad", customer));

//        Customer c = Customer.builder()
//                .first_name("Test").last_name("test")
//                .address("del").state("del")
//                .city("de").phone("4").street("sds").email("sda")
//                .build();
//
//        System.out.println(webClientService.createCustomer(c));
//        System.out.println((new Customer().builder().first_name("Test").last_name("test").build()));
//        System.out.println(webClientService.createCustomer(new Customer().builder().last_name("test").build()));
//
//        "login_id" : "test@sunbasedata.com",
//                "password" :"Test@123"
//    }https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp

    ModelAndView mav = new ModelAndView("");
        if (true) {
            // resp 200
            mav.setViewName(""); //to all view
        }
        return mav;
    }

}
