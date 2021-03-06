package com.example.cheggclone.screens

import android.widget.ProgressBar
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cheggclone.models.SampleDataSet
import com.example.cheggclone.ui.theme.DeepOrange
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun PracticeScreen() {
    val sampleData = SampleDataSet.myDeckSample[0].cardList // card들 모아놓은 리스트
    val pagerState = rememberPagerState() // pager state
    val (count, setCount) = remember { mutableStateOf(0f) } // 현재 카드 수


    // ???
    LaunchedEffect(key1 = true) {
        setCount(1f)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        setCount((pagerState.currentPage + 1).toFloat())
    }
    // ???



    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${pagerState.currentPage + 1}/${sampleData.size}",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "close screen"
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Shuffle,
                        contentDescription = "shuffle cards"
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
        )
    }
    ) {
        Column {
            // 프로그레스바
            Box(modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)) {
                ProgressBar(count = count, totCount = sampleData.size)
            }
            Box {
                HorizontalPager( // Pager
                    count = sampleData.size,
                    state = pagerState, // 선언한 pagerState 사용 (선언하지 않으면 내부에서 자동으로 사용)
                    contentPadding = PaddingValues(32.dp)
                    // 양쪽에 이전, 다음 카드를 보여줌
                ) { page ->
                    FlipCard(
                        back = {
                            CardBack(text = sampleData[page].back)
                        },
                        front = {
                            CardFront(text = sampleData[page].front)
                        }
                    )
                }
            }
        }
    }
}


/////////////progressbar////////////
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    color: Color = DeepOrange,
    animDuration: Int = 300,
    animDelay: Int = 0,
    count: Float,
    totCount: Int,
) {
    val curPercentage by animateFloatAsState(
        targetValue = count / totCount,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay,
            easing = LinearOutSlowInEasing
        )
    )
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
            .clip(CircleShape),
        progress = curPercentage,
        color = color,
        backgroundColor = Color.LightGray
    )
}


/////////////flip card////////////
enum class CardFace(val angle: Float) { // 카드 앞, 뒷면 상태
    Front(angle = 0f) { // Front -> angle = 0도
        override val next: CardFace
            get() = Back
    },
    Back(angle = 180f) { // Back -> angle = 180도
        override val next: CardFace
            get() = Front
    };

    abstract val next: CardFace
}

enum class RotationAxis {
    AxisX,
    AxisY,
}

@Composable
fun CardFront(text: String) { // 카드 앞면
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(width = 2.dp, color = Color.LightGray)
            .padding(16.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.h3)
    }
}

@Composable
fun CardBack(text: String) { // 카드 뒷면
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(width = 2.dp, color = Color.LightGray)
            .verticalScroll(state = scrollState)
            .padding(16.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.h4)
    }
}

@ExperimentalMaterialApi
@Composable
fun FlipCard(
    modifier: Modifier = Modifier,
    axis: RotationAxis = RotationAxis.AxisY,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    // 앞,뒷면 상태
    var cardFace by remember { mutableStateOf(CardFace.Front) }

    // cardFace의 angle을 animation
    val rotation = animateFloatAsState(
        targetValue = cardFace.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing,
        )
    )
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            onClick = {  cardFace = cardFace.next  }, //클릭 시 카드 뒤집기
            modifier = modifier
                .graphicsLayer {
                    // 변화하는 rotation 값을 rotationX 또는 rotationY로 사용
                    if (axis == RotationAxis.AxisX) {
                        rotationX = rotation.value
                    } else {
                        rotationY = rotation.value
                    }
                    cameraDistance = 12f * density
                },
        ) {
            if (rotation.value <= 90f) { // 90도 이하일 때 -> 앞면
                Box(
                    Modifier.fillMaxSize()
                ) {
                    front()
                }
            } else { // 90도보다 클 때
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            if (axis == RotationAxis.AxisX) {
                                rotationX = 180f
                            } else {
                                rotationY = 180f
                            }
                        },
                ) {
                    back() // 뒷면
                }
            }
        }
    }
}