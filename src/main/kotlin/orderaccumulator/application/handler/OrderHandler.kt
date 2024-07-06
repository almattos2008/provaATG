package orderaccumulator.application.handler

import orderaccumulator.domain.model.Order
import orderaccumulator.domain.model.OrderResponse
import orderaccumulator.domain.usecase.OrderUseCase

open class OrderHandler(private val orderUseCase: OrderUseCase) {

    fun orderDealerHandler(order: Order): OrderResponse{

        var erros: MutableList<String> = arrayListOf()

        erros = checkErrors(order, erros)

        return if (erros.isEmpty()){
            orderUseCase.orderDealer(order)
        } else{
            buildErrorReturn(erros)
        }

    }

    private fun checkErrors(order: Order, erros: MutableList<String>): MutableList<String>{
        val priceLowComparison = order.preco.compareTo(0.01.toBigDecimal())
        val priceHighComparison = order.preco.compareTo(1000.toBigDecimal())
        if (order.ativo == "PETR4" || order.ativo == "VALE3" || order.ativo == "VIIA4"){

        }else{
            erros.add("Ativo não permitido")
        }
        if (order.lado == "C" || order.lado == "V"){

        }else{
            erros.add("Lado não permitido")
        }
        if (order.quantidade<1 || order.quantidade > 100000){
            erros.add("Quantidade não permitida")
        }
        if (priceLowComparison==-1 || priceHighComparison==1){
            erros.add("Preço não permitido")
        }
        return erros
    }

    private fun buildErrorReturn(erros: MutableList<String>): OrderResponse{
        val errorString = "Erro aconteceu porque "+erros.joinToString(separator = ", ")

        return OrderResponse.buildOrderResponse(false,  null, errorString)

    }

}