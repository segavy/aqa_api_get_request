package by.intexsoft.api;

import by.intexsoft.api.models.Country;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BorderCountriesTest {
    private static final String URL = "https://restcountries.com/v2/alpha";
    private static final String COUNTRY_CODE = "RUS";
    private static final String SECOND_COUNTRY_CODE = "BLR";
    private static final String THIRD_COUNTRY_CODE = "EST";
    private static final String INCORRECT_CODE = "BLB";

    @Test
    public void verifyTwoNeighboringCountriesHaveCommonBorder() {
        String firstCountry = COUNTRY_CODE;
        String secondCountry = SECOND_COUNTRY_CODE;
        Response response = Connection.getRequest(URL, Arrays.asList(firstCountry, secondCountry));
        List<Country> countries = Connection.getBodyFromResponse(response);

        Assertions.assertEquals(ResponseCode.OK.getCode(), response.statusCode(), "Response Code is incorrect");

        Assertions.assertTrue(countries.stream()
                .filter(c -> c.getAlpha3Code().equals(firstCountry))
                .anyMatch(b -> b.getBorders().contains(secondCountry)),
                "The country with code '" + firstCountry + "' should have a common border with country '" + secondCountry + "'");
    }

    @Test
    public void verifyTwoCountriesHaveNotCommonBorder() {
        String firstCountry = COUNTRY_CODE;
        String secondCountry = THIRD_COUNTRY_CODE;
        Response response = Connection.getRequest(URL, Arrays.asList(firstCountry, secondCountry));
        List<Country> countries = Connection.getBodyFromResponse(response);

        Assertions.assertEquals(ResponseCode.OK.getCode(), response.statusCode(), "Response Code is incorrect");

        Assertions.assertTrue(countries.stream()
                        .filter(c -> c.getAlpha3Code().equals(firstCountry))
                        .anyMatch(b -> b.getBorders().contains(secondCountry)),
                "The country with code '" + firstCountry + "' shouldn't have a common border with country '" + secondCountry + "'");
    }

    @Test
    public void verifyNoDuplicatedCountriesInTheResponseIfDuplicatesInTheQueryString() {
        String firstCountry = COUNTRY_CODE;
        Response response = Connection.getRequest(URL, Arrays.asList(firstCountry, firstCountry, firstCountry));
        List<Country> countries = Connection.getBodyFromResponse(response);

        Assertions.assertEquals(ResponseCode.OK.getCode(), response.statusCode(), "Response Code is incorrect");
        Assertions.assertEquals(1, countries.size(), "There shouldn't be duplicated countries in the response");
    }

    @Test
    public void verifyGetErrorInCaseOfAbsentCountryValue() {
        Response response = Connection.getRequest(URL, new ArrayList<>());

        Assertions.assertEquals(ResponseCode.BAD_REQUEST.getCode(), response.statusCode(), "Response Code is incorrect");
    }

    @Test
    public void verifyGetErrorInCaseOfSingleIncorrectCountryValue() {
        Response response = Connection.getRequest(URL, Arrays.asList(INCORRECT_CODE));

        Assertions.assertEquals(ResponseCode.NOT_FOUND.getCode(), response.statusCode(), "Response Code is incorrect");
    }

    @Test
    public void verifyItIsPossibleToUseAtLeastOneCorrectCountryValueTogetherWithInCorrectValue() {
        Response response = Connection.getRequest(URL, Arrays.asList(COUNTRY_CODE, INCORRECT_CODE));

        Assertions.assertEquals(ResponseCode.OK.getCode(), response.statusCode(), "Response Code is incorrect");
    }
}
