package com.epam.esm.model;

import com.epam.esm.domain.GiftCertificate;
import com.epam.esm.domain.Tag;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Maps GiftCertificate from persistence layer to GiftCertificateBusinessModel.
 *
 * @see com.epam.esm.domain.GiftCertificate
 * @see GiftCertificateBusinessModel
 */
@Component
public class GiftCertificateMapper {

    private final TagMapper tagMapper;

    GiftCertificateMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    /**
     * Maps gift certificate and related tags to GiftCertificateBusinessModel.
     *
     * @param tags related to gift certificate
     * @return GiftCertificateBusinessModel
     */
    @Transactional
    public GiftCertificateBusinessModel toGiftCertificateBusinessModel(GiftCertificate giftCertificate, Set<Tag> tags) {
        GiftCertificateBusinessModel giftCertificateBusinessModel = new GiftCertificateBusinessModel();
        giftCertificateBusinessModel.setId(giftCertificate.getId());
        giftCertificateBusinessModel.setName(giftCertificate.getName());
        giftCertificateBusinessModel.setDescription(giftCertificate.getDescription());
        giftCertificateBusinessModel.setPrice(giftCertificate.getPrice());
        giftCertificateBusinessModel.setDuration(giftCertificate.getDuration());
        giftCertificateBusinessModel.setCreateDate(dateAsISO8601(giftCertificate.getCreateDate()));
        giftCertificateBusinessModel.setLastUpdateDate(dateAsISO8601(giftCertificate.getLastUpdateDate()));
        Set<TagBusinessModel> tagsBM = tags.stream().map(tagMapper::toTagBusinessModel).collect(Collectors.toSet());
        giftCertificateBusinessModel.setTags(tagsBM);
        return giftCertificateBusinessModel;
    }

    /**
     * Extracts gift certificate from GiftCertificateBusinessModel.
     *
     * @param certificateBusinessModel
     * @return
     */
    public GiftCertificate extractCertificateFromBusinessModel(GiftCertificateBusinessModel certificateBusinessModel) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateBusinessModel.getId());
        giftCertificate.setName(certificateBusinessModel.getName());
        giftCertificate.setDescription(certificateBusinessModel.getDescription());
        giftCertificate.setPrice(certificateBusinessModel.getPrice());
        giftCertificate.setDuration(certificateBusinessModel.getDuration());
        giftCertificate.setCreateDate(prepareDate(certificateBusinessModel.getCreateDate()));
        giftCertificate.setLastUpdateDate(prepareDate(certificateBusinessModel.getLastUpdateDate()));
        return giftCertificate;
    }

    /**
     * Extracts tags related to gift certificate from GiftCertificateBusinessModel.
     *
     * @param certificateBusinessModel
     * @return
     */
    public Set<Tag> extractTagsFromCertificateBusinessModel(GiftCertificateBusinessModel certificateBusinessModel) {
        return certificateBusinessModel.getTags()
                .stream()
                .map(tagMapper::toTag)
                .collect(Collectors.toSet());
    }

    private String dateAsISO8601(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return formatter.format(localDateTime);
    }

    private LocalDateTime prepareDate(String date) {
        return date == null ? LocalDateTime.now() : LocalDateTime.parse(date);
    }
}
