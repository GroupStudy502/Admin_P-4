package com.jmt.board.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmt.file.entities.FileInfo;
import com.jmt.global.entities.BaseEntity;
import com.jmt.member.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardData extends BaseEntity {
    private Long seq;
    private String gid;
    private Board board;
    private Member member;
    private String guestPw; // 비회원 비밀번호(수정, 삭제)
    private String category; // 게시글 분류
    private boolean notice; // 공지글 여부
    private String poster;
    private String subject;
    private String content;
    private int viewCount; // 조회수
    private int commentCount; // 댓글 수
    private boolean editorView; // 에디터를 사용해서 글 작성했는지 여부
    private String ip; // IP 주소
    private String ua; // User-Agent
    private Long num1; // 정수 추가 필드1
    private Long num2; // 정수 추가 필드2
    private Long num3; // 정수 추가 필드3
    private String text1; // 한줄 텍스트 추가 필드1
    private String text2; // 한줄 텍스트 추가 필드2
    private String text3; // 한줄 텍스트 추가 필드3

    private String longText1; // 여러줄 텍스트 추가 필드1
    private String longText2; // 여러줄 텍스트 추가 필드2
    @JsonIgnore
    private List<FileInfo> editorImages;
    @JsonIgnore
    private List<FileInfo> attachFiles;
    private boolean editable; // 수정, 삭제 가능 여부

    private boolean commentable; // 댓글 작성 가능 여부

    private boolean showEdit; // 글쓰기,수정 버튼 노출 여부

    private boolean showDelete; // 글삭제 버튼 노출 여부

    private boolean showList; // 글목록 버튼 노출 여부

    private boolean mine; // 게시글 소유자
}