import com.example.pcbuildai.data.remote.dto.FavoritesDto
import com.example.pcbuildai.domain.models.Favorites
import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
fun FavoritesDto.toDomain() = Favorites(
    id = UUID.fromString(id),
    userId = UUID.fromString(userId),
    buildId = UUID.fromString(buildId),
    createdAt = Instant.parse(createdAt)
)
