package com.jmt.member.constants;

public enum Authority {
    ALL("전체"),
    USER("일반"),
    ADMIN("관리자");

    private final String title;

    Authority(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
