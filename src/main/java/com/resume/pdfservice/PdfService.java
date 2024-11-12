package com.resume.pdfservice;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.itextpdf.html2pdf.HtmlConverter;
import com.resume.entity.User;

@Service
public class PdfService {

	@Autowired
    private SpringTemplateEngine templateEngine;

    public void generatePdf(User user,String template) throws Exception {
        // Prepare Thymeleaf context
        Context context = new Context();
        context.setVariable("resume", user);

        // Render HTML from Thymeleaf template
        String htmlContent = templateEngine.process(template, context);

        // Convert HTML to PDF
        try (OutputStream outputStream = new FileOutputStream("src/main/resources/static/resume.pdf")) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
        }
    }
}