package lesson.project.controler;

import lesson.project.service.AuthService;
import lesson.project.dto.AuthTokenResponseDTO;
import lesson.project.dto.LoginPasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * контроллер для авторизации и выхода из приложения
 */

@RestController()
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //метод авторизации. Получение токена
    @PostMapping("/login")
    public AuthTokenResponseDTO login(@RequestBody LoginPasswordRequestDto dto) {
        return authService.login(dto);
    }

    //метод выхода из приложения, удаляет токен
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
