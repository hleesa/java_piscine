package com.hello.java.service;

import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /*
        회원 가입
     */
    @Transactional
    public Long join(User user) {

        verifyDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void verifyDuplicateUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User findByName(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }


}
