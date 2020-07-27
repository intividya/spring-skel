package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemService {

    void create(Item item);

    Optional<Item> update(long id, Item item);

    Optional<Item> find(long id);

    Collection<Item> findByName(String name);

    Collection<Item> findByState(State name);

    Collection<Item> findByNameAndState(String name, State state);

    List<Item> findAll();

    void delete(long id);
}
