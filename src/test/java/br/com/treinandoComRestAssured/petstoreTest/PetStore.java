package br.com.treinandoComRestAssured.petstoreTest;


import br.com.treinandoComRestAssured.util.LeitorJSON;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PetStore {
    // URI o endereço que vou acessar para fazer as request
    private final String URI = "https://petstore.swagger.io/v2/pet";

    // path do json que vou enviar para o request
    String pathJson = "src/test/resources/data/pet.json";

    // referência de classe que lê objetos JSON
    LeitorJSON manipuladorJson;

    // utilizando a anotação @Test do TestNg
    @Test
    public void testDeJson(){
        manipuladorJson = new LeitorJSON();
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
        .then().log().all().statusCode(200)
                ;

    }





}
