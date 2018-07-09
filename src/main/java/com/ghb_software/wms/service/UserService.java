package com.ghb_software.wms.service;

import com.ghb_software.wms.model.*;
import com.ghb_software.wms.repository.CustomerRepository;
import com.ghb_software.wms.repository.UserRepository;
import com.ghb_software.wms.repository.VerificationTokenRepository;
import com.ghb_software.wms.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailService emailService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Value("${user.activation.token-availability-hours}")
    private int tokenAvailabilityHours;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findOneByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
        com.ghb_software.wms.security.UserDetails userDetails = new com.ghb_software.wms.security.UserDetails(user.getEmail(), user.getPassword(), user.authorities());
        userDetails.setId(user.getId());
        return userDetails;
    }

    public void delete(long id) {
        User user = userRepository.getOne(id);
        user.setActive(false);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public boolean toggleActiveUserById(final long userId) {
        User user = userRepository.getOne(userId);
        user.setActive(!user.isActive());
        return userRepository.save(user).isActive();
    }

    public VerificationToken createVerificationTokenForUser(final User user) {
        VerificationToken token = new VerificationToken(user, tokenAvailabilityHours);
        return verificationTokenRepository.saveAndFlush(token);
    }

    public boolean emailAlreadyExists(String email) {
        Optional<User> incomingUser = userRepository.findOneByEmail(email);

        return incomingUser.isPresent();
    }

    @Transactional
    public void registerUser(User user, List<Group> groups) {
        UserDetails userDetails = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Customer customer = customerRepository.getOne(userDetails.getCustomerId());
        String plainPassword = user.getPassword();

        user.setActive(false);
        user.setDeleted(true);
        user.setLanguage("en");
        user.setCustomer(customer);
        user.setPassword(passwordEncoder.encode(plainPassword));
        user.getGroups().addAll(groups);
        user = userRepository.save(user);

        VerificationToken verificationTokenForUser = createVerificationTokenForUser(user);
        emailService.sendNewCustomerNotification(verificationTokenForUser, plainPassword);
    }
}