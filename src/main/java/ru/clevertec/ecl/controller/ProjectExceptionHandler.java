package ru.clevertec.ecl.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.exception.ServiceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ProjectExceptionHandler {
    private int exceptionId = 330;

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallDetailedError handleServiceExceptionServerError(
            HttpServletRequest request,
            ServiceException ex
    ) {
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("errorMessage", ex.getMessage());
        detail.put("errorCode", "400" + exceptionId);
        exceptionId++;
        details.add(detail);
        return new ApiCallDetailedError("HTTP status: 400", details);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ApiCallDetailedError {
        private String status;
        private List<Map<String, String>> details;
    }
}
