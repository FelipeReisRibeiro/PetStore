// 1 - pacote
package petstore;



//2 - bliblioteca



import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

// 3 - classe
public class Pet {

// 3.1 - atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço entidade pet

// 3.2 metodos e funções
    public String lerJson(String caminhoJson ) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    // incluir - create - post
    @Test //indentifica como um metodo ou função de teste
    public void incluirPet() throws IOException {
       String jsonBoody = lerJson("db/pet1.json");

       //Sintaxe Gherkin
       //Dado - Quando - Entao
        //Given - When - Then

        given()//dado
                .contentType("application/json") // comum em API REST - antigas era "test/xml
                .log().all()
                .body(jsonBoody)
        .when()//quando
                .post(uri)
        .then()//então
                .log().all()
                .statusCode(200)  // esta tudo a mesma linha des do given por isso o ; so agora
                .body("name", is("Thor"))
                .body("status", is("available"))

                ;
    }


}
