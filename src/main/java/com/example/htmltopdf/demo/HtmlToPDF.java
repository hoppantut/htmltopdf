package com.example.htmltopdf.demo;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

class HtmlToPDF {

    private final SpringTemplateEngine templateEngine;
    private final String baseFolder = "htmltemplate/";
    private final String baseUrl = format("%s/%s",
            getClass().getProtectionDomain().getCodeSource().getLocation().toString(),
            baseFolder);

    HtmlToPDF() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    private File createFile() {
        Path path = Paths.get(format("%s/%s", System.getProperty("java.io.tmpdir"), "test.pdf"));
        try {
            Files.deleteIfExists(path);
            return Files.createFile(path).toFile();
        } catch (IOException e) {
            throw new RuntimeException("Dang...");
        }
    }

    void convertToPDF(Context templateValues) {
        try (OutputStream fos = new FileOutputStream(createFile())) {
            String html = templateEngine.process(baseFolder + "template.html", templateValues);
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, baseUrl);
            builder.toStream(fos);
            builder.run();
        } catch (Exception e) {
            throw new IllegalStateException("Klarte ikke opprette pdf document", e);
        }
    }
}
