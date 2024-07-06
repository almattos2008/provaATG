package orderaccumulator.application.controller

import orderaccumulator.application.handler.OrderHandler
import orderaccumulator.domain.model.Order
import orderaccumulator.domain.model.OrderResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController("orderaccumulator")
class OrderController(private val orderHandler: OrderHandler) {
        @GetMapping("/order")
        fun orderDealer(order: Order): OrderResponse {
                return orderHandler.orderDealerHandler(order)
        }


}