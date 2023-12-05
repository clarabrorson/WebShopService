package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.Role;
import com.Molndal.WebShopService.Repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

}
