package com.wipdev.dadbodissue.menu.menus;

import com.wipdev.dadbodissue.DadBodIssue;
import com.wipdev.dadbodissue.menu.ChestMenu;
import com.wipdev.dadbodissue.menu.ChestMenuItem;
import com.wipdev.dadbodissue.menu.ChestMenuItemProvider;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColorMenu extends ChestMenu implements ChestMenuItemProvider {

    private static final Map<Material,ChatColor> colors = new HashMap<>();
    public ColorMenu() {
        super(18, "ColorMenu");
        colors.put(Material.WHITE_STAINED_GLASS_PANE,ChatColor.WHITE);
        colors.put(Material.ORANGE_STAINED_GLASS_PANE,ChatColor.GOLD);
        colors.put(Material.MAGENTA_STAINED_GLASS_PANE,ChatColor.LIGHT_PURPLE);
        colors.put(Material.LIGHT_BLUE_STAINED_GLASS_PANE,ChatColor.AQUA);
        colors.put(Material.YELLOW_STAINED_GLASS_PANE,ChatColor.YELLOW);
        colors.put(Material.LIME_STAINED_GLASS_PANE,ChatColor.GREEN);
        colors.put(Material.GRAY_STAINED_GLASS_PANE,ChatColor.GRAY);
        colors.put(Material.LIGHT_GRAY_STAINED_GLASS_PANE,ChatColor.GRAY);
        colors.put(Material.CYAN_STAINED_GLASS_PANE,ChatColor.DARK_AQUA);
        colors.put(Material.PURPLE_STAINED_GLASS_PANE,ChatColor.DARK_PURPLE);
        colors.put(Material.BLUE_STAINED_GLASS_PANE,ChatColor.DARK_BLUE);

    }

    @Override
    public ChestMenuItemProvider getChestMenuItemProvider() {
        return this;
    }

    @Override
    public List<ChestMenuItem> getChestMenuItems(Player player) {
        List<ChestMenuItem> menuItems = new ArrayList<>();
        int slot = 0;
        for(Material material : colors.keySet()){

            menuItems.add(createChestMenuItem(material,"§6§lColor",slot,(player1) -> {
                player1.sendMessage("§7Your name color has been set to " + material.name());
                player1.setDisplayName(colors.get(material) + player1.getName());
                DadBodIssue.getInstance().dataRepository.setNameColor(player1,colors.get(material).toString());
            }));
            slot++;
        }
        return menuItems;
    }
}
