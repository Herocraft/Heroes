package com.herocraftonline.heroes.common.effects;

import com.google.common.base.Optional;
import com.herocraftonline.heroes.characters.CharacterBase;
import com.herocraftonline.heroes.effects.EffectExpirable;
import com.herocraftonline.heroes.plugin.HeroesPlugin;
import org.spongepowered.api.service.scheduler.Task;

import java.util.UUID;
import java.util.logging.Level;

public class ExpirableEffect extends BasicEffect implements EffectExpirable {

    protected long applyTime;
    protected long originalExpireTime;
    protected long expireTime;
    private long length;
    private UUID cancelTask;

    public ExpirableEffect(HeroesPlugin plugin, String name, long duration) {
        super(plugin, name);
        length = duration;
    }

    @Override
    public void apply(final CharacterBase character) {
        applyTime = System.currentTimeMillis();
        expireTime = applyTime + length;
        originalExpireTime = expireTime;
        Optional<Task> taskOptional = plugin.getGame().getScheduler().runTaskAfter(plugin, new Runnable() {

            @Override
            public void run() {
                character.removeEffect(getName());
            }
        }, length / 1000 * 20);
        if (taskOptional.isPresent()) {
            cancelTask = taskOptional.get().getUniqueId();
        }
    }

    @Override
    public void remove(final CharacterBase character) {
        Optional<Task> t = plugin.getGame().getScheduler().getTaskById(cancelTask);
        if (t.isPresent()) {
            t.get().cancel();
        }
    }

    @Override
    public long getDuration() {
        return System.currentTimeMillis() - applyTime + getRemainingDuration();
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public long getOriginalDuration() {
        return originalExpireTime - applyTime;
    }

    @Override
    public long getOriginalExpireTime() {
        return originalExpireTime;
    }

    @Override
    public long getRemainingDuration() {
        return expireTime - System.currentTimeMillis();
    }

    @Override
    public long getStartTime() {
        return applyTime;
    }

    @Override
    public void setRemainingDuration(long duration) {
        expireTime = System.currentTimeMillis() + duration;
        long remaining = getRemainingDuration();
        Optional<Task> t = plugin.getGame().getScheduler().getTaskById(cancelTask);
        if (t.isPresent()) {
            Task run = t.get();
            Runnable r = run.getRunnable();
            run.cancel();
            if (remaining <= 0) {
                plugin.getGame().getScheduler().runTask(plugin, r);
            } else {
                plugin.getGame().getScheduler().runTaskAfter(plugin, r, remaining/1000*20);
            }
        } else {
            plugin.getLogger().log(Level.WARNING, "An attempt was made to change the duration of an already expired " +
                    "effect");
        }

    }
}
