import com.example.pcbuildai.domain.models.User
import com.example.pcbuildai.data.remote.dto.AuthResponse
import java.util.UUID

fun AuthResponse.toDomain(): User {
    val userId = this.user?.id ?: throw Exception("User not found in response")
    val email = this.user.email ?: ""
    return User(id = UUID.fromString(userId), email = email)
}
