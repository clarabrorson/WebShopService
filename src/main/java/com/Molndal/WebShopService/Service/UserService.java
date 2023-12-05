package com.Molndal.WebShopService.Service;

import com.Molndal.WebShopService.Models.History;
import com.Molndal.WebShopService.Models.User;
import com.Molndal.WebShopService.Repository.HistoryRepository;
import com.Molndal.WebShopService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    public List<History> getPurchaseHistory(){
        User currentUser = getCurrentUser();
        return historyRepository.findByUser(currentUser);
    }
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
