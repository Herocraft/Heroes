package com.herocraftonline.heroes.common.effects;

import com.google.common.base.Optional;
import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectPeriodic;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.service.scheduler.RepeatingTask;
import org.spongepowered.api.service.scheduler.Task;

import java.util.UUID;

public abstract class PeriodicEffect extends BasicEffect implements EffectPeriodic {

    protected long period;
    protected UUID tickTask;

    public PeriodicEffect(HeroesPlugin plugin, String name, long period) {
        super(plugin, name);
        this.period = period;
    }

    @Override
    public void apply(final CharacterBase character) { // Use a simple repeating tickTask for performance
        Optional<RepeatingTask> taskOptional = plugin.getGame().getScheduler().runRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {
                tick(character);
            }
        }, period / 1000 * 20);
        if (taskOptional.isPresent()) {
            tickTask = taskOptional.get().getUniqueId();
        }
    }

    @Override
    public void remove(final CharacterBase character) {
        Optional<Task> taskOptional = plugin.getGame().getScheduler().getTaskById(tickTask);
        if (taskOptional.isPresent()) {
            taskOptional.get().cancel();
        }
    }

    @Override
    public long getPeriod() {
        return period;
    }

    @Override
    public long timeTillNextTick() {
        return period;
    }
}
