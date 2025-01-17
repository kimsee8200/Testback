package org.example.plain.organ;

import org.example.plain.dto.OrganDTO;
import org.example.plain.entity.Organ;
import org.example.plain.repository.OrganRepository;
import org.example.plain.service.OrganService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OrganServiceTest {

    @Autowired
    private OrganService organService;
    @Autowired
    private OrganRepository organRepository;

    private Organ organ1;
    private Organ organ2;
    private OrganDTO organDTO1;
    private OrganDTO organDTO2;

    @BeforeEach
    public void setup() {
        organ1 = Organ.builder().organId(UUID.randomUUID().toString()).organName("대마고").build();
        organ2 = Organ.builder().organId(UUID.randomUUID().toString()).organName("플레인").build();
        organDTO1 = OrganDTO.builder().organId(UUID.randomUUID().toString()).organName("용암중").build();
        organDTO2 = OrganDTO.builder().organId(UUID.randomUUID().toString()).organName("상당초").build();
    }

    @Test
    public void createOrgan() {
        // given
        organRepository.deleteAll();

        // when
        organService.createOrgan(organDTO1);
        organService.createOrgan(organDTO2);

        // then
        List<Organ> organList = organRepository.findAll();
        assertThat(organList.size()).isEqualTo(2);
    }

    @Test
    public void readOrganAll() {
        // given
        organRepository.deleteAll();
        organRepository.save(organ1);
        organRepository.save(organ2);

        // when
        List<OrganDTO> organList = organService.readOrganAll();

        // then
        assertThat(organList.size()).isEqualTo(2);
        assertThat(organList.get(0).getOrganName()).isEqualTo(organ1.getOrganName());
        assertThat(organList.get(1).getOrganName()).isEqualTo(organ2.getOrganName());

    }
    // 개별 조회, 수정, 삭제
    // 그룹 내 맴버 조회
    @Test
    public void readOrganById() {
        //given
        organRepository.deleteAll();
        organRepository.save(organ1);
        organRepository.save(organ2);

        // when
        OrganDTO organ = organService.readOrgan(organ1.getOrganId());

        // then
        assertThat(organ.getOrganId()).isEqualTo(organ1.getOrganId());
        assertThat(organ.getOrganName()).isEqualTo(organ1.getOrganName());
    }

    @Test
    public void updateOrgan() {
        // given
        organRepository.deleteAll();
        organRepository.save(organ1);
        organRepository.save(organ2);

        // when
        OrganDTO organDTO = OrganDTO.builder().organId(organ2.getOrganId()).organName("직화육포").build();
        organService.updateOrgan(organDTO);

        // then
        Optional<Organ> updatedOrgan = organRepository.findById(organ2.getOrganId());
        updatedOrgan.ifPresent(organ -> assertThat(organ.getOrganName()).isEqualTo(organDTO.getOrganName()));
    }

    @Test
    public void deleteOrgan() {
        // given
        organRepository.deleteAll();
        organRepository.save(organ1);
        organRepository.save(organ2);

        // when
        organService.deleteOrgan(organ1.getOrganId());

        // then
        List<Organ> organList = organRepository.findAll();
        assertThat(organList.size()).isEqualTo(1);
    }
}
