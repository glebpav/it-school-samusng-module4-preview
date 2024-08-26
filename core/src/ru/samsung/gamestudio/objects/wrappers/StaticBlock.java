package ru.samsung.gamestudio.objects.wrappers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.objects.PhysicalObject;

public class StaticBlock implements Hittable {

    private final PhysicalObject physicalObject;

    public StaticBlock(World world, Rectangle bounds) {
        physicalObject = new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                .addRectangularFixture(bounds.width, bounds.height, GameSettings.FLOOR_BIT)
                .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                .build(this);
    }

    public PhysicalObject getPhysicalObject() {
        return physicalObject;
    }

    @Override
    public void hit(short hitObjectBits) {

    }

    @Override
    public void release(short releaseBits) {

    }
}
