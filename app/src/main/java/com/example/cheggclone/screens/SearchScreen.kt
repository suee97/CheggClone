package com.example.cheggclone.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cheggclone.ui.theme.DeepOrange

@Preview
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Find flashcards",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
        ) {
            FindFlashCards(onClick = { })
            Spacer(modifier = Modifier.height(24.dp))
            Divider(
                Modifier
                    .fillMaxWidth(.15f)
                    .height(4.dp), color = DeepOrange
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Choose your subject",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Jump into studying with free flashcards that are right for you",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            repeat(7) {
                SubjectItem()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun FindFlashCards(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .border(1.dp, Color.LightGray, CircleShape)
            .clickable(onClick = onClick)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
        )
        Text(
            text = " Find flashcards",
            color = Color.Gray,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun SubjectItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(size = 8.dp),
                width = 2.dp,
                color = Color.LightGray
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Computer,
            contentDescription = "bookmark",
            tint = DeepOrange,
            modifier = Modifier.size(36.dp)
        )
        Text(
            text = "  Computer Science",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
    }
}