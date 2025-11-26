import com.example.pcbuildai.data.remote.dto.AuthResponse
import com.example.pcbuildai.domain.models.User
import java.util.UUID

fun AuthResponse.toDomain(): User{
    return User(
        id = UUID.fromString(user.id),
        email = user.email
    )
}