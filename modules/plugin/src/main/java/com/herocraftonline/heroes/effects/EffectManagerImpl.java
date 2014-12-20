package com.herocraftonline.heroes.effects;

import com.google.common.base.Optional;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.service.scheduler.RepeatingTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EffectManagerImpl implements EffectManager {

    private static final long EFFECT_TICK_TIMER = 2; //TODO: config?

    private final HeroesPlugin plugin;
    private final Set<EffectTickable> registeredEffects;
    private final Set<EffectTickable> effectRemoveQueue;
    private final Set<EffectTickable> effectAddQueue;
    private UUID task;

    public EffectManagerImpl(HeroesPlugin plugin) {
        this.plugin = plugin;
        registeredEffects = new HashSet<EffectTickable>();
        effectRemoveQueue = new HashSet<EffectTickable>();
        effectAddQueue = new HashSet<EffectTickable>();
        Optional<RepeatingTask> t = plugin.getGame().getScheduler().runRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                tick();
            }
        }, EFFECT_TICK_TIMER);
        if (t.isPresent()) {
            task = t.get().getUniqueId();
        }
    }

    @Override
    public void registerEffect(EffectTickable effect) {
        effectAddQueue.add(effect);
    }

    @Override
    public void removeEffect(EffectTickable effect) {
        effectRemoveQueue.add(effect);
    }

    private void tick() {
        for (EffectTickable remove : new HashSet<EffectTickable>(effectRemoveQueue)) {
            registeredEffects.remove(remove);
        }
        effectRemoveQueue.clear();
        for (EffectTickable add : new HashSet<EffectTickable>(effectAddQueue)) {
            registeredEffects.add(add);
        }
        effectAddQueue.clear();
        long tickTime = System.currentTimeMillis();
        for (EffectTickable effect : registeredEffects) {
            if (effect.shouldStopTicking(tickTime)) {
                effectRemoveQueue.add(effect);
            } else if (effect.canTick(tickTime)) {
                effect.tick(effect.getCharacter());
                if (effect.shouldStopTicking(tickTime)) {
                    effectRemoveQueue.add(effect);
                }
            }
        }

    }
}
