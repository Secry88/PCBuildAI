import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryInsertDto(
    @SerialName("build_id")
    val buildId: String,

    @SerialName("user_id")
    val userId: String,

    @SerialName("search_budget")
    val searchBudget: Float? = null
)
