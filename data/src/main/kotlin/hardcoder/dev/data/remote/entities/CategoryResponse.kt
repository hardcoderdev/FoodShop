package hardcoder.dev.data.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(val categories: List<CategoryRemote>)