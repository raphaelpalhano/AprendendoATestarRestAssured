package br.com.treinandoComRestAssured.petstoreTest;


import br.com.treinandoComRestAssured.util.ManipulationJSON;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


public class PetStore {
    // URI o endereço que vou acessar para fazer as request
    private final String URI = "https://petstore.swagger.io/v2/pet";

    // path do json que vou enviar para o request
    String pathJson = "src/test/resources/json/pet.json";
    String pathJosn2 = "src/test/resources/json/petAlteracao.json";
    // referência de classe que lê objetos JSON
    private static ManipulationJSON jsonOrigin;
    private static ManipulationJSON jsonAlteration;
    private static String jsonBody;
    private static String jsonBodyAlteration;

    @BeforeMethod
    public void setup(){
        jsonOrigin = new  ManipulationJSON(pathJson);
       jsonBody = jsonOrigin.getJSONBodyObject();
       jsonAlteration = new ManipulationJSON(pathJosn2);
       jsonBodyAlteration = jsonAlteration.getJSONBodyObject();

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
                .body("tags.name", contains("TRASS"))
                .body("tags.id", contains(2021))
      ;
    }

    @Test(dependsOnMethods = "request_Put_Pet")
    public void request_Get_Verification_Pet(){
        String token =
                given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(this.URI + "/" + jsonOrigin.getKeyObject("id"))

        .then()
                .log().all()
                .statusCode(200)
                .body("category.name", is("ADD31123FFDCKC20CAX"))
                .body("category.id", is(1))
                .body("status", is("sold"))
        .extract().path("category.name")

        ;
        System.out.println("O Token de acesso é " + token);

    }


    @Test(dependsOnMethods = "request_Post_New_Pet")
    public void request_Put_Pet(){

        given()
                .contentType("application/json").log().all()
                .body(jsonBodyAlteration)
        .when()
                .put(URI)
        .then()
                .log().all().statusCode(200)
                .body("status", is("sold"))
                ;
    }

    @Test(dependsOnMethods = "request_Get_Verification_Pet")
    public void request_Delete_Pet(){

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(URI + "/" + jsonOrigin.getKeyObject("id"))

        .then().log().all().statusCode(200)
                .body("code", is(200))
                .body("message", is("27041938"))
                .body("type", is("unknown"))
        ;

    }

}
