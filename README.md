# **SecretManager Module**



## **Concept 및 초기 설계**
<br>

Vault, Redis, Spring Boot를 활용하여 회원 비밀 번호 및 정보를 저장하고 관리하는 모듈.\
회원 비밀번호를 관리하는 모든 Spring Boot 기반 웹 서비스에서 dependency를 추가하여 사용.

<br>

## **Architecture**

초기 설계 구성

```plainText
+-------------------------------+
|     Spring Boot Web Service   |
+-------------------------------+
                |
                |
         +--------------+
         |      API     |
         +--------------+
                |
                |
         +--------------+
         |   Redis DB   |
         +--------------+
                |
                |
         +--------------+
         | Vault Server |
         +--------------+

```

Redis 데이터베이스는 회원 비밀번호와 멤버십 정보를 저장하는 데 사용되며, Vault 서버는 Redis 데이터베이스에 액세스하는 데 사용되는 비밀번호를 안전하게 관리하는 데 사용.

## **Functionality**

모듈의 주요 기능.\
This module provides the following functionality

1. user member 관련 정보를 redis에 캐싱하여 성능적 이점 확보 및 동시접속 이슈 회피
2. vault를 활용한 대한 인증 및 권한 부여
3. vault에서 secret을 가져와서 사용해 redis에 엑세스

<br>

## **Dependencies**

- Spring Boot
- Redis
- Vault
- Gradle

<br>

## **Installation**

가능하다면 installation 까지 지원하기

```gradle
dependencies {
    implementation 'com.example:secret-manager-boot:1.0.0'
}

```

## **Usage**

**`SecretManagerService`** class 를 의존성 주입을 받고 메서드를 활용하여 사용하는 방식.



```java
@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private VaultRedisService vaultRedisService;

    @PostMapping
    public void addMember(@RequestBody Member member) {
        vaultRedisService.setMember(member.getId(), member);
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable String id) {
        return vaultRedisService.getMember(id);
    }
}

```

## **Configuration**

**`application.properties`** or **`application.yml`**  값에 설정값이 필요한 경우 추가 가이드

- 예시

```yml
spring.redis.host=redis-host
spring.redis.port=6379

```