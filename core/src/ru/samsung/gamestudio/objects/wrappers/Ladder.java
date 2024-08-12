package ru.samsung.gamestudio.objects.wrappers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.objects.characters.PhysicalObject;

import static ru.samsung.gamestudio.game.GameSettings.LADDER_BIT;

public class Ladder {

    PhysicalObject physicalObject;

    public Ladder(World world, Rectangle bounds) {

        physicalObject = new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                .addRectangularFixture(bounds.width, bounds.height, LADDER_BIT)
                .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                .setBodyAsSensor()
                .build(this);

    }

}
