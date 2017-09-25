package com.tardigrade.resources.impl;

import com.tardigrade.Tardigrade;
import com.tardigrade.deck.ICard;

public class Card implements ICard {
    protected String id;
    protected String name;
    protected String description;
    protected Attributes attributes;

    // Constantes de atributos

    protected Card(String id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.attributes = new Attributes(Tardigrade.CARD_ATTRIBUTES);
    }

    @Override
    public String getId() { return id; }
    @Override
    public void   setId(String id) { this.id = id; }
    @Override
    public String getName() { return name; }
    @Override
    public void   setName(String name) { this.name = name; }
    @Override
    public String getDescription() { return description; }

    @Override
    public void setAttributeByName(String name, Object value) {
        attributes.setAttribute(name, value);
    }

    @Override
    public Object getAttributeByName(String name) {
        return attributes.getAttribute(name);
    }

    @Override
    public void   setDescription(String description) { this.description = description; }

    @Override
    public void execute() {
        Deck.getInstance(null).useCard(Card.this);
    }

    @Override
    public void revert() {
        return;
    }

    public static ICard Create(String id, String name, String description, String[] attr) {
        ICard card = new Card(id, name, description);
        if(attr.length > 0 && Tardigrade.CARD_ATTRIBUTES.length > 0){
            for(int i=0;i < Math.min(attr.length, Tardigrade.CARD_ATTRIBUTES.length); i++){
                card.setAttributeByName(Tardigrade.CARD_ATTRIBUTES[i], attr[i]);
            }
        }
        return card;
    }
}
