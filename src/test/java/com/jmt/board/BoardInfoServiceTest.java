package com.jmt.board;

import com.jmt.board.controllers.BoardDataSearch;
import com.jmt.board.services.BoardInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardInfoServiceTest {

    @Autowired
    private BoardInfoService infoService;

    @Test
    void test1() {
        BoardDataSearch search = new BoardDataSearch();
        infoService.getList(search);

    }
}
