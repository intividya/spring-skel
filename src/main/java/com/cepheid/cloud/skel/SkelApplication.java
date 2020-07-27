package com.cepheid.cloud.skel;

import com.cepheid.cloud.skel.controller.ItemController;
import com.cepheid.cloud.skel.model.Description;
import com.cepheid.cloud.skel.model.Item;
import com.cepheid.cloud.skel.model.enums.State;
import com.cepheid.cloud.skel.repository.DescriptionRepository;
import com.cepheid.cloud.skel.repository.ItemRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication(scanBasePackageClasses = {ItemController.class, SkelApplication.class})
@EnableJpaRepositories(basePackageClasses = {ItemRepository.class})
@EnableJpaAuditing
public class SkelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkelApplication.class, args);
    }

    @Bean
    ApplicationRunner initItems(ItemRepository itemRepository, DescriptionRepository descriptionRepository) {
        return args -> {
            itemRepository.saveAll(Stream.of("name1", "Lord of the rings", "Hobbit", "Silmarillion", "Unfinished Tales and The History of Middle-earth")
                    .map(name -> {
                        Item item = new Item();
                        item.setDescriptions(IntStream.range(0, 3).mapToObj(i -> {
                            Description description = new Description();
                            description.setText("Description-" + i + ": " + name);
                            description.setItem(item);
                            return description;
                        }).collect(Collectors.toSet()));
                        item.setName(name);
                        item.setState(State.VALID);
                        return item;
                    }).collect(Collectors.toList()));
            itemRepository.findAll().forEach(System.out::println);
        };
    }

}
