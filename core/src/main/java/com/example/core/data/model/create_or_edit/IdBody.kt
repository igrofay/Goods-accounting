package com.example.core.data.model.create_or_edit

import com.example.core.domain.model.create_or_edit.IdModel
import kotlinx.serialization.Serializable

@Serializable
internal data class IdBody(
    override val id: String
) : IdModel
