package com.wipdev.dadbodissue;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class MenuItemListeners implements Listener {

    private ItemStack createMenuItem(){
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6§lMENU");
        meta.setLore(Collections.singletonList("§7Click to open the menu!"));
        item.setItemMeta(meta);
        return item;
    }

    private boolean isItem(ItemStack item){
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("§6§lMENU");
    }

    private void setItemForPlayer(Player player){
        player.getInventory().setItem(8,createMenuItem());
    }
    private boolean containsItem(Player player){
        return Arrays.stream(player.getInventory().getContents()).anyMatch(this::isItem);
    }

    private void openMenu(Player player){
        DadBodIssue.getInstance().menuManager.openMenu("MainMenu",player);
    }

    /***************************************Keep Menu Item Save and usable***************************************/
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!containsItem(event.getPlayer()))
            setItemForPlayer(event.getPlayer());
    }

    //Prevent Moving and Dropping the item
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if(isItem(e.getItemDrop().getItemStack()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.getDrops().removeIf(this::isItem);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        e.getPlayer().getInventory().setItem(8, createMenuItem());
    }
    @EventHandler
    public void onItemMove(InventoryMoveItemEvent e) {
        if(isItem(e.getItem()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e) {
        if (isItem(e.getOldCursor())) {
            e.setCancelled(true);
            openMenu((Player) e.getWhoClicked());
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getClick() == ClickType.NUMBER_KEY) {
            if (e.getHotbarButton() == 8 || isItem(e.getCurrentItem())) {
                e.setCancelled(true);
                return;
            }
        }
        if (isItem(e.getCurrentItem())) {
            openMenu((Player) e.getWhoClicked());
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent e) {
        if (e.getOffHandItem() == null) return;

        if (isItem(e.getOffHandItem())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent e) {
        if (e.getItem() == null) return;

        if (isItem(e.getItem())) {
            e.setCancelled(true);
            openMenu(e.getPlayer());
        }
    }
}
