package org.example.plain.domain.notice.repository;

import org.example.plain.domain.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

}
