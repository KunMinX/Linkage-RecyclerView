package com.kunminx.linkage.bean;

/*
 * Copyright (c) 2018-present. KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Here is another idea of custom items:
 * <p>
 * When the background data does not match the BaseGroupedItem structure of this library,
 * the entity class of the background data can be used as the field of this library item,
 * <p>
 * And when getting the background data, traverse the background data,
 * and instantiate the items of this library, so as to get the available items
 * <p>
 * For details, see the demo of CustomJsonSampleFragment
 * <p>
 * Create by KunMinX at 22/5/23
 */
public class CustomGroupedItem<T> extends BaseGroupedItem<CustomGroupedItem.ItemInfo<T>> {

  public CustomGroupedItem(boolean isHeader, String header) {
    super(isHeader, header);
  }

  public CustomGroupedItem(ItemInfo<T> item) {
    super(item);
  }

  public static class ItemInfo<T> extends BaseGroupedItem.ItemInfo {
    private T t;

    public ItemInfo(String title, String group, T t) {
      super(title, group);
      this.t = t;
    }

    public T getT() {
      return t;
    }

    public void setT(T t) {
      this.t = t;
    }
  }
}
