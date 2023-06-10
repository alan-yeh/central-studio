/*
 * MIT License
 *
 * Copyright (c) 2022-present Alan Yeh <alan@yeh.cn>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package central.security.support.repository;

import central.bean.OptionalEnum;
import central.lang.Arrayx;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 数据类型
 *
 * @author Alan Yeh
 * @since 2023/06/10
 */
@Getter
@AllArgsConstructor
public enum DataType implements OptionalEnum<String> {
    NONE("不存在", "none"),
    STRING("字符串", "string"),
    LIST("列表", "list"),
    SET("无序集合", "set"),
    ZSET("有序集合", "zset"),
    HASH("键值对", "map");

    private final String name;
    private final String value;

    @JsonCreator
    public static @Nullable DataType resolve(String value) {
        return Arrayx.asStream(DataType.values()).filter(it -> Objects.equals(it.getValue(), value)).findFirst().orElse(null);
    }
}
