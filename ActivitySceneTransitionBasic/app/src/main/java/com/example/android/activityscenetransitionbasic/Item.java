/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.activityscenetransitionbasic;

/**
 * Represents an Item in our application. Each item has a name, id, full size image url and
 * thumbnail url.
 */
public class Item {

    public static Item[] ITEMS = new Item[] {
            new Item("Flying in the Light", "Romain Guy", R.drawable.image1),
            new Item("Caterpillar", "Romain Guy", R.drawable.image2),
            new Item("Look Me in the Eye", "Romain Guy", R.drawable.image3),
            new Item("Flamingo", "Romain Guy", R.drawable.image4),
            new Item("Rainbow", "Romain Guy", R.drawable.image5),
            new Item("Over there", "Romain Guy", R.drawable.image6),
            new Item("Jelly Fish 2", "Romain Guy", R.drawable.image7),
            new Item("Lone Pine Sunset", "Romain Guy", R.drawable.image8),
    };

    public static Item getItem(int id) {
        for (Item item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    private final String mName;
    private final String mAuthor;
    private final int mImageResId;

    Item(String name, String author, int imageResId) {
        mName = name;
        mAuthor = author;
        mImageResId = imageResId;
    }

    public int getId() {
        return mName.hashCode() + mImageResId;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getName() {
        return mName;
    }

    public int getImageResId() {
        return mImageResId;
    }
}