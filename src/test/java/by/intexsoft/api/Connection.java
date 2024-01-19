package by.intexsoft.api;

import by.intexsoft.api.models.Country;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.net.URI;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Connection {

    public static Response getRequest(String url, List<String> countryCodes) {
        StringBuilder sb = new StringBuilder(url);

        if (!countryCodes.isEmpty()) {
            sb.append("?codes=");

            boolean isFirst = true;
            for (String cc : countryCodes) {
                if (!isFirst) sb.append("&codes=");
                sb.append(cc);
                isFirst = false;
            }
        }

        return given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .log().all()
                .when()
                .get(sb.toString());
    }

    public static List<Country> getBodyFromResponse(Response response) {
        return response.jsonPath().getList(".", Country.class);
    }

}
