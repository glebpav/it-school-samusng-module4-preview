package ru.samsung.gamestudio.objects.characters;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.Objects;

import static ru.samsung.gamestudio.game.GameSettings.SCALE;

public class PhysicalObject {

    private final Body body;
    private final ArrayList<Fixture> fixturesList;

    private PhysicalObject(Body body, ArrayList<Fixture> fixturesList, Object objectHolder) {
        this.body = body;
        this.fixturesList = fixturesList;

        for (Fixture fixture : this.fixturesList) {
            fixture.setUserData(objectHolder);
        }
    }

    private PhysicalObject(Body body, ArrayList<Fixture> fixturesList) {
        this.body = body;
        this.fixturesList = fixturesList;
    }

    public Body getBody() {
        return body;
    }

    public ArrayList<Fixture> getFixturesList() {
        return fixturesList;
    }

    public static class PhysicalObjectBuilder {
        private final Body body;
        private final ArrayList<Fixture> fixturesList;

        public PhysicalObjectBuilder(World world, BodyDef.BodyType bodyType) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = bodyType;
            body = world.createBody(bodyDef);
            body.setLinearDamping(2f);
            fixturesList = new ArrayList<>();
        }

        public PhysicalObjectBuilder setInitialPosition(float x, float y) {
            body.setTransform(x / SCALE, y / SCALE, 0);
            return this;
        }

        public PhysicalObjectBuilder addCircularFixture(float radius, short categoryBits) {
            FixtureDef fixtureDef = new FixtureDef();
            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(radius / SCALE);
            fixtureDef.shape = circleShape;
            fixtureDef.filter.categoryBits = categoryBits;
            fixturesList.add(body.createFixture(fixtureDef));
            circleShape.dispose();
            return this;
        }

        public PhysicalObjectBuilder addRectangularFixture(float width, float height, short categoryBits) {
            FixtureDef fixtureDef = new FixtureDef();
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(width / 2 / SCALE, height / 2 / SCALE);
            fixtureDef.filter.categoryBits = categoryBits;
            fixtureDef.shape = polygonShape;
            fixturesList.add(body.createFixture(fixtureDef));
            polygonShape.dispose();
            return this;
        }

        public PhysicalObjectBuilder addEdgeFixture(float vx1, float vy1, float vx2, float vy2, short categoryBits) {
            FixtureDef fixtureDef = new FixtureDef();
            EdgeShape edgeShape = new EdgeShape();
            edgeShape.set(vx1 / SCALE, vy1 / SCALE, vx2 / SCALE, vy2 / SCALE);
            fixtureDef.filter.categoryBits = categoryBits;
            fixtureDef.shape = edgeShape;
            fixturesList.add(body.createFixture(fixtureDef));
            edgeShape.dispose();
            return this;
        }

        public PhysicalObjectBuilder setBodyAsSensor() {
            for (Fixture fixture : fixturesList) fixture.setSensor(true);
            return this;
        }

        public PhysicalObject build(Object objectHolder) {
            return new PhysicalObject(body, fixturesList, objectHolder);
        }

        public PhysicalObject build() {
            return new PhysicalObject(body, fixturesList);
        }

    }

}
