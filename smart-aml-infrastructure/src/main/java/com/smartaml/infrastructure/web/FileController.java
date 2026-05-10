package com.smartaml.infrastructure.web;

import com.smartaml.infrastructure.storage.StorageService;
import com.smartaml.shared.response.ApiResponse;
import com.smartaml.shared.exception.FileNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final StorageService storageService;

    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{*filePath}")
    public ResponseEntity<?> getFile(@PathVariable("filePath") String filePath, HttpServletRequest request) {
        String decoded = URLDecoder.decode(filePath, StandardCharsets.UTF_8);
        try {
            var res = storageService.retrieve(decoded);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(res.contentType()))
                    .header("Content-Disposition", "attachment; filename=\"" + res.filename() + "\"")
                    .body(res.stream().readAllBytes());
        } catch (java.io.FileNotFoundException ex) {
            throw new FileNotFoundException("No file found: " + decoded);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
