package ru.mrbedrockpy.arcadelobby.util;

import ru.mrbedrockpy.arcadelobby.ArcadeLobby;
import ru.mrbedrockpy.bedrocklib.manager.ManagerItem;
import ru.mrbedrockpy.bedrocklib.manager.SetManager;

import java.util.Collection;

public abstract class System<I extends ManagerItem<ID>, ID> extends SetManager<ArcadeLobby, I, ID> {

    public System(ArcadeLobby plugin) {
        super(plugin);
    }

    @Override
    public boolean register(I item) {
        if (getById(item.getId()) != null) return false;
        super.register(item);
        return true;
    }

    @Override
    public boolean registerAll(Collection<I> items) {
        items.forEach(this::register);
        return true;
    }
}
