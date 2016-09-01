package github.timpcunningham.modules.util;

import org.bukkit.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapObject {
    private String path;
    private String name;
    private List<Vector> spawns;
    private Random random;
    private World world;

    public MapObject(String name, String path) {
        spawns = new ArrayList<>();
        random = new Random();
        this.name = name;
        this.path = path;
    }

    public void addSpawn(String spawn) throws IllegalArgumentException {
        String[] parts = spawn.split(",");

        if(parts.length != 3) {
            throw new IllegalArgumentException("Vector format for spawn is malformed!");
        }

        spawns.add(new Vector(
                Double.parseDouble(parts[0]),
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2])
        ));
    }

    public String getName() {
        return name;
    }

    public Location getSpawnPoint() {
        Vector vector = spawns.get(random.nextInt(spawns.size()));

        return new Location(world, vector.getX(), vector.getY(), vector.getZ(), 0, 0);
    }

    public void load() {
        if(Bukkit.getWorld(path) == null) { //World is not loaded
            world = new WorldCreator(path)
                    .generator(new NullGenerator())
                    .type(WorldType.FLAT)
                    .generateStructures(false)
                    .createWorld();
        } else {
            world = Bukkit.getWorld(path);
        }
    }

    public void unload() {
        Bukkit.unloadWorld(world, false);
        world = null;
    }

    public boolean isLoaded() {
        return world != null;
    }
}
