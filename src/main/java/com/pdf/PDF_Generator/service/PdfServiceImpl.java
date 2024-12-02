package com.pdf.PDF_Generator.service;

import com.pdf.PDF_Generator.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
@Service
public class PdfServiceImpl implements PdfService{

    private final TemplateEngine templateEngine;
    private static final String PDF_DIRECTORY = "./generated_pdfs/";
    private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

    public PdfServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public String generatePdf(Invoice invoice) throws IOException {

        logger.info("PDF Generation Service started");
        //File path where pdfs will be saved
        String filePath = PDF_DIRECTORY + invoice.getBuyerGstin() + "_" + invoice.getSellerGstin() + ".pdf";

        File directory = new File(PDF_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Check if the file already exists
        File file = new File(filePath);
        if (file.exists()) {
            logger.info("PDF already exists, no need to generate new, re-downloading it without generation");
            return "PDF already exists at " + filePath;
        }
        logger.info("Saving PDF to: " + new File(filePath).getAbsolutePath());
        Context context = new Context();
        context.setVariable("seller", invoice.getSeller());
        context.setVariable("sellerGstin", invoice.getSellerGstin());
        context.setVariable("sellerAddress", invoice.getSellerAddress());
        context.setVariable("buyer", invoice.getBuyer());
        context.setVariable("buyerGstin", invoice.getBuyerGstin());
        context.setVariable("buyerAddress", invoice.getBuyerAddress());
        context.setVariable("items", invoice.getItems());

        String htmlContent = templateEngine.process("PdfView", context);

        // Generate the PDF from the HTML content using iText's HtmlConverter
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            HtmlConverter.convertToPdf(htmlContent, outputStream);
        }
        logger.info("PDF generated at: " + filePath);
        return "New PDF Generated at "+ filePath;
    }
}
