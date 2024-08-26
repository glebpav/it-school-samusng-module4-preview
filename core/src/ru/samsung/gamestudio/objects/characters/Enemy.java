package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.samsung.gamestudio.game.GameResources;
import ru.samsung.gamestudio.objects.PhysicalObject;
import ru.samsung.gamestudio.world.listeners.OnRemoveBodyListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Enemy extends PhysicalActor implements Disposable {

    private enum State {IDLE, RUNNING, DEAD}

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> deadAnimation;
    private TextureRegionDrawable drawable;

    private final OnRemoveBodyListener onRemoveBodyListener;

    private final int walkLength;
    private final float initialX;
    private final float tileScale;

    private float timer;
    private State state;
    private boolean needToBeSwapped;
    private boolean moveRightFlag;

    public Enemy(
            World world,
            Rectangle bounds,
            int walkLength,
            OnRemoveBodyListener onRemoveBodyListener,
            float tileScale) {
        this.onRemoveBodyListener = onRemoveBodyListener;
        this.walkLength = walkLength;
        this.tileScale = tileScale;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
                        .addCircularFixture(bounds.getHeight() / 2, ENEMY_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .build(this)
        );

        createAnimations();
        timer = 0;
        state = State.IDLE;
        moveRightFlag = true;
        setSize(bounds.getWidth() * tileScale, bounds.getHeight() * tileScale);

        initialX = bounds.getX();
        moveRight();

    }

    private void createAnimations() {
        Texture texture = new Texture(GameResources.ENEMY_TILESET_PATH);
        Array<TextureRegion> frames = new Array<>();
        drawable = new TextureRegionDrawable();
        setDrawable(drawable);

        for (int i = 0; i < 8; i++) frames.add(new TextureRegion(texture, i * 34, 0, 34, 30));

        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, i * 34, 30, 34, 30));
        runAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, i * 34, 60, 34, 30));
        deadAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private void setAppropriateDrawable(float delta) {
        TextureRegion region;

        switch (state) {
            case RUNNING: {
                region = runAnimation.getKeyFrame(timer, true);
                break;
            }
            case DEAD: {
                region = deadAnimation.getKeyFrame(timer, false);
                break;
            }
            default:
                region = idleAnimation.getKeyFrame(timer, true);
        }

        if (needToBeSwapped == region.isFlipX()) region.flip(true, false);

        timer += delta;
        drawable.setRegion(region);
    }

    private void moveLeft() {
        getPhysicalObject().getBody().applyForceToCenter(new Vector2(-5f, 0), true);
        needToBeSwapped = true;
        state = State.RUNNING;
    }

    private void moveRight() {
        getPhysicalObject().getBody().applyForceToCenter(new Vector2(5f, 0), true);
        needToBeSwapped = false;
        state = State.RUNNING;
    }

    @Override
    public void act(float delta) {
        setAppropriateDrawable(delta);
        if (state != State.DEAD) {

            setPosition((
                    getPhysicalObject().getBody().getPosition().x) / SCALE * tileScale - getWidth() / 2,
                    (getPhysicalObject().getBody().getPosition().y) / SCALE * tileScale - getHeight() / 1.5f
            );

            if (walkLength != 0) {
                if (moveRightFlag) moveRight();
                else moveLeft();

                if (getPhysicalObject().getBody().getPosition().x / SCALE - initialX > walkLength * 32) {
                    moveRightFlag = false;
                } else if (getPhysicalObject().getBody().getPosition().x / SCALE - initialX < 0) {
                    moveRightFlag = true;
                }
            }

        } else if (deadAnimation.isAnimationFinished(timer)) {
            remove();
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == PLAYER_BIT && state != State.DEAD) {
            state = State.DEAD;
            timer = 0;
            onRemoveBodyListener.onRemoveBody(getPhysicalObject().getBody());
        }
    }

    @Override
    public void dispose() {
        idleAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
