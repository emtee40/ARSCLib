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
package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.base.*;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.KeyItem;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;

import java.io.IOException;

public class DataItem extends DexContainerItem
        implements PositionedItem, OffsetSupplier, OffsetReceiver, KeyItem, UsageMarker{

    private IntegerReference mReference;

    private ClassId mUserClassId;
    private boolean mShared;
    private int mUsageType;

    private Key mLastKey;

    public DataItem(int childesCount) {
        super(childesCount);
    }


    public void copyFrom(DataItem item){
        if(item == null){
            return;
        }
        BlockReader reader = new BlockReader(item.getBytes());
        try {
            this.readBytes(reader);
        } catch (IOException ignored) {
        }
    }
    public boolean isSharedUsage() {
        return mShared;
    }
    public void addClassUsage(ClassId classId){
        if(classId == null || mShared || classId == mUserClassId){
            return;
        }
        if(mUserClassId != null){
            mShared = true;
        }else {
            mUserClassId = classId;
        }
    }
    public void removeSelf() {
        Block parent = getParent();
        if(!(parent instanceof DexItemArray<?>)){
            return;
        }
        BlockArray<DataItem> itemArray = (BlockArray<DataItem>) parent;
        setParent(null);
        itemArray.remove(this);
        setPosition(0);
    }
    @Override
    public Key getKey() {
        return null;
    }
    @SuppressWarnings("unchecked")
    <T1 extends Key> T1 checkKey(SectionType<?> sectionType, T1 newKey){
        Key lastKey = this.mLastKey;
        if(lastKey == null || !lastKey.equals(newKey)){
            this.mLastKey = newKey;
            keyChanged(sectionType, lastKey);
            lastKey = newKey;
        }
        return (T1) lastKey;
    }
    void keyChanged(SectionType<?> sectionType, Key oldKey){
        if(oldKey == null){
            return;
        }
        SectionList sectionList = getSectionList();
        if(sectionList != null){
            sectionList.keyChanged(sectionType, oldKey);
        }
    }
    @Override
    public void setPosition(int position) {
        IntegerReference reference = getOffsetReference();
        if(reference == null){
            reference = new NumberIntegerReference(position);
            setOffsetReference(reference);
        }else {
            reference.set(position);
        }
    }
    public int getOffset(){
        IntegerReference reference = getOffsetReference();
        if(reference != null){
            return reference.get();
        }
        return 0;
    }
    @Override
    public IntegerReference getOffsetReference() {
        return mReference;
    }
    @Override
    public void setOffsetReference(IntegerReference reference) {
        this.mReference = reference;
    }


    @Override
    public int getUsageType() {
        return mUsageType;
    }
    @Override
    public void addUsageType(int usage){
        this.mUsageType |= usage;
    }
    @Override
    public boolean containsUsage(int usage){
        if(usage == 0){
            return this.mUsageType == 0;
        }
        return (this.mUsageType & usage) == usage;
    }
    @Override
    public void clearUsageType(){
        this.mUsageType = USAGE_NONE;
    }
    public void removeLastAlign(){

    }
}