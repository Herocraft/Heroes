package com.herocraftonline.heroes.skills;

import java.util.UUID;

/**
 * <p>Skill results used to denote results of skill execution in
 * {@link Skill#execute(com.herocraftonline.heroes.characters.CharacterBase, String[])}.</p>
 *
 * <p>The provided static constants are recommended to be used as the default implementation will have built in support
 * for all of them, but should differing skill results be wanted, simply use {@link SkillResult#valueOf(String, String)}
 * </p>
 */
public final class SkillResult {
    /**
     * Normal operation of skill, skill completed successfully
     */
    public static final SkillResult NORMAL = valueOf("normal", null);

    /**
     * Skill could not be executed due to being on cooldown
     */
    public static final SkillResult COOLDOWN = valueOf("cooldown", null);

    /**
     * Skill could not be executed because mana costs were not met
     */
    public static final SkillResult MANA = valueOf("mana", null);

    /**
     * Skill could not be executed because health costs were not met
     */
    public static final SkillResult HEALTH = valueOf("health", null);

    /**
     * Skill could not be executed because stamina costs were not met
     */
    public static final SkillResult STAMINA = valueOf("stamina", null);

    /**
     * Skill could not finish executing because there was no valid target for it
     */
    public static final SkillResult INVALID_TARGET = valueOf("no-target", null);

    /**
     * Delayed skill has started execution successfully and is entering its delay period
     */
    public static final SkillResult STARTED_DELAYED = valueOf("delayed", null);

    /**
     * An interactive skill has been cancelled and was not executed
     */
    public static final SkillResult CANCELLED = valueOf("cancelled", null);

    /**
     * Skill completed normally, but post-skill execution operations such as cost deduction and placing the skill on
     * cooldown should not occur
     */
    public static final SkillResult SKIP_POST = valueOf("no-post", null);

    /**
     * Skill could not complete execution due to no-combat restrictions
     */
    public static final SkillResult COMBAT = valueOf("no-combat", null);

    /**
     * @param message The message to send, use {@link #SILENT_FAIL} if no message should be sent
     * @return Skill failed to execute because of [the parameter message], which will be relayed to the character
     *         attempting to execute the skill as appropriate
     */
    public static final SkillResult FAIL(String message) {
        return valueOf("fail-generic", message);
    }

    /**
     * Skill failed to execute, do not send any messages
     */
    public static final SkillResult SILENT_FAIL = valueOf("fail-silent", null);

    /**
     * Constructs a new SkillResult with the given name.
     * @param name The name to identify with the skill result
     * @param data additional data to attach to the skill result where appropriate in string format
     * @return The SkillResult instance
     */
    public static final SkillResult valueOf(String name, String data) {
        return new SkillResult(name, data);
    }

    private final String name;
    private final String data;
    private final UUID uid; // Used because long comparisons are faster

    private SkillResult(String name, String data) {
        this.name = name;
        this.data = data;
        this.uid = UUID.nameUUIDFromBytes(name.getBytes());
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SkillResult) {
            return ((SkillResult)obj).uid.equals(this.uid);
        } else if (obj instanceof String) {
            return obj.equals(name);
        } else {
            return false;
        }
    }
}
