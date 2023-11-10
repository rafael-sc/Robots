package com.orafaelsc.robots

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.orafaelsc.robots.ui.components.GameGrid
import com.orafaelsc.robots.ui.theme.RobotsTheme

class RobotsActivity : ComponentActivity() {

    private val viewModel by viewModels<RobotsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RobotsTheme {
                Surface(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onBackground,
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(
                            modifier = Modifier.padding(40.dp),
                            onClick = {
                                viewModel.toggleAutoMode()
                            },
                        ) {
                            MainText()
                        }
                        CreateGameGrid(viewModel = viewModel)
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()

        applicationContext?.let {
            verifyAppUpdate(it)
        }
    }

    private fun verifyAppUpdate(context: Context) {
//        val appUpdateManager = com.google.android.play.core.appupdate.AppUpdateManagerFactory.create(context)
//        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
//        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == com.google.android.play.core.install.model.UpdateAvailability.UPDATE_AVAILABLE
//                && appUpdateInfo.isUpdateTypeAllowed(com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE)
//            ) {
//                appUpdateManager.startUpdateFlowForResult(
//                    appUpdateInfo,
//                    com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE,
//                    this,
//                    1,
//                )`
//            }
//        }

        val appUpdateManager = AppUpdateManagerFactory.create(context)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

            if (appUpdateInfo != null)
                when (appUpdateInfo.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> {
                        Log.d("ROBOTS!", "UPDATE_AVAILABLE")
                    }

                    UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                        requestAppUpdate(appUpdateInfo, appUpdateManager)
                    }

                    UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                        Log.d("ROBOTS!", "DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS")
                    }

                    UpdateAvailability.UNKNOWN -> {
                        Log.d("ROBOTS!", "UNKNOWN")
                    }
                }
        }
    }

    private fun requestAppUpdate(
        appUpdateInfo: AppUpdateInfo,
        appUpdateManager: AppUpdateManager
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    private fun MainText() {
        val buttonText by viewModel.buttonText.collectAsState("")
        Text(text = buttonText)
    }

    @Composable
    private fun CreateGameGrid(viewModel: RobotsViewModel) {
        val gameData by viewModel.gameData.collectAsState(null)
        Log.d("ROBOTS!", "gameData: $gameData")
        GameGrid(gameData) {
            viewModel.newGame()
        }
    }
}
