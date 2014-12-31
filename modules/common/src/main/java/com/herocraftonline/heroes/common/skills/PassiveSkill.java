package com.herocraftonline.heroes.common.skills;

import com.herocraftonline.heroes.api.characters.CharacterBase;
import com.herocraftonline.heroes.api.characters.Hero;
import com.herocraftonline.heroes.api.plugin.HeroesPlugin;
import com.herocraftonline.heroes.api.skills.Skill;
import com.herocraftonline.heroes.api.skills.SkillResult;
import com.herocraftonline.heroes.common.effects.BasicEffect;
import org.spongepowered.api.text.chat.ChatTypes;

public abstract class PassiveSkill implements Skill {

    protected HeroesPlugin plugin;

    @Override
    public void onInit(HeroesPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onAttach(CharacterBase character) {
        character.addEffect(new BasicEffect(plugin, getName()));
    }

    @Override
    public void onRemove(CharacterBase character) {
        character.removeEffect(getName());
    }

    @Override
    public final String[] getIdentifiers() {
        return new String[] {getName()};
    }

    @Override
    public SkillResult execute(CharacterBase character, String[] args) {
        if (character instanceof Hero) {
            ((Hero)character).getPlayer().sendMessage(ChatTypes.CHAT, plugin.getConfigManager()
                    .getMessaging("passive-skill-no-use", "$1 is a passive skill and is active without " +
                            "being run").replace("$1", getName()));
            //TODO: configurable messaging (if even feasible in common)
        }
        return SkillResult.NORMAL;
    }
}
