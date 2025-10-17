package ru.together.documents.service;

import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Service;
import ru.together.documents.entity.Document;
import ru.together.documents.entity.LibUser;
import ru.together.documents.repository.DocumentRepository;
import ru.together.documents.repository.UserRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    // Статистика по документам по месяцам
    public byte[] generateDocumentsByMonthChart() throws IOException {
        List<Document> documents = documentRepository.findAll();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> monthlyData = new HashMap<>();
        
        for (Document doc : documents) {
            LocalDate date = doc.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
            String month = date.getYear() + "-" + String.format("%02d", date.getMonthValue());
            monthlyData.put(month, monthlyData.getOrDefault(month, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : monthlyData.entrySet()) {
            dataset.addValue(entry.getValue(), "Документы", entry.getKey());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Количество документов по месяцам",
            "Месяц",
            "Количество",
            dataset
        );
        
        // Настройка стиля
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(74, 144, 226));

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2)
        );
        
        return addWatermarkAndConvertToBytes(chart, 800, 600);
    }

    // Статистика по темам пользователей
    public byte[] generateUserThemesChart() throws IOException {
        List<LibUser> users = userRepository.findAll();
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Integer> themeData = new HashMap<>();
        
        for (LibUser user : users) {
            String theme = user.getTheme();
            themeData.put(theme, themeData.getOrDefault(theme, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : themeData.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Распределение пользователей по темам",
            dataset,
            true,
            true,
            false
        );
        
        // Настройка стиля
        chart.setBackgroundPaint(Color.WHITE);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        
        // Цвета для тем
        plot.setSectionPaint("light", new Color(74, 144, 226));
        plot.setSectionPaint("dark", new Color(45, 55, 72));
        plot.setSectionPaint("colorblind", new Color(0, 0, 0));
        
        return addWatermarkAndConvertToBytes(chart, 600, 400);
    }

    // Статистика по языкам пользователей
    public byte[] generateUserLanguagesChart() throws IOException {
        List<LibUser> users = userRepository.findAll();
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> languageData = new HashMap<>();
        
        for (LibUser user : users) {
            String language = user.getLanguage();
            languageData.put(language, languageData.getOrDefault(language, 0) + 1);
        }
        
        for (Map.Entry<String, Integer> entry : languageData.entrySet()) {
            String langName = entry.getKey().equals("ru") ? "Русский" : "English";
            dataset.addValue(entry.getValue(), "Пользователи", langName);
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Распределение пользователей по языкам",
            "Язык",
            "Количество пользователей",
            dataset
        );
        
        // Настройка стиля
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(255, 126, 95));
        
        return addWatermarkAndConvertToBytes(chart, 600, 400);
    }

    // Статистика по публичности документов
    public byte[] generateDocumentVisibilityChart() throws IOException {
        List<Document> documents = documentRepository.findAll();
        
        long publicCount = documents.stream().filter(Document::isPublic).count();
        long privateCount = documents.size() - publicCount;
        
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Публичные", publicCount);
        dataset.setValue("Приватные", privateCount);
        
        JFreeChart chart = ChartFactory.createPieChart(
            "Распределение документов по видимости",
            dataset,
            true,
            true,
            false
        );
        
        // Настройка стиля
        chart.setBackgroundPaint(Color.WHITE);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        
        plot.setSectionPaint("Публичные", new Color(39, 174, 96));
        plot.setSectionPaint("Приватные", new Color(231, 76, 60));
        
        return addWatermarkAndConvertToBytes(chart, 600, 400);
    }


    // Добавление водяного знака и конвертация в байты
    private byte[] addWatermarkAndConvertToBytes(JFreeChart chart, int width, int height) throws IOException {
        BufferedImage chartImage = chart.createBufferedImage(width, height);
        
        // Создание изображения с водяным знаком
        BufferedImage watermarkedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = watermarkedImage.createGraphics();
        
        // Рендеринг основного изображения
        g2d.drawImage(chartImage, 0, 0, null);
        
        // Добавление водяного знака
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        
        FontMetrics fm = g2d.getFontMetrics();
        String watermark = "DocuShare Statistics";
        int x = width - fm.stringWidth(watermark) - 20;
        int y = height - 20;
        
        g2d.drawString(watermark, x, y);
        
        // Добавление дополнительного водяного знака
        g2d.setFont(new Font("Arial", Font.ITALIC, 16));
        fm = g2d.getFontMetrics();
        String dateWatermark = "Generated: " + new Date().toString();
        x = 20;
        y = height - 20;
        g2d.drawString(dateWatermark, x, y);
        
        g2d.dispose();
        
        // Конвертация в байты
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(watermarkedImage, "PNG", baos);
        return baos.toByteArray();
    }

    // Получение общей статистики
    public Map<String, Object> getGeneralStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalUsers = userRepository.count();
        long totalDocuments = documentRepository.count();
        long publicDocuments = documentRepository.findByIsPublicTrue().size();
        
        stats.put("totalUsers", totalUsers);
        stats.put("totalDocuments", totalDocuments);
        stats.put("publicDocuments", publicDocuments);
        stats.put("privateDocuments", totalDocuments - publicDocuments);
        
        return stats;
    }
}
