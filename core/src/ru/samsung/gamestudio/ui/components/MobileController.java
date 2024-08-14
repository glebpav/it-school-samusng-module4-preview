package ru.samsung.gamestudio.ui.components;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import static ru.samsung.gamestudio.game.GameSettings.SCREEN_WIDTH;

public class MobileController extends Table {

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
        add(backButton).height(100).width(100).padLeft(30).padBottom(130);
        add(forwardButton).height(100).width(100).padLeft(30).padBottom(130);
        add().expand();
        add(attackButton).height(100).width(100).padRight(30).padBottom(130);
        add(upButton).height(100).width(100).padRight(30).padBottom(130);

    }

}
