package org.example.plain.organ;

import org.example.plain.entity.Organ;
import org.example.plain.repository.OrganRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class OrganRepositoryTest {
    private Organ organ1;
    private Organ organ2;
    private Organ organ3;

    @Autowired
    private OrganRepository organRepository;

    @Test
    public void testClass() {
        System.out.println("__________" + organRepository.getClass().getName());
    }

    @BeforeEach
    void setup() {
        // build() 키워드로 Group 객체 최종 반환
        organ1 = Organ.builder().o_id("1").o_name("플레인").build();
        organ2 = Organ.builder().o_id("2").o_name("심볼즈").build();
        organ3 = Organ.builder().o_id("3").o_name("대마고").build();
    }

    @Test
    public void createAddRead() {
        organRepository.deleteAll();

        // given
        organRepository.save(this.organ1); // DB에 Group 데이터가 저장되도록
        organRepository.save(this.organ2);

        // when
        Optional<Organ> test1 = organRepository.findById(this.organ1.getO_id()); // select로 가져오기
        Optional<Organ> test2 = organRepository.findById(this.organ2.getO_id());

        // then
        assertThat(test1.isPresent()).isTrue();
        assertThat(test2.isPresent()).isTrue();

        assertThat(test1.get().getO_id()).isEqualTo(this.organ1.getO_id());
        assertThat(test1.get().getO_name()).isEqualTo(this.organ1.getO_name());
        assertThat(test2.get().getO_id()).isEqualTo(this.organ2.getO_id());
        assertThat(test2.get().getO_name()).isEqualTo(this.organ2.getO_name());
    }

    @Test
    public void update() {
        organRepository.deleteAll();

        // given
        organRepository.save(this.organ1);
        organRepository.save(this.organ2);

        // when
        // id와 업데이트된 이름 받기
        String id = "1";
        Organ updatedOrganData = Organ.builder().o_id("1").o_name("용암중").build();
        Organ updatedOrgan = Organ.builder().o_id(id).o_name(updatedOrganData.getO_name()).build();
        organRepository.save(updatedOrgan);

        // then
        Optional<Organ> test1 = organRepository.findById(id);

        assertThat(test1.isPresent()).isTrue();
        assertThat(test1.get().getO_id()).isEqualTo(id);
        assertThat(test1.get().getO_name()).isEqualTo(updatedOrganData.getO_name());
    }
}
