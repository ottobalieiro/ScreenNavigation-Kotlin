package br.com.fiap.navegacao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.navegacao.screens.LoginScreen
import br.com.fiap.navegacao.screens.MenuScreen
import br.com.fiap.navegacao.screens.PedidosScreen
import br.com.fiap.navegacao.screens.PerfilScreen
import br.com.fiap.navegacao.ui.theme.NavegaçãoTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavegaçãoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.End,
                                tween(1000)
                            ) + fadeOut(animationSpec = tween(1000))
                        },
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Down,
                                tween(1000)
                                )
                        }

                    ) {
                        composable(route = "login") {
                            LoginScreen(navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(navController)
                        }
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument(name = "nome") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "idade") {
                                    type = NavType.IntType

                                }
                            )
                        ) {
                            val nome = it.arguments?.getString("nome")
                            val idade = it.arguments?.getInt("idade")
                            PerfilScreen(navController, nome!!, idade!!)
                        }
                        composable(
                            route = "pedidos?numero={numero}",
                            arguments = listOf(
                                navArgument(name = "numero") {
                                defaultValue = "sem valor"
                            })
                        ) {
                            PedidosScreen(navController, it.arguments?.getString("numero")!!)
                        }
                    }
                }
            }
        }
    }
}
