package github.timpcunningham.modules.module.thrownItem;

import com.sun.org.apache.xpath.internal.operations.Mod;
import github.timpcunningham.Murder;
import github.timpcunningham.modules.ModuleLoader;
import github.timpcunningham.modules.module.droppedItem.DroppedItemModule;
import github.timpcunningham.modules.module.droppedItem.Pickup;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public abstract class AbstractThrownItem implements ThrownItem {
    private Item item;
    private ItemStack itemStack;
    private Player shooter;

    public AbstractThrownItem() {

    }

    public AbstractThrownItem setItem(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public void shoot(Player player) {
        item = player.getWorld().dropItem(player.getLocation().clone().add(0,1,0), itemStack);
        item.setPickupDelay(Integer.MAX_VALUE);
        item.setVelocity(player.getLocation().getDirection().multiply(2));
        player.getInventory().setItemInMainHand(null);
        this.shooter = player;
    }

    public void onLand() {
        ((ThrownItemModule) ModuleLoader.getInstance().getModule(ThrownItemModule.class)).removeThrownItem(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                ((DroppedItemModule) ModuleLoader.getInstance().getModule(DroppedItemModule.class)).addItem(Pickup.ALL, item);
                item.remove();
            }
        }.runTask(Murder.getInstance());
    }

    @Override
    public void displayTrail() {

    }

    @Override
    public void onHit(List<Entity> hit) {

    }

    public Item getItem() {
        return item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
