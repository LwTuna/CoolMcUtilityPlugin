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
import java.util.stream.Collectors;

public class DelhomeExecutor implements CommandExecutor, TabCompleter {
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
                if(homeData.getPlayerUUID().equals(player.getUniqueId())){
                    dataRepository.deleteHome(homeData);
                    player.sendMessage("Home deleted!");
                }else{
                    player.sendMessage("You do not own this home!");
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
                    return dataRepository.getAllHomesForPlayer(player).stream().map(HomeData::getName).collect(Collectors.toList());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
