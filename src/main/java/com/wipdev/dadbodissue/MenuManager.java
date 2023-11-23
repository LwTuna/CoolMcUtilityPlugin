package com.wipdev.dadbodissue;

import com.wipdev.dadbodissue.menu.ChestMenu;
import com.wipdev.dadbodissue.menu.ChestMenuItem;
import com.wipdev.dadbodissue.menu.menus.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager implements Listener {

    private Map<String, ChestMenu> menus = new HashMap<>();

    public MenuManager(){
        initializeMenus();
    }

    private void initializeMenus() {
        registerMenu(MainMenu.title,new MainMenu());
        registerMenu("HomeMenu",new HomeMenu());
        registerMenu("HomeSettingsMenu",new HomeSettingsMenu());
        registerMenu("ColorMenu",new ColorMenu());
    }

    public void registerMenu(String name, ChestMenu chestMenu){
        menus.put(name,chestMenu);
    }

    public void openMenu(String name, Player player){
        if(menus.containsKey(name)){
            menus.get(name).openMenu(player);
        }else{
            player.sendMessage("§cThe menu §e" + name + " §cdoes not exist!");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        DadBodIssue.getInstance().getLogger().info("Clicked Menu: " + e.getView().getTitle());
        if(e.getClickedInventory() != null && menus.containsKey(e.getView().getTitle())){
            e.setCancelled(true);
            ChestMenu chestMenu = menus.get(e.getView().getTitle());

            if(e.getWhoClicked() instanceof Player){
                Player player = (Player) e.getWhoClicked();
                if(e.getCurrentItem() != null){
                    chestMenu.onClick(player,e.getCurrentItem(),e.getSlot());
                }
            }
        }
    }





}
