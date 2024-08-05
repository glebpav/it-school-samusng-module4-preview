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

import static ru.samsung.gamestudio.GameSettings.*;

public class Enemy extends Hero implements Updatable {

    public enum State {IDLE, RUNNING}

    private Animation<TextureRegion> idle;
    private Animation<TextureRegion> run;

    float timer;
    State state;
    boolean needToBeSwapped;
    boolean moveRightFlag;

    float territoryLen = 120;
    float initialX;

    public Enemy(World world, Rectangle bounds) {
        super(world, bounds, ENEMY_BIT);
        createAnimations();
        timer = 0;
        state = State.IDLE;
        setSize(bounds.getWidth() * PPI, bounds.getHeight() * PPI);
        // fixture.setFilterData();

        initialX = bounds.getX();
        moveRight();

    }

    private void createAnimations() {

        Texture texture = new Texture("texture/enemy/enemy1-tileset.png");
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < 5; i++) frames.add(new TextureRegion(texture, 0, i * 30, 34, 30));
        idle = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        for (int i = 0; i < 6; i++) frames.add(new TextureRegion(texture, 64, i * 30, 34, 30));
        run = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();

    }

    private Drawable getFrame(float delta) {

        TextureRegion region;

        switch (state) {
            case RUNNING: {
                region = run.getKeyFrame(timer, true);
                break;
            }
            default:
                region = idle.getKeyFrame(timer, true);
        }

        if (needToBeSwapped == region.isFlipX())
            region.flip(true, false);

        timer += delta;
        return (new Image(region)).getDrawable();

    }

    @Override
    public void act(float delta) {
        // todo: rewrite update to act method
    }

    @Override
    public void update(float delta) {
        setPosition((body.getPosition().x) * SCALE * PPI - getWidth() / 2, (body.getPosition().y) * SCALE * PPI - getHeight() / 1.5f);
        setDrawable(getFrame(delta));
        // System.out.println(body.getPosition().x * SCALE - initialX);

        if (moveRightFlag) moveRight();
        else moveLeft();

        if (body.getPosition().x * SCALE - initialX >= territoryLen) moveRightFlag = false;
        else if (body.getPosition().x * SCALE - initialX <= 0) moveRightFlag = true;
    }

    public void moveLeft() {
        body.applyForceToCenter(new Vector2(-7f, 0), true);
        needToBeSwapped = true;
        state = State.RUNNING;
    }

    public void moveRight() {
        body.applyForceToCenter(new Vector2(7f, 0), true);
        needToBeSwapped = false;
        state = State.RUNNING;
    }

    @Override
    public void hit(short hitObjectBits) {
        System.out.println("enemy hit");
    }
}
