package com.cs346.chronolog.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cs346.chronolog.R
import com.cs346.chronolog.ui.utils.ChronologNavigationContentPosition

@Composable
fun ChronologBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth().height(77.dp),
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { chronologDestination ->
            NavigationBarItem(
                selected = selectedDestination == chronologDestination.route,
                onClick = { navigateToTopLevelDestination(chronologDestination) },
                icon = {
                    Icon(
                        imageVector = chronologDestination.selectedIcon,
                        contentDescription = chronologDestination.iconText
                    )
                },
                label = { Text(text = chronologDestination.iconText) },
                alwaysShowLabel = false
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermanentNavigationDrawerContent(
    selectedDestination: String,
    addNote: () -> Unit,
    navigationContentPosition: ChronologNavigationContentPosition,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit,
) {
    PermanentDrawerSheet(modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)) {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    MyExtendedFloatingActionButton(
                        modifier = Modifier,
                        addNote = addNote
                    )
                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { chronologDestination ->
                        ChronologNavigationDrawerItem(
                            selectedDestination = selectedDestination,
                            chronologDestination = chronologDestination,
                            navigateToTopLevelDestination = navigateToTopLevelDestination
                        )
                    }
                }
            },
            measurePolicy = getMeasurePolicy(navigationContentPosition)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: ChronologNavigationContentPosition,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
    addNote: () -> Unit,
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    }
                    MyExtendedFloatingActionButton(
                        modifier = Modifier,
                        addNote = addNote
                    )
                }

                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { chronologDestination ->
                        ChronologNavigationDrawerItem(
                            selectedDestination = selectedDestination,
                            chronologDestination = chronologDestination,
                            navigateToTopLevelDestination = navigateToTopLevelDestination
                        )
                    }
                }
            },
            measurePolicy = getMeasurePolicy(navigationContentPosition)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChronologNavigationDrawerItem(
    selectedDestination: String,
    chronologDestination: ChronologTopLevelDestination,
    navigateToTopLevelDestination: (ChronologTopLevelDestination) -> Unit,
) {
    NavigationDrawerItem(
        selected = selectedDestination == chronologDestination.route,
        label = {
            Text(
                text = chronologDestination.iconText,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        icon = {
            Icon(
                imageVector = chronologDestination.selectedIcon,
                contentDescription = chronologDestination.iconText
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        ),
        onClick = { navigateToTopLevelDestination(chronologDestination) }
    )
}

@Composable
fun MyExtendedFloatingActionButton(
    modifier: Modifier = Modifier,
    addNote: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = addNote,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 40.dp),
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(id = R.string.edit),
            modifier = Modifier.size(18.dp)
        )
        Text(
            text = stringResource(id = R.string.new_notes),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}





