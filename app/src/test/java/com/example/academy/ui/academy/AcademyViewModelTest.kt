package com.example.academy.ui.academy

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.academy.data.source.local.entity.CourseEntity
import com.example.academy.data.AcademyRepository
import com.example.academy.utils.DataDummy
import com.example.academy.vo.Resource
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AcademyViewModelTest{

    private lateinit var viewModel: AcademyViewModel

    @get:Rule
    //Anda perlu menambahkan InstantTaskExecutorRule karena pengujiannya berupa proses asynchronous
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var academyRepository: AcademyRepository

    @Mock
    private lateinit var observer:  Observer<Resource<PagedList<CourseEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<CourseEntity>

    @Before
    fun setUp() {
        viewModel = AcademyViewModel(academyRepository)
    }

    @Test
    fun testGetCourses() {
        val dummyCourses = Resource.success(pagedList)
        `when`(dummyCourses.data?.size).thenReturn(5)
        val courses = MutableLiveData<Resource<PagedList<CourseEntity>>>()
        courses.value = dummyCourses

        `when`(academyRepository.getAllCourse()).thenReturn(courses)
        val courseEntities = viewModel.getCourses().value?.data
        verify(academyRepository).getAllCourse()
        assertNotNull(courseEntities)
        assertEquals(5, courseEntities?.size)

        viewModel.getCourses().observeForever(observer)
        verify(observer).onChanged(dummyCourses)
    }
}