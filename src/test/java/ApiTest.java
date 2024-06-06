import API.ApiHelper;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;

@Log4j2
public class ApiTest extends BaseTest{

    public List<String> films;
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
}
