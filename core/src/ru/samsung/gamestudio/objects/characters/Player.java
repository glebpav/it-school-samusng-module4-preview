package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.samsung.gamestudio.game.GameResources;
import ru.samsung.gamestudio.world.listeners.OnLoseListener;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnDamageListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Player extends PhysicalActor implements Disposable {

    private enum State {JUMPING, IDLE, RUNNING, ATTACKING, DEAD, GETTING_DAMAGE}

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> gettingDamageAnimation;
    private Animation<TextureRegion> becomeDeadAnimation;
    private TextureRegionDrawable drawable;

    private final float tileScale;

    private float timer;
    private State state;
    private int leftLives;
    private boolean needToBeSwapped;
    private boolean hasTouchedFloor;
    private boolean onLadder;

    private final OnLoseListener onLoseListener;
    private final OnDamageListener onDamageListener;
    private final OnScoreEarnedListener onScoreEarnedListener;

    public Player(
            World world,
            Rectangle bounds,
            OnDamageListener onDamageListener,
            OnScoreEarnedListener onScoreEarnedListener,
            OnLoseListener onLoseListener,
            float tileScale
    ) {
        this.onLoseListener = onLoseListener;
        this.onDamageListener = onDamageListener;
        this.onScoreEarnedListener = onScoreEarnedListener;
        this.tileScale = tileScale;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
                        .addCircularFixture(bounds.getHeight() / 2, PLAYER_BIT)
                        .addEdgeFixture(-bounds.getWidth() / 8, (bounds.getHeight() / 2), (bounds.getWidth() / 8), (bounds.getHeight() / 2), PLAYER_HEAD_BIT)
                        .addEdgeFixture(-bounds.getWidth() / 4, -(bounds.getHeight() / 2), (bounds.getWidth() / 4), -(bounds.getHeight() / 2), PLAYER_FEET_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .build(this)
        );

        createAnimations();
        timer = 0;
        state = State.IDLE;
        leftLives = PLAYER_LIVES;
        setSize(bounds.getWidth() * 2 * tileScale, bounds.getHeight() * tileScale);

        hasTouchedFloor = false;
        onLadder = false;
    }

    private void createAnimations() {
        Texture texture = new Texture(GameResources.PLAYER_TILESET_PATH);
        Array<TextureRegion> frames = new Array<>();
        drawable = new TextureRegionDrawable();
        setDrawable(drawable);

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 64 * i, 4 * 40, 64, 40));
        idleAnimation = new Animation(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 64 * i, 0, 64, 40));
        runAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 3; i++) frames.add(new TextureRegion(texture, 64 * i, 40, 64, 40));
        jumpAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 3; i++) frames.add(new TextureRegion(texture, 64 * i, 3 * 40, 64, 40));
        attackAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 64 * i, 2 * 40, 64, 40));
        gettingDamageAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 64 * i, 5 * 40, 64, 40));
        becomeDeadAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private void setAppropriateDrawable(float delta) {
        TextureRegion region;

        switch (state) {
            case RUNNING: {
                region = runAnimation.getKeyFrame(timer, true);
                break;
            }
            case JUMPING: {
                region = jumpAnimation.getKeyFrame(timer, false);
                break;
            }
            case ATTACKING: {
                region = attackAnimation.getKeyFrame(timer, false);
                break;
            }
            case GETTING_DAMAGE: {
                region = gettingDamageAnimation.getKeyFrame(timer, false);
                break;
            }
            case DEAD: {
                region = becomeDeadAnimation.getKeyFrame(timer, false);
                break;
            }
            default:
                region = idleAnimation.getKeyFrame(timer, true);
        }

        if (needToBeSwapped != region.isFlipX())
            region.flip(true, false);

        timer += delta;
        drawable.setRegion(region);
    }

    public void moveLeft() {
        if (state == State.ATTACKING && !attackAnimation.isAnimationFinished(timer)
                || state == State.GETTING_DAMAGE && !gettingDamageAnimation.isAnimationFinished(timer)
                || state == State.DEAD) return;

        getPhysicalObject().getBody().applyForceToCenter(new Vector2(-10f, 0), true);
        needToBeSwapped = true;
        state = State.RUNNING;
    }

    public void moveRight() {
        if (state == State.ATTACKING && !attackAnimation.isAnimationFinished(timer)
                || state == State.GETTING_DAMAGE && !gettingDamageAnimation.isAnimationFinished(timer)
                || state == State.DEAD) return;

        getPhysicalObject().getBody().applyForceToCenter(new Vector2(10f, 0), true);
        needToBeSwapped = false;
        state = State.RUNNING;
    }

    public void moveUp() {

        if (onLadder) {
            getPhysicalObject().getBody().setLinearVelocity(
                    getPhysicalObject().getBody().getLinearVelocity().x, 2.5f
            );
            return;
        }

        if (!hasTouchedFloor || state == State.JUMPING && jumpAnimation.isAnimationFinished(timer)
                || state == State.DEAD) return;


        getPhysicalObject().getBody().setLinearVelocity(new Vector2(
                getPhysicalObject().getBody().getLinearVelocity().x, 5f));
        state = State.JUMPING;
        hasTouchedFloor = false;
        timer = 0;
    }

    public void attack() {
        if (state == State.DEAD) return;
        state = State.ATTACKING;
        timer = 0;
    }

    private int getLeftLives() {
        return leftLives;
    }

    private boolean setLeftLives(int leftLives) {
        this.leftLives = leftLives;
        return !(leftLives <= 0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(
                (getPhysicalObject().getBody().getPosition().x) / SCALE * tileScale - getWidth() / 2,
                (getPhysicalObject().getBody().getPosition().y) / SCALE * tileScale - getHeight() / 1.5f
        );
        setAppropriateDrawable(delta);
        if (state == State.RUNNING
                && getPhysicalObject().getBody().getLinearVelocity().y == 0
                && getPhysicalObject().getBody().getLinearVelocity().x == 0
                || state == State.ATTACKING && attackAnimation.isAnimationFinished(timer)
                || state == State.JUMPING && jumpAnimation.isAnimationFinished(timer)
                || state == State.GETTING_DAMAGE && gettingDamageAnimation.isAnimationFinished(timer)) {
            state = State.IDLE;
        } else if (state == State.DEAD && becomeDeadAnimation.isAnimationFinished(timer)) {
            onLoseListener.onLose(GameResources.LOOSE_TEXT_NO_LIVES_LEFT_CASE);
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == ENEMY_BIT) {

            if (state != State.ATTACKING) {

                if (setLeftLives(getLeftLives() - ENEMY_DAMAGE)) state = State.GETTING_DAMAGE;
                else state = State.DEAD;

                onDamageListener.onDamage(getLeftLives());
                timer = 0;

            } else {
                onScoreEarnedListener.onScoreEarned(KILLED_ENEMY_VALUE);
            }

        } else if (hitObjectBits == (PLAYER_FEET_BIT | FLOOR_BIT) || hitObjectBits == (PLAYER_FEET_BIT | BONUS_BIT)) {
            hasTouchedFloor = true;
        } else if (hitObjectBits == LADDER_BIT) {
            onLadder = true;
        }
    }

    @Override
    public void release(short hitObjectBits) {
        if (hitObjectBits == LADDER_BIT) {
            onLadder = false;
        }
    }

    @Override
    public void dispose() {
        idleAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
