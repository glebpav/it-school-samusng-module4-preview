package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.utils.B2WorldManager;
import ru.samsung.gamestudio.utils.OnLooseListener;

public class PitBlock extends StaticBlock implements Hittable {

    private final OnLooseListener onLooseListener;

    public PitBlock(World world, Rectangle bounds, OnLooseListener onLooseListener) {
        super(world, bounds, GameSettings.PIT_BIT);
        fixture.setSensor(true);
        this.onLooseListener = onLooseListener;
    }

    @Override
    public void hit() {
        System.out.println("Pit hit");
        onLooseListener.onLoose("Don't fall into the pit");
    }
}
