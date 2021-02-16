package com.test.study.project1.mycontact.repository;

import com.test.study.project1.mycontact.domain.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;

    @Test
    void crud(){
        Block block = Block.builder()
                .name("martin")
                .reason("그냥 싫음")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
        blockRepository.save(block);

        List<Block> blockList = blockRepository.findAll();

        assertThat(blockList.size()).isEqualTo(1);
        assertThat(blockList.get(0).getName()).isEqualTo("martin");
    }

}