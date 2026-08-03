package com.awaisakram.woltcompose.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "WoltCompose",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
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

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = viewModel::search,
        ) {
            Text("Search")
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        uiState.location?.let {
            Text(
                text = "Latitude: ${it.latitude}",
                textAlign = TextAlign.Center,
            )

            Text(
                text = "Longitude: ${it.longitude}",
                textAlign = TextAlign.Center,
            )
        }

        uiState.error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
            )
        }
    }
}