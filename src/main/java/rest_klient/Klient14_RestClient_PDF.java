package rest_klient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

import javax.swing.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Klient14_RestClient_PDF {

    public static void main(String[] args) {
        try {
            Client client = ClientBuilder.newClient();

            String[] formaty = {"txt", "json", "xml", "pdf", "html"};
            String format = (String) JOptionPane.showInputDialog(null, "Wybierz format danych", "Wybór",
                    JOptionPane.QUESTION_MESSAGE, null, formaty, "txt");

            String mediaType = switch(format) {
                case "txt" -> "text/plain";
                case "json" -> "application/json";
                case "xml" -> "application/xml";
                case "pdf" -> "application/pdf";
                case "html" -> "text/html";
                default -> throw new IllegalArgumentException();
            };

            // Klient może wybrać format (mediaType), w jakim oczekuje odpowiedzi - to wpływa na nagłówek Accept
            Response response = client.target(Ustawienia.ADRES_USLUGI)
                    .path("products")
                    .request(mediaType)
                    .buildGet()
                    .invoke();

            JOptionPane.showMessageDialog(null, String.format("""
                	Status: %d
                	C-Type: %s
                	Length: %d""", response.getStatus(), response.getMediaType(), response.getLength()));

            Path plik = Paths.get("wynik13." + format);
            InputStream stream = response.readEntity(InputStream.class);
            Files.copy(stream,  plik, StandardCopyOption.REPLACE_EXISTING);
            JOptionPane.showMessageDialog(null, "Zapisano plik " + plik);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}


