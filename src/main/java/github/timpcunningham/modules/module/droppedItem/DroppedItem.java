package github.timpcunningham.modules.module.droppedItem;

import github.timpcunningham.modules.ModuleLoader;
import github.timpcunningham.modules.module.player.PlayerModule;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import java.util.UUID;

public class DroppedItem {
    private Pickup canInteract;
    private ItemStack itemStack;
    private Location location;
    private ArmorStand droppedItem;

    public DroppedItem(Pickup canInteract) {
        this.canInteract = canInteract;
    }

    public DroppedItem setItem(ItemStack item) {
        this.itemStack = item;
        return this;
    }

    public DroppedItem setLocation(Location location) {
        this.location = location;
        return this;
    }

    public boolean canInteractWith(UUID uuid) {
        return ((PlayerModule) ModuleLoader.getInstance().getModule(PlayerModule.class))
                .getRole(uuid).name().equalsIgnoreCase(canInteract.name()) || canInteract.equals(Pickup.ALL);
    }

    public DroppedItem spawn(Item item) {
        droppedItem = (ArmorStand) location.getWorld().spawnEntity(location.subtract(0, .5, 0), EntityType.ARMOR_STAND);
        droppedItem.setBasePlate(false);
        droppedItem.setVisible(false);
        droppedItem.setItemInHand(itemStack);
        droppedItem.setRightArmPose(new EulerAngle(0, Math.toRadians(-90), 0));
        droppedItem.setSmall(true);
        droppedItem.setGravity(false);
        droppedItem.setCustomName(String.valueOf(UUID.randomUUID()));

        droppedItem.setCollidable(false);

        item.remove();
        return this;
    }

    public void remove() {
        if(droppedItem != null) {
            droppedItem.remove();
        }
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Location getLocation() {
        return location;
    }

    public ArmorStand getDroppedItem() {
        return droppedItem;
    }
}
