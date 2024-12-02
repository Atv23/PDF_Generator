package com.pdf.PDF_Generator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdf.PDF_Generator.model.Invoice;
import com.pdf.PDF_Generator.model.Items;
import com.pdf.PDF_Generator.service.PdfService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PdfService pdfService;

    @Test
    void shouldGeneratePdfAndReturnResponse() throws Exception {
        String pdfPath = "generated_pdfs/sample.pdf";
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

        Mockito.when(pdfService.generatePdf(Mockito.any())).thenReturn(pdfPath);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/generate-pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invoice)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Disposition", "attachment; filename=sample.pdf"))
                .andExpect(MockMvcResultMatchers.content().bytes(Files.readAllBytes(Paths.get(pdfPath))));
    }

}
