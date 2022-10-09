package fi.metropolia.project.souvenirapp.viewmodel

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

class NavigationViewModel(val navController: NavHostController) : ViewModel() {

    val lastDestinationRoute = MutableLiveData("")


    @Composable
    fun setLastDestination() {
        val currentStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentStackEntry?.destination ?: return

        if (currentDestination.route != lastDestinationRoute.value) {
            lastDestinationRoute.value = currentDestination.route!!
        }
    }
}