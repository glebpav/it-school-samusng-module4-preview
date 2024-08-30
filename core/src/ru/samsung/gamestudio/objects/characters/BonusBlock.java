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
import ru.samsung.gamestudio.world.listeners.OnRemoveBodyListener;
import ru.samsung.gamestudio.world.listeners.OnScoreEarnedListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class BonusBlock extends PhysicalActor implements Disposable {

    private enum State {IDLE, DESTROYED}

    private Animation<TextureRegion> destroyAnimation;
    private Animation<TextureRegion> idleAnimation;
    private TextureRegionDrawable drawable;

    private State state;
    private float timer;

    private final OnRemoveBodyListener onRemoveBodyListener;
    private final OnScoreEarnedListener onScoreEarnedListener;

    public BonusBlock(
            World world,
            Rectangle bounds,
            OnRemoveBodyListener onRemoveBodyListener,
            OnScoreEarnedListener onScoreEarnedListener,
            float tileScale
    ) {
        this.onRemoveBodyListener = onRemoveBodyListener;
        this.onScoreEarnedListener = onScoreEarnedListener;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                        .addRectangularFixture(bounds.width, bounds.height, BONUS_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .build(this)
        );

        state = State.IDLE;
        timer = 0;
        setSize(bounds.getWidth() * tileScale, bounds.getHeight() * tileScale);
        setPosition(bounds.getX() * tileScale, bounds.getY() * tileScale);

        createAnimations();

    }

    private void createAnimations() {
        Texture texture = TextureLoader.loadTexture(GameResources.BONUS_TILESET_PATH, "BonusBlock");

        Array<TextureRegion> frames = new Array<>();
        drawable = new TextureRegionDrawable();
        setDrawable(drawable);

        frames.add(new TextureRegion(new TextureRegion(texture, 0, 0, 32, 32)));
        idleAnimation = new Animation<>(1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 8; i++) frames.add(new TextureRegion(texture, 32 * i, 0, 32, 32));
        destroyAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private void setAppropriateDrawable(float delta) {
        TextureRegion region;

        switch (state) {
            case DESTROYED: {
                region = destroyAnimation.getKeyFrame(timer, false);
                break;
            }
            default:
                region = idleAnimation.getKeyFrame(timer, false);
        }

        timer += delta;
        drawable.setRegion(region);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setAppropriateDrawable(delta);
        if (state == State.DESTROYED && destroyAnimation.isAnimationFinished(timer)) {
            remove();
            onRemoveBodyListener.onRemoveBody(getPhysicalObject().getBody());
            onScoreEarnedListener.onScoreEarned(BONUS_VALUE);
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == PLAYER_HEAD_BIT && state == State.IDLE) {
            state = State.DESTROYED;
            timer = 0;
        }
    }

    @Override
    public void dispose() {
        idleAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
