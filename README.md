# **SecretManager Module**



# **Concept 및 초기 설계**
<br>

Vault, Redis, Spring Boot를 활용하여 회원 비밀 번호 및 정보를 저장하고 관리하는 모듈입니다.
회원 비밀번호를 관리하는 모든 Spring Boot 기반 웹 서비스에서 dependency를 추가하여 사용할 수 있습니다.

This is a module that utilizes Vault, Redis, and Spring Boot to store and manage member secrets and information. It can be used as a dependency in any Spring Boot based web service that manages member secrets.

<br>

## **Architecture**

초기 설계 구성

The proposed architecture for this module is as follows:


```
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

용할 수 있는 API를 노출합니다. Redis 데이터베이스는 회원 비밀번호와 멤버십 정보를 저장하는 데 사용되며, Vault 서버는 Redis 데이터베이스에 액세스하는 데 사용되는 비밀번호를 안전하게 관리하는 데 사용.

The Spring Boot web service exposes an API that allows clients to interact with the Redis database and Vault server. The Redis database is used to store member secrets and membership information, while the Vault server is used to securely manage the secrets used to access the Redis database.

## **Functionality**

This module provides the following functionality:

1. user member 관련 정보를 redis에 캐싱하여 성능적 이점 확보 및 동시접속 이슈 회피
2. vault를 활용한 대한 인증 및 권한 부여
3. vault에서 secret을 가져와서 사용해 redis에 엑세스

<br>

1. Store and retrieve member secrets and membership information from Redis.
2. Authenticate and authorize requests using Vault.
3. Retrieve secrets from Vault and use them to access Redis.

<br>

## **Dependencies**

- Spring Boot
- Redis
- Vault
- Gradle

<br>

## **Installation**

가능하다면 installation 까지 지원하기

```
groovyCopy code
dependencies {
    implementation 'com.example:secret-manager-boot:1.0.0'
}

```

## **Usage**

**`SecretManagerService`** class 를 의존성 주입응ㄹ 받고 메서드를 활용하여 사용하는 방식.

To use this module in your Spring Boot web service, you can autowire the **`SecretManagerService`** class and use its methods to store and retrieve member secrets and membership information from Redis.

```
javaCopy code
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

To use this module with your own Redis and Vault servers, you need to configure the following properties in your **`application.properties`** or **`application.yml`** file:

```
yamlCopy code
spring.redis.host=redis-host
spring.redis.port=6379

```