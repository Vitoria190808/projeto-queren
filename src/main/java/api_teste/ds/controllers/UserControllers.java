package api_teste.ds.controllers;



import java.net.URI; // importa a classe URI para construir e manipular HTTP de novos

import  org.springframework.beans.factory.annotation.Autowired; // injcão automatica do Spring
import  org.springframework.http.ResponseEntity; // importa a classe para montar a resposta HTTP completa(status, heards,corpo)
import  org.springframework.validation.annotation.Validated; //importa anotação para habitar suporte a validação no Controller
import  org.springframework.web.bind.annotation.DeleteMapping; // mapeia requisições do tipo delete 
import  org.springframework.web.bind.annotation.GetMapping; // mapeia requisições do tipo get
import  org.springframework.web.bind.annotation.PatchMapping; // mapeia variaveis passadas diretamente via ca,imho da URL
import org.springframework.web.bind.annotation.PathVariable;
import  org.springframework.web.bind.annotation.PostMapping; // mapeia requisições do tipo POST
import  org.springframework.web.bind.annotation.PutMapping;  // mapeia requisições do tipo PUT
import  org.springframework.web.bind.annotation.RequestBody;  // converte objetos JSON em objetos JAVA
import  org.springframework.web.bind.annotation.RequestMapping; // importa anotação para definir o caminho/rorta base do controlle
import  org.springframework.web.bind.annotation.RestController; // importa a anotação que define estta classe como um controller REST
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; //importa utilitario para gerar a URI da requisição atual dinamicamente

import api_teste.ds.models.User;
import api_teste.ds.models.User.CreateUser;
import api_teste.ds.User.UpdateUser;
import api_teste.ds.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
 

@RestController  // define a classe como um controlador REST que retorna respostas em JSON
@RequestMapping ("/user") // define que todas as rotas desta classe terão como prefixo o ca,imho "/user"
@Validated  // Ativa a verificação de validações nos parametros recebidos no controller

public class UserControllers {


    @Autowired 
    private UserService userService;

    @GetMapping("/{id}") // mapeia requisições HTTP GET na rota "/user/{id}"
    public ResponseEntity<User> findById(@PathVariable Long Id){ // metodo para buscar usuario por id capturado da URL
        User obj=this.userService.findById(Id); // invoca a busca do usuario atraves do ID recebido
        return ResponseEntity.ok().body(obj); // retorn codigo HTTP 200(ok) com o objeto no corpo da resposta
    } // fim do metodo FindById

    @PostMapping 
    public ResponseEntity<void> create(@Validated (CreateUser.class) @RequestBody User obj){
        this.userService.create(obj);
        URI url = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(obj.getId()).toUri();
            return  ResponseEntity.created(url).build();
    }
    
    
    
}


    

    

