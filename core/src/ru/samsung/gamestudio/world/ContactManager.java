package ru.samsung.gamestudio.world;

import com.badlogic.gdx.physics.box2d.*;
import ru.samsung.gamestudio.game.GameSettings;
import ru.samsung.gamestudio.objects.Hittable;

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
                || (GameSettings.PLAYER_BIT | GameSettings.PIT_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.COIN_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.FLOOR_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.LADDER_BIT) == cDef
                || (GameSettings.PLAYER_HEAD_BIT | GameSettings.BONUS_BIT) == cDef
                || (GameSettings.PLAYER_BIT | GameSettings.BONUS_BIT) == cDef
        ) {
            if (fixA.getUserData() instanceof Hittable) {
                ((Hittable) fixA.getUserData()).hit(cDef2);
            }
            if (fixB.getUserData() instanceof Hittable) {
                ((Hittable) fixB.getUserData()).hit(cDef1);
            }
        } else if ((GameSettings.PLAYER_FEET_BIT | GameSettings.BONUS_BIT) == cDef
                || (GameSettings.PLAYER_FEET_BIT | GameSettings.FLOOR_BIT) == cDef
                || (GameSettings.PLAYER_FEET_BIT | GameSettings.WALL_BIT) == cDef
        ) {
            ((Hittable) fixA.getUserData()).hit((short) cDef);
            ((Hittable) fixB.getUserData()).hit((short) cDef);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        short cDef1 = fixA.getFilterData().categoryBits;
        short cDef2 = fixB.getFilterData().categoryBits;
        int cDef = cDef1 | cDef2;

        if ((GameSettings.PLAYER_BIT | GameSettings.LADDER_BIT) == cDef) {

            if (fixA.getUserData() instanceof Hittable) {
                ((Hittable) fixA.getUserData()).release(fixB.getFilterData().categoryBits);
            }
            if (fixB.getUserData() instanceof Hittable) {
                ((Hittable) fixB.getUserData()).release(fixA.getFilterData().categoryBits);
            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}

