package hardcoder.dev.data.remote.entities

import kotlinx.serialization.Serializable

@Serializable
data class AllCategoriesResponse(val categories: List<CategoryRemote>)