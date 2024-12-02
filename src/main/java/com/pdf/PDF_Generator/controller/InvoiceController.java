package com.pdf.PDF_Generator.controller;

import com.pdf.PDF_Generator.model.Invoice;
import com.pdf.PDF_Generator.service.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private PdfService pdfService;

    private Logger logger = LoggerFactory.getLogger(InvoiceController.class);

    @PostMapping("/generate-pdf")
    public ResponseEntity<String> generatePDF(@RequestBody Invoice invoice) {
        logger.info("Generate PDF API called");
        try {
            String msg = pdfService.generatePdf(invoice);
            logger.info(msg);
            return new ResponseEntity<>(msg,HttpStatus.OK);
        } catch (IOException e) {
            logger.error("Error generating PDF", e);
            return new ResponseEntity<>("Could not generate the PDF",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
