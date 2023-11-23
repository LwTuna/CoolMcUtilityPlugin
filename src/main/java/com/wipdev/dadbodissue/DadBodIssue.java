package com.wipdev.dadbodissue;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.wipdev.dadbodissue.Model.DataRepository;
import com.wipdev.dadbodissue.commands.*;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DadBodIssue extends JavaPlugin {

    @Getter
    private static DadBodIssue instance;
    public DataRepository dataRepository;
    public MenuManager menuManager = new MenuManager();

    public DadBodIssue() {
        if(instance != null)
            throw new IllegalStateException("Instance already exists");
        instance = this;
    }


    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            setupDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        getServer().getPluginManager().registerEvents(new JoinQuitListener(), this);
        getServer().getPluginManager().registerEvents(new MenuItemListeners(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(menuManager, this);
        SethomeExecutor sethomeExecutor = new SethomeExecutor();
        HomeExecutor homeExecutor = new HomeExecutor();
        DelhomeExecutor delhomeExecutor = new DelhomeExecutor();
        getCommand("sethome").setExecutor(sethomeExecutor);
        getCommand("sethome").setTabCompleter(sethomeExecutor);
        getCommand("home").setExecutor(homeExecutor);
        getCommand("home").setTabCompleter(homeExecutor);
        getCommand("delhome").setExecutor(delhomeExecutor);
        getCommand("delhome").setTabCompleter(delhomeExecutor);

    }

    private void setupDatabase() throws SQLException {
        String datbaseName = getConfig().getString("databaseName");
        if(!new File(getDataFolder(), datbaseName+".db").exists())
            createNewDatabase(getDataFolder().getPath()+"/"+datbaseName+".db");
        else {
            getLogger().info("Using existing database");
        }
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:"+getDataFolder().getPath()+"/"+datbaseName+".db");

        dataRepository = new DataRepository(connectionSource);
    }

    public static void createNewDatabase(String fileName) {
        instance.getLogger().info(fileName);
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:"+fileName)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                instance.getLogger().info("The driver name is " + meta.getDriverName());
                instance.getLogger().info("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    @Override
    public void onDisable() {
    }

}
