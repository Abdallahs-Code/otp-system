package training.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import training.task.dto.StringResponse;
import training.task.dto.ValidityResponse;
import training.task.service.OtpService;

@RequiredArgsConstructor
@RestController
public class controller {
    private final OtpService OtpService;

    // Task 1
    @GetMapping("/helloworld")
    public ResponseEntity<StringResponse> helloworld() {
        return new ResponseEntity<>(new StringResponse("Hello World!"), HttpStatus.OK);
    }

    // Task 2
    @GetMapping("/otp")
    public ResponseEntity<StringResponse> generate() {
        return new ResponseEntity<>(OtpService.generateOtp(), HttpStatus.OK);
    }

    @PostMapping("/otp/{otp}")
    public ResponseEntity<ValidityResponse> use(@PathVariable String otp) {
        return new ResponseEntity<>(OtpService.useOtp(otp), HttpStatus.OK);
    }
}
