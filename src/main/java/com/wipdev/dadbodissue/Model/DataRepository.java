package com.wipdev.dadbodissue.Model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class DataRepository {

    private final Dao<PlayerData, UUID> playerDataDao;
    private final Dao<HomeData, Integer> homeDataDao;

    public DataRepository(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, PlayerData.class);
        playerDataDao = DaoManager.createDao(connectionSource, PlayerData.class);

        TableUtils.createTableIfNotExists(connectionSource, HomeData.class);
        homeDataDao = DaoManager.createDao(connectionSource, HomeData.class);

    }


    public boolean isPlayerAdmin(Player player) throws SQLException {
        return playerDataDao.queryForId(player.getUniqueId()).isAdmin();

    }

    public List<HomeData> getAllHomesVisibleForPlayer(Player player) throws SQLException {
        if(isPlayerAdmin(player)){
            return homeDataDao.queryForAll();
        }else{
            List<HomeData> data =  homeDataDao.queryBuilder().where().eq("owner_uuid",player.getUniqueId()).query();
            data.addAll(homeDataDao.queryBuilder().where().eq("is_public",true).and().ne("owner_uuid",player.getUniqueId()).query());
            return data;
        }
    }

    public List<HomeData> getAllHomesForPlayer(Player player) throws SQLException {
        if(isPlayerAdmin(player)){
            return homeDataDao.queryForAll();
        }else{
            return homeDataDao.queryBuilder().where().eq("owner_uuid",player.getUniqueId()).query();
        }
    }

    public HomeData getByHomeName(String homeName) throws SQLException {
        return homeDataDao.queryBuilder().where().eq("name",homeName).queryForFirst();
    }

    public void createOrUpdateHome(HomeData homeData) throws SQLException {
        homeDataDao.createOrUpdate(homeData);
    }
    public void createOrUpdatePlayerData(PlayerData playerData) throws SQLException {
        playerDataDao.createOrUpdate(playerData);
    }

    public PlayerData getPlayerData(UUID uuid) throws SQLException {
        return playerDataDao.queryForId(uuid);
    }

    public boolean existPlayer(Player player) throws SQLException {
        PlayerData playerData = playerDataDao.queryForId(player.getUniqueId());
        return playerData != null;
    }

    public void toggleHomePublicity(HomeData homeData) {
        try {
            homeData.setPublic(!homeData.isPublic());
            homeDataDao.createOrUpdate(homeData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNameColor(Player player,String colorPrefix){
        try {
            PlayerData playerData = playerDataDao.queryForId(player.getUniqueId());
            playerData.setNameColor(colorPrefix);
            playerDataDao.createOrUpdate(playerData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteHome(HomeData homeData) {
        try {
            homeDataDao.delete(homeData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
