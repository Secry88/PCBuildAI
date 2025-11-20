import com.example.pcbuildai.Data.Remote.Dto.ProfileDto
import com.example.pcbuildai.Domain.Models.Profile
import java.util.UUID

fun ProfileDto.toDomain(email: String) = Profile(
    id = UUID.fromString(id),
    name = name,
    surname = surname,
    avatar = avatar,
    phoneNumber = phoneNumber,
    email = email
)