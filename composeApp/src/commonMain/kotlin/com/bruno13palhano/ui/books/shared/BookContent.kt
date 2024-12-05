package com.bruno13palhano.ui.books.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bruno13palhano.ui.components.CustomIntegerField
import com.bruno13palhano.ui.components.CustomTextField
import com.bruno13palhano.ui.components.InputDialog
import org.jetbrains.compose.resources.stringResource
import studiorum.composeapp.generated.resources.Res
import studiorum.composeapp.generated.resources.add_category
import studiorum.composeapp.generated.resources.author
import studiorum.composeapp.generated.resources.author_placeholder
import studiorum.composeapp.generated.resources.categories
import studiorum.composeapp.generated.resources.category
import studiorum.composeapp.generated.resources.category_placeholder
import studiorum.composeapp.generated.resources.pages
import studiorum.composeapp.generated.resources.pages_placeholder
import studiorum.composeapp.generated.resources.title
import studiorum.composeapp.generated.resources.title_placeholder
import studiorum.composeapp.generated.resources.was_read

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun BookContent(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    categories: List<String>,
    category: String,
    pages: String,
    wasRead: Boolean,
    categoryVisible: Boolean,
    invalidField: Boolean,
    updateTitleChange: (title: String) -> Unit,
    updateAuthorChange: (author: String) -> Unit,
    updateCategoryChange: (category: String) -> Unit,
    addCategory: (category: String) -> Unit,
    removeCategory: (category: String) -> Unit,
    updatePagesChange: (pages: String) -> Unit,
    updateWasReadChange: (wasRead: Boolean) -> Unit,
    updateCategoryVisibility: (visible: Boolean) -> Unit
) {
    Column(modifier = modifier) {
        CustomTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = title,
            onValueChange = updateTitleChange,
            label = stringResource(Res.string.title),
            placeholder = stringResource(Res.string.title_placeholder),
            isError = invalidField && title.isBlank()
        )

        CustomTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = author,
            onValueChange = updateAuthorChange,
            label = stringResource(Res.string.author),
            placeholder = stringResource(Res.string.author_placeholder),
            isError = invalidField && author.isBlank()
        )

        CustomIntegerField(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            value = pages,
            onValueChange = updatePagesChange,
            label = stringResource(Res.string.pages),
            placeholder = stringResource(Res.string.pages_placeholder),
            isError = invalidField && pages.isBlank()
        )

        ElevatedCard(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(120.dp),
                verticalItemSpacing = (-8).dp
            ) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        text = stringResource(resource = Res.string.categories),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                items(items = categories) { category ->
                    Chip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { removeCategory(category) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = category,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    if (categoryVisible) {
                        InputDialog(
                            value = category,
                            label = stringResource(resource = Res.string.category),
                            placeholder = stringResource(
                                resource = Res.string.category_placeholder
                            ),
                            onValueChange = updateCategoryChange,
                            onOk = {
                                addCategory(it)
                                updateCategoryVisibility(false)
                            },
                            onCancel = { updateCategoryVisibility(false) }
                        )
                    }

                    Button(
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentSize(),
                        onClick = { updateCategoryVisibility(true) }
                    ) {
                        Text(text = stringResource(resource = Res.string.add_category))
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Checkbox(
                checked = wasRead,
                onCheckedChange = updateWasReadChange
            )

            Text(text = stringResource(resource = Res.string.was_read))
        }
    }
}