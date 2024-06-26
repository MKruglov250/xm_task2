import API.ApiHelper;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Log4j2
public class ApiTest extends BaseTest{

    public static LinkedHashMap singlePerson;
    public static Response film;
    public static String vaderStarshipEndpoint;

    @AfterMethod(description = "Log symbols")
    public void logDivision(){
        log.info("----------------------***TEST FINISHED***----------------------");
    }

    @Test(description = "Search for a person: Darth Vader")
    public void findDarthVader(){
        log.info("Requesting List of People");
        String expectedName = "Darth Vader";
        Response response = requests.getPeople(HttpStatus.SC_OK);
        int peopleCount = response.then().extract().path("count");
        int pagesCount = (int) Math.ceil((double) peopleCount / 10);
        singlePerson = ApiHelper.findPerson(pagesCount,"Darth Vader");
        Assert.assertEquals(singlePerson.get("name"), expectedName);
    }

    @Test(description = "Verify Film With Vader's Starship", priority = 1)
    public void checkFilmWithLessPlanets(){
        log.info("Finding Film with least Planets");
        List<String> films = (List<String>) (singlePerson.get("films"));
        Response minPlanetsfilm = ApiHelper.getFilmWithMinimumPlanets(films);
        List<String> planets = minPlanetsfilm.then().extract().path("planets");
        Assert.assertEquals(planets.size(),3);
        film = minPlanetsfilm;
    }

    @Test(description = "Check that Vader starship is in the film", priority = 2)
    public void checkVaderStartshipInFilm(){
        log.info("Checking Starship endpoint");
        boolean starshipInList = false;
        List<String> starships = (List<String>) (singlePerson.get("starships"));
        vaderStarshipEndpoint = starships.getFirst();
        List<String> starshipsList = ApiHelper.getStarshipsList(film);
        for (int i = 0; i < starshipsList.size(); i++){
            if (vaderStarshipEndpoint.equals(starshipsList.get(i))){
                starshipInList = true;
                break;
            }
        }
        Assert.assertTrue(starshipInList);
    }

    @Test(description = "Get Oldest Person", priority = 3)
    public void checkOldestPerson(){
        log.info("Finding olderst person to play in Star Wars");
        Response response = requests.getPeople(HttpStatus.SC_OK);
        int peopleCount = response.then().extract().path("count");
        int pagesCount = (int) Math.ceil((double) peopleCount / 10);
        LinkedHashMap oldestPerson = ApiHelper.getOldestPerson(pagesCount);
        String oldestPersonaName = (String) (oldestPerson.get("name"));
        Assert.assertEquals(oldestPersonaName, "Yoda");
        int oldestRequestsCount = ApiHelper.getOldestRequestsCount();
        Assert.assertTrue(oldestRequestsCount < 10);
    }

    @Test(description = "Contract Test", priority = 4)
    public void checkPeopleJsonStructure(){
        log.info("Checking Model for List People request");
        Response response = requests.getPeople(8, HttpStatus.SC_OK);
        log.info("Check response matches schema: People Users");
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("listPeople.json"));
    }
}
