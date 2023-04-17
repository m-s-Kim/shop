package com.shop.entity;

import com.shop.constant.Orderstatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private Orderstatus orderstatus;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime regTime;
    private LocalDateTime updateTime;


}
