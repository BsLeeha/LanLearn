import com.google.gson.stream.JsonReader;

import java.io.StringReader;

public class GsonTest {
    public static void main(String[] args) {
        String jsonData = "{\"brand\": \"Toyota\", \"doors\": 5}";
        JsonReader reader = new JsonReader(new StringReader(jsonData));
    }
}
