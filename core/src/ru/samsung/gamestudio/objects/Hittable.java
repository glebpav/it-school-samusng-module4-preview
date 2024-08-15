package ru.samsung.gamestudio.objects;

public interface Hittable {
    void hit(short hitObjectBits);
    void release(short hitObjectBits);
}
