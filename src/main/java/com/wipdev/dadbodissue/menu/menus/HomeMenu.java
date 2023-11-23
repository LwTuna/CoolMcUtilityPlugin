package com.wipdev.dadbodissue.menu.menus;

import com.wipdev.dadbodissue.DadBodIssue;
import com.wipdev.dadbodissue.Model.HomeData;
import com.wipdev.dadbodissue.menu.ChestMenu;
import com.wipdev.dadbodissue.menu.ChestMenuItem;
import com.wipdev.dadbodissue.menu.ChestMenuItemProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeMenu extends ChestMenu implements ChestMenuItemProvider {

    public HomeMenu() {
        super(54, "HomeMenu");
    }

    @Override
    public List<ChestMenuItem> getChestMenuItems(Player player) {
        List<ChestMenuItem> homeMenuItems = new ArrayList<>();
        try {
            List<HomeData> homeDataList = DadBodIssue.getInstance().dataRepository.getAllHomesVisibleForPlayer(player);
            for(int i=0;i<homeDataList.size();i++){
                HomeData homeData = homeDataList.get(i);
                homeMenuItems.add(createChestMenuItem(Material.CHEST,homeData.getName(),i,player1 -> player1.teleport(homeData.getLocation())));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> homeSettingsLore = List.of("§7Toggle the publicity of your homes.");
        homeMenuItems.add(createChestMenuItem(Material.GREEN_GLAZED_TERRACOTTA,"§6§lHomeSettingsMenu",53,player1 -> DadBodIssue.getInstance().menuManager.openMenu("HomeSettingsMenu",player1),homeSettingsLore));
        return homeMenuItems;
    }

    @Override
    public ChestMenuItemProvider getChestMenuItemProvider() {
        return this;
    }
}
