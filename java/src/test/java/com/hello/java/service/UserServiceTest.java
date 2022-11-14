package com.hello.java.service;

import com.hello.java.domain.user.User;
import com.hello.java.domain.user.UserRepository;
import com.hello.java.web.dto.UserSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 유저가_생성된다() {

        //given
        String username = "salee2";
        String password = "42gg";

        User user = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .build()
                .toEntity();

        //when
        Long userId = userService.join(user);
        User findUser = userRepository.findById(userId).orElseThrow();

        //then
        assertThat(findUser.getUsername()).isEqualTo(username);
        assertThat(findUser.getPassword()).isEqualTo(password);
    }

    @Test
    public void 유저가_이름으로_검색된다() {
        //given
        String username = "salee2";
        String password = "42gg";

        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        //when
        userRepository.save(user);
        User findUser = userService.findByName(username);

        //then
        assertThat(findUser.getUsername()).isEqualTo(username);
        assertThat(findUser.getPassword()).isEqualTo(password);
    }

    @Test
    public void 유저_이름_중복검사(){
        //given
        String username = "salee2";
        String password = "42gg";

        User user1 = User.builder()
                .username(username)
                .password(password)
                .build();
        User user2 = User.builder()
                .username(username)
                .password(password)
                .build();
        //when
        userService.join(user1);
        //then
        assertThatThrownBy(() -> userService.join(user2)).isInstanceOf(IllegalStateException.class);
    }
}