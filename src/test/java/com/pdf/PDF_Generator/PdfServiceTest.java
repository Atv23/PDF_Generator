package com.pdf.PDF_Generator;

import com.pdf.PDF_Generator.model.Invoice;
import com.pdf.PDF_Generator.model.Items;
import com.pdf.PDF_Generator.service.PdfService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.thymeleaf.TemplateEngine;

import java.io.File;
import java.util.Arrays;

@SpringBootTest
public class PdfServiceTest {

    @Autowired
    private PdfService pdfService;

    @MockitoBean
    private TemplateEngine templateEngine;

    @Test
    void shouldGeneratePdfAndReturnFilePath() throws Exception {

        Items item1 = Items.builder()
                .name("Product 1")
                .quantity("12 Nos")
                .rate(123.00)
                .amount(1476.00)
                .build();

        Items item2 = Items.builder()
                .name("Product 2")
                .quantity("5 Nos")
                .rate(500.00)
                .amount(2500.00)
                .build();

        Invoice invoice = Invoice.builder()
                .seller("XYZ Pvt. Ltd.")
                .sellerGstin("29AABBCCDD121ZD")
                .sellerAddress("New Delhi, India")
                .buyer("Vedant Computers")
                .buyerGstin("29AABBCCDD131ZD")
                .buyerAddress("Mumbai, India")
                .items(Arrays.asList(item1, item2))
                .build();

        // Mock Thymeleaf behavior
        Mockito.when(templateEngine.process(Mockito.anyString(), Mockito.any())).thenReturn("<html>Mock HTML</html>");

        String filePath = pdfService.generatePdf(invoice);

        Assertions.assertTrue(new File(filePath).exists());
    }
}
