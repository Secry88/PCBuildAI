import com.example.pcbuildai.data.remote.dto.FavoritesDto
import com.example.pcbuildai.domain.models.Favorites
import kotlinx.datetime.Instant
import java.util.UUID

@OptIn(kotlin.time.ExperimentalTime::class)
fun FavoritesDto.toDomain() = Favorites(
    id = id?.let { UUID.fromString(it) },
    userId = userId?.let { UUID.fromString(it) },
    buildId = buildId?.let { UUID.fromString(it) },
    createdAt = createdAt?.let { Instant.parse(it) }
)

