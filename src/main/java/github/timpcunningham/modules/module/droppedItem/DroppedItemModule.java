package github.timpcunningham.modules.module.droppedItem;


import github.timpcunningham.Murder;
import github.timpcunningham.modules.AbstractModule;
import github.timpcunningham.modules.AbstractRepeatingTaskModule;
import github.timpcunningham.modules.LoadPriority;
import github.timpcunningham.modules.util.RepeatingTask;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@RepeatingTask
public class DroppedItemModule extends AbstractRepeatingTaskModule {
    private List<DroppedItem> droppedItems;

    public DroppedItemModule() {
        setLoadPriority(LoadPriority.NORMAL);
        droppedItems = new ArrayList<>();
    }

    public  void addItem(Pickup canInteract, Item item) {
        DroppedItem droppedItem = new DroppedItem(canInteract).setItem(item.getItemStack()).setLocation(item.getLocation());

        droppedItem.spawn(item);
        droppedItems.add(droppedItem);
    }

    @Override
    public void run() {
        if(droppedItems.size() < 1) {
            return;
        }

        List<DroppedItem> items = new ArrayList<>(droppedItems);

        items.forEach(droppedItem -> {
            for(Entity entity : droppedItem.getDroppedItem().getNearbyEntities(0.5, 0.25, 0.5)) {
                if(entity instanceof Player) {
                    Player player = (Player) entity;

                    if(droppedItem.canInteractWith(player.getUniqueId())) {
                        ItemStack itemStack = droppedItem.getItemStack();

                        if(!player.getInventory().contains(itemStack.getType())) {
                            new BukkitRunnable() {

                                @Override
                                public void run() {
                                    player.getInventory().addItem(itemStack);
                                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                                    droppedItems.remove(droppedItem);
                                    droppedItem.remove();
                                }
                            }.runTask(Murder.getInstance());
                            break;
                        }
                    }
                }
            }
        });
    }
}
