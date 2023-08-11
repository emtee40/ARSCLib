/*
 *  Copyright (C) 2022 github.com/REAndroid
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.reandroid.dex.item;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.dex.base.*;

public class AnnotationsDirectory extends BaseItem {

    private final IntegerItem classOffset;
    private final IntegerItem fieldCount;
    private final IntegerItem methodCount;
    private final IntegerItem parameterCount;

    private final IndexAndOffsetArray fieldsOffset;
    private final IndexAndOffsetArray methodsOffset;
    private final IndexAndOffsetArray parametersOffset;

    public AnnotationsDirectory() {
        super(7);
        this.classOffset = new IntegerItem();
        this.fieldCount = new IntegerItem();
        this.methodCount = new IntegerItem();
        this.parameterCount = new IntegerItem();

        this.fieldsOffset = new IndexAndOffsetArray(fieldCount);
        this.methodsOffset = new IndexAndOffsetArray(methodCount);
        this.parametersOffset = new IndexAndOffsetArray(parameterCount);

        addChild(0, classOffset);
        addChild(1, fieldCount);
        addChild(2, methodCount);
        addChild(3, parameterCount);

        addChild(4, fieldsOffset);
        addChild(5, methodsOffset);
        addChild(6, parametersOffset);
    }

    @Override
    public String toString() {
        return "classOffset=" + classOffset +
                ", fieldCount=" + fieldCount +
                ", methodCount=" + methodCount +
                ", parameterCount=" + parameterCount +
                ", fieldsOffset=" + fieldsOffset +
                ", methodsOffset=" + methodsOffset +
                ", parametersOffset=" + parametersOffset;
    }
}
