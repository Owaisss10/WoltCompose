package com.awaisakram.woltcompose.presentation.restaurants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.presentation.restaurants.components.RestaurantCard

@Composable
fun RestaurantsScreen(
    navController: NavController,
    city: City,
    viewModel: RestaurantsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(city) {
        viewModel.loadRestaurants(city)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 48.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Restaurants in ${city.name}",
            style = MaterialTheme.typography.headlineMedium,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.query,
            onValueChange = viewModel::onQueryChanged,
            label = {
                Text("Search restaurants")
            },
            singleLine = true,
        )

        when {

            uiState.isLoading -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = uiState.error ?: "Something went wrong",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

            uiState.restaurants.isEmpty() -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "No restaurants found",
                    )
                }
            }

            else -> {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {

                    items(
                        items = uiState.restaurants,
                        key = { restaurant ->
                            restaurant.id
                        },
                    ) { restaurant ->

                        RestaurantCard(
                            restaurant = restaurant,
                        )
                    }
                }
            }
        }
    }
}