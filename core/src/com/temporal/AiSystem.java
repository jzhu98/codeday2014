package com.temporal;

import ashley.core.Entity;
import ashley.core.Family;
import ashley.systems.IteratingSystem;

import com.badlogic.gdx.Gdx;

public class AiSystem extends IteratingSystem
{
    private Engine engine;
    private Entity player;

    public AiSystem(int priority, Engine engine, Entity player)
    {
        super(Family.getFamilyFor(Enemy.class, Direction.class, Velocity.class, Position.class), priority);
        this.engine = engine;
        this.player = player;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime)
    {
        Enemy enemy = entity.getComponent(Enemy.class);
        Direction enemyDir = entity.getComponent(Direction.class);
        Velocity enemyVel = entity.getComponent(Velocity.class);
        Position enemyPos = entity.getComponent(Position.class);

        Position playerPos = player.getComponent(Position.class);

        switch (enemy.type)
        {
            case Enemy.SHOOTER:
                enemyDir.x = playerPos.x - enemyPos.x;
                enemyDir.y = playerPos.y - enemyPos.y;
                // Normalize
                double length = Math.sqrt(enemyDir.x * enemyDir.x + enemyDir.y * enemyDir.y);
                enemyDir.x /= length;
                enemyDir.y /= length;

                // Shoot at player
                if (enemy.cooldown <= 0.0f)
                {
                    Position bulletPos = new Position(enemyPos.x, enemyPos.y);
                    Velocity bulletVel = new Velocity(enemyDir.x * 100, enemyDir.y * 100);
                    EnemyBullet damage = new EnemyBullet(1);
                    engine.addEnemyBullet(bulletPos, bulletVel, damage);
                    enemy.cooldown = 1.0f;
                }
                else
                {
                    enemy.cooldown -= deltaTime;
                }
                break;
            case Enemy.CHARGER:
                break;
            default:
                break;
        }
    }
}
