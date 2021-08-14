package br.com.treinandoComRestAssured.petstoreTest;


import br.com.treinandoComRestAssured.util.ManipulationJSON;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class PetStore {
    // URI o endereço que vou acessar para fazer as request
    private final String URI = "https://petstore.swagger.io/v2/pet";

    // path do json que vou enviar para o request
    String pathJson = "src/test/resources/json/pet.json";

    // referência de classe que lê objetos JSON
    ManipulationJSON manipuladorJson;

    @BeforeMethod
    public void setup(){
        manipuladorJson = new  ManipulationJSON();
    }

    // utilizando a anotação @Test do TestNg
    @Test
    public void testDeJson(){

        String jsonBody = manipuladorJson.getJSONBody(pathJson);
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
                .body("id", is(27041998))

        ;


    }





}
