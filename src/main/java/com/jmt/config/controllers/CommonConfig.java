package com.jmt.config.controllers;

import com.jmt.menus.Menu;
import com.jmt.menus.MenuDetail;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public interface CommonConfig {
    @ModelAttribute("menuCode")
    default String getMenuCode() {

        return "config";
    }

    @ModelAttribute("subMenus")
    default List<MenuDetail> getSubMenus() {
        return Menu.getMenus("config");
    }

}
