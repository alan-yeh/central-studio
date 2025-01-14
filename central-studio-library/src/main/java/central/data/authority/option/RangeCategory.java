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

package central.data.authority.option;

import central.bean.OptionalEnum;
import central.lang.Arrayx;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Range Category
 * <p>
 * 授权范围分类
 *
 * @author Alan Yeh
 * @since 2024/12/15
 */
@Getter
@AllArgsConstructor
public enum RangeCategory implements OptionalEnum<String> {
    /**
     * 授权组织架构
     * <p>
     * 数据与组织架构相关时使用此授权类型
     */
    ORGANIZATION("组织架构授权", "organization"),
    /**
     * 直接授权指定的数据
     * <p>
     * 此时 dataId 为待授权的数据的主键
     */
    DATA("数据授权", "data");


    private final String name;
    private final String value;

    @Override
    public String toString() {
        return this.value;
    }

    public static @Nullable RangeCategory resolve(String value) {
        return Arrayx.asStream(RangeCategory.values()).filter(it -> Objects.equals(it.getValue(), value)).findFirst().orElse(null);
    }
}
