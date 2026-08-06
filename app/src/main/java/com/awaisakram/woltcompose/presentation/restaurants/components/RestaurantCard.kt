package com.awaisakram.woltcompose.presentation.restaurants.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.awaisakram.woltcompose.domain.model.Restaurant

@Composable
fun RestaurantCard(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                AsyncImage(
                    model = restaurant.imageUrl,
                    contentDescription = restaurant.name,
                    modifier = Modifier
                        .size(90.dp)
                        .clip(
                            RoundedCornerShape(16.dp)
                        ),
                    contentScale = ContentScale.Crop,
                )


                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    ) {

                    Text(
                        text = restaurant.name,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                    )


                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )


                    if (restaurant.description.isNotBlank()) {
                        Text(
                            text = restaurant.description,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 3,
                        )
                    }
                }
            }


            Spacer(
                modifier = Modifier.height(16.dp)
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                Text(
                    text = "🚴 ${restaurant.deliveryTime}",
                    style = MaterialTheme.typography.titleMedium,
                )


                restaurant.priceRange?.let {
                    Text(
                        text = "€".repeat(it),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }


            if (restaurant.tags.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                Text(
                    text = restaurant.tags
                        .take(3)
                        .joinToString(" • "),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }


            restaurant.address
                ?.takeIf { it.isNotBlank() }
                ?.let {

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
        }
    }
}