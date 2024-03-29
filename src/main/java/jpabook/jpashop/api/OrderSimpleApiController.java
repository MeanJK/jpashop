package jpabook.jpashop.api;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*
* X To One (Many To One, One To One)
* Order
* Order -> Member
* Order -> Delivery
*/

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all){
            order.getMember().getName(); // Lazy 강제 초기화
            order.getMember().getAddress(); // Lazy 강제 초기화 해서 문제가 있다.
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){ // 원래는 List가 아닌 result로 감싸야함.
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;

    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4(){
        return orderSimpleQueryRepository.findOrderDtos();
    }


    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName(); //LAZY 초기화 -> memberId를 갖고 영속성 컨텍스트를 찾아본 후 없다면 db에 쿼리를 날림
            orderDate = order.getOrderDate();    // 즉-> 많은 쿼리를 날리므로 시간이 오래걸린다 효율 -
            orderStatus = order.getOrderStatus();
            address = order.getDelivery().getAddress();
        }
    }

}
