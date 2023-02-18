package com.example.lazier.controller.traffic;

import com.example.lazier.dto.traffic.TrafficInput;
import com.example.lazier.service.traffic.TrafficService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traffic")
@AllArgsConstructor
public class TrafficController {

    private final TrafficService trafficService;

    @PostMapping("/user")
    public ResponseEntity<?> addUserInfo(HttpServletRequest request,
        @RequestBody @Valid TrafficInput parameter) {
        trafficService.add(request, parameter);
        return ResponseEntity.ok().body("저장되었습니다.");
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        return new ResponseEntity<>(trafficService.getUserInfo(request), HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUserInfo(HttpServletRequest request,
        @RequestBody @Valid TrafficInput parameter) {
        trafficService.update(request, parameter);

        return ResponseEntity.ok().body("업데이트 되었습니다.");
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUserInfo(HttpServletRequest request) {
        trafficService.delete(request);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> getTrafficDuration(HttpServletRequest request) {
        return new ResponseEntity<>(trafficService.getTrafficDuration(request), HttpStatus.OK);
    }
}
