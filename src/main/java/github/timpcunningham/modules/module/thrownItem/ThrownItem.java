package github.timpcunningham.modules.module.thrownItem;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface ThrownItem {
    void displayTrail();
    void onHit(List<Entity> hit);
    void shoot(Player player);
    void onLand();
}
