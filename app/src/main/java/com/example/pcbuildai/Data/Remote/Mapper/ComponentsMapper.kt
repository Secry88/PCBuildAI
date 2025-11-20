import com.example.pcbuildai.Data.Remote.Dto.ComponentsDto
import com.example.pcbuildai.Domain.Models.Components
import java.util.UUID

fun ComponentsDto.toDomain() = Components(
    id = UUID.fromString(id),
    type = type,
    name = name,
    price = price,
    description = description
)

fun List<ComponentsDto>.toDomainList() = this.map { it.toDomain() }
