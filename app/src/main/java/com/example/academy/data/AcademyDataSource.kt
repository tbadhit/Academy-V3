package com.example.academy.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.academy.data.source.local.entity.CourseEntity
import com.example.academy.data.source.local.entity.CourseWithModule
import com.example.academy.data.source.local.entity.ModuleEntity
import com.example.academy.vo.Resource

interface AcademyDataSource {

    fun getAllCourse(): LiveData<Resource<PagedList<CourseEntity>>>

    fun getBookmarkedCourses(): LiveData<PagedList<CourseEntity>>

    fun getCourseWithModule(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    fun setCourseBookmark(course: CourseEntity, state: Boolean)

    fun setReadModule(module: ModuleEntity)
}