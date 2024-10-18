package ex04;

import java.io.IOException;
import com.google.gson.*;
import okhttp3.*;

public class Main {
	private static final String API_KEY = "4e414f4f39ba40d589b06d9e6472ed53";
	private static final String REGION = "brazilsouth";
	private OkHttpClient httpClient = new OkHttpClient();

	// Método para realizar a solicitação POST para a API de tradução
	public String sendTranslationRequest() throws IOException {
		MediaType jsonMediaType = MediaType.parse("application/json");
		RequestBody requestBody = RequestBody.create(
				"[{\"Text\": \"Programming opens up a world of endless possibilities!\"}]", jsonMediaType);
		Request request = new Request.Builder()
				.url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=fr,zu,pt-br,ru,it")
				.post(requestBody)
				.addHeader("Ocp-Apim-Subscription-Key", API_KEY)
				.addHeader("Ocp-Apim-Subscription-Region", REGION)
				.addHeader("Content-type", "application/json")
				.build();

		try (Response response = httpClient.newCall(request).execute()) {
			return response.body().string();
		}
	}

	// Método para formatar a resposta JSON
	public static String formatJsonResponse(String jsonString) {
		JsonElement jsonElement = JsonParser.parseString(jsonString);
		Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
		return prettyGson.toJson(jsonElement);
	}

	public static void main(String[] args) {
		Main translator = new Main();
		try {
			String apiResponse = translator.sendTranslationRequest();
			System.out.println(formatJsonResponse(apiResponse));
		} catch (IOException e) {
			System.err.println("Error during API request: " + e.getMessage());
		}
	}
}
