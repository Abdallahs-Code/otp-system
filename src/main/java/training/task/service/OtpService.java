package training.task.service;

import java.security.SecureRandom;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import training.task.dto.StringResponse;
import training.task.dto.ValidityResponse;

@Service
public class OtpService {
    private final SecureRandom random = new SecureRandom();
    private final Set<String> activeOtps = ConcurrentHashMap.newKeySet();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public StringResponse generateOtp() {
        // Generating an Otp that is unique at the moment and lasts for 1 mins
        String otp;
        do {
            otp = String.format("%04d", random.nextInt(10000));
        } while (activeOtps.contains(otp));

        activeOtps.add(otp);

        // Scheduling automatic removal
        final String otpToRemove = otp;
        scheduler.schedule(() -> activeOtps.remove(otpToRemove), 60, TimeUnit.SECONDS);

        return new StringResponse(otp);
    }

    public ValidityResponse useOtp(String otp) {
        return new ValidityResponse(activeOtps.remove(otp));
    }
}