package com.wipdev.dadbodissue;

import com.wipdev.dadbodissue.Model.DataRepository;
import com.wipdev.dadbodissue.Model.PlayerData;
import lombok.var;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.Objects;

public class JoinQuitListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        String joinMessage = DadBodIssue.getInstance().getConfig().getString("joinMessage");
        DadBodIssue.getInstance().getLogger().info("Join message: " + joinMessage);
        joinMessage = Objects.requireNonNull(joinMessage).replace("%player%", event.getPlayer().getName());
        try {
            if(!DadBodIssue.getInstance().dataRepository.existPlayer(event.getPlayer())){
                onFirstJoinTheServer(event.getPlayer());
                joinMessage = Objects.requireNonNull(DadBodIssue.getInstance().getConfig().getString("firstJoinMessage")).replace("%player%", event.getPlayer().getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        event.setJoinMessage(ChatColor.YELLOW+joinMessage);
    }

    private void onFirstJoinTheServer(Player player) {
        try {
            DadBodIssue.getInstance().dataRepository.createOrUpdatePlayerData(new PlayerData(player.getUniqueId(), player.getName(), "§f", "§f", false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String quitMessage = DadBodIssue.getInstance().getConfig().getString("quitMessage");
        quitMessage = Objects.requireNonNull(quitMessage).replace("%player%", event.getPlayer().getName());
        event.setQuitMessage(quitMessage);
    }
}
