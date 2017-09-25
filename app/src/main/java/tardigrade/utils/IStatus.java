package tardigrade.utils;

public interface IStatus{
    void onStart(Object object);
    void onUpdate(Object object);
    void onEnd(Object object);
}