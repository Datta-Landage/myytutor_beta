package com.myytutor.api;

import com.myytutor.dto.InquiryRequest;
import com.myytutor.service.InquiryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {
    @Autowired
    private InquiryService inquiryService;

    @PostMapping
    public ResponseEntity<?> createInquiry(@Valid @RequestBody InquiryRequest request) {
        inquiryService.createInquiry(request);
        return ResponseEntity.ok(new SimpleResponse("success", "Your inquiry has been received successfully!"));
    }

    static class SimpleResponse {
        public final String status;
        public final String message;
        public SimpleResponse(String status, String message) {
            this.status = status; this.message = message;
        }
    }
}
