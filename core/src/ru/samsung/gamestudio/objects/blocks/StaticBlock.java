package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class StaticBlock extends BaseBlock {

    public StaticBlock(World world, Rectangle bounds) {
        super(world, bounds);
    }

}
