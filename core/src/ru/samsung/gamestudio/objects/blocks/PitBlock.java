package ru.samsung.gamestudio.objects.blocks;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;

public class PitBlock extends StaticBlock implements Hittable {
    public PitBlock(World world, Rectangle bounds) {
        super(world, bounds, GameSettings.PIT_BIT);
        fixture.setSensor(true);
    }

    @Override
    public void hit() {
        System.out.println("Pit hit");
    }
}
