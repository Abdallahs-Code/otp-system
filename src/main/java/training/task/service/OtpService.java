package training.task.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import training.task.model.db.Otp;
import training.task.model.dto.StringDTO;
import training.task.model.dto.ValidityDTO;
import training.task.repository.OtpRepository;

@RequiredArgsConstructor
@Service
public class OtpService {
    private final OtpRepository otpRepository;
    private final SecureRandom random = new SecureRandom();

    // this function is safe for multiple servers (database-level synchronization)
    private boolean add(Otp otp) {
        try {
            otpRepository.save(otp);
            return true;
        }
        catch (DataIntegrityViolationException e) {
            return false;
        }
    }

    // expired otps that users have not tried to use them wont be removed from the database and therefore we have 3 options
    // 1- use an automatic cleanup on the database side (like using pg_cron for postgres)
    // 2- use a NoSql database like mongodb that has built-in TTL (deletes immediately once expired)
    // 3- make a scheduler on the server side to cleanup periodically
    @Scheduled(fixedRate = 60000) // every 1 minute
    public void cleanupExpiredOtps() {
        otpRepository.deleteByExpirationBefore(LocalDateTime.now());
    }

    // this function is safe for multiple servers (database-level synchronization)
    private boolean remove(String code) {
        // using the first delete function because it is database-level synchronized
        boolean valid = otpRepository.deleteByCodeAndExpirationAfter(code, LocalDateTime.now()) == 1;
        if (!valid) {
            // either otp code is not found or it has expired
            // second delete function for the case of expired otp
            // for the case of not found otp code, second delete function is useless
            otpRepository.deleteById(code);
        }
        return valid;
    }

    public StringDTO generateOtp() {
        String code;
        do {
            code = String.format("%04d", random.nextInt(10000));
        } while (!add(new Otp(code)));

        return new StringDTO(code);
    }

    public ValidityDTO useOtp(String code) {
        return new ValidityDTO(remove(code));
    }
}