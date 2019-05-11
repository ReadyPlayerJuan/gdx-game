package com.mygdx.game.weapons.stats;

public enum WeaponStat {
    BULLET_DAMAGE                       ("Damage", WeaponStatType.NUMBER, true, 1, 1),
    BULLET_DAMAGE_FALLOFF_PCT           ("Damage Falloff", WeaponStatType.PERCENTAGE, false, -1, 0), //not between zero and one. perhaps snipers can have reverse damage falloff??
    BULLET_DAMAGE_FALLOFF_DISTANCE      ("Damage Falloff Distance", WeaponStatType.NUMBER, false, 1, 0),
    BULLET_SPEED                        ("Bullet Speed", WeaponStatType.NUMBER, false, 1, 0),
    BULLET_NUM                          ("Num Bullets", WeaponStatType.NUMBER, false, 1, 1),
    BULLET_ACCELERATION                 ("Bullet Accel", WeaponStatType.NUMBER, false, 1, 0),
    BULLET_SIZE                         ("Bullet Size", WeaponStatType.NUMBER, false, 1, 1),
    BULLET_BOUNCE                       ("Bounce", WeaponStatType.NUMBER, false, 1, 1),
    BULLET_LIFETIME                     ("Bullet Lifetime", WeaponStatType.TIME, false, 1, 1),
    BULLET_KNOCKBACK                    ("Knockback", WeaponStatType.NUMBER, false, 1, 0),

    WEAPON_SPREAD                       ("Spread", WeaponStatType.PERCENTAGE_BETWEEN_ZERO_AND_ONE, false, -1, 1),
    WEAPON_FIRE_RATE                    ("Fire Rate", WeaponStatType.NUMBER, false, 1, 1),
    WEAPON_KICK                         ("Kick", WeaponStatType.NUMBER, false, -1, 0),
    WEAPON_LIFESTEAL                    ("Lifesteal", WeaponStatType.PERCENTAGE, false, 1, 1),
    WEAPON_CLIP_SIZE                    ("Clip Size", WeaponStatType.NUMBER, false, 1, 1),

    PLAYER_SPEED                        ("Player Speed", WeaponStatType.PERCENTAGE, false, 1, 0),
    PLAYER_HEALTH                       ("Player Health", WeaponStatType.PERCENTAGE, true, 1, 0),
    PLAYER_REGEN                        ("Player Regen", WeaponStatType.PERCENTAGE, true, 1, 0),
    PLAYER_RESISTANCE                   ("Player Damage Resist", WeaponStatType.PERCENTAGE, false, 1, 0),

    EXPLOSION_DAMAGE                    ("Explosion Damage", WeaponStatType.NUMBER, true, 1, 1),
    EXPLOSION_RADIUS                    ("Explosion Radius", WeaponStatType.NUMBER, false, 1, 0),
    EXPLOSION_KNOCKBACK                 ("Explosion Knockback", WeaponStatType.NUMBER, false, 1, 0),

    FIRE_DAMAGE                         ("Fire Damage", WeaponStatType.NUMBER, true, 1, 1),
    FIRE_DURATION                       ("Fire Duration", WeaponStatType.TIME, false, 1, 1),
    FIRE_SPREAD_CHANCE                  ("Fire Spread", WeaponStatType.PERCENTAGE_BETWEEN_ZERO_AND_ONE, false, 1, 0),

    LIGHTNING_DAMAGE                    ("Lightning Damage", WeaponStatType.NUMBER, true, 1, 1),
    LIGHTNING_MAX_BOUNCES               ("Lightning Bounces", WeaponStatType.NUMBER, false, 1, 1),
    LIGHTNING_STUN_PCT                  ("Lightning Stun Chance", WeaponStatType.PERCENTAGE_BETWEEN_ZERO_AND_ONE, false, 1, 0),
    LIGHTNING_STUN_DURATION             ("Lightning Stun", WeaponStatType.TIME, false, 1, 1);

    private String name;
    private WeaponStatType statType;
    private boolean affectedByRarity;
    private int statBenefitSign, numDecimals;
    WeaponStat(String name, WeaponStatType statType, boolean affectedByRarity, int statBenefitSign, int numDecimals) {
        this.name = name;
        this.statType = statType;
        this.affectedByRarity = affectedByRarity;
        this.statBenefitSign = statBenefitSign;
        this.numDecimals = numDecimals;
    }
    public String getName() {
        return name;
    }
    public WeaponStatType getStatType() {
        return statType;
    }
    public boolean isAffectedByRarity() {
        return affectedByRarity;
    }
    public int getStatBenefitSign() {
        return statBenefitSign;
    }
    public int getNumDecimals() {
        return numDecimals;
    }
}
