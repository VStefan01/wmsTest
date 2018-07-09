package com.ghb_software.wms.service;

import com.ghb_software.wms.model.Customer;
import com.ghb_software.wms.model.User;
import com.ghb_software.wms.repository.CustomerRepository;
import com.ghb_software.wms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupService groupService;


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    @Transactional
    public void registerNewCustomer(Customer customer, User user) {
        customer = customerRepository.save(customer);

        user.setActive(false);
        user.setLanguage("en");
        user.setCustomer(customer);
        user.getGroups().add(groupService.getAdminGroup());
        String password = newRandomPassword();
        user.setPassword(passwordEncoder.encode(password));
        System.out.println("### New user " + user.getEmail() + " created with temp password: " + password);
        user = userRepository.save(user);

    }

    private String newRandomPassword() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            double index = Math.random() * characters.length();
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
