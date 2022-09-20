package lesson.project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {

    @Id
    @Column(unique = true)
    private String filename;

    @Column(nullable = false)
    private LocalDateTime date;
    @Lob
    private byte[] fileContent;

    private Long size;

    @ManyToOne
    private User user;

}
