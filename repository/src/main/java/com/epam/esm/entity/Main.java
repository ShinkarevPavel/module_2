package com.epam.esm.entity;

import com.epam.esm.configuration.SpringDataSourceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import lombok.Builder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Main  {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        GiftCertificateDao dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);


        Optional<String> s = null;
//        Tag tag1 = new Tag();
//        tag1.setName("Epam");
//        Tag tag2 = new Tag();
//        tag2.setName("Java");
//        Tag tag3 = new Tag();
//        tag3.setName("IT");
//        List<Tag> tags = new ArrayList<>();
//        tags.add(tag1);
//        tags.add(tag2);
//        tags.add(tag3);
////
//        GiftCertificate certificate = GiftCertificate.builder()
//                .name("EpamCertificate")
//                .description("Cool gift")
//                .price(850)
//                .duration(10)
//                .createDate(LocalDateTime.now())
//                .lastUpdateDate(LocalDateTime.now())
//                .tags(tags).build();
//        dao.create(certificate);
//        System.out.println(certificate);

//        System.out.println(dao.findById(1).get());

    }
}
