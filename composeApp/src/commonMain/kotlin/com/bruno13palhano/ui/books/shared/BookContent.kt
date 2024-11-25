package com.bruno13palhano.ui.books.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bruno13palhano.ui.components.CustomIntegerField
import com.bruno13palhano.ui.components.CustomTextField
import org.jetbrains.compose.resources.stringResource
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.author
import studiorum.composeapp.generated.resources.author_placeholder
import studiorum.composeapp.generated.resources.pages
import studiorum.composeapp.generated.resources.pages_placeholder
import studiorum.composeapp.generated.resources.title
import studiorum.composeapp.generated.resources.title_placeholder

@Composable
internal fun BookContent(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    pages: String,
    updateTitleChange: (String) -> Unit,
    updateAuthorChange: (String) -> Unit,
    updatePagesChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        CustomTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = title,
            onValueChange = updateTitleChange,
            label = stringResource(Res.string.title),
            placeholder = stringResource(Res.string.title_placeholder)
        )

        CustomTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = author,
            onValueChange = updateAuthorChange,
            label = stringResource(Res.string.author),
            placeholder = stringResource(Res.string.author_placeholder)
        )

        CustomIntegerField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = pages,
            onValueChange = updatePagesChange,
            label = stringResource(Res.string.pages),
            placeholder = stringResource(Res.string.pages_placeholder)
        )
    }
}