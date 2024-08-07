package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;

public class StaticBlock extends BaseBlock implements Hittable {

    public StaticBlock(World world, Rectangle bounds) {
        super(world, bounds, GameSettings.FLOOR_BIT);
    }

    public StaticBlock(World world, Rectangle bounds, short categoryBits) {
        super(world, bounds, categoryBits);
    }

    @Override
    public void hit(short hitObjectBits) {

    }

    @Override
    public void release(short releaseBits) {

    }
}
