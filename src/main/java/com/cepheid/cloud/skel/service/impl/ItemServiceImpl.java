package com.cepheid.cloud.skel.service.impl;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;
import com.cepheid.cloud.skel.repository.ItemRepository;
import com.cepheid.cloud.skel.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void create(Item item) {
        itemRepository.save(item);
    }

    @Override
    public Optional<Item> update(long id, Item item) {
        Item pristineItem = find(id).get();
        if (item.getDescriptions() != null && item.getDescriptions().size() > 0)
            pristineItem.setDescriptions(item.getDescriptions());
        pristineItem.setName(item.getName());
        pristineItem.setState(item.getState());
        itemRepository.save(pristineItem);
        return itemRepository.findById(id);
    }

    @Override
    public Optional<Item> find(long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Collection<Item> findByName(String name) {
        return itemRepository.findByName(name);
    }

    @Override
    public Collection<Item> findByState(State name) {
        return itemRepository.findByState(name);
    }

    @Override
    public Collection<Item> findByNameAndState(String name, State state) {
        return itemRepository.findByNameAndState(name, state);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public void delete(long id) {
        itemRepository.delete(itemRepository.findById(id).get());
    }

}