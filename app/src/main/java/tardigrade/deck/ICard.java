package tardigrade.deck;

public interface ICard {
	void setId(String id);
	String getId();

	void setName(String name);
	String getName();

	void setDescription(String description);
	String getDescription();

    void setAttributeByName(String name, Object value);
	Object getAttributeByName(String name);

    void execute();
	void revert();
}
