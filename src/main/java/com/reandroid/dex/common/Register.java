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
package com.reandroid.dex.common;

import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;

import java.io.IOException;

public class Register implements SmaliFormat {

    private final int number;
    private final boolean parameter;

    public Register(int number, boolean parameter){
        this.number = number;
        this.parameter = parameter;
    }

    public int getNumber() {
        return number;
    }
    public boolean isParameter() {
        return parameter;
    }
    public char getSymbol(){
        if(isParameter()){
            return 'p';
        }
        return 'v';
    }

    @Override
    public void append(SmaliWriter writer) throws IOException {
        writer.append(getSymbol());
        writer.append(getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Register)) {
            return false;
        }
        Register register = (Register) obj;
        return getNumber() == register.getNumber() && isParameter() == register.isParameter();
    }

    @Override
    public int hashCode() {
        int hash = 31;
        if(isParameter()){
            hash = hash + 1;
        }
        hash = hash * 31 + getNumber();
        return hash;
    }

    @Override
    public String toString() {
        return getSymbol() + Integer.toString(getNumber());
    }
}
