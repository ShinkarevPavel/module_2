package com.pavelshinkarev.entity;

import com.pavelshinkarev.configuration.SpringDataSourceConfiguration;
import com.pavelshinkarev.dao.impl.GiftCertificateDaoImpl;
import lombok.Builder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

@Builder
public class Main  {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        Tag tag = context.getBean("tag", Tag.class);
        GiftCertificate certificate = context.getBean("certificate", GiftCertificate.class);
//        GiftCertificate giftCertificate = GiftCertificate.builder()
//                .id(2l)
//                .name("Yo")
//                .description("qewrtyu")
//                .price(150)
//                .duration(5)
//                .createDate(LocalDateTime.now())
//                .lastUpdateDate(LocalDateTime.now()).build();
//        GiftCertificateDaoImpl dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);
//        System.out.println(dao.findAll());

//        tag.setId(1L);
//        tag.setName("Pavel");
//        System.out.println(tag);
//        System.out.println(certificate);
//        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
//        System.out.println(jdbcTemplate.toString());

    }
}
