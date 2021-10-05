package com.epam.esm.entity;

import com.epam.esm.configuration.SpringDataSourceConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.SqlQueryBuilder;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.dao.rowmapper.GiftCertificateMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringDataSourceConfiguration.class);
        GiftCertificateDao dao = context.getBean("giftCertificateDaoImpl", GiftCertificateDaoImpl.class);

        String tagName = "JAVA";
        String part = "java";
        List<String> fields = new ArrayList<>();
//        fields.add("gc.name");
//        fields.add("gc.duration");
        List<String> sort = Arrays.asList("DESC");

        System.out.println(dao.findByNameOrDescription(tagName, null, fields, sort));

//        System.out.println(dao.findAll());
//        Map<String, Object> map = new HashMap<>();
//        SqlQueryBuilder queryBuilder = new SqlQueryBuilder();
//        map.put(GiftCertificateMapper.PRICE, 500);
//        map.put(GiftCertificateMapper.LUST_UPDATE_DATE, LocalDateTime.now());
//        map.put(GiftCertificateMapper.NAME, "SHow");
//        map.put(GiftCertificateMapper.DESCRIPTION, "Slipknot");

//        System.out.println(queryBuilder.buildQueryForUpdate(map));
////        TagDao tagDao = context.getBean("tagDaoImpl", TagDao.class);
//        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")));
//        LocalDateTime dateTime = LocalDateTime.now();
//        String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
//        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
//        System.out.println(dateTime.format(format));

//
//        Map<String, String> map = new HashMap<>();
//        List<String> mappa = map.keySet().stream().collect(Collectors.toList());
//        Set<String> strings = new HashSet<>();
//        strings.add("name");
//        strings.add("id");
//        strings.add("description");
//
//        List<String> stringList = strings.stream().toList();
//        System.out.println(stringList.get(1));

    }
}
