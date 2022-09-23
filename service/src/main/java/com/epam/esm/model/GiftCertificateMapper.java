package com.epam.esm.model;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maps GiftCertificate from persistence layer to GiftCertificateBusinessModel.
 *
 * @see com.epam.esm.domain.GiftCertificate
 * @see GiftCertificateBusinessModel
 */
@Component
@Transactional
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Maps GiftCertificate to GiftCertificateBusinessModel.
     *
     * @return GiftCertificateBusinessModel
     */
    public GiftCertificateBusinessModel toGiftCertificateBusinessModelWithTags(GiftCertificate giftCertificate) {
        GiftCertificateBusinessModel giftCertificateBusinessModel = new GiftCertificateBusinessModel();
        setBasicFields(giftCertificate, giftCertificateBusinessModel);
        giftCertificateBusinessModel.setTags(extractTags(giftCertificate));
        return giftCertificateBusinessModel;
    }

    /**
     * Maps GiftCertificateBusinessModel to GiftCertificate.
     *
     * @return GiftCertificate
     */
    public GiftCertificate toGiftCertificateEntityModel(GiftCertificateBusinessModel certificateBusinessModel) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateBusinessModel.getId());
        giftCertificate.setName(certificateBusinessModel.getName());
        giftCertificate.setDescription(certificateBusinessModel.getDescription());
        giftCertificate.setPrice(certificateBusinessModel.getPrice());
        giftCertificate.setDuration(certificateBusinessModel.getDuration());
        giftCertificate.setCreateDate(prepareDate(certificateBusinessModel.getCreateDate()));
        giftCertificate.setLastUpdateDate(prepareDate(certificateBusinessModel.getLastUpdateDate()));
        giftCertificate.setTags(new HashSet<>(extractTags(certificateBusinessModel)));
        return giftCertificate;
    }


    private Set<Tag> extractTags(GiftCertificateBusinessModel certificateBusinessModel) {
        return certificateBusinessModel.getTags()
                .stream()
                .map(tagMapper::toTag)
                .collect(Collectors.toSet());
    }

    private Set<TagBusinessModel> extractTags(GiftCertificate giftCertificate) {
        return giftCertificate.getTags()
                .stream()
                .map(tagMapper::toTagBusinessModel)
                .collect(Collectors.toSet());
    }

    private String dateAsISO8601(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return formatter.format(localDateTime);
    }

    private LocalDateTime prepareDate(String date) {
        return date == null ? LocalDateTime.now() : LocalDateTime.parse(date);
    }

    public GiftCertificateBusinessModel toGiftCertificateBusinessModel(GiftCertificate giftCertificate) {
        GiftCertificateBusinessModel giftCertificateBusinessModel = new GiftCertificateBusinessModel();
        setBasicFields(giftCertificate, giftCertificateBusinessModel);
        return giftCertificateBusinessModel;
    }

    private void setBasicFields(GiftCertificate giftCertificate, GiftCertificateBusinessModel giftCertificateBusinessModel) {
        giftCertificateBusinessModel.setId(giftCertificate.getId());
        giftCertificateBusinessModel.setName(giftCertificate.getName());
        giftCertificateBusinessModel.setDescription(giftCertificate.getDescription());
        giftCertificateBusinessModel.setPrice(giftCertificate.getPrice());
        giftCertificateBusinessModel.setDuration(giftCertificate.getDuration());
        giftCertificateBusinessModel.setCreateDate(dateAsISO8601(giftCertificate.getCreateDate()));
        giftCertificateBusinessModel.setLastUpdateDate(dateAsISO8601(giftCertificate.getLastUpdateDate()));
    }
}
