package github.timpcunningham.modules;

import org.bukkit.event.Listener;

public interface Module extends Listener {
    void setLoadPriority(LoadPriority priority);
    LoadPriority getLoadPriority();
}
