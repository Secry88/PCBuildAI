import com.example.pcbuildai.data.remote.dto.BuildDto
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
fun BuildDto.toDomain() = Build(
    id = UUID.fromString(id),
    userId = UUID.fromString(userId),
    createdAt = Instant.parse(createdAt),
    comment = comment,
    totalPrice = totalPrice
)

fun List<BuildDto>.toDomainList(
    componentsMap: Map<String, List<Components>>
): List<Build> {
    return this.map { dto ->
        val components = componentsMap[dto.id] ?: emptyList()
        dto.toDomain()
    }
}
