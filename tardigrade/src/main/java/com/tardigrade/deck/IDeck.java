package com.tardigrade.deck;

import com.tardigrade.utils.ICallback;

import java.io.IOException;
import java.util.List;

public interface IDeck{
	void init();
	void loadDeck() throws IOException;
	void shuffleDeck();
	
	void putCard(ICard card);
	ICard getCard(String id);
	List<ICard> getAllCards();
	
	void useCard(ICard card);

	void setOnLoadDeck(ICallback callback);
	void setOnUseCard(ICallback callback);
}
