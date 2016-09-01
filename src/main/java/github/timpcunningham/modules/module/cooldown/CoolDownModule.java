package github.timpcunningham.modules.module.cooldown;

import github.timpcunningham.modules.AbstractRepeatingTaskModule;
import github.timpcunningham.modules.LoadPriority;
import github.timpcunningham.modules.util.RepeatingTask;
import github.timpcunningham.modules.util.triplet.Triplet;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.PacketPlayOutSetCooldown;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RepeatingTask(period = 5L)
public class CoolDownModule extends AbstractRepeatingTaskModule {
    private Triplet<UUID, Material, Date> cooldowns;

    public CoolDownModule() {
        setLoadPriority(LoadPriority.NORMAL);
        cooldowns = new Triplet<>();
    }

    @Override
    public void run() {
        Map<UUID, List<Material>> items = cooldowns.uniqueKeySet();
        Date now = new Date();

        for(Map.Entry cooldown : items.entrySet()) {
            for(Material mat : (List<Material>) cooldown.getValue()) {
                Date expire = cooldowns.get((UUID)cooldown.getKey(), mat);

                if(expire.before(now)) {
                    cooldowns.remove((UUID)cooldown.getKey(), mat);
                }
            }
        }
    }

    public boolean isOnCooldown(Player player, Material mat) {
        return cooldowns.contains(player.getUniqueId(), mat);
    }

    public void applyCooldown(Player player, ItemStack itemStack, int duration) {
        PacketPlayOutSetCooldown packet = new PacketPlayOutSetCooldown(Item.getById(itemStack.getTypeId()), duration * 20);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        cooldowns.put(player.getUniqueId(), itemStack.getType(), DateUtils.addSeconds(new Date(), duration));
    }
}
