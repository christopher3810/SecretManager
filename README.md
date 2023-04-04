# **SecretManager Module** 🤫

![GitHub last commit](https://img.shields.io/github/last-commit/christopher3810/SecretManager?style=flat-square) ![GitHub Repo stars](https://img.shields.io/github/stars/christopher3810/SecretManager?style=flat-square)


### SecretManager Module

<img src="https://user-images.githubusercontent.com/61622657/229943926-164d1ccd-9414-4eae-94a2-7bffe29b0de8.jpg" width="500" height="500">




## **Concept 및 초기 설계** 💡
<br>

Vault, Redis, Spring Boot를 활용하여 회원 비밀 번호 및 정보를 저장하고 관리하는 모듈.\
회원 비밀번호를 관리하는 모든 Spring Boot 기반 웹 서비스에서 dependency를 추가하여 사용.

초기 목표 🎯
1. docker container 실습 환경 구축 및 readme 에 필요 명령어 정리.
2. Vault , Redis, Spring Service 연동

<br>

## **Architecture**  📐


초기 설계 구성

![scm_arch_dark_advenced](https://user-images.githubusercontent.com/61622657/225190246-7a2544a0-ea1e-4503-ae0e-9cf99ec1a800.png)


Redis 데이터베이스는 회원 비밀번호와 멤버십 정보를 저장하는 데 사용되며, Vault 서버는 Redis 데이터베이스에 액세스하는 데 사용되는 비밀번호를 안전하게 관리하는 데 사용.

## **Functionality** 🔧

모듈의 주요 기능.\
This module provides the following functionality

1. user member 관련 정보를 redis에 캐싱하여 성능적 이점 확보 및 동시접속 이슈 회피 ⚡
2. vault를 활용한 대한 인증 및 권한 부여 🔑
3. vault에서 secret을 가져와서 사용해 redis에 엑세스 🔓

<br>

## **Dependencies** 🔗

- Spring Boot
- Redis
- Vault
- Gradle

<br>

## Dependency Installation 💾

1. jitpack으로 배포
2. plain.jar 사용
3. build.gradle에 아래 와 같이 입력후 lib 다운로드 후사용.


```gradle

//...

repositories {
//...
    maven { url 'https://jitpack.io' }
}

dependencies {
    //...
    implementation 'com.github.christopher3810:SecretManager:${relase_version}:plain'
}

```

## **Usage** 🖥️

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

## **Configuration** ⚙️

위 프로젝트를 의존성 추가해서 사용하기 위해서는 아래와 같은 Config 설정이 필요합니다.

```yaml

# Email configuration
app:
  base-url: https://${your-application-url}.com
  email:
    sender: ${your-email@example}.com
    mail-host: smtp.example.com
    mail-port: 587
    mail-username: ${EMAIL_USERNAME}
    mail-password: ${EMAIL_PASSWORD}
    mail-smtp-auth: true
    mail-smtp-starttls-enable: true

# JWT configuration# JWT configuration
    
jwt:
  secret: ${JWT_SECRET}
  refreshTokenExpirationInMs: 86400000


# Database configuration
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driverClassName: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

# Reactive Redis configuration
spring:
  redis:
    reactive:
      url: redis://${REDIS_HOST}:${REDIS_PORT}

# Vault configuration
spring:
  cloud:
    vault:
      token: ${VAULT_TOKEN}
      scheme: https
      host: ${VAULT_HOST}
      port: ${VAULT_PORT}
      config:
        order: -10

```

## License

### MIT License

<br>

![rainbow](https://media.tenor.com/S-TQKsUL38YAAAAC/rainbow-spongebob.gif)
