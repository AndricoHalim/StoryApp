package com.andricohalim.storyapp.utils

import com.andricohalim.storyapp.response.ListStoryItem

object DataDummy {
    fun generateDummyStoryItem(): List<ListStoryItem> {
        val storyList = ArrayList<ListStoryItem>()
        for (i in 0..10) {
            val story = ListStoryItem(
                "id $i",
                "https://story-api.dicoding.dev/images/stories/photos-1698059079604_3H1c_1HA.jpg",
                "test $i",
                "2023-10-23T11:04:39.611Z",
                "test $i",
                110.71106,
                -7.2361833,
            )
            storyList.add(story)
        }
        return storyList
    }
}