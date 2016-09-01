package github.timpcunningham.modules.util;

import com.comphenix.packetwrapper.WrapperPlayServerBed;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.BlockPosition;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class DeadPlayer {

    public static void display(Player player) {
        WrapperPlayServerBed bedPacket = new WrapperPlayServerBed();
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();


        EntityPlayer deadPlayer = new EntityPlayer(
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) player.getWorld()).getHandle(),
                ((CraftPlayer) player).getProfile(),
                new PlayerInteractManager(((CraftWorld) player.getWorld()).getHandle())
        );

        ((CraftWorld) player.getWorld()).getHandle().addEntity(deadPlayer);

        bedPacket.setEntityID(deadPlayer.getId());
        bedPacket.setLocation(new BlockPosition(player.getLocation().toVector()));

        for(Player viewer : manager.getEntityTrackers(deadPlayer.getBukkitEntity())) {
            try {
                manager.sendServerPacket(viewer, bedPacket.getHandle());
            } catch (InvocationTargetException e) {
                Bukkit.broadcastMessage("Something went wrong?!");
            }
        }
    }
}
