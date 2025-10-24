package com.example.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature_characters.ui.characters.CharactersScreen
import com.example.feature_characters.ui.characters.CharactersViewModel
import com.example.feature_characters.ui.details.CharacterDetailsScreen
import com.example.feature_characters.ui.details.CharacterDetailsViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AppNavHost()
            }
        }
    }
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "characters"
    ) {
        composable("characters") {
            val charactersViewModel: CharactersViewModel = koinViewModel()
            CharactersScreen(
                viewModel = charactersViewModel,
                navController = navController
            )
        }

        composable(
            "details/{characterId}",
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            val detailsViewModel: CharacterDetailsViewModel = koinViewModel()
            CharacterDetailsScreen(
                viewModel = detailsViewModel,
                characterId = characterId,
                navController = navController
            )
        }
    }
}
