package com.example.feature_characters.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.feature_characters.ui.characters.CharactersViewModel

@Composable
fun FilterDialog(
    viewModel: CharactersViewModel,
    onApply: () -> Unit,
    onDismiss: () -> Unit
) {
    val status by viewModel.selectedStatus.collectAsState()
    val gender by viewModel.selectedGender.collectAsState()
    val species by viewModel.selectedSpecies.collectAsState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Фильтры",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Divider()

                FilterDropdownFallback(
                    label = "Статус",
                    options = listOf("Alive", "Dead", "Unknown"),
                    selected = status,
                    onSelectedChange = { viewModel.onStatusChanged(it) }
                )

                FilterDropdownFallback(
                    label = "Пол",
                    options = listOf("Male", "Female", "Genderless", "Unknown"),
                    selected = gender,
                    onSelectedChange = { viewModel.onGenderChanged(it) }
                )

                FilterDropdownFallback(
                    label = "Вид",
                    options = listOf("Human", "Alien", "Robot"),
                    selected = species,
                    onSelectedChange = { viewModel.onSpeciesChanged(it) }
                )

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        viewModel.onStatusChanged(null)
                        viewModel.onGenderChanged(null)
                        viewModel.onSpeciesChanged(null)
                    }) {
                        Text("Сбросить")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = onApply,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text("Применить")
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterDropdownFallback(
    label: String,
    options: List<String>,
    selected: String?,
    onSelectedChange: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelectedChange(option)
                        expanded = false
                    }
                )
            }
            DropdownMenuItem(
                text = { Text("Очистить") },
                onClick = {
                    onSelectedChange(null)
                    expanded = false
                }
            )
        }
    }
}
