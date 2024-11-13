package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;

public class MobileController extends Table {

    private final int CONTROLLER_PADDING_HORIZONTAL = 30;
    private final int CONTROLLER_PADDING_BOTTOM = 130;
    private final int BUTTONS_SIZE = 100;

    public final Button upButton;
    public final Button attackButton;
    public final Button backButton;
    public final Button forwardButton;

    public MobileController(Skin skin) {
        upButton = new Button(skin, "buttonUp");
        backButton = new Button(skin, "buttonBack");
        forwardButton = new Button(skin, "buttonForward");
        attackButton = new Button(skin, "buttonAttack");

        setWidth(SCREEN_WIDTH);

        add(backButton).height(BUTTONS_SIZE).width(BUTTONS_SIZE)
                .padLeft(CONTROLLER_PADDING_HORIZONTAL).padBottom(CONTROLLER_PADDING_BOTTOM);

        add(forwardButton).height(BUTTONS_SIZE).width(BUTTONS_SIZE)
                .padLeft(CONTROLLER_PADDING_HORIZONTAL).padBottom(CONTROLLER_PADDING_BOTTOM);

        add().expand();

        add(attackButton).height(BUTTONS_SIZE).width(BUTTONS_SIZE)
                .padRight(CONTROLLER_PADDING_HORIZONTAL).padBottom(CONTROLLER_PADDING_BOTTOM);

        add(upButton).height(BUTTONS_SIZE).width(BUTTONS_SIZE)
                .padRight(CONTROLLER_PADDING_HORIZONTAL).padBottom(CONTROLLER_PADDING_BOTTOM);
    }

}
