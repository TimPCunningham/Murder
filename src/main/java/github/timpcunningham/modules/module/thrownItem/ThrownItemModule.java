package github.timpcunningham.modules.module.thrownItem;

import github.timpcunningham.modules.AbstractRepeatingTaskModule;
import github.timpcunningham.modules.LoadPriority;
import github.timpcunningham.modules.util.RepeatingTask;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@RepeatingTask
public class ThrownItemModule extends AbstractRepeatingTaskModule {
    private List<AbstractThrownItem> thrownItems;

    public ThrownItemModule() {
        thrownItems = new ArrayList<>();
        setLoadPriority(LoadPriority.NORMAL);
    }

    public void addThrownItem(AbstractThrownItem item, Player shooter) {
        item.shoot(shooter);
        thrownItems.add(item);
    }

    public void removeThrownItem(AbstractThrownItem item) {
        thrownItems.remove(item);
    }

    @Override
    public void run() {
        List<AbstractThrownItem> items = new ArrayList<>(thrownItems);

        items.stream().forEach(item -> {
            if(item.getItem().isOnGround()) {
                item.onLand();
            }

            item.displayTrail();
            List<Entity> nearby = item.getItem().getNearbyEntities(0.5, 0.5, 0.5);

            if(nearby.size() > 0) {
                item.onHit(nearby);
            }
        });
    }
}
