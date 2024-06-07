import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import API.Requests;

public class BaseTest {

//    Gson gson;
    Requests requests = new Requests();

    @AfterMethod
    public void restoreSpecification(){
        requests.restoreSpecification();
    }

    public BaseTest() {
        RestAssured.baseURI="https://swapi.dev/";
    }
}