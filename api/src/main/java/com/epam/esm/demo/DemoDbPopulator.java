package com.epam.esm.demo;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateBusinessModel;
import com.epam.esm.model.TagBusinessModel;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.InvalidFieldValueException;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@Profile("demo")
class DemoDbPopulator {


    @Autowired
    private WordFactory wordFactory;
    Random random = new Random();


    @Bean
    CommandLineRunner createDemoData(TagService tagService,
                                     UserDAO userDAO,
                                     GiftCertificateService giftCertificateService,
                                     OrderService orderService) {
        return args -> {
            DataFactory dataFactory = new DataFactory();
            int numberOfUsers = 1000;

            Set<String> tagNames = generateTagNames(1000);
            List<TagBusinessModel> tags = insertTags(tagService, tagNames);
            List<GiftCertificateBusinessModel> certificates = insertCertificates(giftCertificateService, tags);
            insertUsers(userDAO, dataFactory, numberOfUsers);
            placeOrders(orderService, certificates,numberOfUsers);
        };
    }

    private Set<String> generateTagNames(int numberOfNames) {
        Set<String> words = new HashSet<>();
        while (words.size() < numberOfNames) {
            String word = wordFactory.getRandomWord();
            words.add(word);
        }
        return words;
    }

    private List<TagBusinessModel> insertTags(TagService tagService, Set<String> names) {
        List<TagBusinessModel> tags = new ArrayList<>();
        TagBusinessModel tag = new TagBusinessModel();
        for (String word : names) {
            tag.setName(word);
            tags.add(tagService.create(tag));
        }
        return tags;
    }

    private List<GiftCertificateBusinessModel> insertCertificates(GiftCertificateService giftCertificateService, List<TagBusinessModel> tags) {
        List<GiftCertificateBusinessModel> certificates = new ArrayList<>();
        GiftCertificateBusinessModel certificate = new GiftCertificateBusinessModel();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 10000; i++) {
            certificate.setName(wordFactory.getSentence(2));
            certificate.setDescription(wordFactory.getSentence(random.nextInt(7) + 3));
            certificate.setPrice(BigDecimal.valueOf(Math.random() * 500));
            certificate.setDuration(random.nextInt(360) + 10);
            LocalDateTime createDate = now.minusHours(random.nextInt(15000) + 3600);
            certificate.setCreateDate(createDate.toString());
            LocalDateTime updateDate = now.minusHours(random.nextInt(3599));
            certificate.setLastUpdateDate(updateDate.toString());
            Set<TagBusinessModel> tagsForCertificate = getRandomTags(tags);
            certificate.setTags(tagsForCertificate);
            certificates.add(giftCertificateService.create(certificate));
        }
        return certificates;
    }

    private Set<TagBusinessModel> getRandomTags(List<TagBusinessModel> tags) {
        Set<TagBusinessModel> randomTags = new HashSet<>();
        for (int i = 0; i < random.nextInt(3) + 3; i++) {
            randomTags.add(tags.get(random.nextInt(tags.size())));
        }
        return randomTags;
    }

    private void insertUsers(UserDAO userDAO, DataFactory dataFactory, int numberOfUsers) {
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User();
            user.setName(dataFactory.getName());
            userDAO.create(user);
        }
    }

    private void placeOrders(OrderService orderService,
                             List<GiftCertificateBusinessModel> certificates,
                             int numberOfUsers)
            throws ResourceNotFoundException, InvalidFieldValueException {
        for (GiftCertificateBusinessModel certificate : certificates) {
            long userId = random.nextInt(numberOfUsers) + 1L;
            orderService.placeOrder(userId, certificate.getId());
        }
    }
}
