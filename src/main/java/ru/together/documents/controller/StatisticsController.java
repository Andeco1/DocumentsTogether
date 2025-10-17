package ru.together.documents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.together.documents.service.StatisticsService;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public String statisticsPage(Model model) {
        Map<String, Object> generalStats = statisticsService.getGeneralStatistics();
        model.addAttribute("stats", generalStats);
        return "statistics";
    }

    @GetMapping("/chart/documents-by-month")
    public ResponseEntity<byte[]> documentsByMonthChart() {
        try {
            byte[] chartBytes = statisticsService.generateDocumentsByMonthChart();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "documents-by-month.png");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chart/user-themes")
    public ResponseEntity<byte[]> userThemesChart() {
        try {
            byte[] chartBytes = statisticsService.generateUserThemesChart();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "user-themes.png");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chart/user-languages")
    public ResponseEntity<byte[]> userLanguagesChart() {
        try {
            byte[] chartBytes = statisticsService.generateUserLanguagesChart();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "user-languages.png");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chart/document-visibility")
    public ResponseEntity<byte[]> documentVisibilityChart() {
        try {
            byte[] chartBytes = statisticsService.generateDocumentVisibilityChart();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentDispositionFormData("attachment", "document-visibility.png");
            return new ResponseEntity<>(chartBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
