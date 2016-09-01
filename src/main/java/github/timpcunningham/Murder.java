package github.timpcunningham;

import github.timpcunningham.modules.AbstractModule;
import github.timpcunningham.modules.Module;
import github.timpcunningham.modules.ModuleLoader;
import github.timpcunningham.modules.util.Config;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Murder extends JavaPlugin{
    private static Murder self;

    public static Murder getInstance() {
        return self;
    }

    @Override
    public void onEnable() {
        self = this;

        getConfig().options().copyDefaults(true);
        saveConfig();
        Config.getInstance().reloadConfig();

        try {
            ModuleLoader.getInstance().buildModules();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerEvents(Module module) {
        Bukkit.getServer().getPluginManager().registerEvents(module, this);
    }
}
