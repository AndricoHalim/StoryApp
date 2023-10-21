//package com.andricohalim.data
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import com.andricohalim.storyapp.response.StoryResponse
//import com.andricohalim.storyapp.retrofit.ApiService
//
//class StoryPagingSource (private val apiService: ApiService) : PagingSource<Int, StoryResponse>() {
//    override fun getRefreshKey(state: PagingState<Int, StoryResponse>): Int? {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryResponse> {
//        return try {
//            val position = params.key ?: INITIAL_PAGE_INDEX
//            val responseData = apiService.getStories(position, params.loadSize)
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
//            )
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}