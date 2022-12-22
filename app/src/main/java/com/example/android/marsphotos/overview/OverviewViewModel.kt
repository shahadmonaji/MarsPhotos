/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.marsphotos.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.android.marsphotos.network.MarsApi
import com.example.android.marsphotos.network.MarsPhoto
import kotlinx.coroutines.launch
// enum class its const in programing language
//يعرض دايم قيم ثابته
enum class MarsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<MarsApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<MarsApiStatus> = _status

    private val _photos = MutableLiveData<List<MarsPhoto>>()

    val photos: LiveData<List<MarsPhoto>> = _photos


    init {
        getMarsPhotos()
    }

    private fun getMarsPhotos() {
        //coroutineScope
//اي عمليه تحصل في الbackground
// concurrency tow or more of operations in the same time
// التطبيق مارح يكون فيه crush or save any falue with null  ؟ ، !
//ثابته على view model scope

        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            try {
                _photos.value = MarsApi.retrofitService.getPhotos()
                _status.value = MarsApiStatus.DONE
            }
            catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _photos.value = listOf()
            }
        }
    }
}