package com.wipdev.dadbodissue.menu.menus;

import com.wipdev.dadbodissue.DadBodIssue;
import com.wipdev.dadbodissue.Model.HomeData;
import com.wipdev.dadbodissue.menu.ChestMenu;
import com.wipdev.dadbodissue.menu.ChestMenuItem;
import com.wipdev.dadbodissue.menu.ChestMenuItemProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomeSettingsMenu extends ChestMenu implements ChestMenuItemProvider{
    public HomeSettingsMenu() {
        super(54, "HomeSettingsMenu");
    }

    @Override
    public List<ChestMenuItem> getChestMenuItems(Player player) {
        List<ChestMenuItem> homeSettingsMenuItems = new ArrayList<>();
        try {
            List<HomeData>homeDataList = DadBodIssue.getInstance().dataRepository.getAllHomesForPlayer(player);
            for(int i=0;i<homeDataList.size();i++){
                HomeData homeData = homeDataList.get(i);
                Material material = homeData.isPublic() ? Material.GREEN_GLAZED_TERRACOTTA : Material.RED_GLAZED_TERRACOTTA;
                List<String> lore = List.of("§7Click to toggle the publicity of this home.","§7Currently: " + (homeData.isPublic() ? "§aPublic" : "§cPrivate"));
                homeSettingsMenuItems.add(createChestMenuItem(material,homeData.getName(),i,
                        (player1) -> {
                            DadBodIssue.getInstance().dataRepository.toggleHomePublicity(homeData);
                            openMenu(player1);
                        }
                        ,lore));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return homeSettingsMenuItems;
    }

    @Override
    public ChestMenuItemProvider getChestMenuItemProvider() {
        return this;
    }


}
