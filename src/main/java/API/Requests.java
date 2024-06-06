package API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Requests extends BaseApi{

    Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public Response getPeople(int page, int statusCode){
        log.info("Performing GET People request");
        requestSpecification.queryParam("page",page);
        Response response = get("/people");
        restoreSpecification();
        return response;
    }

    public Response getPeople(int statusCode){
        log.info("Performing GET People request");
        return get("/people");
    }

}