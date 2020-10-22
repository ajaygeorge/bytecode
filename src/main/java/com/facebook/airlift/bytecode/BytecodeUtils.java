/*
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
package com.facebook.airlift.bytecode;

import com.google.common.base.CharMatcher;

import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicLong;

import static com.facebook.airlift.bytecode.ParameterizedType.typeFromJavaClassName;

public final class BytecodeUtils
{
    private static final AtomicLong CLASS_ID = new AtomicLong();

    private BytecodeUtils() {}

    public static ParameterizedType uniqueClassName(String basePackage, String baseName)
    {
        String className = toJavaIdentifierString(baseName) + "_" + CLASS_ID.incrementAndGet();
        return typeFromJavaClassName(basePackage + ".$gen." + className);
    }

    public static String toJavaIdentifierString(String className)
    {
        // replace invalid characters with '_'
        return CharMatcher.forPredicate(Character::isJavaIdentifierPart).negate()
                .replaceFrom(className, '_');
    }

    public static String dumpBytecodeTree(ClassDefinition classDefinition)
    {
        StringWriter writer = new StringWriter();
        new DumpBytecodeVisitor(writer).visitClass(classDefinition);
        return writer.toString();
    }
}
