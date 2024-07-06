package orderaccumulator.domain.repository

import orderaccumulator.domain.model.Order

interface ExposicaoFinanceiraRepository {

    fun retrieveOrders(ativo: String): List<Order?>
    fun saveOrder(order: Order): Long

}