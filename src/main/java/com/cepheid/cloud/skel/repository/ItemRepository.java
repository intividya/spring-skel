package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Collection<Item> findByName(String name);

    Collection<Item> findByState(State name);

    Collection<Item> findByNameAndState(String name, State state);

}
