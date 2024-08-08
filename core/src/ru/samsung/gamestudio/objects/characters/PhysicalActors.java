package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ru.samsung.gamestudio.objects.Hittable;

import static ru.samsung.gamestudio.game.GameSettings.*;

public abstract class PhysicalActors extends Image implements Hittable {

    protected Body body;
    protected Fixture fixture;

    public PhysicalActors(World world, Rectangle bounds, short categoryBits) {
        createBody(world, bounds, categoryBits);
    }

    private void createBody(World world, Rectangle bounds, short categoryBits) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(bodyDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(bounds.getHeight() / SCALE / 2f);

        fixtureDef.shape = circleShape;
        fixtureDef.filter.categoryBits = categoryBits;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        body.setLinearDamping(2);
        body.setTransform(
                (bounds.getX() + bounds.getWidth() / 2) / SCALE,
                (bounds.getY() + bounds.getHeight() / 2) / SCALE,
                0
        );
        circleShape.dispose();
    }

    @Override
    public void release(short hitObjectBits) {

    }
}
