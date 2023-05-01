package com.example.core.data.model.create

import com.example.core.domain.model.create.IdModel
import kotlinx.serialization.Serializable

@Serializable
internal data class IdBody(
    override val id: String
) : IdModel
