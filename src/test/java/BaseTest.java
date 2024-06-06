import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.AfterMethod;
import API.Requests;

public class BaseTest {

    Gson gson;
    Requests requests = new Requests();

    @AfterMethod
    public void restoreSpecification(){
        requests.restoreSpecification();
    }

    public BaseTest() {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        RestAssured.baseURI="https://swapi.dev/";
    }
}