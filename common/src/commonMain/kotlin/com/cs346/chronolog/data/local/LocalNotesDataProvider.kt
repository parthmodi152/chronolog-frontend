package com.cs346.chronolog.data.local

import com.cs346.chronolog.data.model.Note

object LocalNotesDataProvider {
    val allNotes = listOf(
        Note(
            id = 0L,
            author = LocalAccountsDataProvider.UserAccount,
            subject = "Chronolog",
            body = """
# CS346-Fall2023 Team_118

## Goal
Provide a journaling application that allows for conveniently tracking and organizing daily information through means of calendar integration, a notification system, and provided templates including but not limited to medication, daily gratitude, to-do lists, and food-tracking templates. The existing solutions do not adequately address the diverse needs of users, and our app aims to fill this gap by offering a more personalized and comprehensive journaling experience.

## Team Members
Dhruv Agarwal - d23agarw@uwaterloo.ca   
Parth Modi - pmodi@uwaterloo.ca.  
Sean Devine - sean.devine@uwaterloo.ca.  
Ahmed Mushtaha - amushtaha@uwaterloo.ca.  

## Screenshots/Videos
Optional, but often helpful to have a screenshot or demo-video for new users.

## Quick-Start Instructions
Instructions. Details on how to install and launch your application. 

## Project Documents
https://git.uwaterloo.ca/cs346-fall2023/cs346-fall2023/-/wikis/Proposal

## Software Releases
https://git.uwaterloo.ca/cs346-fall2023/cs346-fall2023/-/wikis/Software-Release

                """.replace("^", "$").trimIndent(),
            category = LocalCategoriesDataProvider.getCategoryById(0L),
            isStarred = true,
            createdAt = "10 mins ago"
        )
    )
}
