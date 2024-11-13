package ru.samsung.gamestudio.ui.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ru.samsung.gamestudio.ui.UiComponent;
import ru.samsung.gamestudio.ui.components.LiveBackground;

import static ru.samsung.gamestudio.game.GameSettings.localizationBundle;

public class MenuUi extends UiComponent {

    private final int BUTTON_HEIGHT = 60;
    private final int SPACE_BETWEEN_COMPONENTS = 10;
    private final int SCROLL_PANEL_WIDTH = 400;
    private final int SCROLL_PANEL_HEIGHT = 250;
    private final int START_BUTTON_WIDTH = 400;
    private final int SECONDARY_BUTTON_WIDTH = 195;
    private final int LIST_VIEW_WIDTH = 250;
    private final int COMMON_PADDING_BOTTOM = 30;

    public ScrollPane scrollPane;
    public List<String> listView;

    public TextButton startButton;
    public TextButton exitButton;
    public TextButton settingsButton;

    public MenuUi(Skin skin) {
        LiveBackground liveBackground = new LiveBackground();
        startButton = new TextButton(localizationBundle.get("startButtonText"), skin);
        exitButton = new TextButton(localizationBundle.get("exitButtonText"), skin);
        settingsButton = new TextButton(localizationBundle.get("settingsButtonText"), skin);
        Label title = new Label(localizationBundle.get("menuScreenTitleText"), skin, "labelTitle");

        listView = new List<>(skin);
        scrollPane = new ScrollPane(listView, skin);

        listView.setAlignment(Align.center);
        listView.setWidth(LIST_VIEW_WIDTH);
        scrollPane.setActor(listView);

        addActor(liveBackground);

        columnDefaults(2);
        add(title).colspan(2).padBottom(COMMON_PADDING_BOTTOM);
        row();
        add(scrollPane).width(SCROLL_PANEL_WIDTH).height(SCROLL_PANEL_HEIGHT).colspan(2).space(SPACE_BETWEEN_COMPONENTS);
        row();
        add(startButton).width(START_BUTTON_WIDTH).height(BUTTON_HEIGHT).colspan(2).space(SPACE_BETWEEN_COMPONENTS);
        row();
        add(exitButton).width(SECONDARY_BUTTON_WIDTH).height(BUTTON_HEIGHT).space(SPACE_BETWEEN_COMPONENTS);
        add(settingsButton).width(SECONDARY_BUTTON_WIDTH).height(BUTTON_HEIGHT).space(SPACE_BETWEEN_COMPONENTS);
    }

}
