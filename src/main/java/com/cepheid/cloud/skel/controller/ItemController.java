package com.cepheid.cloud.skel.controller;

import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;
import com.cepheid.cloud.skel.service.impl.ItemServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Component
@Path("/api/1.0/items")
@Api()
public class ItemController {

    private ItemServiceImpl itemServiceImpl;

    @Autowired
    public ItemController(ItemServiceImpl itemServiceImpl) {
        this.itemServiceImpl = itemServiceImpl;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Item> getItems() {
        return itemServiceImpl.findAll();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Item createItem(Item item) {
        itemServiceImpl.create(item);
        return itemServiceImpl.find(item.getId()).get();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Item findItem(@PathParam("id") long id) {
        return itemServiceImpl.find(id).get();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Collection<Item> findItemByName(@QueryParam("name") String name, @QueryParam("state") String state) {
        if (name != null && state != null)
            return itemServiceImpl.findByNameAndState(name, State.valueOf(state));
        else if (name != null)
            return itemServiceImpl.findByName(name);
        else
            return itemServiceImpl.findByState(State.valueOf(state));
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Item updateItem(@PathParam("id") long id, Item item) {
        return itemServiceImpl.find(itemServiceImpl.update(id, item).get().getId()).get();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public ResponseEntity<String> createItem(@PathParam("id") long id) {
        itemServiceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

}
