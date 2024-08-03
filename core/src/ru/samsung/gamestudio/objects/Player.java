package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;


import static ru.samsung.gamestudio.GameSettings.PPI;
import static ru.samsung.gamestudio.GameSettings.SCALE;

public class Player extends Image implements Updatable {

    public enum State {FALLING, JUMPING, IDLE, RUNNING, ATTACKING}

    public Body body;
    Fixture fixture;

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> attack;

    float timer;
    State state;
    boolean needToBeSwapped;

    public Player(World world, Rectangle bounds) {

        createBody(world, bounds);
        createAnimations();
        timer = 0;
        state = State.IDLE;
        setSize(bounds.getWidth() * 2 * PPI, bounds.getHeight() * PPI);

    }

    private void createBody(World world, Rectangle bounds) {
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (bounds.getX() + bounds.getWidth() / 2) / SCALE,
                (bounds.getY() + bounds.getHeight() / 2) / SCALE
        );

        body = world.createBody(bodyDef);

        pShape.setAsBox((bounds.getWidth() / 2) / SCALE, (bounds.getHeight() / 2) / SCALE);
        fixtureDef.shape = pShape;
        fixture = body.createFixture(fixtureDef);

        body.setLinearDamping(5);

        pShape.dispose();
    }

    private void createAnimations() {

        Texture texture = new Texture("texture/player/hero-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 64 * 3, i * 40, 64, 40));
        idle = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 0, i * 40, 64, 40));
        run = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 0, 40, 64, 40));
        jump = new Animation<>(0.5f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 3; i++) frames.add(new TextureRegion(texture, 64 * 2, i * 40, 64, 40));
        attack = new Animation<>(0.3f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private Drawable getFrame(float delta) {

        TextureRegion region;

        switch (state) {
            case RUNNING: {
                region = run.getKeyFrame(timer, true);
                break;
            }
            case JUMPING: {
                region = jump.getKeyFrame(timer, false);
                break;
            }
            case ATTACKING: {
                region = attack.getKeyFrame(timer, false);
                break;
            }
            default:
                region = idle.getKeyFrame(timer, true);
        }

        if (needToBeSwapped != region.isFlipX())
            region.flip(true, false);

        timer += delta;
        return (new Image(region)).getDrawable();

    }

    @Override
    public void update(float delta) {
        setPosition((body.getPosition().x) * SCALE * PPI - getWidth() / 2, (body.getPosition().y) * SCALE * PPI - getHeight() / 1.5f);
        setDrawable(getFrame(delta));
        if (body.getLinearVelocity().y == 0 && body.getLinearVelocity().y == 0) state = State.IDLE;
    }

    public void moveLeft() {
        body.applyForceToCenter(new Vector2(-20f, 0), true);
        needToBeSwapped = true;
        state = State.RUNNING;
    }

    public void moveRight() {
        body.applyForceToCenter(new Vector2(20f, 0), true);
        needToBeSwapped = false;
        state = State.RUNNING;
    }

    public void moveUp() {
        body.applyForceToCenter(new Vector2(0, 300f), true);
        state = State.JUMPING;
        timer = 0;
    }

    public void attack() {
        state = State.ATTACKING;
        timer = 0;
    }


}
