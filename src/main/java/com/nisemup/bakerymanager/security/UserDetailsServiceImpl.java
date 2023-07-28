package com.nisemup.bakerymanager.security;

import com.nisemup.bakerymanager.model.Employees;
import com.nisemup.bakerymanager.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeesRepository employeesRepository;

    @Autowired
    public UserDetailsServiceImpl(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employees employees = employeesRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found!"));
        return SecurityEmployees.fromEmployee(employees);
    }
}
