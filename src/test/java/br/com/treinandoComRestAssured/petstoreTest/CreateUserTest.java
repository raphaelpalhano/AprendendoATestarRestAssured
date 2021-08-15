package br.com.treinandoComRestAssured.petstoreTest;

import br.com.treinandoComRestAssured.util.ManipulationJSON;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateUserTest {
    private final String URI = "https://petstore.swagger.io/v2/user/createWithList";
    private ManipulationJSON manipuladorJSON;

    @BeforeTest
    public void setup(){
        manipuladorJSON = new ManipulationJSON("src/test/resources/json/user.json");
    }

    @Test
    public void request_Post_Create_User(){
        System.out.println(manipuladorJSON.getJSONBodyInArray(0));
        System.out.println(manipuladorJSON.getKeyArray(0, "username"));
        System.out.println(manipuladorJSON.getValueArrayJSON(0, "userStatus", 0, "bom"));

    }

}
