package org.example.plain.domain.classMember.repository;

import org.example.plain.domain.classMember.entity.ClassMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMemberRepository extends JpaRepository<ClassMember, String> {
}
