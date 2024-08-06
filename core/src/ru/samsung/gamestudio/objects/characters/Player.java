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
import ru.samsung.gamestudio.objects.Updatable;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnDamageListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Player extends Hero {

    public enum State {FALLING, JUMPING, IDLE, RUNNING, ATTACKING}

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;
    private Animation<TextureRegion> jump;
    private Animation<TextureRegion> attack;

    float timer;
    State state;
    private int leftLives;
    boolean needToBeSwapped;

    private final OnDamageListener onDamageListener;
    private final OnScoreEarnedListener onScoreEarnedListener;

    public Player(
            World world,
            Rectangle bounds,
            OnDamageListener onDamageListener,
            OnScoreEarnedListener onScoreEarnedListener
    ) {
        super(world, bounds, PLAYER_BIT);
        this.onDamageListener = onDamageListener;
        this.onScoreEarnedListener = onScoreEarnedListener;

        createAnimations();
        timer = 0;
        state = State.IDLE;
        leftLives = PLAYER_LIVES;
        setSize(bounds.getWidth() * 2 * PPI, bounds.getHeight() * PPI);

    }

    private void createAnimations() {

        Texture texture = new Texture("texture/player/hero-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 64 * 3, i * 40, 64, 40));
        idle = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 0, i * 40, 64, 40));
        run = new Animation<>(0.3f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 3; i++) frames.add(new TextureRegion(texture, 64, i * 40, 64, 40));
        jump = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

        for (int i = 0; i < 3; i++) frames.add(new TextureRegion(texture, 64 * 2, i * 40, 64, 40));
        attack = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
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

    public int getLeftLives() {
        return leftLives;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == ENEMY_BIT) {
            if (state != State.ATTACKING) {
                setLeftLives(getLeftLives() - ENEMY_DAMAGE);
                onDamageListener.onDamage(getLeftLives());
            } else {
                onScoreEarnedListener.onScoreEarned(KILLED_ENEMY_VALUE);
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition((body.getPosition().x) * SCALE * PPI - getWidth() / 2, (body.getPosition().y) * SCALE * PPI - getHeight() / 1.5f);
        setDrawable(getFrame(delta));
        if (state == State.RUNNING && body.getLinearVelocity().y == 0 && body.getLinearVelocity().y == 0
                || state == State.ATTACKING && attack.isAnimationFinished(timer)
                || state == State.JUMPING && jump.isAnimationFinished(timer))
            state = State.IDLE;
    }

}
