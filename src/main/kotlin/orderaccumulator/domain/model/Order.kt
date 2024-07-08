package orderaccumulator.domain.model

import java.math.BigDecimal
import java.sql.ResultSet

data class Order(
    var ativo: String,
    var lado: String,
    var quantidade: Int,
    var preco: BigDecimal
)  {
    companion object{
        fun fromResultSet(rs: ResultSet) = Order(
            rs.getString("ativo"),
            rs.getString("lado"),
            rs.getInt("quantidade"),
            rs.getBigDecimal("preco")
        )
    }
}


data class OrderResponse(
    var sucesso: String,
    var exposicao_atual: BigDecimal? = null,
    var msg_erro: String? = null
)  {
    companion object{
        fun buildOrderResponse(sucesso: Boolean, exposicao_atual: BigDecimal? = null, msg_erro: String? = null): OrderResponse{
            return OrderResponse(
            sucesso = sucesso.toString(),
            exposicao_atual = exposicao_atual,
            msg_erro = msg_erro
            )
        }
    }
}

enum class AtivoEnum{
    PETR4,VALE3,VIIA4
}

enum class LadoEnum{
    C,V
}

inline fun <reified T : Enum<T>> enumContains(name: String): Boolean {
    return enumValues<T>().any { it.name == name}
}