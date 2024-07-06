package orderaccumulator.application.controller

import orderaccumulator.application.handler.OrderHandler
import orderaccumulator.domain.model.Order
import orderaccumulator.domain.model.OrderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController("")
class OrderController(private val orderHandler: OrderHandler) {
        @PostMapping("/order")
        fun orderDealer(@RequestBody order: Order): OrderResponse {
                return orderHandler.orderDealerHandler(order)
        }

        @GetMapping("/healthCheck")
        fun healthCheck(): String {
                return "SUCESSO"
        }

//        @PostMapping("/order")
//        fun orderDealer(@RequestBody order: Order): ResponseEntity<OrderResponse> {
//                return orderHandler.orderDealerHandler(order)
//        }


}