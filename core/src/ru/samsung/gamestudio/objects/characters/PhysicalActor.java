package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.objects.PhysicalObject;

public class PhysicalActor extends Image implements Hittable {

    private PhysicalObject physicalObject;

    public void setPhysicalObject(PhysicalObject physicalObject) {
        this.physicalObject = physicalObject;
    }

    protected PhysicalObject getPhysicalObject() {
        return physicalObject;
    }

    @Override
    public void hit(short hitObjectBits) {
    }

    @Override
    public void release(short hitObjectBits) {
    }
}
