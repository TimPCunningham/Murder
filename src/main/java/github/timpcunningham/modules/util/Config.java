package github.timpcunningham.modules.util;

import github.timpcunningham.Murder;
import org.bukkit.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static Config self;

    // Config Values
    private boolean autoStart;
    private int players;
    private double killerChance;
    private double vigilanteChance;
    private MapObject lobbyWorld;
    private List<MapObject> mapWorlds;

    private Config() {
        mapWorlds = new ArrayList<>();
    }

    public static Config getInstance() {
        if(self == null) {
            self = new Config();
        }
        return self;
    }

    public void reloadConfig() throws IllegalArgumentException {
        Configuration config = Murder.getInstance().getConfig();

        autoStart = config.getBoolean("autostart", false);
        players = config.getInt("players", 8);
        killerChance = config.getDouble("killer-percentage", .25);
        vigilanteChance = config.getDouble("vigilante-percentage", .25);

        String path = config.getString("lobby.path", "lobby");
        String spawn = config.getString("lobby.spawn", "0,64,0");

        lobbyWorld = new MapObject("Lobby", path);
        lobbyWorld.addSpawn(spawn);

        for(String key : config.getConfigurationSection("maps").getKeys(false)) {
            MapObject map;
            String mapPath = config.getString("maps." + key + ".path");

            map = new MapObject(key, mapPath);
            config.getStringList("maps." + key + ".spawns").forEach(map::addSpawn);
            mapWorlds.add(map);
        }
    }

    public boolean doesAutoStart() {
        return autoStart;
    }

    public int playersToStart() {
        return players;
    }

    public double getKillerChance() {
        return killerChance;
    }

    public double getVigilanteChance() {
        return vigilanteChance;
    }

    public MapObject getLobbyWorld() {
        return lobbyWorld;
    }

    public List<MapObject> getMapWorlds() {
        return mapWorlds;
    }
}
