package com.wipdev.dadbodissue.commands;

import com.wipdev.dadbodissue.DadBodIssue;
import com.wipdev.dadbodissue.Model.DataRepository;
import com.wipdev.dadbodissue.Model.HomeData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class HomeExecutor implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            DataRepository dataRepository = DadBodIssue.getInstance().dataRepository;
            String homename = strings[0];
            if(homename == null)
                return false;
            try {
                HomeData homeData = dataRepository.getByHomeName(homename);
                if(homeData == null){
                    return false;
                }
                if(homeData.getPlayerUUID().equals(player.getUniqueId()) || homeData.isPublic()){
                    player.teleport(homeData.getLocation());
                    player.sendMessage("Teleported to home: "+homename);
                }
            } catch (SQLException e) {
                player.sendMessage("Error getting home data, name not found?");
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            DataRepository dataRepository = DadBodIssue.getInstance().dataRepository;
            try {
                if(strings.length==1)
                    return dataRepository.getAllHomesVisibleForPlayer(player).stream().map(HomeData::getName).collect(java.util.stream.Collectors.toList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


}
