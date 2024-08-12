package com.jmt.board.services;

import com.jmt.board.entities.Board;
import com.jmt.board.repository.BoardConfigRepository;
import com.jmt.file.services.FileDeleteService;
import com.jmt.global.Utils;
import com.jmt.global.exceptions.script.AlertException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardConfigDeleteService {

    private final BoardConfigRepository boardRepository;
    private final BoardConfigInfoService configInfoService;
    private final FileDeleteService fileDeleteService;
    private final Utils utils;

    /**
     * 게시판 삭제
     *
     * @param bid : 게시판 아이디
     */
    public void delete(String bid) {
        Board board = configInfoService.get(bid);

        String gid = board.getGid();

        boardRepository.delete(board);

        boardRepository.flush();

        fileDeleteService.delete(gid);
    }

    public void deleteList(List<Integer> chks) {
        if (chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 게시판을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for (int chk : chks) {
            String bid = utils.getParam("bid_" + chk);
            delete(bid);
        }
    }
}
