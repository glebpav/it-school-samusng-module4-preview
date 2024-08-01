package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.MyGdxGame;

import static ru.samsung.gamestudio.GameSettings.SCALE;

public abstract class BaseBlock {

    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

    public BaseBlock (World world, Rectangle bounds) {
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / SCALE, (bounds.getY() + bounds.getHeight() / 2) / SCALE);
        body = world.createBody(bodyDef);

        pShape.setAsBox((bounds.getWidth() / 2) / SCALE,(bounds.getHeight() / 2) / SCALE);
        fixtureDef.shape = pShape;
        fixture = body.createFixture(fixtureDef);

        pShape.dispose();
    }

}
