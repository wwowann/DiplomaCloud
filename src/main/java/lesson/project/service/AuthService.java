package lesson.project.service;

import lesson.project.dto.AuthTokenResponseDTO;
import lesson.project.dto.LoginPasswordRequestDto;
import lesson.project.repository.AuthRepository;
import lesson.project.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Service
@Slf4j// логирование
public class AuthService {
    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

//    private static final Map<String, String> TOKENS = new ConcurrentHashMap<>();

    public AuthTokenResponseDTO login(LoginPasswordRequestDto request) {
        final String username = request.getLogin();//получение логина из объекта DTO - request
        final String password = request.getPassword();//получение пароля из объекта DTO - request
        //проверка на наличие в базе пользователя с данными логина и пароля в запросе
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        //получаем имя пользователя, используемое для аутентификации пользователя
        final UserDetails userDetails = userService.loadUserByUsername(username);
        //генерация токена доступа JWT
        final String token = jwtTokenUtil.generateToken(userDetails);
//        TOKENS.put(token, username);//сохранение токена и имени клиента в мапу
        log.info("User " + username + " login");
        return new AuthTokenResponseDTO(token);// генерация response с текущим токеном
    }

    public void logout(String authToken) {
        final String token = authToken.substring(7);
        final String username = authRepository.getUsernameByToken(token);
        log.info("User {} logout. JWT is disabled.", username);
        authRepository.removeTokenAndUsernameByToken(token);
    }
}