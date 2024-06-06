package API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseApi {
    Gson gson;
    ContentType contentType = ContentType.JSON;
    public RequestSpecification requestSpecification;

    public BaseApi() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestAssured.baseURI="https://swapi.dev/api/";
        requestSpecification = given().header("content-type",contentType)
                .log().all();
    }

    public Response get(String endpoint){
        return requestSpecification.when()
                .get(endpoint).then()
                .contentType(ContentType.JSON)
                .extract().response();
    }


    public void restoreSpecification(){
        requestSpecification = given().header("content-type",contentType)
                .log().all();
    }
}
