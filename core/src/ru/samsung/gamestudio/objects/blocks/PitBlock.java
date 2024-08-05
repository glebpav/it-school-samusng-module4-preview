package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.world.listeners.OnLoseListener;

public class PitBlock extends StaticBlock implements Hittable {

    private final OnLoseListener onLoseListener;

    public PitBlock(World world, Rectangle bounds, OnLoseListener onLoseListener) {
        super(world, bounds, GameSettings.PIT_BIT);
        fixture.setSensor(true);
        this.onLoseListener = onLoseListener;
    }

    @Override
    public void hit(short hitObjectBits) {
        System.out.println("Pit hit");
        onLoseListener.onLose("Don't fall into the pit");
    }
}
