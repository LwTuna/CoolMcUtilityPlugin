package com.wipdev.dadbodissue.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter @DatabaseTable(tableName = "player_data")
public class PlayerData {

    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField
    private String name;

    @DatabaseField
    private String nameColor;

    @DatabaseField
    private String chatColor;

    @DatabaseField
    private boolean admin;

    public PlayerData(UUID uuid, String name, String nameColor, String chatColor, boolean admin) {
        this.uuid = uuid;
        this.name = name;
        this.nameColor = nameColor;
        this.chatColor = chatColor;
        this.admin = admin;
    }

    public PlayerData() {
    }
}
