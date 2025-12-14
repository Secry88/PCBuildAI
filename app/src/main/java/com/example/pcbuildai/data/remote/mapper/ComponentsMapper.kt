import com.example.pcbuildai.data.remote.dto.ComponentsDto
import com.example.pcbuildai.domain.models.Components
import java.util.UUID

fun ComponentsDto.toDomain() = Components(
    id = UUID.fromString(id),
    typeId = typeId,
    name = name,
    price = price,
    description = description
)

fun List<ComponentsDto>.toDomainList() = this.map { it.toDomain() }
