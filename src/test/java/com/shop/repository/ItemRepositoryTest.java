package com.shop.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import com.shop.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품저장 테스트")
    public void createItemTest(){
        for(int i =0; i<10;i++){
            Item item = new Item();

            item.setItemNm("test Product"+i);
            item.setPrice(1000000+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트상품 설명"+i);
            item.setStockNumber(100+i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
            System.out.println(savedItem.toString());
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNm("test Product2");
        for(Item item : itemList){
            System.out.println(item.toString());
        }

    }
    @Test
    @DisplayName("상품명 상품명 상세 조회")
    public void findByItemNmOrItemDetail(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("test Product2","테스트상품 설명2");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품가격 테스트")
    public void findByPriceLessThan(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThan(1000003);
        for(Item item : itemList){
            System.out.println(item.toString());
        }

    }

    @Test
    @DisplayName("상품가격 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(1000004);
        for(Item item : itemList){
            System.out.println(item.toString());
        }

    }

    @Test
    @DisplayName("@query를 이용한 조회")
    public void findByItemDetailTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetail("테스트상품 설명6");

        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("NatvieQuery를 이용한 조회")
    public void findByItemDetailByNative(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemDetailByNative("테스트상품 설명4");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }


    @Test
    @DisplayName("Querydsl 조회")
    public void queryDslTest(){
        this.createItemTest();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%"+"테스트"+"%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();
        for(Item item: itemList){
            System.out.println(item.toString());
        }

    }

   public void createItemList2(){
        for(int i=0;i<5;i++){
            Item item = new Item();
            item.setItemNm("test Product"+i);
            item.setPrice(1000000+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setItemDetail("테스트상품 설명"+i);
            item.setStockNumber(100+i);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }

       for(int i=5;i<10;i++){
           Item item = new Item();
           item.setItemNm("test Product"+i);
           item.setPrice(1000000+i);
           item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
           item.setItemDetail("테스트상품 설명"+i);
           item.setStockNumber(0);
           item.setRegTime(LocalDateTime.now());
           item.setUpdateTime(LocalDateTime.now());
           Item savedItem = itemRepository.save(item);
       }

   }


    @Test
    @DisplayName("Querydsl 조회 2")
    public void queryDsl2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem qItem = QItem.item;

        String itemDetail = "테스트상품 설명";
        int price = 1000002;
        String itemSellStat = "SELL";

        booleanBuilder.and(qItem.itemDetail.like("%"+itemDetail+"%"));
        booleanBuilder.and(qItem.price.gt(price));

       if(StringUtils.equals(itemSellStat,ItemSellStatus.SELL)){
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
        }

        Pageable pageable = PageRequest.of(0,5);
        Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder,pageable);
        for(Item item : itemPagingResult){
            System.out.println(item.toString());
        }


    }

}