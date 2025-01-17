package org.example.plain.repository;

import org.example.plain.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganRepository extends JpaRepository<Organ, String> {
    List<Organ> findAllByOrderByOrganNameAsc();
}
