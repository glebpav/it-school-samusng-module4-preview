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
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.world.listeners.OnWinListener;

import static ru.samsung.gamestudio.game.GameSettings.PPI;

public class FinishLine extends PhysicalActors implements Hittable {

    private final OnWinListener onWinListener;
    private float timer;

    private Animation<TextureRegion> idleAnimation;

    public FinishLine(World world, Rectangle bounds, OnWinListener onWinListener) {
        super(world, bounds, GameSettings.EXIT_BIT);
        this.onWinListener = onWinListener;

        fixture.setSensor(true);
        body.setType(BodyDef.BodyType.StaticBody);
        createAnimations();
        timer = 0;
        setSize(bounds.getWidth() * PPI, bounds.getHeight() * PPI);
        setPosition(bounds.getX() * PPI, bounds.getY() * PPI);
    }

    private void createAnimations() {

        Texture texture = new Texture("texture/items/finish-line-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 9; i++) frames.add(new TextureRegion(texture, 34 * i, 0, 34, 93));
        idleAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

    }

    private Drawable getFrame(float delta) {

        TextureRegion region = idleAnimation.getKeyFrame(timer, true);
        timer += delta;
        return (new Image(region)).getDrawable();

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setDrawable(getFrame(delta));
    }

    @Override
    public void hit(short hitObjectBits) {
        onWinListener.onWin();
    }
}
