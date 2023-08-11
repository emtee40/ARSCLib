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

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.DexFile;
import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.index.FieldId;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.writer.SmaliWriter;

import java.io.IOException;
import java.util.List;

public class FieldDef extends Def {
    public FieldDef() {
        super(0);
    }
    public FieldId getFieldIndex(){
        DexFile dexFile = getParentInstance(DexFile.class);
        if(dexFile != null){
            return dexFile.getSectionList().get(SectionType.FIELD_ID, getDefIndexId());
        }
        return null;
    }
    @Override
    public void onReadBytes(BlockReader reader) throws IOException {
        super.onReadBytes(reader);
    }
    @Override
    public void append(SmaliWriter writer) throws IOException {
        writer.newLine();
        writer.append(".field ");
        AccessFlag[] accessFlags = AccessFlag.getForField(getAccessFlagsValue());
        for(AccessFlag af:accessFlags){
            writer.append(af.toString());
            writer.append(' ');
        }
        FieldId fieldId = getFieldIndex();
        writer.append(fieldId.getNameString().getString());
        writer.append(':');
        fieldId.getFieldType().append(writer);
        List<AnnotationSet> annotations = fieldId.getAnnotations();
        if (annotations.size()>0){
            writer.indentPlus();
            writer.newLine();
            for(AnnotationSet itemList:annotations){
                itemList.append(writer);
            }
            writer.indentMinus();
            writer.newLine();
            writer.append(".end field");
        }
    }
    @Override
    public String toString() {
        FieldId fieldId = getFieldIndex();
        if(fieldId != null){
            return ".field " + AccessFlag.formatForField(getAccessFlagsValue())
                    + " " + fieldId.toString();
        }
        return ".field " + AccessFlag.formatForField(getAccessFlagsValue())
                + " " + getIdValue();
    }
}
