package rest_klient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Klient01_URL {
//Najprostszy sposób w Javie, aby pobrać dane z adresu URL to użycie klasy URL.
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/TC-RESTSerwer-1.0-SNAPSHOT");
            System.out.println("Odczytuje dane ... ");
            try (InputStream input = url.openStream()) {
                input.transferTo(System.out);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
