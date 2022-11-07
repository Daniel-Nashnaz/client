
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.imageio.IIOException;
import org.json.JSONObject;

public class GetDataLive {

    private final String URI_NAME = "http://localhost:3000/";

    public String getData(String token) throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.URI_NAME))
                .header("content-type", "application/json")
                .header("x-api-key", token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IIOException("Error: " + response.body() + "statusCode:" + response.statusCode());
        }
        return response.body();
    }

    public String getToken(JSONObject userDetails) throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(this.URI_NAME))
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(userDetails.toString()))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IIOException("Error: " + response.body() + "status code:" + response.statusCode());
        }
        return response.body();
    }

    public boolean isTravelError(JSONObject data) {
        //ret false if Error
        return !(data.getJSONObject("ForwardWarning").getString("Directions").equals("Good")
                || !(data.getJSONObject("Speed").getBoolean("SpeedAboveLimit"))
                || !(data.getBoolean("SuddenBraking"))
                || data.getString("LaneDepartureWarning").equals("Good"));
    }

}
