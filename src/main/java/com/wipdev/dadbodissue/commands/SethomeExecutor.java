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

public class SethomeExecutor implements CommandExecutor, TabCompleter {
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
                boolean isPublic = strings.length > 1 && strings[1].equalsIgnoreCase("public");
                if(homeData == null){
                    //Create new home
                    HomeData newHome = new HomeData();
                    newHome.setName(homename);
                    newHome.setPlayerUUID(player.getUniqueId());
                    createHome(player, dataRepository, isPublic, newHome);
                    player.sendMessage("Home created!");
                }else{
                    if(homeData.getPlayerUUID().equals(player.getUniqueId())){
                        //Update home
                        createHome(player, dataRepository, isPublic, homeData);
                        player.sendMessage("Home updated!");
                    }else {
                        player.sendMessage("You do not own this home!");
                        return false;
                    }
                }
            } catch (SQLException e) {
                player.sendMessage("Error creating home: "+e.getMessage());
                return false;
            }
            return true;
        }
        return false;
    }

    private void createHome(Player player, DataRepository dataRepository, boolean isPublic, HomeData newHome) throws SQLException {
        newHome.setWorldUUID(player.getWorld().getUID());
        newHome.setX(player.getLocation().getX());
        newHome.setY(player.getLocation().getY());
        newHome.setZ(player.getLocation().getZ());
        newHome.setYaw(player.getLocation().getYaw());
        newHome.setPitch(player.getLocation().getPitch());
        newHome.setPublic(isPublic);
        dataRepository.createOrUpdateHome(newHome);
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            DataRepository dataRepository = DadBodIssue.getInstance().dataRepository;
            try {
                if(strings.length==1)
                    return dataRepository.getAllHomesForPlayer(player).stream().map(HomeData::getName).collect(java.util.stream.Collectors.toList());
                if(strings.length==2)
                    return List.of("public", "private");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
