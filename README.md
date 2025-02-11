# PDF Generation Project

This project allows users to generate invoices in PDF format using Thymeleaf templates and iText for HTML to PDF conversion. The backend is implemented with Spring Boot, exposing an API to create PDFs based on the provided invoice data.

---

## Features
- **Generate PDF Invoices**: Converts invoice details into a PDF format.
- **Thymeleaf Template**: Uses Thymeleaf to render dynamic content in the HTML template.
- **File Handling**: Saves the generated PDF in a specified directory and checks if the file already exists.
- **Logging**: Uses SLF4J with LoggerFactory for logging errors and important actions.

---

## Technologies Used
- **Spring Boot**: For creating the REST API and managing the application's backend logic.
- **Thymeleaf**: A templating engine used to generate HTML content.
- **iText**: A library to convert HTML content into PDF.
- **SLF4J**: For logging the operations performed by the application.

---

## How It Works

### API Endpoint
The project exposes the following endpoint to generate the PDF:

  #### POST `/api/generate-pdf`
This endpoint accepts a `POST` request with the invoice data in JSON format and generates a PDF based on that data.

**Request Payload**: You need to provide the invoice details such as the seller's and buyer's information, items, quantities, rates, and amounts.

**Response**: 
- **200 OK**: If the PDF was successfully generated.
- **500 Internal Server Error**: If there was an error during PDF generation.

### Service Logic

1. **Invoice Data**: The `InvoiceController` class receives the invoice data from the client and calls the `PdfService` to generate the PDF.
2. **Template Rendering**: The `PdfServiceImpl` uses Thymeleaf to process the `PdfView.html` template with the provided data.
3. **PDF Generation**: The processed HTML is then converted into a PDF using the iText `HtmlConverter`.
4. **File Handling**: The generated PDF is saved to a specified directory (`./generated_pdfs/`) using the seller's and buyer's GSTIN as part of the file name.

### Thymeleaf HTML Template
The `PdfView.html` file is used to format the invoice content. The template renders the seller, buyer, and item details dynamically using the Thymeleaf syntax.

---

## Screenshots

### Generated PDF Invoice
Below is an example of a generated PDF invoice:
![Screenshot 2024-12-03 031941](https://github.com/user-attachments/assets/c7f16e74-2997-480f-bbf8-b7bb91cec5d9)

### Postman Request and Response
Here is an example of a Postman request and response for the `/api/generate-pdf` endpoint:

**Postman Response for a new request**:
![Screenshot 2024-12-03 031715](https://github.com/user-attachments/assets/69d78de5-e33a-4c29-8fa8-88b2bb13b837)


**Postman Response for a repeated request**:
![Screenshot 2024-12-03 031726](https://github.com/user-attachments/assets/018f2d31-560b-4c3b-8cdb-28e2c001cd31)


---

## Running the Project

### Prerequisites:
- JDK 11 or higher
- Maven

### Steps to Run:

1. Clone the repository:
```bash
   git clone https://github.com/your-repository/pdf-generation.git
   cd pdf-generation
  ```
2. Build the project:
```
mvn clean install
```
3. Run the Spring Boot application:
```
mvn spring-boot:run
```
4. Send a POST request to `/api/generate-pdf` with the invoice data.
