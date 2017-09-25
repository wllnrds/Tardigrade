package tardigrade.game;

public interface IAttributes {
    void setAttribute(String name, Object value);
    Object getAttribute(String name);
    void removeAttribute(String name);
}
