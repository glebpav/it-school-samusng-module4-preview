package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import ru.samsung.gamestudio.objects.Updatable;
import ru.samsung.gamestudio.objects.blocks.StaticBlock;

import static ru.samsung.gamestudio.GameSettings.COIN_BIT;
import static ru.samsung.gamestudio.GameSettings.PPI;

public class Coin extends Hero {

    enum State {IDLE, COLLECTED}

    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> disappearAnimation;

    State state;
    float timer;

    public Coin(World world, Rectangle bounds) {
        super(world, bounds, COIN_BIT);
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
        setDrawable(getFrame(delta));
        if (state == State.COLLECTED && disappearAnimation.isAnimationFinished(timer)) {
            remove();
            body.destroyFixture(fixture);
        }
    }

    @Override
    public void hit() {
        state = State.COLLECTED;
        timer = 0;
    }

}
