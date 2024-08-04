package ru.samsung.gamestudio.utils;

import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;
import ru.samsung.gamestudio.objects.characters.Hero;

public class ContactManager implements ContactListener {

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        short cDef1 = fixA.getFilterData().categoryBits;
        short cDef2 = fixB.getFilterData().categoryBits;
        int cDef = cDef1 | cDef2;

        if ((GameSettings.PLAYER_BIT | GameSettings.ENEMY_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.EXIT_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.PIT_BIT) == cDef) {

            ((Hittable) fixA.getUserData()).hit();
            ((Hittable) fixB.getUserData()).hit();

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

