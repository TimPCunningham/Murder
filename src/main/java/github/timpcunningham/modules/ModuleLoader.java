package github.timpcunningham.modules;

import github.timpcunningham.Murder;
import github.timpcunningham.modules.module.cooldown.CoolDownModule;
import github.timpcunningham.modules.module.droppedItem.DroppedItemModule;
import github.timpcunningham.modules.module.gun.GunModule;
import github.timpcunningham.modules.module.knife.KnifeModule;
import github.timpcunningham.modules.module.player.PlayerModule;
import github.timpcunningham.modules.module.thrownItem.ThrownItemModule;
import github.timpcunningham.modules.util.RepeatingTask;

import java.util.HashMap;
import java.util.Map;

public class ModuleLoader {
    private static ModuleLoader self;
    private Map<Class, Module> modules;
    private Class[] builder = {
            CoolDownModule.class,
            DroppedItemModule.class,
            GunModule.class,
            KnifeModule.class,
            PlayerModule.class,
            ThrownItemModule.class
    };

    private ModuleLoader() {
        modules = new HashMap<>();
    }

    public static ModuleLoader getInstance() {
        if(self == null) {
            self = new ModuleLoader();
        }
        return self;
    }

    public void buildModules() throws Exception {
        for(LoadPriority priority : LoadPriority.values()) {
            for (Class clazz : builder) {
                try {
                    Module module = (Module) clazz.newInstance();
                    if(module.getLoadPriority().equals(priority)) {
                        modules.put(clazz, module);
                        Murder.getInstance().registerEvents(module);

                        if(module instanceof AbstractRepeatingTaskModule) {
                            if(!clazz.isAnnotationPresent(RepeatingTask.class)) {
                                throw new Exception(clazz.getName() + ": must have \"@RepeatingTask\" annotation");
                            }

                            RepeatingTask task = (RepeatingTask) clazz.getAnnotation(RepeatingTask.class);
                            if(task.async()) {
                                ((AbstractRepeatingTaskModule) module).runTaskTimerAsynchronously(Murder.getInstance(), task.delay(), task.period());
                            } else {
                                ((AbstractRepeatingTaskModule) module).runTaskTimer(Murder.getInstance(), task.delay(), task.period());
                            }
                        }
                        Murder.getInstance().getLogger().info(clazz.getSimpleName() + " loaded!");
                    }
                } catch (IllegalAccessException | InstantiationException e) {
                    throw new Exception("There was an error loading modules!");
                }
            }
        }
    }

    public Module getModule(Class clazz) {
        return modules.get(clazz);
    }
}
