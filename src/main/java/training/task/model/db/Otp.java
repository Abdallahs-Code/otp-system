package training.task.model.db;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Otp {
    @Id
    private String code;

    @Column(nullable = false)
    private LocalDateTime expiration;

    public Otp(String code) {
        this.code = code;
        this.expiration = LocalDateTime.now().plusMinutes(1);
    }
}
