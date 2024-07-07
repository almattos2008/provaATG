package orderaccumulator.application.controller

import orderaccumulator.application.handler.OrderHandler
import orderaccumulator.domain.model.Order
import orderaccumulator.domain.model.OrderResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController("")
class OrderController(private val orderHandler: OrderHandler) {
        @CrossOrigin(origins = ["*"])
        @PostMapping("/order")
        fun orderDealer(@RequestBody order: Order): ResponseEntity<OrderResponse> {
                return orderHandler.orderDealerHandler(order)
        }

        @CrossOrigin(origins = ["*"])
        @GetMapping("/healthCheck")
        fun healthCheck(): ResponseEntity<String> {
                return  ResponseEntity.ok().body("{}")
        }

//        @PostMapping("/order")
//        fun orderDealer(@RequestBody order: Order): ResponseEntity<OrderResponse> {
//                return orderHandler.orderDealerHandler(order)
//        }


}