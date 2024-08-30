package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureLoader {

    public static Texture loadTexture(String path, String tag) {
        try {
            return new Texture(Gdx.files.internal(path));
        } catch (GdxRuntimeException e) {
            Gdx.app.error(tag, "Failed to load texture " + path, e);
            throw e;
        }
    }
}