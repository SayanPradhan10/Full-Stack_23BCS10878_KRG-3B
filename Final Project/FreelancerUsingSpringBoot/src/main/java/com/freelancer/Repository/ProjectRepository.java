package com.freelancer.Repository;
import java.util.List;
import java.util.ArrayList;

import com.freelancer.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
     List<Project> findByEmployerId(Long employerId);
}
