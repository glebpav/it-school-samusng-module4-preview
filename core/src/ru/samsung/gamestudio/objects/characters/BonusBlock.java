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

import java.util.List;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class BonusBlock extends PhysicalActors {

    private enum State {IDLE, DESTROYED}

    private Animation<TextureRegion> destroyAnimation;
    private Animation<TextureRegion> idleAnimation;

    private State state;
    private float timer;

    public BonusBlock(World world, Rectangle bounds) {
        super(world, bounds, BONUS_BIT);

        state = State.IDLE;
        timer = 0;
        setSize(bounds.getWidth() * PPI, bounds.getHeight() * PPI);
        setPosition(bounds.getX() * PPI, bounds.getY() * PPI);
        body.setType(BodyDef.BodyType.StaticBody);
        createAnimations();

    }

    private void createAnimations() {
        Texture texture = new Texture("texture/items/bonus-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        frames.add(new TextureRegion(new TextureRegion(texture, 0,0, 32, 32)));
        idleAnimation = new Animation<>(1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 0, i * 32, 32, 32));
        destroyAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.NORMAL);
        frames.clear();
    }

    private Drawable getFrame(float delta) {

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
        return (new Image(region)).getDrawable();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDrawable(getFrame(delta));
        if (state == State.DESTROYED && destroyAnimation.isAnimationFinished(timer)){
            remove();
        }
    }

    @Override
    public void hit(short hitObjectBits) {
        if (hitObjectBits == PLAYER_HEAD_BIT) {
            state = State.DESTROYED;
            timer = 0;
        }

    }
}
