package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.world.listeners.OnLoseListener;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnDamageListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Player extends PhysicalActor {

    private enum State {JUMPING, IDLE, RUNNING, ATTACKING, DEAD, GETTING_DAMAGE}

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> jumpAnimation;
    private Animation<TextureRegion> attackAnimation;
    private Animation<TextureRegion> gettingDamageAnimation;
    private Animation<TextureRegion> becomeDeadAnimation;

    float timer;
    State state;
    private int leftLives;
    boolean needToBeSwapped;
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
            OnLoseListener onLoseListener
    ) {
        this.onLoseListener = onLoseListener;
        this.onDamageListener = onDamageListener;
        this.onScoreEarnedListener = onScoreEarnedListener;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.DynamicBody)
                        .addCircularFixture(bounds.getHeight() / 2, PLAYER_BIT)
                        .addEdgeFixture(-bounds.getWidth() / 4, (bounds.getHeight() / 2), (bounds.getWidth() / 4), (bounds.getHeight() / 2), PLAYER_HEAD_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .build(this)
        );

        // createHeadHitBox(bounds);
        createAnimations();
        timer = 0;
        state = State.IDLE;
        leftLives = PLAYER_LIVES;
        setSize(bounds.getWidth() * 2 * PPI, bounds.getHeight() * PPI);

        hasTouchedFloor = false;
    }

    private void createAnimations() {

        Texture texture = new Texture("texture/player/hero-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 64 * i, 4 * 40, 64, 40));
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 64 * i, 0, 64, 40));
        runAnimation = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
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

    private Drawable getFrame(float delta) {

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
        return (new Image(region)).getDrawable();

    }

    public void moveLeft() {
        if (state == State.ATTACKING && !attackAnimation.isAnimationFinished(timer)
            || state == State.GETTING_DAMAGE && !gettingDamageAnimation.isAnimationFinished(timer)) return;

        getPhysicalObject().getBody().applyForceToCenter(new Vector2(-10f, 0), true);
        needToBeSwapped = true;
        state = State.RUNNING;
    }

    public void moveRight() {
        if (state == State.ATTACKING && !attackAnimation.isAnimationFinished(timer)
                || state == State.GETTING_DAMAGE && !gettingDamageAnimation.isAnimationFinished(timer)) return;

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

        if (!hasTouchedFloor || state == State.JUMPING && jumpAnimation.isAnimationFinished(timer)) return;

        getPhysicalObject().getBody().applyForceToCenter(new Vector2(0, 300f), true);
        state = State.JUMPING;
        hasTouchedFloor = false;
        timer = 0;
    }

    public void attack() {
        state = State.ATTACKING;
        timer = 0;
    }

    public int getLeftLives() {
        return leftLives;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition((
                        getPhysicalObject().getBody().getPosition().x) * SCALE * PPI - getWidth() / 2,
                (getPhysicalObject().getBody().getPosition().y) * SCALE * PPI - getHeight() / 1.5f
        );
        setDrawable(getFrame(delta));
        if (state == State.RUNNING
                && getPhysicalObject().getBody().getLinearVelocity().y == 0
                && getPhysicalObject().getBody().getLinearVelocity().y == 0
                || state == State.ATTACKING && attackAnimation.isAnimationFinished(timer)
                || state == State.JUMPING && jumpAnimation.isAnimationFinished(timer)
                || state == State.GETTING_DAMAGE && gettingDamageAnimation.isAnimationFinished(timer)) {
            state = State.IDLE;
        } else if (state == State.DEAD && becomeDeadAnimation.isAnimationFinished(timer)) {
            onLoseListener.onLose("Don't touch enemies, they don't like it");
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == ENEMY_BIT) {

            if (state != State.ATTACKING) {

                setLeftLives(getLeftLives() - ENEMY_DAMAGE);
                onDamageListener.onDamage(getLeftLives());
                timer = 0;

                if (getLeftLives() <= 0) state = State.DEAD;
                else state = State.GETTING_DAMAGE;

            } else {
                onScoreEarnedListener.onScoreEarned(KILLED_ENEMY_VALUE);
            }

        } else if (hitObjectBits == FLOOR_BIT || hitObjectBits == BONUS_BIT) {
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

}
