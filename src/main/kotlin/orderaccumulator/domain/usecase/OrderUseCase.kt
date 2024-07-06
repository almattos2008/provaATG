package orderaccumulator.domain.usecase

import orderaccumulator.domain.model.Order
import orderaccumulator.domain.model.OrderResponse
import orderaccumulator.domain.repository.ExposicaoFinanceiraRepository
import java.math.BigDecimal

class OrderUseCase(private val exposicaoFinanceiraRepository : ExposicaoFinanceiraRepository) {

    fun orderDealer(newOrder: Order): OrderResponse {
        val orders = exposicaoFinanceiraRepository.retrieveOrders(newOrder.ativo)
        val exposicaoFinanceira = financialExposureCalculator(orders, newOrder)
        val exposicaoFinanceiraIndicator = financialExposureDecider(exposicaoFinanceira)

        return if(exposicaoFinanceiraIndicator){
            OrderResponse.buildOrderResponse(false, exposicaoFinanceira,  "Erro aconteceu porque a exposição financeira é maior que a permitida")
        }else{
            exposicaoFinanceiraRepository.saveOrder(newOrder)
            OrderResponse.buildOrderResponse(true, exposicaoFinanceira)

        }

    }

    private fun financialExposureCalculator(orders: List<Order?>, newOrder: Order):BigDecimal{
        var somaCompra = BigDecimal.ZERO
        var somaVenda = BigDecimal.ZERO
        var exposicaoFinanceira = BigDecimal.ZERO
            orders.forEach(){order->
                order?.let{
                    if(order.lado == "C"){
                        somaCompra += (order.preco.multiply(order.quantidade.toBigDecimal()))
                    } else if(order.lado == "V"){
                        somaVenda += (order.preco.multiply(order.quantidade.toBigDecimal()))
                    }
                }
        }
        exposicaoFinanceira = somaCompra.subtract(somaVenda)
        if (newOrder.lado=="C"){
            exposicaoFinanceira = exposicaoFinanceira.add(newOrder.preco.multiply(newOrder.quantidade.toBigDecimal()))
        } else if(newOrder.lado=="V"){
            exposicaoFinanceira = exposicaoFinanceira.subtract(newOrder.preco.multiply(newOrder.quantidade.toBigDecimal()))
        }
        return exposicaoFinanceira
    }

    private fun financialExposureDecider(exposicaoFinanceira:BigDecimal): Boolean{
        return Math.abs(exposicaoFinanceira.toFloat()) > 1000F
    }

}