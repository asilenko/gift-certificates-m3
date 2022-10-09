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
 * Maps GiftCertificate from persistence layer to GiftCertificateModel.
 *
 * @see com.epam.esm.domain.GiftCertificate
 * @see GiftCertificateModel
 */
@Component
@Transactional
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Maps GiftCertificate to GiftCertificateModel.
     *
     * @return GiftCertificateModel
     */
    public GiftCertificateModel toGiftCertificateBusinessModelWithTags(GiftCertificate giftCertificate) {
        GiftCertificateModel giftCertificateModel = new GiftCertificateModel();
        setBasicFields(giftCertificate, giftCertificateModel);
        giftCertificateModel.setTags(extractTags(giftCertificate));
        return giftCertificateModel;
    }

    /**
     * Maps GiftCertificateModel to GiftCertificate.
     *
     * @return GiftCertificate
     */
    public GiftCertificate toGiftCertificateEntityModel(GiftCertificateModel certificateModel) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateModel.getId());
        giftCertificate.setName(certificateModel.getName());
        giftCertificate.setDescription(certificateModel.getDescription());
        giftCertificate.setPrice(certificateModel.getPrice());
        giftCertificate.setDuration(certificateModel.getDuration());
        giftCertificate.setCreateDate(prepareDate(certificateModel.getCreateDate()));
        giftCertificate.setLastUpdateDate(prepareDate(certificateModel.getLastUpdateDate()));
        giftCertificate.setTags(new HashSet<>(extractTags(certificateModel)));
        return giftCertificate;
    }


    private Set<Tag> extractTags(GiftCertificateModel certificateModel) {
        return certificateModel.getTags()
                .stream()
                .map(tagMapper::toTag)
                .collect(Collectors.toSet());
    }

    private Set<TagModel> extractTags(GiftCertificate giftCertificate) {
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

    public GiftCertificateModel toGiftCertificateBusinessModel(GiftCertificate giftCertificate) {
        GiftCertificateModel giftCertificateModel = new GiftCertificateModel();
        setBasicFields(giftCertificate, giftCertificateModel);
        return giftCertificateModel;
    }

    private void setBasicFields(GiftCertificate giftCertificate, GiftCertificateModel giftCertificateModel) {
        giftCertificateModel.setId(giftCertificate.getId());
        giftCertificateModel.setName(giftCertificate.getName());
        giftCertificateModel.setDescription(giftCertificate.getDescription());
        giftCertificateModel.setPrice(giftCertificate.getPrice());
        giftCertificateModel.setDuration(giftCertificate.getDuration());
        giftCertificateModel.setCreateDate(dateAsISO8601(giftCertificate.getCreateDate()));
        giftCertificateModel.setLastUpdateDate(dateAsISO8601(giftCertificate.getLastUpdateDate()));
    }
}
