`@EnableJpaAuditing` 어노테이션은 Spring Data JPA에서 제공하는 감사(Auditing) 기능을 활성화하는 데 사용됩니다. 감사 기능은 엔티티의 생성, 수정 등의 이벤트를 추적하고 기록하는 데 사용됩니다.

`@EnableJpaAuditing` 어노테이션을 사용하면 다음과 같은 기능을 사용할 수 있습니다:

1. `@CreatedDate`: 엔티티의 생성 시간을 자동으로 기록합니다. 해당 필드에 `@CreatedDate` 어노테이션을 적용하면 엔티티가 저장될 때 현재 시간이 자동으로 설정됩니다.

2. `@LastModifiedDate`: 엔티티의 최종 수정 시간을 자동으로 기록합니다. 해당 필드에 `@LastModifiedDate` 어노테이션을 적용하면 엔티티가 수정될 때마다 현재 시간이 자동으로 업데이트됩니다.

3. `@CreatedBy`: 엔티티의 생성자를 자동으로 기록합니다. 해당 필드에 `@CreatedBy` 어노테이션을 적용하면 엔티티가 저장될 때 현재 인증된 사용자의 정보가 자동으로 설정됩니다.

4. `@LastModifiedBy`: 엔티티의 최종 수정자를 자동으로 기록합니다. 해당 필드에 `@LastModifiedBy` 어노테이션을 적용하면 엔티티가 수정될 때마다 현재 인증된 사용자의 정보가 자동으로 업데이트됩니다.

`@EnableJpaAuditing` 어노테이션을 사용하려면 다음과 같은 단계를 따릅니다:

1. `@Configuration` 어노테이션이 적용된 클래스에 `@EnableJpaAuditing` 어노테이션을 추가합니다.

```java
@Configuration
@EnableJpaAuditing
public class JpaConfig {
    // ...
}
```

2. 감사 정보를 저장할 엔티티에 적절한 어노테이션을 적용합니다.

```java
@Entity
public class MyEntity {
    // ...

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    // ...
}
```

3. `@CreatedBy`와 `@LastModifiedBy`를 사용하려면 `AuditorAware` 인터페이스를 구현하여 현재 인증된 사용자 정보를 제공해야 합니다.

```java
@Component
public class MyAuditorAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // 현재 인증된 사용자 정보를 반환
        // 예: Spring Security를 사용하는 경우
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Optional.of(authentication.getName());
        }
        return Optional.empty();
    }
}
```

위의 설정을 완료하면 Spring Data JPA는 엔티티의 생성, 수정 시 자동으로 감사 정보를 기록할 것입니다.

`@EnableJpaAuditing` 어노테이션은 JPA 감사 기능을 편리하게 사용할 수 있도록 지원하며, 엔티티의 변경 이력을 추적하고 관리하는 데 유용합니다. 이를 통해 엔티티의 생성일, 수정일, 생성자, 수정자 등의 정보를 자동으로 기록할 수 있습니다.
