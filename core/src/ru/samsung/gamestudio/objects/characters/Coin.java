package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnRemoveBodyListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Coin extends PhysicalActors {

    private enum State {IDLE, COLLECTED}

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> disappearAnimation;

    private State state;
    private float timer;

    private final OnRemoveBodyListener onRemoveBodyListener;
    private final OnScoreEarnedListener onScoreEarnedListener;

    public Coin(
            World world,
            Rectangle bounds,
            OnScoreEarnedListener onScoreEarnedListener,
            OnRemoveBodyListener onRemoveBodyListener
    ) {
        super(world, bounds, COIN_BIT);
        this.onScoreEarnedListener = onScoreEarnedListener;
        this.onRemoveBodyListener = onRemoveBodyListener;

        state = State.IDLE;
        createAnimations();
        setSize(bounds.getWidth() * PPI, bounds.getHeight() * PPI);
        setPosition(bounds.getX() * PPI, bounds.getY() * PPI);
        timer = 0;
        body.setType(BodyDef.BodyType.StaticBody);
        fixture.setSensor(true);

    }

    private void createAnimations() {

        Texture texture = new Texture("texture/items/coin-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 0, i * 24, 24, 24));
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 24, i * 24, 24, 24));
        disappearAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();

    }

    private Drawable getFrame(float delta) {

        TextureRegion region;

        switch (state) {
            case COLLECTED: {
                region = disappearAnimation.getKeyFrame(timer, false);
                break;
            }
            default:
                region = idleAnimation.getKeyFrame(timer, true);
        }

        timer += delta;
        return (new Image(region)).getDrawable();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDrawable(getFrame(delta));
        if (state == State.COLLECTED && disappearAnimation.isAnimationFinished(timer)) {
            remove();
            onRemoveBodyListener.onRemoveBody(body);
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == PLAYER_BIT) {
            state = State.COLLECTED;
            timer = 0;
            onScoreEarnedListener.onScoreEarned(COIN_VALUE);
        }
    }

}
