package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;

public class FinishLine extends StaticBlock implements Hittable {
    public FinishLine(World world, Rectangle bounds) {
        super(world, bounds, GameSettings.EXIT_BIT);
        fixture.setSensor(true);
    }

    @Override
    public void hit() {
        System.out.println("Finish hit");
    }
}
