package orderaccumulator.infrastructure

import orderaccumulator.domain.repository.ExposicaoFinanceiraRepository
import orderaccumulator.domain.model.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class ExposicaoFinanceiraDataBaseAdapter(
    private val jdbcTemplate: JdbcTemplate
): ExposicaoFinanceiraRepository {

    private class ExposicaoFinanceiraRowMapper: RowMapper<Order?>{
        override fun mapRow(rs: ResultSet, rowNum: Int): Order{
            return Order.fromResultSet(rs)
        }
    }

    override fun retrieveOrders(ativo: String): MutableList<Order?> {
       val query = """SELECT * FROM exposicao_financeira
           |WHERE ativo = '$ativo'
       """.trimMargin()


        return jdbcTemplate.query(query, ExposicaoFinanceiraRowMapper())
    }

    override fun saveOrder(order: Order): Long {
        val query = (
                """INSERT INTO exposicao_financeira (
                    |ativo,
                    |lado,
                    |quantidade,
                    |preco
                    |)
                    |VALUES (?,?,?,?)""".trimMargin()
                )
        val id = Array(1) { "id"}
        val keyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            {
            val ps = it.prepareStatement(query, id)
            ps.setString(1, order.ativo)
            ps.setString(2, order.lado)
            ps.setInt(3, order.quantidade)
            ps.setBigDecimal(4, order.preco)
            ps
        },
            keyHolder
        )
        return keyHolder.key!!.toLong()
    }
}