package com.wipdev.dadbodissue.menu;


import com.wipdev.dadbodissue.MenuManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
@Getter
public abstract class ChestMenu {

    protected final int chestSize;
    protected final String title;

    private final ChestMenuItemProvider chestMenuItemProvider;

    protected Inventory inventory;

    protected MenuManager menuManager;

    public ChestMenu(int chestSize,String title){
        assert chestSize % 9 == 0;
        this.chestSize = chestSize;
        this.title = title;
        this.chestMenuItemProvider = getChestMenuItemProvider();
    }


    void buildMenu(Player player){
        inventory = Bukkit.createInventory(null,chestSize,title);
        chestMenuItemProvider.getChestMenuItems(player).forEach(item -> inventory.setItem(item.getSlot(),item.getItemStack()));
    }

    public void openMenu(Player player){
        buildMenu(player);
        player.openInventory(inventory);
    }

    public void onClick(Player player, ItemStack itemStack,int slot){

        chestMenuItemProvider.getChestMenuItems(player).stream().filter(item -> item.getSlot() == slot || item.getItemStack().equals(itemStack)).findFirst().ifPresent((item) -> {
            if(item.getItemClickHandler() != null){
                item.getItemClickHandler().onClick(player);
            }

        });
    }


    protected static ChestMenuItem createChestMenuItem(Material material,String displayName, int slot, ItemClickHandler itemClickHandler){
        ItemStack item = new ItemStack(material);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);
        return new ChestMenuItem(slot,item,itemClickHandler);
    }

    protected static ChestMenuItem createChestMenuItem(Material material,String displayName, int slot, ItemClickHandler itemClickHandler,List<String> lore){
        ItemStack item = new ItemStack(material);
        item.setAmount(1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return new ChestMenuItem(slot,item,itemClickHandler);
    }

    public abstract ChestMenuItemProvider getChestMenuItemProvider();



}
