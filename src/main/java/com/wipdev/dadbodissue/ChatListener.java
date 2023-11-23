package com.wipdev.dadbodissue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        String message = "";
        try {
             message=DadBodIssue.getInstance().dataRepository.getPlayerData(event.getPlayer().getUniqueId()).getNameColor();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        message +="<"+ event.getPlayer().getName() + ">: " + event.getMessage();
        DadBodIssue.getInstance().getServer().broadcastMessage(message);

    }
}
