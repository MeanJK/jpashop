package jpabook.jpashop.api;


import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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
}
