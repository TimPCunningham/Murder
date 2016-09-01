package github.timpcunningham.modules.module.gun;

import github.timpcunningham.Murder;
import github.timpcunningham.modules.AbstractModule;
import github.timpcunningham.modules.LoadPriority;
import github.timpcunningham.modules.ModuleLoader;
import github.timpcunningham.modules.module.cooldown.CoolDownModule;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class GunModule extends AbstractModule {

    public GunModule() {
        setLoadPriority(LoadPriority.NORMAL);
    }

    @EventHandler
    public void onShoot(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        CoolDownModule cooldown = (CoolDownModule) ModuleLoader.getInstance().getModule(CoolDownModule.class);
        Action action = event.getAction();

        if(itemStack == null || !itemStack.getType().equals(Material.BOW)) {
            return;
        }
        if(!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if(cooldown.isOnCooldown(player, itemStack.getType())) {
            return;
        }

        Location shootPos = player.getEyeLocation().add(player.getLocation().getDirection());


        //Do it myselft with protocol lib?!

        event.setCancelled(true);
        Snowball snowball = (Snowball) player.getWorld().spawnEntity(shootPos, EntityType.SNOWBALL);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1.5f);

        snowball.setVelocity(player.getLocation().getDirection().multiply(3));
        snowball.setMetadata("shooter", new FixedMetadataValue(Murder.getInstance(), player.getUniqueId()));
        cooldown.applyCooldown(player, itemStack, 5);
    }

    @EventHandler
    public void onBlockHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof  Snowball) {
            event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.BLOCK_METAL_BREAK, 2, 1.5f);
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        Entity entityDamaged = event.getEntity();
        Entity entityDamager = event.getDamager();

        if(entityDamager instanceof Snowball && entityDamaged instanceof LivingEntity) {
            LivingEntity damaged = (LivingEntity) entityDamaged;

            if(entityDamager.hasMetadata("shooter")) {
                UUID player = UUID.fromString(entityDamager.getMetadata("shooter").get(0).asString());
                // Call death event
                event.setDamage(damaged.getHealth());
                event.setCancelled(true);
            }
        }
    }
}
