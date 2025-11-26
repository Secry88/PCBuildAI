import com.example.pcbuildai.Data.Remote.Dto.FavoritesDto
import com.example.pcbuildai.Domain.Models.Favorites
import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
fun FavoritesDto.toDomain() = Favorites(
    id = UUID.fromString(id),
    userId = UUID.fromString(userId),
    buildId = UUID.fromString(buildId),
    createdAt = Instant.parse(createdAt)
)
