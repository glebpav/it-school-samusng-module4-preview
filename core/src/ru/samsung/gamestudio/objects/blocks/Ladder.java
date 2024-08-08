package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

import static ru.samsung.gamestudio.game.GameSettings.LADDER_BIT;

public class Ladder extends StaticBlock {

    public Ladder(World world, Rectangle bounds) {
        super(world, bounds, LADDER_BIT);
        body.setType(BodyDef.BodyType.StaticBody);
        fixture.setSensor(true);
    }

}
