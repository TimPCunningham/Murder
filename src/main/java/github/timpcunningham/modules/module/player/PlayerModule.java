package github.timpcunningham.modules.module.player;

import github.timpcunningham.modules.AbstractModule;
import github.timpcunningham.modules.LoadPriority;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerModule extends AbstractModule {
    private List<UUID> particpants;
    private List<UUID> killers;
    private List<UUID> innocents;

    public PlayerModule() {
        setLoadPriority(LoadPriority.NORMAL);
        killers = new ArrayList<>();
        innocents = new ArrayList<>();
        particpants = new ArrayList<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        particpants.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        particpants.remove(event.getPlayer().getUniqueId());
    }

    public void resetPlayers() {
        killers = new ArrayList<>();
        innocents = new ArrayList<>();

        for(UUID uuid : particpants) {
            Player player = Bukkit.getPlayer(uuid);

            player.setHealth(20);
            player.setSaturation(20);
            player.setFireTicks(0);
            player.setFallDistance(0);
            player.setGameMode(GameMode.ADVENTURE);

            player.getInventory().clear();
            //TODO - Remove Fake name and skin
            //TODO - Teleport to the lobby
        }
    }

    public PlayerRole getRole(UUID uuid) {
        return  killers.contains(uuid) ?
                PlayerRole.KILLER : innocents.contains(uuid) ?
                PlayerRole.INNOCENT : PlayerRole.OTHER;
    }
}
