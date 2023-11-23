package com.wipdev.dadbodissue.menu;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class ChestMenuItem {

    private final int slot;
    private final ItemStack itemStack;
    private final ItemClickHandler itemClickHandler;

    public ChestMenuItem(int slot, ItemStack itemStack, ItemClickHandler itemClickHandler) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.itemClickHandler = itemClickHandler;
    }
}
