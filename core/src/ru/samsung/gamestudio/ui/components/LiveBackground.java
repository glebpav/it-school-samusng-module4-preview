package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import static ru.samsung.gamestudio.game.GameResources.*;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_HEIGHT;
import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;

public class LiveBackground extends Group implements Disposable {

    private final Image backgroundImage;
    private final Image smallReflectionImage;
    private final Image bigReflectionImage;
    private final Image bigCloudImage;

    private final Animation<TextureRegion> bigReflectionAnimation;
    private final Animation<TextureRegion> smallReflectionAnimation;

    private float timer;
    private float cloudX1;
    private float cloudX2;

    private final float CLOUD_SPEED_RATIO = 7;

    public LiveBackground() {

        Texture reflectionsTileset = new Texture(REFLECTIONS_TILESET_PATH);

        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(reflectionsTileset, 0, 10 * i, 170, 10));
        bigReflectionAnimation = new Animation<>(0.1f, frames, Animation.PlayMode.LOOP);
        frames.clear();
        for (int i = 0; i < 4; i++) frames.add(new TextureRegion(reflectionsTileset, 170, 3 * i, 35, 3));
        smallReflectionAnimation = new Animation<>(0.15f, frames, Animation.PlayMode.LOOP);
        frames.clear();

        backgroundImage = new Image(new Texture(BACKGROUND_IMAGE_PATH));
        bigReflectionImage = new Image(getBigReflectionDrawable(0));
        smallReflectionImage = new Image(getSmallReflectionDrawable(0));

        bigCloudImage = new Image(new Texture(BIT_CLOUD_IMAGE_PATH));

        backgroundImage.setPosition(0, 0);
        backgroundImage.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        bigCloudImage.setSize(SCREEN_WIDTH, SCREEN_HEIGHT / 3);

        bigReflectionImage.setSize(670, 50);
        smallReflectionImage.setSize(135, 13);
        bigReflectionImage.setPosition(
                SCREEN_WIDTH / 2 - bigReflectionImage.getWidth() / 2,
                (SCREEN_HEIGHT / 4 - bigReflectionImage.getHeight() / 2)
        );
        smallReflectionImage.setPosition(
                SCREEN_WIDTH / 7 - smallReflectionImage.getWidth() / 2,
                SCREEN_HEIGHT / 6 - smallReflectionImage.getHeight() / 2
        );

        timer = 0;
        cloudX1 = 0;
        cloudX2 = SCREEN_WIDTH;
    }

    private Drawable getBigReflectionDrawable(float timer) {
        TextureRegion region = bigReflectionAnimation.getKeyFrame(timer, true);
        return (new Image(region)).getDrawable();
    }

    private Drawable getSmallReflectionDrawable(float timer) {
        TextureRegion region = smallReflectionAnimation.getKeyFrame(timer, true);
        return (new Image(region)).getDrawable();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        timer += delta;
        bigReflectionImage.setDrawable(getBigReflectionDrawable(timer));
        smallReflectionImage.setDrawable(getSmallReflectionDrawable(timer));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        backgroundImage.draw(batch, parentAlpha);
        bigReflectionImage.draw(batch, parentAlpha);
        bigCloudImage.setPosition(cloudX1, SCREEN_HEIGHT / 3);
        bigCloudImage.draw(batch, parentAlpha);
        bigCloudImage.setPosition(cloudX2, SCREEN_HEIGHT / 3);
        bigCloudImage.draw(batch, parentAlpha);
        smallReflectionImage.setPosition(
                SCREEN_WIDTH / 7 - smallReflectionImage.getWidth() / 2,
                SCREEN_HEIGHT / 6 - smallReflectionImage.getHeight() / 2
        );
        smallReflectionImage.draw(batch, parentAlpha);
        smallReflectionImage.setPosition(
                SCREEN_WIDTH / 1.3f - smallReflectionImage.getWidth() / 2,
                SCREEN_HEIGHT / 12 - smallReflectionImage.getHeight() / 2
        );
        smallReflectionImage.draw(batch, parentAlpha);
    }

    public void computeCloudPositions(float cameraX) {
        cloudX1 = -cameraX / CLOUD_SPEED_RATIO % SCREEN_WIDTH;
        cloudX2 = cloudX1 + SCREEN_WIDTH;
    }

    @Override
    public void dispose() {
        smallReflectionAnimation.getKeyFrame(0).getTexture().dispose();
        bigReflectionAnimation.getKeyFrame(0).getTexture().dispose();
    }
}
