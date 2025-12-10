import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: String
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.loadProfile(userId)
    }

    when {
        state.isLoading -> Text("Loading...")
        state.error != null -> Text("Error: ${state.error}")
        state.profile != null -> ProfileContent(state.profile)
    }
}

@Composable
fun ProfileContent(profile: Profile) {
    Column {
        Text("Name: ${profile.name}")
        Text("Surname: ${profile.surname}")
        Text("Email: ${profile.email}")
    }
}
