package lesson.project.controler;

import lesson.project.dto.FileListResponseDto;
import lesson.project.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * контролер для получения списка пользовательских файлов
 */

@RestController()
@RequestMapping("/list")
@RequiredArgsConstructor
public class ListOfFilesController {
    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<FileListResponseDto>> getList(@RequestHeader("auth-token") String authToken, @RequestParam Integer limit) {

        List<FileListResponseDto> fileList = fileService.getList(authToken, limit);

        return ResponseEntity.ok().body(fileList);
    }

}
