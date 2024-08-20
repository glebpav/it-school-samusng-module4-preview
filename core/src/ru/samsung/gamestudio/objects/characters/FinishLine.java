package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import ru.samsung.gamestudio.game.GameResources;
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.world.listeners.OnWinListener;

import static ru.samsung.gamestudio.game.GameSettings.*;

public class FinishLine extends PhysicalActor implements Hittable, Disposable {

    private final OnWinListener onWinListener;
    private float timer;

    private Animation<TextureRegion> idleAnimation;
    private TextureRegionDrawable drawable;

    public FinishLine(World world, Rectangle bounds, OnWinListener onWinListener, float tileScale) {
        this.onWinListener = onWinListener;

        setPhysicalObject(
                new PhysicalObject.PhysicalObjectBuilder(world, BodyDef.BodyType.StaticBody)
                        .addRectangularFixture(bounds.getWidth(), bounds.getHeight(), EXIT_BIT)
                        .setInitialPosition(bounds.x + bounds.getWidth() / 2, bounds.y + bounds.getHeight() / 2)
                        .setBodyAsSensor()
                        .build(this)
        );

        createAnimations();
        timer = 0;
        setSize(bounds.getWidth() * tileScale, bounds.getHeight() * tileScale);
        setPosition(bounds.getX() * tileScale, bounds.getY() * tileScale);
    }

    private void createAnimations() {
        Texture texture = new Texture(GameResources.FINIS_LINE_TILESET_PATH);
        Array<TextureRegion> frames = new Array<>();
        drawable = new TextureRegionDrawable();
        setDrawable(drawable);

        for (int i = 0; i < 9; i++) frames.add(new TextureRegion(texture, 34 * i, 0, 34, 93));
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();
    }

    private void setAppropriateDrawable(float delta) {
        TextureRegion region = idleAnimation.getKeyFrame(timer, true);
        timer += delta;
        drawable.setRegion(region);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setAppropriateDrawable(delta);
    }

    @Override
    public void hit(short hitObjectBits) {
        onWinListener.onWin();
    }

    @Override
    public void dispose() {
        idleAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
