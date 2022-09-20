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
//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600) //стройка CORS может выполняться на уровне контроллера
@RestController()
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @CrossOrigin
    //метод авторизации. Возврат токена
    @PostMapping("/login")
    public @ResponseBody AuthTokenResponseDTO login(@RequestBody LoginPasswordRequestDto dto) {
        return authService.login(dto);
    }

    //метод выхода из приложения, удаляет токен
    @PostMapping("/logout")
    public @ResponseBody ResponseEntity<?> logout(@RequestHeader("auth-token") String authToken) {
        authService.logout(authToken);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
