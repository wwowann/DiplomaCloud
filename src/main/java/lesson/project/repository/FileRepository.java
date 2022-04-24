package lesson.project.repository;

import lesson.project.model.FileEntity;
import lesson.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFilename(String filename);

    void deleteByFilename(String filename);

    @Modifying
    @Query("UPDATE files f SET f.filename = :newName WHERE f.filename = :filename")
    Optional<FileEntity> updateFilename(@Param(value = "filename") String filename, @Param(value = "newName") String newName);

    List<FileEntity> findAllByUser(User user);
}
