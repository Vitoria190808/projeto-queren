package api_teste.ds.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_teste.ds.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Método derivado personalizado
    List<Task> findByUser_Id(Long id);

    /*
    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
    List<Task> findByUser_IdJPQL(@Param("id") Long id);

    @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
    List<Task> findByUser_IdSQL(@Param("id") Long id);
    */
}