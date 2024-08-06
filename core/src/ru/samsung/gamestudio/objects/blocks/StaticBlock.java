package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.game.GameSettings;

public class StaticBlock extends BaseBlock {

    public StaticBlock(World world, Rectangle bounds) {
        super(world, bounds, GameSettings.FLOOR_BIT);
    }

    public StaticBlock(World world, Rectangle bounds, short categoryBits) {
        super(world, bounds, categoryBits);
    }

}
