package kr.easw.lesson06.service;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import kr.easw.lesson06.model.dto.UserAuthenticationDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.model.repository.UserDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDataService {
    private final UserDataRepository repository;

    private final BCryptPasswordEncoder encoder;

    private final JwtService jwtService;

    // @PostConstruct 어노테이션을 사용하여 이 메서드가 빈 생성 후에 실행되도록 합니다.
    @PostConstruct
    public void init() {
        // 이 메서드는 애플리케이션이 시작될 때 실행됩니다.
        System.out.println("Creating admin user");
        // 만약 admin이라는 아이디를 가진 유저가 없다면, admin이라는 아이디를 가진 유저를 생성합니다.
        createUser(new UserDataEntity(0L, "admin", encoder.encode("admin"), true));
        createUser(new UserDataEntity(0L, "guest", encoder.encode("guest"), false));
    }

    // 이 메서드는 유저가 존재하는지 확인합니다.
    public boolean isUserExists(String userId) {
        return repository.findUserDataEntityByUserId(userId).isPresent();
    }

    // 이 메서드는 유저를 생성합니다.
    public void createUser(UserDataEntity entity) {
        repository.save(entity);
    }

    // 이 메서드는 유저를 생성하고, 생성된 유저의 토큰을 반환합니다.
    @Nullable
    public UserAuthenticationDto createTokenWith(UserDataEntity userDataEntity) {
        // 만약 유저가 존재하지 않는다면, BadCredentialsException을 던집니다.
        Optional<UserDataEntity> entity = repository.findUserDataEntityByUserId(userDataEntity.getUserId());
        if (entity.isEmpty()) throw new BadCredentialsException("Credentials invalid");
        UserDataEntity archivedEntity = entity.get();
        // 만약 유저가 존재한다면, 비밀번호를 비교합니다.
        if (encoder.matches(userDataEntity.getPassword(), archivedEntity.getPassword()))
            // 만약 비밀번호가 일치한다면, 토큰을 생성하여 반환합니다.
            return new UserAuthenticationDto(jwtService.generateToken(archivedEntity.getUserId()));
        // 만약 비밀번호가 일치하지 않는다면, BadCredentialsException을 던집니다.
        throw new BadCredentialsException("Credentials invalid");
    }
}
