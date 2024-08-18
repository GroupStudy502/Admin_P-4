package com.jmt.api.board;

import com.jmt.board.entities.Board;
import com.jmt.board.services.BoardConfigInfoService;
import com.jmt.global.exceptions.RestExceptionProcessor;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ApiBoardController implements RestExceptionProcessor {
    private final BoardConfigInfoService infoService;

    //게시판 설정 요청에 대한 응답
    @GetMapping("/config/{bid}")
    public JSONData getBoard(@PathVariable("bid") String bid) {
        Board board = infoService.get(bid);

        System.out.println(board);
        return new JSONData(board);
    }

}
