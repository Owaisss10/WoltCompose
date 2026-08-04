package com.awaisakram.woltcompose.presentation.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun CitiesScreen(
    viewModel: CitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filteredCities = uiState.cities.filter {
        it.name.contains(uiState.query, ignoreCase = true)
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
            text = "WoltCompose",
            style = MaterialTheme.typography.headlineMedium,
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.query,
            onValueChange = viewModel::onQueryChanged,
            label = {
                Text("Search city")
            },
            singleLine = true,
        )

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        Text(
            text = "Showing ${filteredCities.size} of ${uiState.cities.size} cities",
            style = MaterialTheme.typography.bodyLarge,
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = filteredCities,
                key = { it.id },
            ) { city ->

                Text(
                    text = city.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Next step:
                            // Fetch restaurants for this city
                            viewModel.onCityClicked(city)
                        }
                        .padding(vertical = 12.dp)
                )
            }
        }

        uiState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}