package com.example.onestiapp.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.onestiapp.R
import com.example.onestiapp.Screens
import com.example.onestiapp.drawerScreens
import com.example.onestiapp.ui.theme.DrawerContentIconColor
import com.example.onestiapp.ui.theme.DrawerHighlightRowColor
import com.example.onestiapp.ui.theme.OneStiAppTheme
import com.example.onestiapp.ui.theme.Roboto
import kotlin.system.exitProcess

@Composable
fun OneStiNavDrawer(
    modifier: Modifier = Modifier,
    rowItems: List<Screens.DrawerScreens> = drawerScreens,
    activeHighlightColor: Color = DrawerHighlightRowColor,
    onDestinationClicked: (route: String) -> Unit,
) {
    var selectedItemIndex by remember {
        mutableStateOf(0)
    }
    Column(modifier.fillMaxSize()) {
        OneStiDrawerProfile()
        Column(
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Returns the Home Drawer Row
            NavDrawerRowItem(
                item = rowItems.first(),
                isSelected = rowItems.indexOf(rowItems.first()) == selectedItemIndex,
                activeHighlightColor = activeHighlightColor
            ) {
                onDestinationClicked(rowItems.first().route)
                selectedItemIndex = rowItems.indexOf(rowItems.first())
            }
            Divider()
            // Information Section
            Text(
                text = "Information",
                style = MaterialTheme.typography.subtitle2.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.padding(12.dp)
            )
            // Iterate Information Drawer Rows
            for (index in 1 until 5) {
                NavDrawerRowItem(
                    item = rowItems[index],
                    isSelected = index == selectedItemIndex,
                    activeHighlightColor = activeHighlightColor
                ) {
                    onDestinationClicked(rowItems[index].route)
                    selectedItemIndex = index
                }
            }
            Divider()
            // Iterate Others Drawer Rows
            Text(
                text = "Others",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(12.dp)
            )
            for (index in 5 until rowItems.size - 1) {
                NavDrawerRowItem(
                    item = rowItems[index],
                    isSelected = index == selectedItemIndex,
                    activeHighlightColor = activeHighlightColor
                ) {
                    selectedItemIndex = index
                }
            }
            // Logout
            var openDialog by remember { mutableStateOf(false) }
            val onDismiss = { openDialog = false }
            NavDrawerRowItem(
                item = rowItems.last(),
                isSelected = rowItems.indexOf(rowItems.last()) == selectedItemIndex,
                activeHighlightColor = activeHighlightColor
            ) {
                openDialog = true
            }
            if (openDialog) {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = {
                        Text(
                            text = "Confirm",
                            style = MaterialTheme.typography.h6.copy(fontFamily = Roboto)
                        )
                    },
                    text = {
                        Text(
                            text = "Logout now?",
                            style = MaterialTheme.typography.body2.copy(color = Color.Black)
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = { exitProcess(-1) }) {
                            Text(
                                text = "YES",
                                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { onDismiss() }) {
                            Text(
                                text = "NO",
                                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NavDrawerRowItem(
    modifier: Modifier = Modifier,
    item: Screens.DrawerScreens,
    isSelected: Boolean = false,
    activeHighlightColor: Color = DrawerHighlightRowColor,
    onItemClicked: () -> Unit,
) {
    Row(
        modifier
            .fillMaxWidth()
            .size(48.dp)
            .background(if (isSelected) activeHighlightColor else Color.Transparent)
            .clickable { onItemClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier.size(12.dp))
        Icon(
            imageVector = item.icon,
            contentDescription = item.icon.name,
            tint = DrawerContentIconColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = modifier.size(16.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
fun OneStiDrawerProfile() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.size(18.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_pic),
            contentDescription = "Profile Pic",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(CircleShape)
                .size(76.dp)
        )
        // Hard-coded yet
        Text(
            text = "MY PROFILE",
            style = MaterialTheme.typography.caption.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
        )
        Text(
            text = "EDZON JAEVE BUBAN BAUSA",
            style = MaterialTheme.typography.subtitle2,
        )
        Text(
            text = "02000168406",
            style = MaterialTheme.typography.body2.copy(color = DrawerContentIconColor),
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "BSIT • STI COLLEGE SAN JOSE DEL MONTE",
            style = MaterialTheme.typography.body2.copy(color = DrawerContentIconColor),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        OneStiDivider()
    }
}

@Preview
@Composable
fun DrawerProfilePreview() {
    OneStiAppTheme {
        Surface {
            OneStiDrawerProfile()
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    OneStiAppTheme {
        Surface {
            OneStiNavDrawer { }
        }
    }
}