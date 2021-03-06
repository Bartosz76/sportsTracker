package bm.app.sports.repository;

import bm.app.sports.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findAll();

    @Query("from Exercise where exerciseType like concat('%', 'push', '%')")
    List<Exercise> findAllPushups();
}
