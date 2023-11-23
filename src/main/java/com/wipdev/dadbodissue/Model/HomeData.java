package com.wipdev.dadbodissue.Model;

import com.j256.ormlite.field.DatabaseField;
import com.wipdev.dadbodissue.DadBodIssue;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;
@Getter
@Setter
public class HomeData {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private UUID worldUUID;
    @DatabaseField
    private double x;
    @DatabaseField
    private double y;
    @DatabaseField
    private double z;
    @DatabaseField
    private float yaw;
    @DatabaseField
    private float pitch;

    @DatabaseField(columnName = "owner_uuid")
    private UUID playerUUID;

    @DatabaseField(columnName = "is_public")
    private boolean isPublic;

    public Location getLocation() {
        return new Location(
                DadBodIssue.getInstance().getServer().getWorld(worldUUID),
                x,y,z,yaw,pitch
        );
    }

    public HomeData() {
    }

    public HomeData(int id, String name, UUID worldUUID, double x, double y, double z, float yaw, float pitch, UUID playerUUID, boolean isPublic) {
        this.id = id;
        this.name = name;
        this.worldUUID = worldUUID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.playerUUID = playerUUID;
        this.isPublic = isPublic;
    }
}
