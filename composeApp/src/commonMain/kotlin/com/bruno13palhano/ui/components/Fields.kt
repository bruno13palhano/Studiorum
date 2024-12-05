package com.bruno13palhano.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.stringResource
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.cancel
import studiorum.composeapp.generated.resources.done

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    icon: @Composable (()-> Unit)? = null,
    label: String,
    placeholder: String,
    isError: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        leadingIcon = icon,
        label = {
            Text(
                text = label,
                fontStyle = FontStyle.Italic
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                fontStyle = FontStyle.Italic
            )
        },
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { defaultKeyboardAction(ImeAction.Done) })
    )
}

@Composable
fun CustomIntegerField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (()-> Unit)? = null,
    label: String,
    placeholder: String,
    isError: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
) {
    val patternInt = remember { Regex("^\\d*") }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            if (newValue.isEmpty() || newValue.matches(patternInt)) {
                onValueChange(newValue)
            }
        },
        isError = isError,
        leadingIcon = leadingIcon,
        label = {
            Text(
                text = label,
                fontStyle = FontStyle.Italic
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                fontStyle = FontStyle.Italic
            )
        },
        singleLine = singleLine,
        readOnly = readOnly,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(onDone = { defaultKeyboardAction(ImeAction.Done) })
    )
}

@Composable
fun InputDialog(
    value: String,
    label: String,
    placeholder: String,
    onValueChange: (value: String) -> Unit,
    onOk: (value: String) -> Unit,
    onCancel: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    Dialog(
        onDismissRequest = {
            onCancel()
            onValueChange("")
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    value = value,
                    onValueChange = onValueChange,
                    label = label,
                    placeholder = placeholder
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.padding(start = 4.dp, top = 8.dp, end = 4.dp),
                        onClick = {
                            onOk(value)
                            onValueChange("")
                        }
                    ) {
                        Text(text = stringResource(resource = Res.string.done))
                    }

                    Button(
                        modifier = Modifier.padding(start = 4.dp, top = 8.dp),
                        onClick = {
                            onCancel()
                            onValueChange("")
                        }
                    ) {
                        Text(text = stringResource(resource = Res.string.cancel))
                    }
                }
            }

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}