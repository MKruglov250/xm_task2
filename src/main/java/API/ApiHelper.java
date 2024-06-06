package API;

import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.List;

@Log4j2
public class ApiHelper {

    static Requests requests = new Requests();

    public static LinkedHashMap findPerson(int pagesCount, String personName){
        LinkedHashMap singlePerson = new LinkedHashMap();
        for (int i = 1; i <= pagesCount; i++){
            log.info("Get page: " + i);
            String name = personName;
            boolean personFound = false;
            Response response = requests.getPeople(i, HttpStatus.SC_OK);
            log.info("Go through values in response list");
            List<LinkedHashMap> people = response.then().extract().path("results");
            for (int j = 0; j < people.size(); j++){
                singlePerson = people.get(j);
                if (name.equals(singlePerson.get("name"))){
                    personFound = true;
                    return singlePerson;
                }
            }
        }
        singlePerson = new LinkedHashMap();
        return singlePerson;
    }

    public static Response getFilmWithMinimumPlanets(List<String> films){
        Response film = null;
        int minPlanets = 10000;
        for (int i = 0; i < films.size(); i++){
            Response response = requests.get(films.get(i));
            List<String> planets = response.then().extract().path("planets");
            if(planets.size() < minPlanets){
                minPlanets = planets.size();
                film = response;
            }
        }
        return film;
    }

    public static List<String> getStarshipsList(Response film){
        return film.then().extract().path("starships");
    }
}
