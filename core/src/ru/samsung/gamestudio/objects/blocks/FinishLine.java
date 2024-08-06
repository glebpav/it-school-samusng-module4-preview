package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.world.listeners.OnWinListener;

public class FinishLine extends StaticBlock implements Hittable {

    private final OnWinListener onWinListener;

    public FinishLine(World world, Rectangle bounds, OnWinListener onWinListener) {
        super(world, bounds, GameSettings.EXIT_BIT);
        fixture.setSensor(true);
        this.onWinListener = onWinListener;
    }

    @Override
    public void hit(short hitObjectBits) {
        System.out.println("Finish hit");
        onWinListener.onWin();
    }
}
