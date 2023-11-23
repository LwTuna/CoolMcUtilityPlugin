package com.wipdev.dadbodissue.menu;

import org.bukkit.entity.Player;

import java.util.List;

public interface ChestMenuItemProvider {

    List<ChestMenuItem> getChestMenuItems(Player player);
}
