package training.task.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import training.task.model.db.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> { 
    @Transactional
    int deleteByCodeAndExpirationAfter(String code, LocalDateTime now);
    
    @Transactional
    int deleteByExpirationBefore(LocalDateTime now);
}
