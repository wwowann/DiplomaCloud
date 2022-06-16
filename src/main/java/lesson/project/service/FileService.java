package lesson.project.service;

import lesson.project.dto.FileDto;
import lesson.project.dto.FileListResponseDto;
import lesson.project.dto.FilenameUpdateDto;
import lesson.project.exception.AppException;
import lesson.project.model.FileEntity;
import lesson.project.model.User;
//import lesson.project.repository.AuthRepository;
import lesson.project.repository.FileRepository;
import lesson.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * слой сервиса, отвечающего за работу с хранилищем файлов
 */

@Service
@RequiredArgsConstructor
@Slf4j//логирование
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
//    private final AuthRepository authRepository;


    public boolean save(String authToken, String filename, FileDto dto) {
        MultipartFile file = dto.getFile();
        final User user = getUserByAuthToken(authToken);
        if (user == null) {
            log.error("Загрузка файла не авторизована");
            throw new AppException("Загрузка файла не авторизована");
        }

        try {
            fileRepository.save(new FileEntity(filename,
                    LocalDateTime.now(),
                    file.getBytes(),
                    file.getSize(),
                    user));
            log.info("Success upload file. User {}", user.getUsername());
            return true;
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new AppException("Upload file: Input data exception");
        }
    }

    public byte[] getFile(String filename) {
        Optional<FileEntity> entity = fileRepository.findByFilename(filename);
        if (entity.isPresent()) {
            FileEntity fileEntity = entity.get();
            return fileEntity.getFile();
        } else {
            throw new AppException("File with this name doesn't exist");
        }
    }

    @Transactional
    public void deleteFile(String filename) {
        fileRepository.deleteByFilename(filename);
    }

    @Transactional
    public Optional<FileEntity> updateFilename(String filename, FilenameUpdateDto dto) {
        Optional<FileEntity> fileEntity = fileRepository.updateFilename(filename, dto.getFilename());
        if (fileEntity.isPresent()) {
            return fileEntity;
        } else {
            throw new AppException("File with this name doesn't exist");
        }

    }

    //получение файлов пользователя
    public List<FileListResponseDto> getList(String authToken, Integer limit) {
        //получение пользователя для поиска файлов
        final User user = getUserByAuthToken(authToken);
        if (user == null) {
            log.error("Файлы не получены, не пройдена авторизация");
            throw new AppException("Файлы не получены, не пройдена авторизация");
        }
        log.info("Файлы успешно получены. Пользователь {}", user.getUsername());
        //формирование списка файлов
        return fileRepository.findAllByUser(user).stream()
                .map(o -> new FileListResponseDto(o.getFilename(), o.getSize()))
                .collect(Collectors.toList());
    }

    private List<FileListResponseDto> convertToDto(Page<FileEntity> entityPage) {
        return entityPage.stream()
                .map(o -> new FileListResponseDto(o.getFilename(), o.getSize()))
                .collect(Collectors.toList());
    }

    //получение пользователя по токену
    private User getUserByAuthToken(String authToken) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (authToken.startsWith("Bearer ")) {
            final String authTokenWithoutBearer = authToken.split(" ")[1];
            final String username = user.getUsername();
//                    authRepository.getUsernameByToken(authTokenWithoutBearer);
            return userRepository.findByUsername(username);
        }
        return null;
    }
}
