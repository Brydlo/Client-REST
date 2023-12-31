package rest_klient;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Klient06_HttpClient_JSON {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            URI uri = new URI(Ustawienia.ADRES_USLUGI + "/products/1");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Accept", "application/json")
                    .build();
            // Pobierzemymy obiekt String z całą treścią
            HttpResponse<InputStream> response = httpClient.send(request, BodyHandlers.ofInputStream());
            System.out.println("response " + response);
            System.out.println("status: " + response.statusCode());
            System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));

            JsonReader reader = Json.createReader(response.body());
            JsonObject product = reader.readObject();
            System.out.println("Pobrany obiekt jsonowy: " + product);
            String nazwa = product.getString("productName");
            String opis = product.getString("description", "BRAK OPISU");
            BigDecimal cena = product.getJsonNumber("price").bigDecimalValue();
            System.out.println(nazwa + " za cene " + cena + ", opis: " + opis);
            reader.close();

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


}


