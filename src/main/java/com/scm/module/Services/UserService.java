package com.scm.module.Services;

import com.scm.module.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

//TODO : Conntroller는 서비스를 활용해서 간단하게 처리
// 데이터 관련 서비스 , 사용자 이벤트별 서비스 별도 존재.

@Service
@AllArgsConstructor
public class UserService {

    private final UserDataService userDataServicel;


    public Mono<Void> signUp(UserDto userDto) {
      //UserDataService를 가져다가 사용.
    }
}