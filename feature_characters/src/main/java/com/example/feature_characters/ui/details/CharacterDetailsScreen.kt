package com.example.feature_characters.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.core.model.Character
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun CharacterDetailsScreen(
    viewModel: CharacterDetailsViewModel,
    characterId: Int,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(characterId) {
        viewModel.loadCharacter(characterId)
    }

    when {
        uiState.isLoading -> Text("Loading...", modifier = Modifier.padding(16.dp))
        uiState.error != null -> Text("Error: ${uiState.error}",
            modifier = Modifier.padding(16.dp))
        uiState.character != null -> CharacterDetailsContent(uiState.character!!, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetailsContent(character: Character, navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(character.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = character.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = MaterialTheme.shapes.large,
                tonalElevation = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val statusColor = when (character.status) {
                        "Alive" -> MaterialTheme.colorScheme.primary
                        "Dead" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Status: ", style = MaterialTheme.typography.bodyMedium)
                        Text(text = character.status, color = statusColor)
                    }

                    Text(
                        text = "Species: ${character.species}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Type: ${
                            if (character.type.isNotEmpty()) character.type 
                            else "Unknown"
                        }",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Gender: ${character.gender}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Origin: ${character.origin.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Location: ${character.location.name}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Episodes: ${character.episode.size}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Created: ${formatDate(character.created)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun formatDate(isoString: String): String {
    return try {
        val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        isoFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = isoFormat.parse(isoString)
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        outputFormat.format(date!!)
    } catch (e: Exception) {
        "Unknown"
    }
}
