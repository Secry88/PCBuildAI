import com.example.pcbuildai.data.remote.dto.ProfileDto
import com.example.pcbuildai.domain.models.Profile
import java.util.UUID

fun ProfileDto.toDomain(email: String) = Profile(
    id = UUID.fromString(id),
    name = name,
    surname = surname,
    avatar = avatar,
    phoneNumber = phoneNumber,
    email = email
)