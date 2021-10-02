package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateFields;
import com.epam.esm.entity.GiftCertificate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OnNullChecker {
    public static Map<String,Object> fieldCheck(GiftCertificate giftCertificate) {
        Map<String, Object> notNullField = new HashMap<>();

        if (giftCertificate.getName() != null) {
            notNullField.put(GiftCertificateFields.NAME, giftCertificate.getName());
        }

        if (giftCertificate.getDescription() != null) {
            notNullField.put(GiftCertificateFields.DESCRIPTION, giftCertificate.getDescription());
        }
        if (giftCertificate.getPrice() != null) {
            notNullField.put(GiftCertificateFields.PRICE, giftCertificate.getPrice());
        }
        if (giftCertificate.getDuration() != null) {
            notNullField.put(GiftCertificateFields.DESCRIPTION, giftCertificate.getDuration());
        }
        notNullField.put(GiftCertificateFields.LUST_UPDATE_DATE, LocalDateTime.now());
        return notNullField;
    }
}
