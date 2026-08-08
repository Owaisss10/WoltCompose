package com.awaisakram.woltcompose.presentation.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awaisakram.woltcompose.domain.model.City
import com.awaisakram.woltcompose.presentation.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesScreen(
    onCityClick: (City) -> Unit,
    viewModel: CitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Choose a city",
                    )
                },
            )
        },
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            SearchTextField(
                value = uiState.query,
                label = "Search city",
                onValueChange = viewModel::onQueryChanged,
            )


            if (uiState.isLoading) {
                CircularProgressIndicator()
            }


            Text(
                text = "Showing ${uiState.cities.size} of ${uiState.totalCityCount} cities",
                style = MaterialTheme.typography.bodyLarge,
            )


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                items(
                    items = uiState.cities,
                    key = { it.id },
                ) { city ->

                    Text(
                        text = city.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCityClick(city) }
                            .padding(vertical = 12.dp),
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
}