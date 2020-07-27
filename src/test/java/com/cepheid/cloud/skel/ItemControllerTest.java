package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
public class ItemControllerTest extends TestBase {

    @Test
    public void testGetItems() {
        Builder itemController = getBuilder("/app/api/1.0/items");
        Collection<Item> items = itemController.get(new GenericType<Collection<Item>>() {
        });
        Assert.assertNotNull(items);
        Assert.assertTrue(items.size() > 0);
    }

    @Test
    public void testSearchItems() {
        Builder itemController = getBuilder("/app/api/1.0/items/search?name=name1");
        List<Item> items = itemController.get(new GenericType<List<Item>>() {
        });
        Assert.assertNotNull(items);
        Assert.assertEquals(1, items.size());
        Assert.assertEquals(items.get(0).getName(), "name1");
    }

    @Test
    public void testPostItems() {
        Builder itemController = getBuilder("/app/api/1.0/items");
        Item item = new Item();
        item.setName("hello-123");
        item.setState(State.VALID);
        Description description = new Description();
        description.setItem(item);
        description.setText("description-text");
        item.setDescriptions(Collections.singleton(description));
        Entity<Item> entity = Entity.entity(item, MediaType.valueOf(MediaType.APPLICATION_JSON));
        Item response = itemController.post(entity, Item.class);
        Assert.assertEquals(item.getName(), response.getName());
        Assert.assertEquals(item.getState(), response.getState());
        Assert.assertNotNull(item.getDescriptions());
        Assert.assertNotNull(response.getId());
    }

    @Test
    public void testPutItems() {
        Builder itemController = getBuilder("/app/api/1.0/items/1");
        Item item = new Item();
        item.setName("hello-123-UPDATED");
        Entity<Item> entity = Entity.entity(item, MediaType.valueOf(MediaType.APPLICATION_JSON));
        Item response = itemController.put(entity, Item.class);
        Assert.assertEquals(item.getName(), response.getName());
    }

    @Test
    public void testDeleteItems() {
        Builder itemController = getBuilder("/app/api/1.0/items/1");
        Item item = itemController.get(Item.class);
        String string = itemController.delete(String.class);
        Assert.assertTrue(string.contains("OK"));
        Assert.assertTrue(string.contains("200"));
    }

}
