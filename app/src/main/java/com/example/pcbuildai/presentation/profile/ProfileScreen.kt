import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pcbuildai.domain.models.Profile
import com.example.pcbuildai.presentation.profile.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    userId: String,
    onNavigateToUpdateProfile: (Profile) -> Unit,
) {
    val state = viewModel.state

    val shouldRefresh by viewModel.shouldRefresh.observeAsState()
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh == true) {
            viewModel.loadProfile(userId)
            viewModel.onRefreshConsumed()
        }
    }


    LaunchedEffect(Unit) {
        viewModel.loadProfile(userId)
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }
        state.profile != null -> {
            ProfileContent(
                profile = state.profile,
                onUpdateClick = {onNavigateToUpdateProfile (state.profile)}
            )
        }
    }
}

@Composable
fun ProfileContent(
    profile: Profile,
    onUpdateClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "PCBuildAI",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Профиль",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileField(label = "Имя", value = profile.name ?: "")
        Spacer(modifier = Modifier.height(12.dp))

        ProfileField(label = "Фамилия", value = profile.surname ?: "")
        Spacer(modifier = Modifier.height(12.dp))

        ProfileField(label = "Почта", value = profile.email)
        Spacer(modifier = Modifier.height(12.dp))

        ProfileField(label = "Телефон", value = profile.phoneNumber ?: "")
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Изменить данные",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProfileField(label: String, value: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        label = { Text(label) }
    )
}
