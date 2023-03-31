package com.yudhis.studyhive.composeables

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.yudhis.studyhive.R

@Composable
fun AppBar(
    onNavigationIconClicked: () -> Unit
){
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme. colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClicked) {
                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu), contentDescription = "Navigation Button")
            }
        }
    )
}