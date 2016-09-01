package github.timpcunningham.modules;

public abstract class AbstractModule implements Module {
    private LoadPriority priority;

    public LoadPriority getLoadPriority(){
        return this.priority;
    }

    public void setLoadPriority(LoadPriority priority) {
        this.priority = priority;
    }

}
