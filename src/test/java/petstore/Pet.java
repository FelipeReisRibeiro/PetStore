// 1 - pacote
package petstore;



//2 - bliblioteca



import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;


// 3 - classe
public class Pet {

// 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço entidade pet

// 3.2 metodos e funções
    public String lerJson(String caminhoJson ) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    // incluir - create - post
    @Test(priority = 1) //indentifica como um metodo ou função de teste
    public void incluirPet() throws IOException {
       String jsonBody = lerJson("db/pet2.json");

       //Sintaxe Gherkin
       //Dado - Quando - Entao
        //Given - When - Then

        given()//dado
                .contentType("application/json") // comum em API REST - antigas era "test/xml
                .log().all()
                .body(jsonBody)
        .when()//quando
                .post(uri)
        .then()//então
                .log().all()
                .statusCode(200)  // esta tudo a mesma linha des do given por isso o ; so agora
                .body("name", is("Thor"))
                .body("status", is("sold"))
                .body("category.name", is("AX234TESFDS35"))
                .body("tags.name", contains("data"))
                ;
    }
    @Test(priority=2)
public void consultarPet(){
        String petId ="13991409130";
String token =
        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("category.name", is("AX234TESFDS35"))
                .body("status", is("sold"))
        .extract()
            .path("category.name")
        ;
        System.out.println("o token é " + token);
                ;
}
    @Test(priority=3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Thor"))
                .body("status",is("sold"))
                ;
        }


@Test(priority = 4)
public void excluirPet() {
        String petId = "13991409130";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
                ;
    }
}
