package com.swancodes.notes.model

import java.util.UUID

data class NoteItem(
    var title: String,
    var description: String,
    var id: UUID = UUID.randomUUID()
)
