package training.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import training.task.model.dto.StringDTO;
import training.task.model.dto.ValidityDTO;
import training.task.service.OtpService;

@RequiredArgsConstructor
@RestController
public class controller {
    private final OtpService otpService;

    @GetMapping("/otp")
    public ResponseEntity<StringDTO> generate() {
        return new ResponseEntity<>(otpService.generateOtp(), HttpStatus.OK);
    }

    @PostMapping("/otp")
    public ResponseEntity<ValidityDTO> use(@RequestBody StringDTO otp) {
        return new ResponseEntity<>(otpService.useOtp(otp.otp()), HttpStatus.OK);
    }
}
