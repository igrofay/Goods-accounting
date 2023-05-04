package com.example.goodsaccounting.create.view.material

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.core.domain.model.product.Measurements
import com.example.goodsaccounting.R
import com.example.goodsaccounting.common.view.button.CustomTextButton
import com.example.goodsaccounting.common.view.click.alphaClick
import com.example.goodsaccounting.common.view.edit_text.EditText
import com.example.goodsaccounting.common.view.image.CustomImage
import com.example.goodsaccounting.common.view.theme.padding
import com.example.goodsaccounting.common.view.theme.textColor
import com.example.goodsaccounting.common.view.utils.getDesignation
import com.example.goodsaccounting.common.view_model.EventBase
import com.example.goodsaccounting.create.model.material.CreateMaterialEvent
import com.example.goodsaccounting.create.model.material.CreateMaterialState

@Composable
internal fun MainSettingView(
    state: CreateMaterialState,
    eventBase: EventBase<CreateMaterialEvent>
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { uriIsNotNull ->
            eventBase.onEvent(
                CreateMaterialEvent.SelectImage(uriIsNotNull.toString())
            )
        }
    }
    var isDisplayDialogMeasurementTypeInformation by remember {
        mutableStateOf(false)
    }
    CustomImage(
        image = state.imageUrl,
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clickable { launcher.launch("image/*") }
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.padding.extraLarge1)
            .padding(horizontal = MaterialTheme.padding.medium2),
        verticalArrangement = Arrangement
            .spacedBy(MaterialTheme.padding.medium2),
    ) {
        EditText(
            value = state.name,
            onChange = {
                eventBase.onEvent(CreateMaterialEvent.InputName(it))
            },
            hint = stringResource(R.string.material_name),
            isError = state.isErrorName
        )
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.padding.medium1),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement
                        .spacedBy(MaterialTheme.padding.small2),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.measurement_type),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_question_mark),
                        contentDescription = null,
                        modifier = Modifier
                            .alphaClick { isDisplayDialogMeasurementTypeInformation = true }
                            .size(20.dp)
                            .border(
                                1.dp,
                                MaterialTheme.colors.onSurface.copy(0.7f),
                                CircleShape,
                            )
                            .padding(MaterialTheme.padding.small1)
                    )
                }
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                ) {
                    Row(
                        modifier = Modifier.alphaClick {
                            expanded = !expanded
                        },
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small1)
                    ) {
                        Text(
                            text = state.measurement.getDesignation(),
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.textColor,
                        )
                        val animRotate by animateFloatAsState(
                            targetValue = if (expanded) 180f else 0f
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(animRotate),
                            tint = MaterialTheme.colors.onBackground,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(IntrinsicSize.Min)
                    ) {
                        Measurements.values().forEach { measurements ->
                            DropdownMenuItem(onClick = {
                                eventBase.onEvent(
                                    CreateMaterialEvent.SelectMeasurements(
                                        measurements
                                    )
                                )
                                expanded = false
                            }) {
                                Text(
                                    text = measurements.getDesignation(),
                                    style = MaterialTheme.typography.body2,
//                                    color = MaterialTheme.colors.textColor
                                )
                            }
                        }
                    }
                }
            }
        }

    }
    if (isDisplayDialogMeasurementTypeInformation) {
        Dialog(
            onDismissRequest = { isDisplayDialogMeasurementTypeInformation = false }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.background.copy(1f),
                        MaterialTheme.shapes.medium
                    )
                    .padding(MaterialTheme.padding.medium2),
                verticalArrangement = Arrangement
                    .spacedBy(MaterialTheme.padding.small2)
            ) {
                Text(
                    text = stringResource(R.string.type_of_measurement_needed),
                    style = MaterialTheme.typography.subtitle1,
                )
                Divider()
                Text(
                    text = stringResource(id = R.string.other_implies_that_required),
                    style = MaterialTheme.typography.body2,
                )
                CustomTextButton(
                    label = stringResource(id = R.string.ok),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    isDisplayDialogMeasurementTypeInformation = false
                }
            }

        }
    }
}