package com.temporal;

import ashley.core.Component;

public class PlayerBullet extends Component
{
    public int damage;
    public PlayerBullet(int dmg)
    {
        damage = dmg;
    }
}
