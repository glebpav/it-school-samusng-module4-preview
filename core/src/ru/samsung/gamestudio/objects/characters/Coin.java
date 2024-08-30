package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.samsung.gamestudio.game.GameResources;
import ru.samsung.gamestudio.utils.TextureLoader;
import ru.samsung.gamestudio.objects.PhysicalObject;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;
import ru.samsung.gamestudio.world.listeners.OnRemoveBodyListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class Coin extends PhysicalActor implements Disposable {

    private enum State {IDLE, COLLECTED}

    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> disappearAnimation;
    private TextureRegionDrawable drawable;

    private State state;
    private float timer;

    private final OnRemoveBodyListener onRemoveBodyListener;
    private final OnScoreEarnedListener onScoreEarnedListener;

    public Coin(
            World world,
            Rectangle bounds,
            OnScoreEarnedListener onScoreEarnedListener,
            OnRemoveBodyListener onRemoveBodyListener,
            float tileScale
    ) {
        this.onScoreEarnedListener = onScoreEarnedListener;
        this.onRemoveBodyListener = onRemoveBodyListener;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                        .addCircularFixture(bounds.getHeight() / 2, COIN_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .setBodyAsSensor()
                        .build(this)
        );

        state = State.IDLE;
        createAnimations();
        setSize(bounds.getWidth() * tileScale, bounds.getHeight() * tileScale);
        setPosition(bounds.getX() * tileScale, bounds.getY() * tileScale);
        timer = 0;
    }

    private void createAnimations() {
        Texture texture = TextureLoader.loadTexture(GameResources.COIN_TILESET_PATH, "Coin");

        Array<TextureRegion> frames = new Array<>();
        drawable = new TextureRegionDrawable();
        setDrawable(drawable);

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 0, i * 24, 24, 24));
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(texture, 24, i * 24, 24, 24));
        disappearAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private void setAppropriateDrawable(float delta) {
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
        drawable.setRegion(region);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setAppropriateDrawable(delta);
        if (state == State.COLLECTED && disappearAnimation.isAnimationFinished(timer)) {
            remove();
            onRemoveBodyListener.onRemoveBody(getPhysicalObject().getBody());
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

    @Override
    public void dispose() {
        idleAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
