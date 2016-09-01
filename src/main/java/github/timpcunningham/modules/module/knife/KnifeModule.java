package github.timpcunningham.modules.module.knife;

import github.timpcunningham.modules.AbstractModule;
import github.timpcunningham.modules.LoadPriority;

import github.timpcunningham.modules.ModuleLoader;
import github.timpcunningham.modules.module.thrownItem.ThrownItemModule;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class KnifeModule extends AbstractModule {

    public KnifeModule() {
        setLoadPriority(LoadPriority.NORMAL);
    }

    @EventHandler
    public void onKnifeThrow(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if(item != null && item.getType().equals(Material.IRON_SWORD)) {
            if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                ((ThrownItemModule) ModuleLoader.getInstance().getModule(ThrownItemModule.class)).addThrownItem(
                        new KnifeThrownItem().setItem(new ItemStack(Material.IRON_SWORD)),
                        player
                );
            }
        }
    }
}
