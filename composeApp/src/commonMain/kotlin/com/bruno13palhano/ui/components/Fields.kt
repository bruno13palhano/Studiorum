package com.bruno13palhano.ui.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

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