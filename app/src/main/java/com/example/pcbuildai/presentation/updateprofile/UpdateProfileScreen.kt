import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.updateprofile.UpdateProfileViewModel

@Composable
fun UpdateProfileScreen(
    viewModel: UpdateProfileViewModel = hiltViewModel(),
    profile: Profile,
    userId: String,
    onDone: () -> Unit
) {
    LaunchedEffect(profile) {
        viewModel.init(profile)
    }

    val state = viewModel.state

    Column {
        TextField(state.name, onValueChange = viewModel::updateName, label = { Text("Name") })
        TextField(state.surname, onValueChange = viewModel::updateSurname, label = { Text("Surname") })
        TextField(state.phoneNumber, onValueChange = viewModel::updatePhone, label = { Text("Phone") })

        Button(onClick = { viewModel.saveChanges(userId) }) {
            if (state.isSaving) Text("Saving...")
            else Text("Save")
        }

        if (state.isSuccess) {
            Text("Saved!")
            onDone()
        }

        state.error?.let { Text("Error: $it") }
    }
}
