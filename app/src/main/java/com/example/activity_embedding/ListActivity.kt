/*
 * Copyright 2023 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.activity_embedding

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * The list portion of a list-detail layout.
 */
class ListActivity : AppCompatActivity() {

    private var recylerViewState: Parcelable? = null
    private var rememberPosition: Int? = 0
    private lateinit var listRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        listRecyclerView = findViewById(R.id.listRecyclerView)
        val arraySize = 50
        listRecyclerView.adapter = ItemAdapter(
            Array(arraySize) {
                    i -> if (i == arraySize - 1) "Summary"
                         else "Item ${(i + 1)}"
            }
        )
    }

    override fun onPause() {
        super.onPause()
        try {
            val layoutManager = listRecyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val child = listRecyclerView.getChildAt(firstVisibleItemPosition)
            rememberPosition = listRecyclerView.layoutManager?.getPosition(child)
        } catch (e: Exception) {
            e.printStackTrace()
            rememberPosition = 30
        }
    }

    override fun onResume() {
        super.onResume()
        rememberPosition?.let { listRecyclerView.scrollToPosition(it) }
    }
}
