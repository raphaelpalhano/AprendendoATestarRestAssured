package br.com.treinandoComRestAssured.petstoreTest;


import br.com.treinandoComRestAssured.util.ManipulationJSON;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class PetStore {
    // URI o endereço que vou acessar para fazer as request
    private final String URI = "https://petstore.swagger.io/v2/pet";

    // path do json que vou enviar para o request
    String pathJson = "src/test/resources/json/pet.json";

    // referência de classe que lê objetos JSON
    ManipulationJSON manipuladorJson;
    public static String jsonBody;

    @BeforeMethod
    public void setup(){
        manipuladorJson = new  ManipulationJSON(pathJson);
       jsonBody = manipuladorJson.getJSONBodyObject();
    }

    // utilizando a anotação @Test do TestNg
    @Test
    public void request_Post_New_Pet(){
       // método da classe RestAssured
        //dado que tem o contentType(application/json)
      given()

                .contentType("application/json")

                //registra o envio da informação
                .log()

                //coloco o conteúdo que quero enviar no body da request
                .all().body(jsonBody)

                //então eu faço uma requisição do tipo POST para registra recurso
        .when().post(this.URI)

        //então eu registro esse envio e verifico se foi cadastro mesmo (ok -200)
        .then()
                .log().all()

                // Validações:
                .statusCode(200)
                .body("name", is("Baily"))
                .body("status", is("available"))
                .body("id", is(27041938))
                .body("tags.name", contains(manipuladorJson.getValueObjectJson("tags", 0, "name")))
                .body("tags.id", contains(manipuladorJson.getValueObjectJson("tags", 0, "id")))

      ;




    }

    @Test(dependsOnMethods = "request_Post_New_Pet")
    public void request_Get_Verification_Pet(){
        String token =
                given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(this.URI + "/" + manipuladorJson.getKeyObject("id"))

        .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is(manipuladorJson.getValueObjectIntoObject("category", "name")))
                .body("category.id", is(manipuladorJson.getValueObjectIntoObject("category", "id")))
        .extract().path("category.name")

        ;
        System.out.println("O Token de acesso é " + token);


    }





}
