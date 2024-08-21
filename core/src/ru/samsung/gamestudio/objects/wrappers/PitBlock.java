package ru.samsung.gamestudio.objects.wrappers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.game.GameResources;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.objects.characters.PhysicalObject;
import ru.samsung.gamestudio.world.listeners.OnLoseListener;

import static ru.samsung.gamestudio.game.GameSettings.PIT_BIT;
import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class PitBlock implements Hittable {

    private final OnLoseListener onLoseListener;
    private final PhysicalObject physicalObject;

    public PitBlock(World world, Rectangle bounds, OnLoseListener onLoseListener) {
        this.onLoseListener = onLoseListener;

        physicalObject = new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                .addRectangularFixture(bounds.width, bounds.height, PIT_BIT)
                .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                .setBodyAsSensor()
                .build(this);

    }

    public PhysicalObject getPhysicalObject() {
        return physicalObject;
    }

    @Override
    public void hit(short hitObjectBits) {
        onLoseListener.onLose(localizationBundle.get("loseTextPitCase"));
    }

    @Override
    public void release(short hitObjectBits) {
    }
}
