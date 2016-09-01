package github.timpcunningham.modules;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractRepeatingTaskModule extends BukkitRunnable implements Module {
    private LoadPriority priority;

    public LoadPriority getLoadPriority(){
        return this.priority;
    }

    public void setLoadPriority(LoadPriority priority) {
        this.priority = priority;
    }
}
