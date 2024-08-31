package com.jmt.board.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.board.entities.BoardData;
import com.jmt.board.services.BoardDataService;
import com.jmt.board.services.BoardInfoService;
import com.jmt.global.ListData;
import com.jmt.global.Pagination;
import com.jmt.global.Utils;
import com.jmt.global.rests.JSONData;
import com.jmt.menus.Menu;
import com.jmt.menus.MenuDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardDataController
{
    private final BoardInfoService boardInfoService;
    private final BoardDataService boardDataService;
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;

    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "board";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return Menu.getMenus("board");
    }

    @GetMapping("/posts")
    public String dataList2(@ModelAttribute BoardDataSearch search, Model model)  {
        if(search.getLimit() == 0) search.setLimit(20); //디폴트 limit
        ListData<BoardData> data = boardInfoService.getList(search);
        List<BoardData> items = data.getItems();
        Pagination pagination = data.getPagination();


        model.addAttribute("posts", items);
        model.addAttribute("pagination", pagination);

        return "board/posts";
    }
    @GetMapping("/posts/delete/{seq}")
    public String deletePost(@PathVariable("seq") Long seq, @RequestParam("mode") String mode, BoardDataSearch search, Model model ) {

        System.out.println("mode: " + mode);
        BoardData post = boardDataService.deleteBoardData(seq, mode);

        /* 이건 자바스크립트로 처리하기
        if(post.getSeq() != null && Objects.equals(post.getSeq(), seq)) {
            model.addAttribute("message", "삭제 성공");
        } else {
            model.addAttribute("message", "삭제 실패");
        }
        */
        //return "redirect:" + utils.redirectUrl("/board/posts" );
        model.addAttribute("script", "parent.location.reload();");
        return "common/_execute_script";
    }

    @GetMapping("/posts2")
    public String dataList(Model model) throws JsonProcessingException {

        String url = utils.url("/board_data/list", "api-service");
        System.out.println("url:" + url );

        HttpHeaders headers = utils.getCommonHeaders("GET");
        HttpEntity<JSONData> request = new HttpEntity<>(headers);

        ResponseEntity<JSONData> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, request, JSONData.class);

        if(!response.getStatusCode().toString().contains("200")) {
            throw new RuntimeException("api Server returned: " + response.getStatusCode());
        }

        String jsonString = om.writeValueAsString(response.getBody().getData());
        System.out.println("jsonString :" + jsonString);

        List<BoardData> items = om.readValue(jsonString, new TypeReference<List<BoardData>>() {});
        System.out.println(items);

        model.addAttribute("posts", items);
        commonProcess("posts", model);
        return "board/posts";
    }

    private void commonProcess(String mode, Model model) {
        String pageTitle = "게시판 목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if (mode.equals("add")) {
            pageTitle = "게시판 등록";

        } else if (mode.equals("edit")) {
            pageTitle = "게시판 수정";

        } else if (mode.equals("posts")) {
            pageTitle = "게시글 관리";

        }

        model.addAttribute("pageTitle", pageTitle);
    }
}
