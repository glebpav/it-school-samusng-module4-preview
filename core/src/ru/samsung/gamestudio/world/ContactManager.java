package ru.samsung.gamestudio.world;

import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        short cDef1 = fixA.getFilterData().categoryBits;
        short cDef2 = fixB.getFilterData().categoryBits;
        int cDef = cDef1 | cDef2;

        // System.out.println("cdef1: " + cDef1);
        // System.out.println("cdef2: " + cDef2);

        if ((GameSettings.PLAYER_BIT | GameSettings.ENEMY_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.EXIT_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.PIT_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.COIN_BIT) == cDef) {

            ((Hittable) fixA.getUserData()).hit(cDef2);
            ((Hittable) fixB.getUserData()).hit(cDef1);

        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}

