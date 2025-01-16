package org.example.plain.repository;

import jakarta.transaction.Transactional;
import org.example.plain.entity.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganRepository extends JpaRepository<Organ, String> {
}
