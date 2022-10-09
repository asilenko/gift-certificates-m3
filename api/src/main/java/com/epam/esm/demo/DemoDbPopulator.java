package com.epam.esm.demo;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.domain.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.GiftCertificateModel;
import com.epam.esm.model.TagModel;
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

    private static final int ROWS = 1000;
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
            Set<String> tagNames = generateTagNames();
            List<TagModel> tags = insertTags(tagService, tagNames);
            List<GiftCertificateModel> certificates = insertCertificates(giftCertificateService, tags);
            insertUsers(userDAO, dataFactory);
            placeOrders(orderService, certificates);
        };
    }

    private Set<String> generateTagNames() {
        Set<String> words = new HashSet<>();
        while (words.size() < ROWS) {
            String word = wordFactory.getRandomWord();
            words.add(word);
        }
        return words;
    }

    private List<TagModel> insertTags(TagService tagService, Set<String> names) {
        List<TagModel> tags = new ArrayList<>();
        TagModel tag = new TagModel();
        for (String word : names) {
            tag.setName(word);
            tags.add(tagService.create(tag));
        }
        return tags;
    }

    private List<GiftCertificateModel> insertCertificates(GiftCertificateService certificateService, List<TagModel> tags) {
        List<GiftCertificateModel> certificates = new ArrayList<>();
        GiftCertificateModel certificate = new GiftCertificateModel();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < ROWS * 10; i++) {
            certificate.setName(wordFactory.getSentence(2));
            certificate.setDescription(wordFactory.getSentence(random.nextInt(7) + 3));
            certificate.setPrice(BigDecimal.valueOf(Math.random() * 500));
            certificate.setDuration(random.nextInt(360) + 10);
            LocalDateTime createDate = now.minusHours(random.nextInt(15000) + 3600);
            certificate.setCreateDate(createDate.toString());
            LocalDateTime updateDate = now.minusHours(random.nextInt(3599));
            certificate.setLastUpdateDate(updateDate.toString());
            Set<TagModel> tagsForCertificate = getRandomTags(tags);
            certificate.setTags(tagsForCertificate);
            certificates.add(certificateService.create(certificate));
        }
        return certificates;
    }

    private Set<TagModel> getRandomTags(List<TagModel> tags) {
        Set<TagModel> randomTags = new HashSet<>();
        for (int i = 0; i < random.nextInt(3) + 3; i++) {
            randomTags.add(tags.get(random.nextInt(tags.size())));
        }
        return randomTags;
    }

    private void insertUsers(UserDAO userDAO, DataFactory dataFactory) {
        for (int i = 0; i < ROWS; i++) {
            User user = new User();
            user.setName(dataFactory.getName());
            userDAO.create(user);
        }
    }

    private void placeOrders(OrderService orderService,
                             List<GiftCertificateModel> certificates)
            throws ResourceNotFoundException, InvalidFieldValueException {
        for (GiftCertificateModel certificate : certificates) {
            long userId = random.nextInt(ROWS) + 1L;
            orderService.placeOrder(userId, certificate.getId());
        }
    }
}
