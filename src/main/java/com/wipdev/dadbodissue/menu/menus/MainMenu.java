package com.wipdev.dadbodissue.menu.menus;

import com.wipdev.dadbodissue.DadBodIssue;
import com.wipdev.dadbodissue.menu.ChestMenu;
import com.wipdev.dadbodissue.menu.ChestMenuItem;
import com.wipdev.dadbodissue.menu.ChestMenuItemProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends ChestMenu implements ChestMenuItemProvider {

    public static final String title = "MainMenu";
    public MainMenu() {
        super(18, title);
    }

    @Override
    public List<ChestMenuItem> getChestMenuItems(Player p) {
        List<ChestMenuItem> mainMenuItems = new ArrayList<>();
        mainMenuItems.add(createChestMenuItem(Material.END_CRYSTAL,"§6§lTeleport to Spawn",2, player -> player.teleport(player.getWorld().getSpawnLocation())));
        List<String> homeLore = new ArrayList<>();
        homeLore.add("§7Click to view your homes.");
        homeLore.add("§6/sethome <name> §7to set a home.");
        homeLore.add("§6/delhome <name> §7to delete a home.");
        homeLore.add("§6/home <name> §7to teleport to a home.");
        mainMenuItems.add(createChestMenuItem(Material.BRICK_STAIRS, "§6§lHomes",6,player -> DadBodIssue.getInstance().menuManager.openMenu("HomeMenu",player),homeLore));
        List<String> mcmmoInfo = new ArrayList<>();
        mcmmoInfo.add("McMMO is the skill plugin for this Server.");
        mcmmoInfo.add("For Help type /mcmmo help");
        mcmmoInfo.add("To Check your Skills type /mcstats");
        mainMenuItems.add(createChestMenuItem(Material.IRON_SWORD,"§6§lMcMMO",11,null,mcmmoInfo));
        List<String> colorLore = new ArrayList<>();
        colorLore.add("§7Click to open the color picker to set your chat/name color.");

        mainMenuItems.add(createChestMenuItem(Material.NAME_TAG, "§6§lChat/Name Color Picker",13,player -> DadBodIssue.getInstance().menuManager.openMenu("ColorMenu",player),colorLore));
        mainMenuItems.add(createChestMenuItem(Material.FISHING_ROD, "§6§lCustom Items",15,player -> DadBodIssue.getInstance().menuManager.openMenu("CustomItems",player), List.of("§7Coming Soon!")));

        //TODO Add Admin/WIP Tools here conditionally when player is admin

        return mainMenuItems;
    }

    @Override
    public ChestMenuItemProvider getChestMenuItemProvider() {
        return this;
    }
}
