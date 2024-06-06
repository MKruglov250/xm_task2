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
        return get("/people");
    }

    public Response getPeople(int statusCode){
        log.info("Performing GET People request");
        return get("/people");
    }

    public Response getSinglePerson(int personId, int statusCode){
        log.info("Performing GET Single Person request: personID = " + personId);
        return get("/people/" + personId);
    }

    public Response getFilmsFromPerson(String request){
        log.info("Performing GET Single Film request:" + request);
        return get(request.replaceAll("https://swapi.dev/api",""));
    }

    public Response getStarships(int page, int statusCode){
        log.info("Performing GET Starships request");
        requestSpecification.queryParam("page",page);
        return get("/users");
    }

    public Response getSingleStarship(int starshipId, int statusCode){
        log.info("Performing GET Single Starship request: starshipId = " + starshipId);
        return get("/starship/" + starshipId);
    }

    public Response getPlanets(int page, int statusCode){
        log.info("Performing GET Planets request");
        requestSpecification.queryParam("page",page);
        return get("/users");
    }

    public Response getSinglePlanet(int planetId, int statusCode){
        log.info("Performing GET Single Planet request: planetId = " + planetId);
        return get("/planets/" + planetId);
    }

}