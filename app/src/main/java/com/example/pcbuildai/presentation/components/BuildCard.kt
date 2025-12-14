// presentation/main/components/BuildCard.kt
package com.example.pcbuildai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pcbuildai.domain.models.Build
import com.example.pcbuildai.domain.models.Components
import java.text.DecimalFormat

@Composable
fun BuildCard(
    build: Build,
    components: List<Components>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Заголовок с комментарием
            Text(
                text = "💻 ${build.comment}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Список компонентов
            Text(
                text = "Компоненты:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            components.forEachIndexed { index, component ->
                ComponentItem(
                    component = component,
                    isLast = index == components.size - 1
                )
            }

            // Разделитель
            Spacer(modifier = Modifier.height(16.dp))
            Divider(modifier = Modifier.fillMaxWidth())

            // Итоговая стоимость
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Общая стоимость:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "${formatPrice(build.totalPrice)} ₽",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ComponentItem(
    component: Components,
    isLast: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = component.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            if (component.description.isNotBlank()) {
                Text(
                    text = component.description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${formatPrice(component.price)} ₽",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF2E7D32) // Зелёный цвет для цены
        )
    }

    if (!isLast) {
        Divider(
            modifier = Modifier.padding(vertical = 4.dp),
            thickness = 0.5.dp,
            color = Color(0xFFEEEEEE)
        )
    }
}

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Float = 1f,
    color: Color = Color(0xFFEEEEEE)
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness.dp)
            .background(color)
    )
}

private fun formatPrice(price: Float): String {
    return DecimalFormat("#,###").format(price).replace(",", " ")
}