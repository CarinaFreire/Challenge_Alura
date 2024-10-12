package Api;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import model.ExchangeRates;

import java.io.IOException;

public class ApiClient {
    private final OkHttpClient client;

    public ApiClient() {
        this.client = new OkHttpClient();
    }

    public String getExchangeRates() throws IOException {
        String url = "https://api.exchangerate-api.com/v4/latest/USD";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            if (response.body() == null) {
                throw new IOException("Response body is null");
            }

            return response.body().string();
        }
    }

    // Método para analisar a resposta JSON
    public ExchangeRates parseExchangeRates(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ExchangeRates.class);
    }
}

