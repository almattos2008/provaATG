package orderaccumulator.application.handler

import orderaccumulator.domain.model.*
import orderaccumulator.domain.usecase.OrderUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
open class OrderHandler(private val orderUseCase: OrderUseCase) {

    fun orderDealerHandler(order: Order): ResponseEntity<OrderResponse>{

        var erros: MutableList<String> = arrayListOf()

        erros = checkErrors(order, erros)

        return if (erros.isEmpty()){
            ResponseEntity.ok().body(orderUseCase.orderDealer(order))
        } else{
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorReturn(erros))
        }

    }

    private fun checkErrors(order: Order, erros: MutableList<String>): MutableList<String>{
        val priceLowComparison = order.preco.compareTo(0.01.toBigDecimal())
        val priceHighComparison = order.preco.compareTo(1000.toBigDecimal())
        if (!enumContains<AtivoEnum>(order.ativo)){
            erros.add("ativo não permitido")
        }
        if (!enumContains<LadoEnum>(order.lado)){
            erros.add("lado não permitido")
        }
        if (order.quantidade<1 || order.quantidade > 100000){
            erros.add("quantidade não permitida")
        }
        if (priceLowComparison==-1 || priceHighComparison==1){
            erros.add("preço não permitido")
        }
        return erros
    }

    private fun buildErrorReturn(erros: MutableList<String>): OrderResponse{
        val errorString = "Erro aconteceu porque "+erros.joinToString(separator = ", ")

        return OrderResponse.buildOrderResponse(false,  null, errorString)

    }

}