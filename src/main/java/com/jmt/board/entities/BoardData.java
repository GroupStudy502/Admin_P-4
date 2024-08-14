package com.jmt.board.entities;

import com.jmt.global.entities.BaseEntity;
import com.jmt.member.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}