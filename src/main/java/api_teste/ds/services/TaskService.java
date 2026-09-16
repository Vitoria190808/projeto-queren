
//Pacote onde esta a classe de serviço no projeto
package api_teste.ds.services;

//Importa list da biblioteca padrão do Java para manipular coleções de objetos
import java.util.List;
//Importa Optional, usado para tratar valores que podem não estar presentes (evita NullExceptionPointer)
import java.util.Optional;


//Importa a anotação do Spring para a injeção automatica de dependencias
import org.springframework.beans.factory.annotation.Autowired;
//Importa a anotação que define essa classe como um componente de serviço gerenciado pelo Spring
import org.springframework.stereotype.Service;
//Importa a anotação para gerenciar transações no banco de dados(garante atomicidade na operação)
import org.springframework.transaction.annotation.Transactional;

//Importa o models.Task
import api_teste.ds.models.Task; 
//Importa o models.User
import api_teste.ds.models.User;
//Importa a inteface do repositorio responsavel pelas operações no banco de dados
import api_teste.ds.repositories.TaskRepository;


//Anotação que indica para o Spring que essa classe contem as regras de negocio
@Service
public class TaskService {
    
    //Injeta automaticamente a instancia do TaskRepository gerenciado pelo Spring
    @Autowired
    private  TaskRepository taskRepository;

    //Injeta automaticamente a instancia do Userservice para validar o usuario
    @Autowired
    private UserService userService;


    //Método para buscar task apartir do ID
    public Task findById(long Id){
        //Executa a busca no banco, retorna um Optional contendo (ou não) a task
        Optional <Task> task = this.taskRepository.findByUser_Id(id);

        // Se a tarefa existir, retorna o objeto, se estiver vazio, lança um RunTimeException
        return task.orElseThrow(()-> new RuntimeException(
        "Tarefa não encontrada! id:"+ id + ",Tipo:" + Task.class.getName()
    ));
    }

    //Método para buscar todas as tarefas vinculadas a um determinado usuario
    public List <Task> findByUserId(long UserId )

    //Chama o UserService para garantir que o usuario existe no banco(lança exceção se não existir)
        this.userService.findById(UserId);


        //Executa a busca customizada no repositorio filtrando pelo id do usuario
        List<Task> tasks = this.taskRepository.findByUser_Id(UserId);

        //Retorna a lista de tarefas
        return tasks;

        //Garante que criação ocorra dentro de uma transação de banco de dados(rolback automatico se falhar)
        @Transactional

        public Task create(Task obj){
        //Valida se o usuario informa no objeto realmente existe no banco e recupera seus dados
            User user  = this.userService.findById(obj.getUser().getId());
        
            //Define o ID como null para garantir que o JPA realize uma inserção(INSERT) e não uma atualização
            obj.setId(null);

            //Associa a entidade User completa e validada a tarefa
            obj.setUser(user);
        
            //Salva a nova tarefa no banco de dados e atualiza 'obj' com o ID gerado
            obj = this.taskRepository.save(obj);

            //Retorna a tarefa salva
            return obj;
        }

        //Garante que a atualização ocorra dentro de transação isolada no banco
        @Transactional
        public Task update(Task obj){
        
        //Reaproveita o findByID para verificar se a tarefa a ser atualizada existe realmente
        Task newObj = findById(obj.getId());
        
        newObj.setDescription(obj.getDescription());

        return this.taskRepository.save(newObj);
        }

}
//Método para deletar uma tarefa pelo Id
public void delete(long Id){
//Verifica se a tarefa existe antes de tentar deletar
findById(Id);
try{
//solicita a remoção da tarefa no banco de dados pelo ID
this.taskRepository.deleteById(Id);
} catch (Exception e){
    //Captura execcoes (como violações de chave estrangeira e lança uma mensagem amigavel)
    throw new RuntimeException("Não é possivel excluir pois não há tarefas relacionadas")

}

}

}
