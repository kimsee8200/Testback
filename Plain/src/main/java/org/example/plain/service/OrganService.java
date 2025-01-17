package org.example.plain.service;

import jakarta.transaction.Transactional;
import org.example.plain.dto.OrganDTO;
import org.example.plain.entity.Organ;
import org.example.plain.repository.OrganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganService {

    @Autowired
    private OrganRepository organRepository;

    // 생성
    public void createOrgan(OrganDTO organDTO) {
        organRepository.save(organDTO.toEntity());
    }

    // 전체 조회 -> 정렬 기준 확인
    public List<OrganDTO> readOrganAll() {
        List<Organ> organList = organRepository.findAllByOrderByOrganNameAsc();
        List<OrganDTO> organDTOList = new ArrayList<>();
        for (Organ organ : organList) {
            organDTOList.add(organ.toDTO());
        }
        return organDTOList;
    }
    // 개별 조회
    public OrganDTO readOrgan(String organId) {
        Optional<Organ> organ = organRepository.findById(organId);
        return organ.map(Organ::toDTO).orElse(null);
    }

    // 수정
    public void updateOrgan(OrganDTO organDTO) {
        organRepository.save(organDTO.toEntity());
    }

    // 삭제
    public void deleteOrgan(String organId) {
        organRepository.deleteById(organId);
    }
}
