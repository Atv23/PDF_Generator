package com.pdf.PDF_Generator.service;

import com.pdf.PDF_Generator.model.Invoice;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface PdfService {

    public String generatePdf(Invoice invoice) throws IOException;
}
