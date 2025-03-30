package org.kmp.playground.theme.toggle

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import themetoggle.composeapp.generated.resources.Res
import themetoggle.composeapp.generated.resources.cloud
import themetoggle.composeapp.generated.resources.clouds
import themetoggle.composeapp.generated.resources.compose_multiplatform


@Composable
fun ThemeToggle() {
    val boxWidth = 200.dp
    val boxHeight = 100.dp
    val circleSize = 80.dp
    val shadowSize = 70.dp

    var isToggled by remember { mutableStateOf(false) }

    val targetColor = if (isToggled) Color.Black else Color.White
    val targetCircleColor = if (isToggled) Color(0xFFD9D9D9) else Color.Yellow
    val windowColor = if (isToggled) Color(0xFF5b5b5b) else Color(0xff9fc5e8)

    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "color animation"
    )

    val animatedCircleColor by animateColorAsState(
        targetValue = targetCircleColor,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "color animation"
    )

    val animatedWindowColor by animateColorAsState(
        targetValue = windowColor,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "color animation"
    )

    val circleOffset = if (isToggled) (boxWidth - boxHeight) + 13.dp else 5.dp
    val animatedCircleOffset by animateDpAsState(
        targetValue = circleOffset,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "offset animation"
    )

    val shadowOffsetY = if (isToggled) (circleSize - shadowSize) * 3 else (-shadowSize) + 40.dp
    val shadowOffsetX = if (isToggled) (circleSize - shadowSize) * 3 else 40.dp
    val animatedShadowOffsetY by animateDpAsState(
        targetValue = shadowOffsetY,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "shadow offset animation"
    )
    val animatedShadowOffsetX by animateDpAsState(
        targetValue = shadowOffsetX,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "shadow offset animation"
    )

    val moonCirclesData = remember { generateMoonCircleData(10) }


    val maxOffsetMoonCircle = 60.dp
    val centerMoonCircle = 0.dp

    val animatedMoonCircleOffset by animateDpAsState(
        targetValue = if (isToggled) centerMoonCircle else maxOffsetMoonCircle,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "Moon circle offset"
    )

    val animatedMoonCircleAlpha by animateFloatAsState(
        targetValue = if (isToggled) 0.6f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "Moon circle alpha"
    )

    val skyCirclesData = remember { generateSkyCircleData(20) }
    val animatedSkyCircleOffset by animateDpAsState(
        targetValue = if (isToggled) centerMoonCircle else boxHeight,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "Moon circle offset"
    )

    val cloudIcon: Painter = painterResource(resource = Res.drawable.clouds)
    val cloudOffsetOutside = 180.dp
    val cloudOffsetInside = boxWidth

    val cloudOffset1X by animateDpAsState(
        targetValue = if (isToggled) (cloudOffsetOutside - 10.dp) else cloudOffsetInside / 2,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "cloud 1 offset X"
    )
    val cloudOffset2X by animateDpAsState(
        targetValue = if (isToggled) (cloudOffsetOutside - 30.dp) else cloudOffsetInside / 3,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
        label = "cloud 2 offset X"
    )
    Box(
        modifier = Modifier.fillMaxSize().background(animatedWindowColor)
    ) {
        Box(
            modifier = Modifier
                .size(boxWidth, boxHeight)
                .clip(RoundedCornerShape(65.dp))
                .background(animatedColor)
                .clickable(onClick = {
                    isToggled = !isToggled
                }).align(Alignment.Center)
        ) {
            Image(
                painter = cloudIcon,
                contentDescription = "Cloud 1",
                modifier = Modifier
                    .offset(x = cloudOffset1X)
                    .alpha(0.5f)
                    .size(55.dp)
            )
            Image(
                painter = cloudIcon,
                contentDescription = "Cloud 2",
                modifier = Modifier
                    .offset(x = cloudOffset2X)
                    .alpha(0.5f)
                    .size(40.dp)
            )

            skyCirclesData.forEach { moonCircle ->
                Box {
                    MoonCircle(
                        isSkyCircle = true,
                        circleData = moonCircle,
                        alpha = animatedMoonCircleAlpha,
                        animatedMoonCircleOffset = animatedSkyCircleOffset
                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset(x = animatedCircleOffset)
                    .size(circleSize)
                    .clip(CircleShape)
                    .background(animatedCircleColor)
                    .align(Alignment.CenterStart)
            ) {
                moonCirclesData.forEach { moonCircle ->
                    Box {
                        MoonCircle(
                            circleData = moonCircle,
                            alpha = animatedMoonCircleAlpha,
                            animatedMoonCircleOffset = animatedMoonCircleOffset
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .offset(y = animatedShadowOffsetY, x = animatedShadowOffsetX)
                        .size(shadowSize, shadowSize)
                        .clip(CircleShape)
                        .alpha(0.4f)
                        .background(Color(0xffababab))
                )
            }
        }
    }
}


data class MoonCircleData(
    val size: Dp,
    val offsetX: Dp,
    val offsetY: Dp
)

fun generateMoonCircleData(count: Int): List<MoonCircleData> {
    val circleDataList = mutableListOf<MoonCircleData>()
    val maxOffset = 60
    val minSize = 2
    val maxSize = 10
    val minOffset = 0

    for (i in 0 until count) {
        var newOffsetX: Int
        do {
            newOffsetX = (minOffset..maxOffset).random()
        } while (circleDataList.any { it.offsetX.value.toInt() == newOffsetX })

        val newOffsetY = (minOffset..maxOffset).random()
        val newSize = (minSize..maxSize).random()
        circleDataList.add(MoonCircleData(newSize.dp, newOffsetX.dp, newOffsetY.dp))
    }
    return circleDataList
}


fun generateSkyCircleData(count: Int): List<MoonCircleData> {
    val circleDataList = mutableListOf<MoonCircleData>()
    val maxOffset = 200
    val minSize = 2
    val maxSize = 10
    val minOffset = 0

    for (i in 0 until count) {
        var newOffsetX: Int
        do {
            newOffsetX = (minOffset..maxOffset).random()
        } while (circleDataList.any { it.offsetX.value.toInt() == newOffsetX })

        val newOffsetY = (minOffset..maxOffset).random()
        val newSize = (minSize..maxSize).random()
        circleDataList.add(MoonCircleData(newSize.dp, newOffsetX.dp, newOffsetY.dp))
    }
    return circleDataList
}

@Composable
fun MoonCircle(
    circleData: MoonCircleData,
    alpha: Float,
    animatedMoonCircleOffset: Dp,
    isSkyCircle: Boolean = false
) {

    val xCircle = circleData.offsetX - animatedMoonCircleOffset
    val yCircle = circleData.offsetY - animatedMoonCircleOffset

    val borderColor = if (isSkyCircle) Color.White else Color.LightGray

    Box(
        modifier = Modifier
            .offset(x = xCircle, y = yCircle)
            .size(circleData.size)
            .border(border = BorderStroke(width = 2.dp, color = borderColor), shape = CircleShape)
            .clip(CircleShape)
            //.alpha(alpha)
            .background(if (isSkyCircle) Color.White else Color.Gray)
    )
}
