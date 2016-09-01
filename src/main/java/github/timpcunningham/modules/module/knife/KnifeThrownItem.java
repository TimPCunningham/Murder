package github.timpcunningham.modules.module.knife;

import github.timpcunningham.Murder;
import github.timpcunningham.modules.module.thrownItem.AbstractThrownItem;
import github.timpcunningham.modules.util.DeadPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

class KnifeThrownItem extends AbstractThrownItem {
    @Override
    public void displayTrail() {
        getItem().getWorld().spigot().playEffect(
                getItem().getLocation(),
                Effect.COLOURED_DUST,
                0, 0, 1, 0, 0, 0, 0, 50);
    }

    @Override
    public void onHit(List<Entity> hit) {
        //TODO - death module stuff FIX
        for(Entity entity : hit) {
            if(entity instanceof Player) {

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        //DeadPlayer.display((Player) entity);
                        Bukkit.broadcastMessage("HIT");
                    }
                }.runTask(Murder.getInstance());
            }
        }
    }
}
