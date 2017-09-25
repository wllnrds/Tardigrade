package tardigrade.comunication;

import tardigrade.utils.Flag;

public interface IPack{
    void setValue(Object value);
    Object getValue();

    void setKey(Flag key);
    Flag getKey();
}
